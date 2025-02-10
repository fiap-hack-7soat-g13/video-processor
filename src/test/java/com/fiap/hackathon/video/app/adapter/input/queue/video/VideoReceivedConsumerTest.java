package com.fiap.hackathon.video.app.adapter.input.queue.video;

import com.fiap.hackathon.video.app.adapter.input.queue.video.dto.VideoReceivedEvent;
import com.fiap.hackathon.video.core.usecase.VideoProcessUseCase;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class VideoReceivedConsumerTest {

	@Test
	void consumeShouldProcessValidEvent() {
		VideoProcessUseCase videoProcessUseCase = mock(VideoProcessUseCase.class);
		VideoReceivedConsumer consumer = new VideoReceivedConsumer(videoProcessUseCase);
		VideoReceivedEvent event = VideoReceivedEvent.builder().id(1L).build();

		consumer.consume(event);

		verify(videoProcessUseCase, times(1)).execute(1L);
	}

	@Test
	void consumeShouldHandleNullEvent() {
		VideoProcessUseCase videoProcessUseCase = mock(VideoProcessUseCase.class);
		VideoReceivedConsumer consumer = new VideoReceivedConsumer(videoProcessUseCase);
		VideoReceivedEvent event = VideoReceivedEvent.builder().id(null).build();

		consumer.consume(event);

		verify(videoProcessUseCase, times(1)).execute(null);
	}

	@Test
	void consumeShouldHandleNegativeIdEvent() {
		VideoProcessUseCase videoProcessUseCase = mock(VideoProcessUseCase.class);
		VideoReceivedConsumer consumer = new VideoReceivedConsumer(videoProcessUseCase);
		VideoReceivedEvent event = VideoReceivedEvent.builder().id(-1L).build();

		consumer.consume(event);

		verify(videoProcessUseCase, times(1)).execute(-1L);
	}
}