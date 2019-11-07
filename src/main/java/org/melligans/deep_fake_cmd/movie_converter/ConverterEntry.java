package org.melligans.deep_fake_cmd.movie_converter;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

import java.util.stream.IntStream;

import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_PANIC;
import static org.bytedeco.ffmpeg.global.avutil.av_log_set_level;

public class ConverterEntry {

    private static double count = 0;
    private MovieClip movie_src;
    private ConvertMovieClip movie_converter;
    private final int cores = Runtime.getRuntime().availableProcessors();
    private ProgressBar progressBar;
    private long frames_to_process;

    public ConverterEntry(String sourceFile, String destinationDirectory) {

        // No log output from Frame Grabber
        av_log_set_level(AV_LOG_PANIC);

        movie_src = new MovieClip(sourceFile);

        double total_frames = movie_src.getLengthInFrames();

        frames_to_process = Math.round(total_frames/cores);

        // Set that progress bar
        progressBar = new ProgressBar("Processing Frames using "+cores+" CPU cores!", (long) total_frames, ProgressBarStyle.ASCII);

        movie_converter = new ConvertMovieClip(movie_src, destinationDirectory, progressBar);

        convertUsingAllCPUs();
    }

    private void convertUsingAllCPUs() {

        this.progressBar.start();

        IntStream CPU_cores_range = IntStream.rangeClosed(1, cores);
        CPU_cores_range.parallel().forEach(core -> {
            long last_frame = core*frames_to_process;
            long first_frame = last_frame-frames_to_process;
            movie_converter.convert(first_frame, last_frame);
        });

        this.progressBar.stop();
    }

}
