package com.fiap.hackathon.video.core.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class VideoTest {

	@Test
	void videoShouldHaveValidId() {
		Video video = new Video();
		video.setId(1L);
		assertEquals(1L, video.getId());
	}

	@Test
	void videoShouldHandleNullName() {
		Video video = new Video();
		video.setName(null);
		assertNull(video.getName());
	}

	@Test
	void videoShouldHaveValidContentType() {
		Video video = new Video();
		video.setContentType("video/mp4");
		assertEquals("video/mp4", video.getContentType());
	}

	@Test
	void videoShouldHandleNullContentType() {
		Video video = new Video();
		video.setContentType(null);
		assertNull(video.getContentType());
	}

	@Test
	void videoShouldHaveValidSize() {
		Video video = new Video();
		video.setSize(1024L);
		assertEquals(1024L, video.getSize());
	}

	@Test
	void videoShouldHandleNullStatus() {
		Video video = new Video();
		video.setStatus(null);
		assertNull(video.getStatus());
	}

	@Test
	void videoShouldHaveValidCreatedAt() {
		LocalDateTime now = LocalDateTime.now();
		Video video = new Video();
		video.setCreatedAt(now);
		assertEquals(now, video.getCreatedAt());
	}

	@Test
	void videoShouldHandleNullCreatedAt() {
		Video video = new Video();
		video.setCreatedAt(null);
		assertNull(video.getCreatedAt());
	}

	@Test
	void videoShouldHaveValidCreatedBy() {
		Video video = new Video();
		video.setCreatedBy(1L);
		assertEquals(1L, video.getCreatedBy());
	}

	@Test
	void videoShouldHandleNullCreatedBy() {
		Video video = new Video();
		video.setCreatedBy(null);
		assertNull(video.getCreatedBy());
	}

	@Test
	void videoShouldHaveValidCreatedByEmail() {
		Video video = new Video();
		video.setCreatedByEmail("test@example.com");
		assertEquals("test@example.com", video.getCreatedByEmail());
	}

	@Test
	void videoShouldHandleNullCreatedByEmail() {
		Video video = new Video();
		video.setCreatedByEmail(null);
		assertNull(video.getCreatedByEmail());
	}
}