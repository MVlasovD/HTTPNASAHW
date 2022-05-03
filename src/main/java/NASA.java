/**
 * @author VMN
 */

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class NASA {

    public static ObjectMapper mapper = new ObjectMapper();
    static String nameFile;

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        String token = "TB1OlXx6jdlG0fE9B9rn0dZfqwNLuBui632gtmAD";
        HttpGet request = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=" + token);
        CloseableHttpResponse response = httpClient.execute(request);
        Data data = mapper.readValue(response.getEntity().getContent(), new TypeReference<Data>() {
        });
        String uri = data.getUrl();
        HttpGet request2 = new HttpGet(uri);
        CloseableHttpResponse response2 = httpClient.execute(request2);
        String body = new String(response2.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        File saveFile = new File("C:\\Users\\User\\IdeaProjects\\HTTPNASAHW\\src\\tmp");
        String[] nameFileArr = uri.split("/");
        for (int i = nameFileArr.length -1; i < nameFileArr.length; i++){
            nameFile = nameFileArr[i];
        }

        URL url = new URL(uri);
        File file = new File(saveFile, nameFile);
        file.createNewFile();
        FileUtils.copyURLToFile(url, file);
    }
}

