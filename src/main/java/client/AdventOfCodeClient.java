package client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdventOfCodeClient {
    private final String ENV_FILE = "src/main/resources/.env";

    public String getInput(String day) {
        try {
            // Define the regex pattern to capture two parts: text and number
            String regex = "^[a-zA-Z]+(\\d+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(day.toLowerCase());
            if (!matcher.find()) {
                throw new RuntimeException("Invalid day format");
            }
            var dayNumber = matcher.group(1);

            var envFile = Files.readAllLines(Paths.get(ENV_FILE));
            var sessionCookie = envFile.get(0);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://adventofcode.com/2024/day/" + dayNumber + "/input"))
                    .GET()
                    .setHeader("cookie", "session=" + sessionCookie)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Can't get the input file");
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
