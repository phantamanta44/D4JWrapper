package io.github.phantamanta44.discord4j.util.io;

import com.github.fge.lambdas.Throwing;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import io.github.phantamanta44.discord4j.core.Discord;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.Deferreds;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.UnaryDeferred;
import io.github.phantamanta44.discord4j.util.math.MathUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class IOUtils {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    private static final CloseableHttpClient HTTP_CLI = HttpClients.custom()
            .setUserAgent(USER_AGENT)
            .build();
    
    public static IUnaryPromise<String> requestXml(String url, String... headers) {
        if (headers.length % 2 != 0)
            throw new IllegalArgumentException("Headers must come in name-value pairs!");
        UnaryDeferred<String> def = new UnaryDeferred<>();
        Discord.executorPool().submit(() -> {
            try {
                GetRequest req = Unirest.get(url);
                for (int i = 0; i < headers.length; i += 2)
                    req.header(headers[i], headers[i + 1]);
                HttpResponse<String> resp = req.asString();
                int status = resp.getStatus();
                if (MathUtils.bounds(status, 400, 600))
                    def.reject(new HttpException(status));
                def.resolve(resp.getBody());
            } catch (Exception e) {
                def.reject(e);
            }
        });
        return def.promise();
    }

    public static IUnaryPromise<JsonElement> requestJson(String url, String... headers) {
        return requestXml(url, headers).map(new JsonParser()::parse);
    }

    public static IUnaryPromise<List<String>> readFile(File file) {
        return Deferreds.call(Throwing.supplier(() -> Files.readAllLines(file.toPath()))).promise();
    }
    
}
