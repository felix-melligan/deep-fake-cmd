package org.melligans.deep_fake_cmd.movie_converter;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;

import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_PANIC;
import static org.bytedeco.ffmpeg.global.avutil.av_log_set_level;

public class ConverterEntry {

    private MovieClip movie_src;
    private ConvertMovieClip movie_converter;
    private final int cores = Runtime.getRuntime().availableProcessors();

    public ConverterEntry(String sourceFile, String destinationDirectory) {

        // No log output from Frame Grabber
        av_log_set_level(AV_LOG_PANIC);

        movie_src = new MovieClip(sourceFile);
        movie_converter = new ConvertMovieClip(movie_src, destinationDirectory);

        Instant start = Instant.now();
        convertUsingAllCPUs();
        Instant end = Instant.now();
        long duration = Duration.between(start, end).getSeconds();
        System.out.printf("Conversion took: %s seconds.", duration);
    }

    private void convertUsingAllCPUs() {
        long frames_to_process = Math.round(movie_src.getLengthInFrames()/cores);
        IntStream CPU_cores_range = IntStream.rangeClosed(1, cores);
        CPU_cores_range.parallel().forEach(core -> {
            long last_frame = core*frames_to_process;
            long first_frame = last_frame-frames_to_process;
            movie_converter.convert(first_frame, last_frame);
        });
    }

}
