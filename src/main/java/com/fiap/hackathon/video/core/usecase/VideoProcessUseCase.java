package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.core.common.Commands;
import com.fiap.hackathon.video.core.common.Compression;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VideoProcessUseCase {

    private final VideoGateway videoGateway;
    private final Path processDirectory;

    public VideoProcessUseCase(VideoGateway videoGateway, @Value("${application.process.directory}") String processDirectory) {
        this.videoGateway = videoGateway;
        this.processDirectory = Path.of(processDirectory);
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        Files.createDirectories(processDirectory);
    }

    @SneakyThrows
    public void execute(Long id) {
        File directory = null;
        try {
            videoGateway.dispatch(id, VideoStatus.PROCESSING);
            directory = Files.createTempDirectory(processDirectory, String.format("%s_", id)).toFile();
            Path outputDirectory = Files.createDirectories(directory.toPath().resolve("thumbnail"));
            Path original = directory.toPath().resolve(id.toString());
            Path thumbnail = directory.toPath().resolve("thumbnail.zip");
            String storageName = id.toString();
            videoGateway.downloadFile(storageName, thumbnail);
            Commands.generateThumbnails(original.toFile(), outputDirectory.resolve("%d.jpg").toFile()).execute();
            Compression.zipDirectory(outputDirectory, thumbnail);
            videoGateway.createFile(storageName, thumbnail);
            videoGateway.dispatch(id, VideoStatus.SUCCEEDED);
        } catch (Exception e) {
            videoGateway.dispatch(id, VideoStatus.FAILED);
            throw e;
        } finally {
            FileUtils.deleteQuietly(directory);
        }
    }

}
