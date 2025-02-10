package com.fiap.hackathon.video.app.adapter.output.persistence.gateway;

import com.fiap.hackathon.video.app.adapter.output.queue.VideoStatusChangedEvent;
import com.fiap.hackathon.video.app.adapter.output.storage.FileStorage;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VideoGatewayImplTest {

	private final RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
	private final FileStorage fileStorage = mock(FileStorage.class);
	private final String videoStatusChangedQueue = "videoStatusChangedQueue";
	private final VideoGatewayImpl videoGateway = new VideoGatewayImpl(rabbitTemplate, videoStatusChangedQueue, fileStorage);

	@Test
	void dispatchShouldSendEventToQueue() {
		Long id = 1L;
		VideoStatus status = VideoStatus.PROCESSING;

		videoGateway.dispatch(id, status);

		ArgumentCaptor<VideoStatusChangedEvent> eventCaptor = ArgumentCaptor.forClass(VideoStatusChangedEvent.class);
		verify(rabbitTemplate).convertAndSend(eq(videoStatusChangedQueue), eventCaptor.capture());
		VideoStatusChangedEvent event = eventCaptor.getValue();
		assertEquals(id, event.getId());
		assertEquals(status, event.getStatus());
	}

	@Test
	void createFileShouldInvokeFileStorageCreate() {
		String storageName = "storageName";
		Path thumbnail = Paths.get("thumbnail.png");

		videoGateway.createFile(storageName, thumbnail);

		verify(fileStorage).create(fileStorage.getThumbnailLocation(), storageName, thumbnail);
	}

	@Test
	void downloadFileShouldInvokeFileStorageDownload() {
		String storageName = "storageName";
		Path thumbnail = Paths.get("thumbnail.png");

		videoGateway.downloadFile(storageName, thumbnail);

		verify(fileStorage).download(fileStorage.getThumbnailLocation(), storageName, thumbnail);
	}

}