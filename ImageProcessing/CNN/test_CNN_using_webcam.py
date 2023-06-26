import cv2
import json
import numpy as np
from tensorflow import keras
import tensorflow as tf


path_to_haar_cascade = "haarcascade_frontalface_default.xml"
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + path_to_haar_cascade)

model = keras.models.load_model("..\\Model\\final_model")

cap = cv2.VideoCapture(0)


dim = 100

with open('..\\Model\\labels.json') as json_file:
    labels = json.load(json_file)
print(labels)


margin = 5
while True:
    ret, frame = cap.read()
    frame = cv2.flip(frame, 1)
    gray = cv2.cvtColor(frame, cv2.COLOR_BGRA2GRAY)
    np_image = np.array(gray, dtype="uint8")

    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.3, minNeighbors=5)
    x  = y = w = h = 0
    for x, y , w , h in faces :
        rect_gray = np_image[y-margin:y + h+margin, x-margin:x + w+margin]
        try:
            img = cv2.resize(rect_gray, (dim, dim), interpolation=cv2.INTER_CUBIC)
            img_array = tf.keras.utils.img_to_array(img)
            img_array = tf.expand_dims(img_array, 0)  # Create a batch

            predictions = model.predict(img_array)

            print(
                "{} - {:.2f} % conf."
                .format(labels[str(np.argmax(predictions))], 100 * np.max(predictions))
            )

        except:
            pass
        # if conf > 20:
        #     font = cv2.FONT_HERSHEY_PLAIN
        #     name= labels.get(ids).replace("_" , " " ).upper() + " {p:.2f}".format(p=conf)
        #     color = (0,255,0,200)
        #     cv2.putText(frame , name , (x,y-10), font , 1 , color , 1 , cv2.LINE_AA)


        cv2.rectangle(frame ,pt1 = (x , y), pt2 = ( x + w, y + h ),color = (255, 0 ,0) ,thickness=2 )

    cv2.imshow('frame',frame)

    if cv2.waitKey(1) == 27 :
        break

cap.release()
cv2.destroyAllWindows()