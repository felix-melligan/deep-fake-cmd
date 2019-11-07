package org.melligans.deep_fake_cmd.facial_recognition;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.io.File;
import java.util.Objects;

public class FaceCropper {

    // face cascade classifier
    private CascadeClassifier faceCascade;
    private int absoluteFaceSize;

    public FaceCropper(File source_directory) {

        faceCascade = new CascadeClassifier();
        absoluteFaceSize = 0;

        cropAndReturn(Objects.requireNonNull(source_directory.listFiles())[0]);
    }

    private void cropAndReturn(File image) {
        Mat matrix = Imgcodecs.imread(image.getAbsolutePath());

        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        // convert the frame in gray scale
        Imgproc.cvtColor(matrix, grayFrame, Imgproc.COLOR_BGR2GRAY);
        // equalize the frame histogram to improve the result
        Imgproc.equalizeHist(grayFrame, grayFrame);

        // compute minimum face size (20% of the frame height, in our case)
        if (absoluteFaceSize == 0)
        {
            int height = grayFrame.rows();
            if (Math.round(height * 0.2f) > 0)
            {
                absoluteFaceSize = Math.round(height * 0.2f);
            }
        }

        // detect faces
        this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, Objdetect.CASCADE_SCALE_IMAGE,
                new Size(absoluteFaceSize, absoluteFaceSize), new Size());

        // each rectangle in faces is a face: draw them!
        Rect[] facesArray = faces.toArray();
        for (int i = 0; i < facesArray.length; i++)
//            Imgproc.rectangle(matrix, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
            System.out.println("Face: "+facesArray[i]);
    }
}
