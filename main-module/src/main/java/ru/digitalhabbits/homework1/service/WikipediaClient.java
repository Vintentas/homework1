package ru.digitalhabbits.homework1.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.utils.URIBuilder;
import javax.annotation.Nonnull;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Map;
import java.util.Set;

public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);
        // TODO: NotImplemented -> Done
        return getContent(uri);
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String getContent(URI uri) {
        String extract = "";

        try {
            //Открыть соединение
            HttpURLConnection request = (HttpURLConnection) uri.toURL().openConnection();

            //Получить файл и распарсить
            Set<Map.Entry<String, JsonElement>> pagesSet =
                    JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()))
                            .getAsJsonObject()
                            .getAsJsonObject("query")
                            .getAsJsonObject("pages")
                            .entrySet();

            //Достать нужные строки
            for (Map.Entry<String, JsonElement> entry : pagesSet) {
                extract = entry
                        .getValue()
                        .getAsJsonObject()
                        .get("extract")
                        .getAsString().concat(extract);
            }
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return extract;
    }
}
