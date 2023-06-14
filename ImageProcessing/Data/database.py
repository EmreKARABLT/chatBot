import os

import cv2
import numpy as np
from PIL import Image


class database():
    def __init__(self , path_face_classifier , path_IMAGE_DIR):
        self.face_classifier = cv2.CascadeClassifier(path_face_classifier)
        self.IMAGE_DIR = path_IMAGE_DIR

    def my_data(self , height , width ):

        x_train = []
        y_labels = []
        for root, directories, files in os.walk(self.IMAGE_DIR):

            for file in files:
                if file.endswith("png") or file.endswith("jpg") :
                    path = os.path.join(root, file)

                    label = os.path.basename(os.path.dirname(path))

                    image = cv2.imread(path)
                    rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
                    gray = cv2.cvtColor(image,cv2.COLOR_BGR2GRAY)


                    faces = self.face_classifier.detectMultiScale(gray, scaleFactor=1.3, minNeighbors=5)



                    for x, y, w, h in faces:
                        rect_rgb = rgb[y:y + h, x:x + w]
                        rect_rgb = cv2.resize(rect_rgb, (height, width), interpolation=cv2.INTER_CUBIC)

                        x_train.append(rect_rgb)
                        y_labels.append(label)

        print("LFW data set created")

        return x_train , y_labels

