package view;

import Face.CNN;
import Face.FaceDetection;
import Face.Haar;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import javafx.application.Application;


import javax.imageio.ImageIO;
import javax.swing.*;


public class ImageLogin extends BorderPane{

    Button back;
    JFrame frame;
    Image image;
    MainInterface mainInterface;
    volatile ImageView imageView;

    Button converstation;

    public ImageLogin(MainInterface mainInterface){
//        frame.getContentPane().add(label);
        imageView = new ImageView();
        Runnable camera = new CameraThread(this);
        Thread thread = new Thread(camera);
        thread.setDaemon(true);
        thread.start();

        HBox titlePane = new HBox();
        titlePane.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        titlePane.getStyleClass().add("content-title");
        Label title = new Label("                 Image Detection");
        titlePane.setAlignment(Pos.TOP_CENTER);
        title.getStyleClass().add("title-label");
        this.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        this.getStyleClass().add("menu-pane");

        this.mainInterface = mainInterface;
        this.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        this.getStyleClass().add("menu-pane");
        back = new Button("Back");
        back.getStyleClass().add("my-button");
        back.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.menu);
        });

        converstation = new Button("Start Conversation");
        converstation.getStyleClass().add("my-button");
        converstation.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.cfgAssistant);
        });
        VBox content = new VBox();
        content.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        content.getStyleClass().add("content");
        content.setAlignment(Pos.TOP_CENTER);
        content.getChildren().add(converstation);
        this.setBottom(content);
        converstation.setVisible(false);
        this.setCenter(imageView);
        titlePane.getChildren().addAll(back,title);
        this.setTop(titlePane);

    }

    class CameraThread implements Runnable{
        VideoCapture video ;
        FaceDetection cnn;
        ImageLogin login;
        public CameraThread(ImageLogin login){
            OpenCV.loadLocally();
            cnn = new CNN();
            video = new VideoCapture(0);
            this.login = login;
        }
        @Override
        public void run() {
            Mat f = new Mat();
            while(true){
                video.read(f);
                cnn.evaluate(f);

                if(cnn.positions.length!=0){
                    login.converstation.setVisible(true);
                }else{
                    login.converstation.setVisible(false);
                }
                drawRectangle(f,cnn.positions);
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

        private Mat drawRectangle(Mat a,int[][] posistions){
            int thickness = 2;
            Scalar color = new Scalar(0, 0, 255); // Red color in BGR
            Scalar textColor = new Scalar(0, 255 , 0);
            for (int i = 0; i < posistions.length; i++) {
                Point topLeft = new Point(posistions[i][0], posistions[i][1]);
                Point bottomRight = new Point(posistions[i][0]+posistions[i][2], posistions[i][1]+posistions[i][3]);
                Imgproc.rectangle(a, topLeft, bottomRight, color, thickness);


                Point textLoc = new Point(posistions[i][0]-15, posistions[i][1]);
                if(cnn.names != null )
                    Imgproc.putText(a, cnn.names[i], textLoc , Imgproc.FONT_HERSHEY_SIMPLEX, 0.7, textColor, thickness);
            }

            return a;
        }
    }
}
