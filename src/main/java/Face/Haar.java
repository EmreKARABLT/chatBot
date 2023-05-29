package Face;
import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class Haar extends FaceDetection {

    private CascadeClassifier faceCascade;
    private String filenameFaceCascade = "src/main/java/Face/haarcascade_frontalface_default.xml";

    public Haar() {
        OpenCV.loadLocally();
        this.faceCascade = new CascadeClassifier();
        this.faceCascade.load(this.filenameFaceCascade);
    }

    public void evaluate(Mat frame) {

        Mat frameGray = new Mat();
        Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(frameGray, frameGray);
        // -- Detect faces
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(frameGray, faces,1.1,11);
        List<Rect> listOfFaces = faces.toList();

        this.positions = new int[listOfFaces.size()][4];

        for (int i = 0; i < listOfFaces.size() ; i++) {
            Rect face = listOfFaces.get(i);
            int[] face_location = new int[]{face.x,face.y,face.width,face.height};
            positions[i] = face_location;

            // TODO: draw the rectangle in GUI part
            Imgproc.rectangle(frame, new Point(face.x, face.y), new Point(face.x + face.width, face.y + face.height), new Scalar(0, 0, 255));
        }
        //instead of imshow we will use FaceDetection.getLocations()
        HighGui.imshow("Capture - Face detection", frame );
    }

    public static void main(String[] args) {
        FaceDetection haar = new Haar();
        int cameraDevice = args.length > 2 ? Integer.parseInt(args[2]) : 0;
        VideoCapture capture = new VideoCapture(cameraDevice);
        Mat frame = new Mat();
        while (capture.read(frame)) {
            if (frame.empty()) {
                System.err.println("--(!) No captured frame -- Break!");
                break;
            }
            //-- 3. Apply the classifier to the frame
            haar.evaluate(frame);
            if (HighGui.waitKey(1) == 27) {
                break;// escape
            }
        }
        System.exit(0);
    }
}