import torch
import torch.nn as nn
from torch.nn import functional as F

class Head(nn.Module):
    
    def __init__(self,embd, head_size,block_size):
        super().__init__()
        self.key = nn.Linear(embd, head_size, bias=False)
        self.query = nn.Linear(embd, head_size, bias=False)
        self.value = nn.Linear(embd, head_size, bias=False)
        self.register_buffer('tril', torch.tril(torch.ones(block_size, block_size)))

        self.dropout = nn.Dropout(0.2)

    def forward(self, x):
        batch,time,channels = x.shape
        key = self.key(x)   
        query = self.query(x) 
        weights = query @ key.transpose(-2,-1) * channels**-0.5 
        weights = weights.masked_fill(self.tril[:time, :time] == 0, float('-inf')) # type: ignore 
        weights = F.softmax(weights, dim=-1) 
        weights = self.dropout(weights)
        
        values = self.value(x) 
        out = weights @ values 
        return out

class MultiHeadAttention(nn.Module):
    def __init__(self,embd ,num_heads, head_size,block_size):
        super().__init__()
        self.heads = nn.ModuleList([Head(embd,head_size,block_size) for _ in range(num_heads)])
        self.proj = nn.Linear(embd, embd)
        self.dropout = nn.Dropout(0.2)

    def forward(self, x):
        out = torch.cat([h(x) for h in self.heads], dim=-1)
        out = self.dropout(self.proj(out))
        return out

class FeedFoward(nn.Module):
    def __init__(self, embd,):
        super().__init__()
        self.net = nn.Sequential(
            nn.Linear(embd, 4 * embd),
            nn.ReLU(),
            nn.Linear(4 * embd, embd),
            nn.Dropout(0.2),
        )

    def forward(self, x):
        return self.net(x)

class Block(nn.Module):

    def __init__(self, embd, head,block_size):
       
        super().__init__()
        head_size = embd // head
        self.sa = MultiHeadAttention(embd,head, head_size,block_size)
        self.ffwd = FeedFoward(embd)
        self.ln1 = nn.LayerNorm(embd)
        self.ln2 = nn.LayerNorm(embd)

    def forward(self, x):
        x = x + self.sa(self.ln1(x))
        x = x + self.ffwd(self.ln2(x))
        return x

class TextGenTransformer(nn.Module):

    def __init__(self,vocab_size,block_size,embd,head,layer,device):
        super().__init__()
        self.token_embedding_table = nn.Embedding(vocab_size, embd)
        self.position_embedding_table = nn.Embedding(block_size, embd)
        self.blocks = nn.Sequential(*[Block(embd,head,block_size) for _ in range(layer)])
        self.ln_f = nn.LayerNorm(embd) 
        self.lm_head = nn.Linear(embd, vocab_size)
        self.device = device

    def forward(self, idx, targets=None):
        B, T = idx.shape
        tok_emb = self.token_embedding_table(idx)
        pos_emb = self.position_embedding_table(torch.arange(T, device=self.device))
        x = tok_emb + pos_emb
        x = self.blocks(x)
        x = self.ln_f(x) 
        logits = self.lm_head(x) 

        if targets is None:
            loss = None
        else:
            B, T, C = logits.shape
            logits = logits.view(B*T, C)
            targets = targets.view(B*T)
            loss = F.cross_entropy(logits, targets)

        return logits, loss

    def generate(self, tokens, max_new_tokens,block_size):
        print(tokens)
        tokens.to(self.device)
        for _ in range(max_new_tokens):
            tokens_condensed = tokens[:, -block_size:]
            model_out, loss = self(tokens_condensed)
            model_out = model_out[:, -1, :]
            probs = F.softmax(model_out, dim=-1)
            next_token = torch.multinomial(probs, num_samples=1)
            tokens = torch.cat((tokens, next_token), dim=1) # (B, T+1)
        return tokens



