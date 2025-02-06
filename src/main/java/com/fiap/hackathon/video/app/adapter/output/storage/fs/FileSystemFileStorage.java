package com.fiap.hackathon.video.app.adapter.output.storage.fs;

import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@ConditionalOnProperty(name = "application.storage.fileSystem.active", havingValue = "true")
public class FileSystemFileStorage implements FileStorage {

    private final FileSystemLocation videoLocation;
    private final FileSystemLocation thumbnailLocation;

    public FileSystemFileStorage(@Value("${application.storage.fileSystem.videoDirectory}") String videoDirectory,
                                 @Value("${application.storage.fileSystem.thumbnailDirectory}") String thumbnailDirectory) {
        this.videoLocation = new FileSystemLocation(Path.of(videoDirectory));
        this.thumbnailLocation = new FileSystemLocation(Path.of(thumbnailDirectory));
    }

    @PostConstruct
    public void post() throws IOException {
        Files.createDirectories(videoLocation.getDirectory());
        Files.createDirectories(thumbnailLocation.getDirectory());
    }

    @Override
    public void create(Location location, String name, Path source) {
        try {
            Path target = getPath(location, name);
            Files.copy(source, target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void download(Location location, String name, Path target) {
        try {
            Path source = getPath(location, name);
            Files.copy(source, target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Location getVideoLocation() {
        return this.videoLocation;
    }

    @Override
    public Location getThumbnailLocation() {
        return this.thumbnailLocation;
    }

    private Path getPath(Location location, String name) {
        return ((FileSystemLocation) location).getDirectory().resolve(name);
    }

}
