package com.fiap.hackathon.video.core.gateway;

import com.fiap.hackathon.video.app.adapter.output.storage.Location;
import com.fiap.hackathon.video.core.domain.VideoStatus;

import java.nio.file.Path;

public interface VideoGateway {

	void dispatch(Long id, VideoStatus status);

	void createFile(String name, Path source);

	void downloadFile(String name, Path source);

}
