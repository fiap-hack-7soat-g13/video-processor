package com.fiap.hackathon.video.core.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public class Command {

    private final List<String> command;

    public Command(List<String> command) {
        this.command = command;
    }

    @SneakyThrows
    public void execute() {

        Process process = new ProcessBuilder(command)
                .redirectErrorStream(true)
                .start();

        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((line = reader.readLine()) != null) {
                log.info(line);
                if (Thread.currentThread().isInterrupted()) {
                    log.info("Process interrupted");
                    process.destroy();
                }
            }
        }

        process.waitFor();
    }

}
