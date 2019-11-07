package org.melligans.deep_fake_cmd;

import org.melligans.deep_fake_cmd.utils.AppEntry;

public class App {

    public static void main(String[] args) {
        new AppEntry(args);
        System.out.println("\nConversion Complete!");

//        FaceRecognizer.recognize(new File(System.getProperty("user.dir")+"/src-frames/").listFiles()[0]);

//        FaceRecognizer.recognize(new File(App.class.getClassLoader().getResource("2faces.jpg").getFile()));
//        FaceCropper cropper = new FaceCropper(new File(System.getProperty("user.dir")+"/src-frames/"));

//        System.out.printf("TensorFlow Version: %s", TensorFlow.version());

    }
}
