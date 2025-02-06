package com.fiap.hackathon.video.app.adapter.output.storage;

import java.nio.file.Path;

public interface FileStorage {

    void create(Location location, String name, Path source);

    void download(Location location, String name, Path target);

    Location getVideoLocation();

    Location getThumbnailLocation();

}
