package io.github.phantamanta44.discord4j.util.io;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

import com.github.fge.lambdas.Throwing;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.Deferreds;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import io.github.phantamanta44.discord4j.core.Discord;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.UnaryDeferred;
import io.github.phantamanta44.discord4j.util.math.MathUtils;

public class IOUtils {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    private static final CloseableHttpClient HTTP_CLI = HttpClients.custom()
            .setUserAgent(USER_AGENT)
            .build();
    
    public static IUnaryPromise<String> requestXml(String uri, String... headers) {
        return requestXml(URI.create(uri.replaceAll("\\s", "+")), headers);
    }
    
    public static IUnaryPromise<String> requestXml(URI uri, String... headers) {
    	if (headers.length % 2 != 0)
            throw new IllegalArgumentException("Headers must come in name-value pairs!");
    	UnaryDeferred<String> def = new UnaryDeferred<>();
    	Discord.executorPool().submit(() -> {
	        HttpUriRequest req = new HttpGet(uri);
	        for (int i = 0; i < headers.length; i+= 2)
	            req.addHeader(headers[i], headers[i + 1]);
	        try (CloseableHttpResponse resp = HTTP_CLI.execute(req)) {
	            int status = resp.getStatusLine().getStatusCode();
	            if (MathUtils.bounds(status, 400, 600))
	            	def.reject(new HttpException(status));
	            def.resolve(EntityUtils.toString(resp.getEntity()));
	        } catch (Exception e) {
	        	def.reject(e);
	        }
    	});
    	return def.promise();
    }

    public static IUnaryPromise<List<String>> readFile(File file) {
        return Deferreds.call(Throwing.supplier(() -> Files.readAllLines(file.toPath()))).promise();
    }
    
}
