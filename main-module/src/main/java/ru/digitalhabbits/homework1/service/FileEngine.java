package ru.digitalhabbits.homework1.service;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


import static java.lang.String.format;
import static java.util.Arrays.stream;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";

    private final String currentDir = System.getProperty("user.dir");
    private final File resultDir = new File(currentDir + "/" + RESULT_DIR);

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {
        // TODO: NotImplemented -> done
        //Создать директорию, если ее нет
        resultDir.mkdirs();

        //путь к файлу
        String fileName = format((resultDir + "/" + RESULT_FILE_PATTERN), pluginName);

        //запись в файл
        try (BufferedWriter out = new BufferedWriter(new FileWriter(fileName))) {
            out.write(text);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void cleanResultDir() {
        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
