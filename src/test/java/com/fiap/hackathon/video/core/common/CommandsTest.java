package com.fiap.hackathon.video.core.common;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandsTest {

	@Test
	void generateThumbnailsShouldCreateValidCommand() {
		File input = new File("input.mp4");
		File output = new File("output_%d.png");

		Command command = Commands.generateThumbnails(input, output);

		assertNotNull(command);
	}

	@Test
	void generateThumbnailsShouldHandleNullInput() {
		File input = null;
		File output = new File("output_%d.png");

		assertThrows(NullPointerException.class, () -> Commands.generateThumbnails(input, output));
	}

	@Test
	void generateThumbnailsShouldHandleNullOutput() {
		File input = new File("input.mp4");
		File output = null;

		assertThrows(NullPointerException.class, () -> Commands.generateThumbnails(input, output));
	}

}