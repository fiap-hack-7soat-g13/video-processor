package com.fiap.hackathon.video.app.adapter.output.persistence.gateway;

import com.fiap.hackathon.video.app.adapter.output.queue.VideoStatusChangedEvent;
import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class VideoGatewayImpl implements VideoGateway {

	private final RabbitTemplate rabbitTemplate;
	private final String videoStatusChangedQueue;
	private final FileStorage fileStorage;

	public VideoGatewayImpl(RabbitTemplate rabbitTemplate,
	                        @Value("${application.queue.videoStatusChanged.name}") String videoStatusChangedQueue,
	                        FileStorage fileStorage) {
		this.rabbitTemplate = rabbitTemplate;
		this.videoStatusChangedQueue = videoStatusChangedQueue;
		this.fileStorage = fileStorage;
	}

	@Override
	public void dispatch(Long id, VideoStatus status) {

		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder()
				.id(id)
				.status(status)
				.build();

		rabbitTemplate.convertAndSend(videoStatusChangedQueue, event);
	}

	public void createFile(String storageName, Path thumbnail) {
		fileStorage.create(fileStorage.getThumbnailLocation(), storageName, thumbnail);
	}

	public void downloadFile(String storageName, Path thumbnail) {
		fileStorage.download(fileStorage.getThumbnailLocation(), storageName, thumbnail);
	}

}