class TextGeneration():
    def __init__(self,block_size,device,embd,head,layer) -> None:
        
        self.block_size = block_size
        self.device = device
        self.embd = embd
        self.head = head
        self.layer = layer
        pass

    def train(self,batch_size,data_path,max_iterations,eval_intervals,lr,eval_iter):
        
        with open(data_path, 'r', encoding='utf-8') as f:
            text = f.read()

        chars = sorted(list(set(text)))
        vocab_size = len(chars)

        self.string_to_int = self.create_stoi(chars)
        self.int_to_string = self.create_itos(chars)

        data = torch.tensor(self.encode_string(text,self.string_to_int), dtype=torch.long)
        n = int(0.9*len(data))
        train_data = data[:n]
        val_data = data[n:]
        
        
        self.model = TextGenTransformer(vocab_size,self.block_size,self.embd,self.head,self.layer,self.device)
        m = self.model.to(self.device)
    
        optimizer = torch.optim.AdamW(self.model.parameters(), lr=lr)
        text = 'step,train loss,val loss\n'
        for iter in range(max_iterations):

            if iter % eval_intervals == 0 or iter == max_iterations - 1:
                losses = self.calculate_loss(model,eval_iter,batch_size,train_data,val_data)
                text += f"{iter},{losses['train']:.4f},{losses['val']:.4f}\n"
            xb, yb = self.get_batch(batch_size,train_data)

            logits, loss = self.model(xb, yb)
            optimizer.zero_grad(set_to_none=True)
            loss.backward()
            optimizer.step()
        
        with open('graph.txt', "w",encoding='utf-8') as file:
            file.write(text)
        model_path = "model.pth"
        torch.save(self.model.state_dict(), model_path)
        pass
    
    def get_batch(self,batch_size,data):
        ix = torch.randint(len(data) - self.block_size, (batch_size,))
        x = torch.stack([data[i:i+self.block_size] for i in ix])
        y = torch.stack([data[i+1:i+self.block_size+1] for i in ix])
        x, y = x.to(self.device), y.to(self.device)
        return x, y
    
    @torch.no_grad()
    def calculate_loss(self,model,eval_iterations,batch_size,train_data,val_data):
        out = {}
        self.model.eval()
        for split in ['train', 'val']:
            losses = torch.zeros(eval_iterations)
            for k in range(eval_iterations):
                if(split == 'train'):
                    X, Y = self.get_batch(batch_size,train_data)
                else:
                    X, Y = self.get_batch(batch_size,val_data)
                logits, loss = self.model(X, Y)
                losses[k] = loss.item()
            out[split] = losses.mean()
        self.model.train()
        return out
    
    def create_stoi(self,chars):
        string_to_int = {ch: i for i, ch in enumerate(chars)}
        return string_to_int

    def create_itos(self,chars):
        int_to_string = {i: ch for i, ch in enumerate(chars)}
        return int_to_string

    def encode_string(self,s, string_to_int):
        encoded = [string_to_int[c] for c in s]
        return encoded

    def decode_list(self,l, int_to_string):
        decoded = ''.join([int_to_string[i] for i in l])
        return decoded
    
    def loadModel(self,model_pth,data_path):
        with open(data_path, 'r', encoding='utf-8') as f:
            text = f.read()
        chars = sorted(list(set(text)))
        vocab_size = len(chars)

        self.string_to_int = self.create_stoi(chars)
        self.int_to_string = self.create_itos(chars)

        self.model = TextGenTransformer(vocab_size,self.block_size,self.embd,self.head,self.layer,self.device)
        self.model.load_state_dict(torch.load(model_pth))
        pass
    
    def generate(self,input):
        x = [self.encode_string(input,self.string_to_int)]
        print(x)
        x = torch.tensor(x,dtype=torch.long)
        x = self.decode_list(self.model.generate(x,500,self.block_size)[0].tolist(),self.int_to_string)
        return x
    
if __name__ == "__main__":
    model = TextGeneration(256,'cpu',192,3,3)
    #model.train(64,'data.txt',10000,100,0.001,200)
    model.loadModel('model3.pth','data.txt')
    print(model.generate("hello"))