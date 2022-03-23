import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import data.NASAData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void jsonParsing() throws Exception {
        JsonArray jsonArray = JsonParser.parseReader(
                        new FileReader(
                                new File(ClassLoader.getSystemResource("TestData.txt").toURI()),
                                StandardCharsets.UTF_8))
                .getAsJsonArray();
        List<NASAData> actual = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray)
            actual.add(Main.jsonParsing(jsonElement.toString()));

        List<NASAData> expected = new ArrayList<>(List.of(
                new NASAData(null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null),
                new NASAData("copyright",
                        null,
                        "explanation",
                        null,
                        "media_type",
                        "service_version",
                        "title",
                        null),
                new NASAData("copyright",
                        LocalDate.of(2022, 3, 22),
                        "explanation",
                        new URL("http://snetology.ru/"),
                        "media_type",
                        "service_version",
                        "title",
                        new URL("http://snetology.ru/"))));

        assertEquals(expected, actual);
    }
}
