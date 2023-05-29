package Face;
import org.opencv.core.Mat;

public abstract class FaceDetection{
    public String[] names = null ;
    public int[][] positions = null ;
    public void evaluate(int[][] image){}; // for testing a 224x224 image
    public void evaluate(Mat image){};

    public String[] getNames(){
        return names;
    };
    public int[][] getPositions(){
        return positions;
    };


}
