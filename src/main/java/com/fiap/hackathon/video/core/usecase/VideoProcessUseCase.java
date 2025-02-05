package com.fiap.hackathon.video.core.usecase;

import com.fiap.hackathon.video.app.adapter.output.queue.VideoStatusChangedDispatcher;
import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.core.common.Commands;
import com.fiap.hackathon.video.core.common.Compression;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VideoProcessUseCase {

    private final VideoStatusChangedDispatcher videoStatusChangedDispatcher;
    private final FileStorage fileStorage;
    private final Path processDirectory;

    public VideoProcessUseCase(VideoStatusChangedDispatcher videoStatusChangedDispatcher, FileStorage fileStorage, @Value("${application.process.directory}") String processDirectory) {
        this.videoStatusChangedDispatcher = videoStatusChangedDispatcher;
        this.fileStorage = fileStorage;
        this.processDirectory = Path.of(processDirectory);
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        Files.createDirectories(processDirectory);
    }

    public void execute(Long id) {
        File directory = null;
        try {
            videoStatusChangedDispatcher.dispatch(id, VideoStatus.PROCESSING);
            directory = Files.createTempDirectory(processDirectory, String.format("%s_", id)).toFile();
            Path outputDirectory = Files.createDirectories(directory.toPath().resolve("thumbnail"));
            Path original = directory.toPath().resolve(id.toString());
            Path thumbnail = directory.toPath().resolve("thumbnail.zip");
            String storageName = id.toString();
            fileStorage.download(fileStorage.getVideoLocation(), storageName, original);
            Commands.generateThumbnails(original.toFile(), outputDirectory.resolve("%d.jpg").toFile()).execute();
            Compression.zipDirectory(outputDirectory, thumbnail);
            fileStorage.create(fileStorage.getThumbnailLocation(), storageName, new FileSystemResource(thumbnail));
            videoStatusChangedDispatcher.dispatch(id, VideoStatus.SUCCEEDED);
        } catch (Exception e) {
            videoStatusChangedDispatcher.dispatch(id, VideoStatus.FAILED);
        } finally {
            FileUtils.deleteQuietly(directory);
        }
    }

}
