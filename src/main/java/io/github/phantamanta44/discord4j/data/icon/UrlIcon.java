package io.github.phantamanta44.discord4j.data.icon;

import io.github.phantamanta44.discord4j.util.ImageUtils;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.Deferreds;
import io.github.phantamanta44.discord4j.util.concurrent.deferred.IUnaryPromise;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;

public class UrlIcon implements IIcon {

    private final String url;
    private BufferedImage image;

    public UrlIcon(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public BufferedImage getImage() {
            if (image == null) {
                try {
                    refreshImage();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return ImageUtils.copy(image);
    }

    public IUnaryPromise<BufferedImage> getImageAsync() {
        return Deferreds.call(this::getImage).promise();
    }

    public void refreshImage() throws IOException {
        image = ImageIO.read(URI.create(url).toURL());
    }

}
