package com.procompras.shared;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class ExtractCSV {

    private ExtractCSV() {
    }

    public static <T> Stream<T> read(
            String filePath,
            Function<String[], T> mapper
    ) throws IOException {

        CSVReader reader = new CSVReaderBuilder(
                Files.newBufferedReader(Path.of(filePath))
        ).withSkipLines(1).build();

        try {
            return StreamSupport.stream(reader.spliterator(), false)
                    .map(mapper)
                    .onClose(() -> {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (RuntimeException e) {
            reader.close();
            throw e;
        }
    }
}