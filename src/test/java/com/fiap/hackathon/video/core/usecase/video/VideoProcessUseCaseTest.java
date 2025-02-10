package com.fiap.hackathon.video.core.usecase.video;

import com.fiap.hackathon.video.core.common.Command;
import com.fiap.hackathon.video.core.common.Commands;
import com.fiap.hackathon.video.core.domain.VideoStatus;
import com.fiap.hackathon.video.core.gateway.VideoGateway;
import com.fiap.hackathon.video.core.usecase.VideoProcessUseCase;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VideoProcessUseCaseTest {

	private VideoGateway videoGateway;
	private VideoProcessUseCase videoProcessUseCase;
	private Path processDirectory;

	@BeforeEach
	void setUp() throws Exception {
		videoGateway = mock(VideoGateway.class);
		processDirectory = Files.createTempDirectory("processDirectory");
		videoProcessUseCase = new VideoProcessUseCase(videoGateway, processDirectory.toString());
	}

	@Test
	void executeShouldHandleProcessingFailure() {
		Long id = 1L;
		doThrow(new RuntimeException("Processing failed")).when(videoGateway).downloadFile(anyString(), any(Path.class));

		assertThrows(RuntimeException.class, () -> videoProcessUseCase.execute(id));

		ArgumentCaptor<VideoStatus> statusCaptor = ArgumentCaptor.forClass(VideoStatus.class);
		verify(videoGateway, times(2)).dispatch(eq(id), statusCaptor.capture());
		assertEquals(VideoStatus.PROCESSING, statusCaptor.getAllValues().get(0));
		assertEquals(VideoStatus.FAILED, statusCaptor.getAllValues().get(1));
	}

	@Test
	void executeShouldHandleNullId() {
		assertThrows(NullPointerException.class, () -> videoProcessUseCase.execute(null));
	}

}