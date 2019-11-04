package org.melligans.deep_fake_cmd.movie_converter;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConvertMovieClip {
    private final File destinationDirectory;
    private final Java2DFrameConverter converter;
    private final String imgType = "png";
    private final MovieClip source_movie;

    public ConvertMovieClip(final MovieClip sourceMovie, final String destinationDirectory) {
        this.destinationDirectory = new File(destinationDirectory+"/src-frames/");
        this.source_movie = sourceMovie;
        converter = new Java2DFrameConverter();

        if(!this.destinationDirectory.exists()) {
            this.destinationDirectory.mkdirs();
        }
    }

    public void convert(long first_frame, long last_frame) {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(source_movie.getSourcePath());

        if(frameGrabber != null) {
            try {
                frameGrabber.start();

                for (long i = first_frame; i < last_frame; i++) {
                    final Frame frame = frameGrabber.grabImage();
                    final String frameNumber = Double.toString(i);
                    final BufferedImage bi = converter.convert(frame);
                    final File img = new File(destinationDirectory + "/frame-" + frameNumber + ".png");
                    ImageIO.write(bi, imgType, img);
                }

                frameGrabber.stop();
            } catch (final IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } else {
            System.out.println("FrameGrabber is null!");
        }
    }
}
