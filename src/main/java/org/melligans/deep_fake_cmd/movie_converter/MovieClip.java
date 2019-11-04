package org.melligans.deep_fake_cmd.movie_converter;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;

import java.sql.Time;

public class MovieClip {
    private Time lengthInTime;
    private String sourcePath;
    private double frames;
    private String format;
    private double frameRate;
    private FFmpegFrameGrabber frameGrabber;

    public MovieClip(String sourcePath) {
        this.sourcePath = sourcePath;
        setFrameGrabber(sourcePath);
        if (frameGrabber != null) {
            try {
                frameGrabber.start();
                this.format = frameGrabber.getMetadata("major_brand");
                this.frames = frameGrabber.getLengthInVideoFrames();
                this.lengthInTime = new Time(frameGrabber.getLengthInTime());
                this.frameRate = frameGrabber.getFrameRate();
                frameGrabber.stop();
            } catch (FrameGrabber.Exception fge) {
                System.out.println(fge.getMessage());
            }
        }
    }

    public double getLengthInFrames() { return frames; }

    public String getSourcePath() { return sourcePath; }

    private void setFrameGrabber(String sourcePath) {
        try {
            frameGrabber = new FFmpegFrameGrabber(sourcePath);
        } catch (NoClassDefFoundError ncdfe) {
            System.out.println(ncdfe.getMessage());
            System.out.println("No source found at: "+sourcePath);
        }

    }

    public String getInfo() {
        return "Video:" +
                "\n\tSource: "+this.sourcePath +
                "\n\tFormat: "+this.format +
                "\n\tLength: "+this.lengthInTime.toString() +
                "\n\tInitial FPS: "+this.frameRate;
    }
}
