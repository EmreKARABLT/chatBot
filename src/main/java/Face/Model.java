package Face;


import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;


@SuppressWarnings("unchecked")
public class Model {

    private SavedModelBundle model;
    private Session session ;
    private Session.Runner runner;
    private String model_path = "src/main/java/Face/fr_model";
    public Model(){
        this.model = SavedModelBundle.load(model_path , "serve");
    }

    public int predict(float[][][][] image , double confidenceThreshold){
        float[][] dst = new float[1][7];
        int index = -1;
        if(model==null) return -1;
        try {
            this.session = model.session();
            this.runner = session.runner();

            // Input tensor
            Tensor input = Tensor.create(image);
            Tensor output = runner.feed("serving_default_input_input", input).fetch("StatefulPartitionedCall:0").run().get(0);

            // Get the predictions
            output.copyTo(dst);
            index = maxIndex(dst , confidenceThreshold);

        }catch (NullPointerException e){
            System.out.println("Model is Null");
        }


        return index;
    }
    public int maxIndex(float[][] array , double confidenceThreshold){
        float max = Integer.MIN_VALUE;
        int max_index = 0;
        for (int i = 0; i < array.length; i++) {
            if( (array[0][i] > max) && (array[0][i]>confidenceThreshold )) {
                max = array[0][i];
                max_index = i ;
            }
        }
        return max_index;
    }

//    public static void main(String[] args) throws IOException {
//        OpenCV.loadLocally();
//        Model model = new Model();
//        String filePath = "C:\\Users\\bpk_e.EMRE\\Desktop\\PROJECTS\\chatBot\\roi_image.jpg";
//        BufferedImage image = ImageIO.read(new File(filePath));
//        float[][][][] pixels = new float[1][224][224][1];
//
//        // Iterate over each pixel of the image
//        for (int y = 0; y < 224; y++) {
//            for (int x = 0; x < 224; x++) {
//                float grayValue = image.getRaster().getSample(x, y, 0);
//
//                // Store the grayscale pixel value in the 2D array
//                pixels[0][y][x][0] = grayValue;
//
//            }
//        }
//        System.out.println(image);
//
//        model.predict(pixels,0.2);
//
//    }
}
