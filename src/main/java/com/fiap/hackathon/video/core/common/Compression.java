package com.fiap.hackathon.video.core.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Compression {

    @SneakyThrows
    public static void zipDirectory(Path source, Path target) {
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(target))) {
            try (Stream<Path> files = Files.walk(source)) {
                files
                        .filter(Predicate.not(Files::isDirectory))
                        .forEach(path -> addZipEntry(zos, source, path));
            }
        }
    }

    @SneakyThrows
    private static void addZipEntry(ZipOutputStream zos, Path source, Path path) {
        ZipEntry zipEntry = new ZipEntry(source.relativize(path).toString());
        zos.putNextEntry(zipEntry);
        Files.copy(path, zos);
        zos.closeEntry();
    }

}
