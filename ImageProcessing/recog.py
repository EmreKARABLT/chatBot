import cv2
def face_cropped(img):
        face_classifier = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        faces = face_classifier.detectMultiScale(gray, 1.3, 5)
        
        if faces == ():
            return None
        for (x,y,w,h) in faces:
            cropped_face = img[y:y+h,x:x+w]
        return cropped_face
def generate_dataset(name):
    cap = cv2.VideoCapture(0)
    img_id = 0
    print("usao")
    while True:
        ret, frame = cap.read()
        if face_cropped(frame) is not None:
            img_id+=1
            face = cv2.resize(face_cropped(frame), (200,200))
            face = cv2.cvtColor(face, cv2.COLOR_BGR2GRAY)
            file_name_path = "C:\\Users\\grado\\Documents\\chatBot\\ImageProcessing\\Images\\"+name+"."+str(img_id)+'.jpg'
            cv2.imwrite(file_name_path, face)
            cv2.putText(face, str(img_id), (50,50), cv2.FONT_HERSHEY_COMPLEX, 1, (0,255,0), 2 )
            
            cv2.imshow("Cropped_Face", face)
            if cv2.waitKey(1)==13 or int(img_id)==100:
                break
                
    cap.release()
    cv2.destroyAllWindows()
    print("Collecting samples is completed !!!")
generate_dataset("Alex")
import numpy as np
def my_label(image_name):
    name = image_name.split('.')[-3]
    if name=="Gabrijel":
        return np.array([1,0,0])
    elif name=="Josh":
        return np.array([0,1,0])
    elif name=="Alex":
        return np.array([0,0,1])
import os
from random import shuffle
from tqdm import tqdm

def my_data():
    data = []
    for img in tqdm(os.listdir("Images")):
        path=os.path.join("Images",img)
        img_data = cv2.imread(path, cv2.IMREAD_GRAYSCALE)
        img_data = cv2.resize(img_data, (50,50))
        data.append([np.array(img_data), my_label(img)])
    shuffle(data)  
    return data
data = my_data()
train = data[:40]  
test = data[40:]
print(len(train))
X_train = np.array([i[0] for i in train]).reshape(-1,50,50,1)
print(X_train.shape)
y_train = [i[1] for i in train]
X_test = np.array([i[0] for i in test]).reshape(-1,50,50,1)
print(X_test.shape)
y_test = [i[1] for i in test]

import numpy as np
import tensorflow as tf
from keras import Sequential
from keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, Dropout

model = Sequential()
model.add(Conv2D(32, (3, 3), activation='relu', input_shape=(50, 50, 1)))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Conv2D(64, (3, 3), activation='relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Conv2D(128, (3, 3), activation='relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Conv2D(64, (3, 3), activation='relu'))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Flatten())
model.add(Dense(1024, activation='relu'))
model.add(Dropout(0.8))
model.add(Dense(3, activation='softmax'))


model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

# Replace the following line:
# model.fit(X_train, y_train, n_epoch=12, validation_set=(X_test, y_test), show_metric=True, run_id="FRS")
# With this line:
model.fit(X_train, np.array(y_train), epochs=30, validation_data=(X_test, np.array(y_test)))


def data_for_visualization():
    Vdata = []
    for img in tqdm(os.listdir("Images")):
        path = os.path.join("Images", img)
        img_num = img.split('.')[0] 
        img_data = cv2.imread(path, cv2.IMREAD_GRAYSCALE)
        img_data = cv2.resize(img_data, (50,50))
        Vdata.append([np.array(img_data), img_num])
    shuffle(Vdata)
    return Vdata
Vdata = data_for_visualization()

import matplotlib.pyplot as plt

fig = plt.figure(figsize=(20, 20))
for num, data in enumerate(Vdata[:20]):
    img_data = data[0]
    y = fig.add_subplot(5, 5, num + 1)
    image = img_data
    data = img_data.reshape(1, 50, 50, 1)
    model_out = model.predict(data)[0]

    if np.argmax(model_out) == 0:
        my_label = 'Gabrijel'
    elif np.argmax(model_out) == 1:
        my_label = 'Josh'
    else:
        my_label = 'Alex'

    y.imshow(image, cmap='gray')
    plt.title(my_label)

    y.axes.get_xaxis().set_visible(False)
    y.axes.get_yaxis().set_visible(False)
plt.show()

