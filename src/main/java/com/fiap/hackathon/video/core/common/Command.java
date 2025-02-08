package com.fiap.hackathon.video.core.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public class Command {

    private static final int SUCCESS_EXIT_CODE = 0;

    private final List<String> command;

    public Command(List<String> command) {
        this.command = List.copyOf(command);
    }

    @SneakyThrows
    public void execute() {

        log.info("Executing command: {}", String.join(" ", command));

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

        int exitCode = process.waitFor();

        if (exitCode != SUCCESS_EXIT_CODE) {
            throw new IOException("Command execution failed");
        }
    }

}
