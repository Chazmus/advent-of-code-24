package client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AdventOfCodeClient {

    public void getInput(String day) {
        // TODO: get session cookie
        var sessionCookie = "asdf";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://adventofcode.com/2024/day/1/input"))
                .GET()
                .setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp," +
                        "image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .setHeader("cookie", sessionCookie)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Write file into resources
            var filePath = Paths.get("src/main/resources/" + day);
            Files.writeString(filePath, response.body(), StandardOpenOption.SYNC, StandardOpenOption.CREATE);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
