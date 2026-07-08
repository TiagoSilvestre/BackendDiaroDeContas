package br.com.tiagosilvestre.diariodecontas.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

final class FileStorageHelper {

	private FileStorageHelper() {
	}

	static void ensureFileExists(Path path) throws IOException {
		Path parent = path.getParent();
		if (parent != null) {
			Files.createDirectories(parent);
		}
		if (Files.notExists(path)) {
			Files.createFile(path);
		}
	}

	static void writeAll(Path path, List<String> lines) throws IOException {
		ensureFileExists(path);
		Files.write(path, lines);
	}

	static List<String> readAll(Path path) throws IOException {
		ensureFileExists(path);
		return Files.readAllLines(path);
	}
}
