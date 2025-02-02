package com.fiap.hackathon.video.core.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Commands {

    public static Command generateThumbnails(File input, File output) {

        List<String> command = new ArrayList<>();

        command.add("ffmpeg");
        command.add("-i");
        command.add(input.getAbsolutePath());
        command.add("-vf");
        command.add("fps=1");
        command.add(output.getAbsolutePath());

        return new Command(command);
    }

}
