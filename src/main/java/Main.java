import com.google.gson.*;
import data.NASAData;
import net.GETRequest;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static String getJsonResponse(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    private static String nonNullJsonPrimitive(JsonPrimitive primitive) {
        if (primitive != null) return primitive.getAsString();
        return null;
    }

    public static NASAData jsonParsing(String json) {
        JsonDeserializer<LocalDate> localDateDeserializer = (jsonElement, type, jsonDeserializationContext) -> {
            String date = nonNullJsonPrimitive((JsonPrimitive) jsonElement);
            if (date != null) {
                try {
                    return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
            return null;
        };


        JsonDeserializer<URL> urlDeserializer = (jsonElement, type, jsonDeserializationContext) -> {
            String url = nonNullJsonPrimitive((JsonPrimitive) jsonElement);
            if (url != null) {
                try {
                    return new URL(url);
                } catch (MalformedURLException e) {
                    return null;
                }
            }
            return null;
        };

        JsonDeserializer<NASAData> nasaDataDeserializer = (jsonElement, type, jsonDeserializationContext) -> {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            NASAData nasaData = new NASAData();
            nasaData.setCopyright(nonNullJsonPrimitive(jsonObject.getAsJsonPrimitive("copyright")));
            nasaData.setDate(jsonDeserializationContext.deserialize(jsonObject.getAsJsonPrimitive("date"), LocalDate.class));
            nasaData.setExplanation(nonNullJsonPrimitive(jsonObject.getAsJsonPrimitive("explanation")));
            nasaData.setHdurl(jsonDeserializationContext.deserialize(jsonObject.getAsJsonPrimitive("hdurl"), URL.class));
            nasaData.setMedia_type(nonNullJsonPrimitive(jsonObject.getAsJsonPrimitive("media_type")));
            nasaData.setService_version(nonNullJsonPrimitive(jsonObject.getAsJsonPrimitive("service_version")));
            nasaData.setTitle(nonNullJsonPrimitive(jsonObject.getAsJsonPrimitive("title")));
            nasaData.setUrl(jsonDeserializationContext.deserialize(jsonObject.getAsJsonPrimitive("url"), URL.class));

            return nasaData;
        };

        return new GsonBuilder()
                .registerTypeAdapter(NASAData.class, nasaDataDeserializer)
                .registerTypeAdapter(LocalDate.class, localDateDeserializer)
                .registerTypeAdapter(URL.class, urlDeserializer)
                .create()
                .fromJson(json, NASAData.class);
    }

    public static String getFilename(URL url) {
        return new File(url.getFile()).getName();
    }

    public static void saveImageByURL(InputStream inputStream, File file) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, false))) {
            int byteData;
            while ((byteData = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(byteData);
                bufferedOutputStream.flush();
            }
        }
    }

    public static void main(String[] args) {
        StringBuilder urlBuilder = new StringBuilder("https://api.nasa.gov/planetary/apod?api_key=");
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите API-ключ для доступа к данным НАСА: ");
            urlBuilder.append(scanner.nextLine());
            try {
                GETRequest requestToNASA = new GETRequest(urlBuilder.toString());
                if (requestToNASA.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    NASAData nasaData = jsonParsing(getJsonResponse(requestToNASA.getInputStream()));
                    URL imageURL = nasaData.getUrl();
                    String filename = getFilename(imageURL);
                    System.out.print("Укажите путь для сохранения файла \"" + filename + "\": ");
                    File filepath = new File(scanner.nextLine(), filename);
                    GETRequest requestToImage = new GETRequest(imageURL);
                    if (requestToImage.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                        saveImageByURL(requestToImage.getInputStream(), filepath);
                    }
                }
            } catch (Exception e) {
                System.err.println("Ошибка получения данных, попробуйте повторить запрос позже.");
            }
        }
    }
}
