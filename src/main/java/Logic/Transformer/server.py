from flask import Flask, request
import torch
from transformer import TextGeneration

app = Flask(__name__)
model_path = 'model3.pth'

model = TextGeneration(256,'cpu',192,3,3)
model.loadModel('model.pth','data.txt')

@app.route("/data", methods=["POST"])
def receive_data():
    data = request.json
    print(data)
    text = 'error'
    if data != None:
        text = data['key']
    #text = data['text']
    s = model.generate(text)
    print(s)
    s = s.split('.')
    out = s[1]+'.' # type: ignore
    if(s[1].__contains__('?')):
        out = s[1].split('?')[0]+'?'
    return out

if __name__ == "__main__":
    app.run()