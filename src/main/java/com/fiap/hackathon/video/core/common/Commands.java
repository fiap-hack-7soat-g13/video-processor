package com.fiap.hackathon.video.core.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Commands {

    public static Command generateThumbnails(File input, File output) {

        List<String> command = new ArrayList<>();

        command.add("ffmpeg");
        command.add("-i");
        command.add(Objects.nonNull(input) ? input.getAbsolutePath() : null);
        command.add("-vf");
        command.add("fps=1");
        command.add(Objects.nonNull(output) ? output.getAbsolutePath() : null);

        return new Command(command);
    }

}
