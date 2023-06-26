import json
import os 


text = ''

with open('dialogues_text.txt', 'r', encoding='utf-8') as f:
    text = f.read()

text = text.replace("__eou__", "")
with open('data.txt', "w",encoding='utf-8') as file:
    file.write(text)

# Arrange the strings
