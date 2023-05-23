package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;



import javax.imageio.ImageIO;
import javax.swing.*;


public class ImageLogin extends BorderPane{

    Button back;
    JFrame frame;
    Image image;
    MainInterface mainInterface;
    volatile ImageView imageView;

    public ImageLogin(MainInterface mainInterface){
//        frame.getContentPane().add(label);
        imageView = new ImageView();
        Runnable camera = new CameraThread();
        Thread thread = new Thread(camera);
        thread.setDaemon(true);
        thread.start();

        this.mainInterface = mainInterface;
        this.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        this.getStyleClass().add("menu-pane");
        back = new Button("Back");
        back.getStyleClass().add("my-button");
        back.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.menu);
        });
        this.setCenter(imageView);
        this.setTop(back);
    }
    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}


//    public void showResult(Mat img) {
//        Imgproc.resize(img, img, new Size(640, 480));
//        MatOfByte m = new MatOfByte();
//        Imgcodecs.imencode(".jpg", img, m);
//        byte[] byteArray = m.toArray();
//        BufferedImage bufImage = null;
//        try {
//            InputStream in = new ByteArrayInputStream(byteArray);
//            bufImage = ImageIO.read(in);
//
//
//            // TODO: label.set(new ImageIcon(bufImage));
//            frame.pack();
//            frame.setVisible(true);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    class CameraThread implements Runnable{
        VideoCapture video = new VideoCapture(0);
        @Override
        public void run() {
            Mat f = new Mat();
            while(true){
                video.read(f);
                Image img = convertMatToImage(f);
                imageView.setImage(img);
            }
        }

        private Image convertMatToImage(Mat mat) {
            Imgproc.resize(mat, mat, new Size(640, 480));
            // Convert the Mat object to a byte array
            MatOfByte buffer = new MatOfByte();
            Imgcodecs.imencode(".png", mat, buffer);
            byte[] imageData = buffer.toArray();

            // Convert the byte array to a JavaFX image
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            return new Image(inputStream);
        }
    }
}
