package com.fiap.hackathon.video.core.common;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandTest {

	@Test
	void executeShouldRunSuccessfully() throws IOException, InterruptedException {
		List<String> commandList = List.of("echo", "Hello, World!");
		Command command = new Command(commandList);
		command.execute();
	}

	@Test
	void executeShouldThrowIOExceptionForInvalidCommand() {
		List<String> commandList = List.of("invalidCommand");
		Command command = new Command(commandList);
		assertThrows(IOException.class, command::execute);
	}

	@Test
	void executeShouldLogOutput() throws IOException, InterruptedException {
		List<String> commandList = List.of("echo", "Hello, World!");
		Command command = new Command(commandList);
		command.execute();
	}
}