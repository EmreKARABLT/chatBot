import cv2
import numpy as np

# capture video from web camera
cap = cv2.VideoCapture(0)

# use loop to capture frame by frame
while True:

    ret_val, frame = cap.read()

    # convert frame to YCrCb color space
    ycbcr = cv2.cvtColor(frame, cv2.COLOR_BGR2YCrCb)

    # define bounds for skin color in YCrCb color space
    lower_skin = np.array([0, 135, 85])
    upper_skin = np.array([255, 180, 135])

    # threshold the image using the bounds above
    thresh = cv2.inRange(ycbcr, lower_skin, upper_skin)

    # remove noise using morphological operations
    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (15, 15))
    mask = cv2.morphologyEx(thresh, cv2.MORPH_OPEN, kernel)
    mask = cv2.morphologyEx(mask, cv2.MORPH_CLOSE, kernel)

    # find contours in the mask
    contours, hierarchy = cv2.findContours(mask, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # set minimum and maximum width and height to help get rid of false positives
    min_face_size = 100
    max_face_size = 500

    # loop over all contours
    for cnt in contours:
        # get bounding rectangles for contours and compute their aspect ratio
        x, y, w, h = cv2.boundingRect(cnt)
        aspect_ratio = float(w) / h

        # if aspect ratio is in a typical range for human faces and rectangle is in the right size range, draw rectangle
        if min_face_size < w < max_face_size and min_face_size < h < max_face_size and 0.5 <= aspect_ratio <= 1.8:
            cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 0, 250), thickness=2)

    # display the frame with detected faces
    cv2.imshow('Face recognition', frame)

    # wait for q key to be pressed to end the face detection screen
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# release capture
cap.release()
cv2.destroyAllWindows()
