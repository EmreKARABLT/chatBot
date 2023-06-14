import os

import numpy as np
import cv2
def face_cropped(img , margin = 25):
        path_to_haar_cascade = "haarcascade_frontalface_default.xml"
        face_classifier = cv2.CascadeClassifier(cv2.data.haarcascades + path_to_haar_cascade)

        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)


        faces = face_classifier.detectMultiScale(gray, 1.1, 5)

        if len(faces) == 0 :
            print("NO FACE DETECTED!!")
            return None
        for (x,y,w,h) in faces:
            try:
                cropped_face = img[y-margin:y+h+margin,x-margin:x+w+margin]
            except:
                pass
        return cropped_face
def generate_dataset(name):
    if len(name)  == 0 :
        raise "Enter Your Name as the parameter of this method"

    folder_path = os.path.join("Data", "Images", name)

    if not os.path.exists(folder_path):
        os.mkdir(folder_path)

    try :
        cap = cv2.VideoCapture(1)
    except:
        cap = cv2.VideoCapture(0)

    img_id = 0
    print("TURN YOUR HEAD")
    while True:
        ret, frame = cap.read()
        cv2.flip(frame,0)
        if face_cropped(frame ) is not None:
            img_id+=1
            face = cv2.resize(face_cropped(frame), (250,250))


            file_name_path = os.path.join(folder_path,  name+"_"+str(img_id)+'.jpg')

            cv2.imwrite(file_name_path, face)
            cv2.putText(face, str(img_id), (50,50), cv2.FONT_HERSHEY_COMPLEX, 1, (0,255,0), 2 )

            cv2.imshow("Cropped_Face", face)
            if cv2.waitKey(1)==13 or int(img_id)==100:
                break

    cap.release()
    cv2.destroyAllWindows()
    print("Collecting samples is completed !!!")


generate_dataset("Emre_Karabulut")