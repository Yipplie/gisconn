package com.fdctech.gisconn.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<File> getXmlFilesInDirectory(String directory) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        Path path = Paths.get(directory);
        Files.find(path, 1, (p, attr) -> true).forEach(p -> {
                    Path fileName = p.getFileName();
                    if (fileName.toString().endsWith(".xml"))
                        files.add(p.toFile());
                }
        );
        return files;
    }

    public static List<File> getAllFilesInDirectory(String directory) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        Path path = Paths.get(directory);
        Files.find(path, 1, (p, attr) -> true).forEach(p -> files.add(p.toFile()));
        return files;
    }

    public static FileInputStream getFileInDirectoryByName(String directory, String name) {
        try {
            return new FileInputStream(String.format("%s\\%s", directory, name));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static String getStringContent(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }

    public static boolean IsDirectoryExist(String directoryPath) {
        return true;
    }

    public static void freeDirectory(String directory) throws IOException {
        List<File> files = getAllFilesInDirectory(directory);
        files.forEach(file -> {
            if (!file.isDirectory())
                file.delete();
        });
    }

}
