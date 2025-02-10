package com.fiap.hackathon.video.app.adapter.output.queue;

import com.fiap.hackathon.video.core.domain.VideoStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VideoStatusChangedEventTest {

	@Test
	void videoStatusChangedEventShouldHaveIdAndStatus() {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder().id(1L).status(VideoStatus.SUCCEEDED).build();
		assertNotNull(event.getId());
		assertEquals(1L, event.getId());
		assertNotNull(event.getStatus());
		assertEquals(VideoStatus.SUCCEEDED, event.getStatus());
	}

	@Test
	void videoStatusChangedEventShouldHandleNullId() {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder().id(null).status(VideoStatus.SUCCEEDED).build();
		assertNull(event.getId());
		assertNotNull(event.getStatus());
		assertEquals(VideoStatus.SUCCEEDED, event.getStatus());
	}

	@Test
	void videoStatusChangedEventShouldHandleNullStatus() {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder().id(1L).status(null).build();
		assertNotNull(event.getId());
		assertEquals(1L, event.getId());
		assertNull(event.getStatus());
	}

	@Test
	void videoStatusChangedEventShouldHandleNegativeId() {
		VideoStatusChangedEvent event = VideoStatusChangedEvent.builder().id(-1L).status(VideoStatus.SUCCEEDED).build();
		assertEquals(-1L, event.getId());
		assertNotNull(event.getStatus());
		assertEquals(VideoStatus.SUCCEEDED, event.getStatus());
	}
}