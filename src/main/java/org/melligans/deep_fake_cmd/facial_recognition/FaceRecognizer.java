package org.melligans.deep_fake_cmd.facial_recognition;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;

public class FaceRecognizer {
    public static void recognize(File image) {
        System.loadLibrary("opencv_java411");

        // Reading the Image from the file and storing it in to a Matrix object
        Mat src = Imgcodecs.imread(image.getAbsolutePath());

        // Instantiating the CascadeClassifier
        String lbp_cascade_name = "lbpcascade_frontalface.xml";
        ClassLoader classLoader = FaceRecognizer.class.getClassLoader();

        File trained_face_xml = new File(classLoader.getResource(lbp_cascade_name).getFile());
        CascadeClassifier classifier = new CascadeClassifier(trained_face_xml.getAbsolutePath());

        // Detecting the face in the snap
        MatOfRect faceDetections = new MatOfRect();
        classifier.detectMultiScale(src, faceDetections);
        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));

        // Drawing boxes
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(
                    src,                                               // where to draw the box
                    new Point(rect.x, rect.y),                            // bottom left
                    new Point(rect.x + rect.width, rect.y + rect.height), // top right
                    new Scalar(0, 0, 255),
                    3                                                     // RGB colour
            );
        }

        // Writing the image
        Imgcodecs.imwrite(image.getAbsolutePath().split(".png")[0]+"faces.png", src);

        System.out.println("Image Processed");
    }
}