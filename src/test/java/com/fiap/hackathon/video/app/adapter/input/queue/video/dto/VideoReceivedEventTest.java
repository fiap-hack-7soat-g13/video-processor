package com.fiap.hackathon.video.app.adapter.input.queue.video.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VideoReceivedEventTest {

	@Test
	void videoReceivedEventShouldHaveId() {
		VideoReceivedEvent event = VideoReceivedEvent.builder().id(1L).build();
		assertNotNull(event.getId());
		assertEquals(1L, event.getId());
	}

	@Test
	void videoReceivedEventShouldHandleNullId() {
		VideoReceivedEvent event = VideoReceivedEvent.builder().id(null).build();
		assertNull(event.getId());
	}

	@Test
	void videoReceivedEventShouldHandleNegativeId() {
		VideoReceivedEvent event = VideoReceivedEvent.builder().id(-1L).build();
		assertEquals(-1L, event.getId());
	}
}