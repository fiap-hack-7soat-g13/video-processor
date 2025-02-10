package com.fiap.hackathon.video.core.common;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assertions.*;

class CompressionTest {

	@Test
	void zipDirectoryShouldCreateZipFile() throws Exception {
		Path source = Files.createTempDirectory("sourceDir");
		Path target = Files.createTempFile("target", ".zip");
		Files.createTempFile(source, "file1", ".txt");
		Files.createTempFile(source, "file2", ".txt");

		Compression.zipDirectory(source, target);

		assertTrue(Files.exists(target));
		try (ZipFile zipFile = new ZipFile(target.toFile())) {
			assertEquals(2, zipFile.size());
		}
	}

	@Test
	void zipDirectoryShouldHandleEmptyDirectory() throws Exception {
		Path source = Files.createTempDirectory("emptyDir");
		Path target = Files.createTempFile("target", ".zip");

		Compression.zipDirectory(source, target);

		assertTrue(Files.exists(target));
		try (ZipFile zipFile = new ZipFile(target.toFile())) {
			assertEquals(0, zipFile.size());
		}
	}

	@Test
	void zipDirectoryShouldHandleNonExistentSource() {
		Path source = Paths.get("nonExistentDir");
		Path target = Paths.get("target.zip");

		assertThrows(Exception.class, () -> Compression.zipDirectory(source, target));
	}

	@Test
	void zipDirectoryShouldHandleNullSource() {
		Path source = null;
		Path target = Paths.get("target.zip");

		assertThrows(NullPointerException.class, () -> Compression.zipDirectory(source, target));
	}

	@Test
	void zipDirectoryShouldHandleNullTarget() {
		Path source = Paths.get("sourceDir");
		Path target = null;

		assertThrows(NullPointerException.class, () -> Compression.zipDirectory(source, target));
	}
}