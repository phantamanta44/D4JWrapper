package io.github.phantamanta44.discord4j.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtils {

    public static BufferedImage copy(BufferedImage image) {
        return new BufferedImage(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
    }

    public static InputStream inputStream(BufferedImage image) {
        try {
            VirtualByteStack bytes = new VirtualByteStack();
            ImageIO.write(image, "png", bytes.getOutputStream());
            return bytes.flip().getInputStream();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
