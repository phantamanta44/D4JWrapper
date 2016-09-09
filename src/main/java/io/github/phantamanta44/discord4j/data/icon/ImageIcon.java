package io.github.phantamanta44.discord4j.data.icon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageIcon implements IIcon {

    private BufferedImage image;

    public ImageIcon(File file) throws IOException {
        this(ImageIO.read(file));
    }

    public ImageIcon(BufferedImage img) {
        this.image = img;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

}
