package com.fiap.hackathon.video.app.adapter.output.storage;

import com.fiap.hackathon.video.core.domain.Video;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorage {

    void create(Video video, MultipartFile file);

    void download(Long id, Path target);

}
