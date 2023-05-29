package Face;
import java.util.Arrays;
import java.util.List;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class CNN extends FaceDetection {

    private CascadeClassifier faceCascade;
    private String filenameFaceCascade = "src/main/java/Face/haarcascade_frontalface_default.xml";
    private Model model ;
    public CNN() {
        OpenCV.loadLocally();
        this.faceCascade = new CascadeClassifier();
        this.faceCascade.load(this.filenameFaceCascade);
        this.model = new Model();
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
        this.names = new String[listOfFaces.size()];
        for (int i = 0; i < listOfFaces.size() ; i++) {
            Rect face = listOfFaces.get(i);
            Imgproc.rectangle(frame, new Point(face.x, face.y), new Point(face.x + face.width, face.y + face.height), new Scalar(0, 0, 255));
            int[] face_location = new int[]{face.x,face.y,face.width,face.height};

            positions[i] = face_location;

            Mat roiImage = new Mat(frameGray, face);
            Mat resizedImage = new Mat();
            int newWidth = 224 , newHeight= 224 ;

            Size newSize = new Size(newWidth, newHeight);
            Imgproc.resize(roiImage, resizedImage, newSize);

            float[][][][] reshapedFloatImage = convertMatTo4dArray(resizedImage);
            int indexOfMax = model.predict(reshapedFloatImage , 0.2);
            names[i] = ""+indexOfMax;

        }

        System.out.println("index of highest matching - > " + Arrays.toString(names));
        System.out.println("x,y,width,height" + Arrays.deepToString(positions));
        //-- Show what you got
        HighGui.imshow("Capture - Face detection", frame );
    }
    public float[][][][] convertMatTo4dArray( Mat image){
        int height = (int)(image.size().height);
        int width = (int)(image.size().width);
        image.reshape(1 , new int[]{1,height,width,1});
        float[][][][] image_float = new float[1][height][width][1];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float grayValue = (float)(image.get(y, x)[0]);
                // Store the grayscale pixel value in the 2D array
                image_float[0][y][x][0] = grayValue;
            }
        }
//        Mat mat = new Mat();
//        mat.create(224,224,1);
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                float gray = image_float[0][y][x][0];
//                mat.put(y,x, gray);
//
//                // Store the grayscale pixel value in the 2D array
//            }
//        }
//        HighGui.imshow("a ", mat);
        return image_float;

    }

    public static void main(String[] args) {
        FaceDetection cnn = new CNN();
        int cameraDevice = args.length > 2 ? Integer.parseInt(args[2]) : 0;
        VideoCapture capture = new VideoCapture(cameraDevice);
        Mat frame = new Mat();

        while (capture.read(frame)) {
            if (frame.empty()) {
                System.err.println("--(!) No captured frame -- Break!");
                break;
            }
            cnn.evaluate(frame);
        }
        System.exit(0);
    }
}