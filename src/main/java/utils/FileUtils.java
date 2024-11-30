package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    public static List<String> readFileFromResources(String fileName)  {
        // Get the file from the resources directory
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        Path filePath = null;
        try {
            filePath = Paths.get(classLoader.getResource("inputs" + File.separator + fileName).toURI());
            return Files.readAllLines(filePath);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String readFile(String file) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.lineSeparator();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();
        }
    }
}
