import cv2

# import HAAR cascade XML file
face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')

# capture video from web camera
cap = cv2.VideoCapture(0)

# use loop to capture frame by frame
while True:

    ret_val, frame = cap.read()

    # convert frame to greyscale
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # detect faces and return their coordinates
    face_coordinates = face_cascade.detectMultiScale(gray, scaleFactor=1.2, minNeighbors=11, minSize=(30, 30))

    # create a rectangle around each detected face
    for (x, y, w, h) in face_coordinates:
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 250), thickness=2)

    # display the frame with detected faces
    cv2.imshow('Face recognition', frame)

    # wait for q key to be pressed to end the face detection screen
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# release capture
cap.release()
cv2.destroyAllWindows()
