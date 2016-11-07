package uk.ac.ebi.atlas.utils;

import javax.imageio.ImageIO;
import javax.inject.Named;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Named
public class ImageIOUtils {

    public BufferedImage read(InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

    public boolean write(RenderedImage im, String formatName, OutputStream output) throws IOException {
        return ImageIO.write(im, formatName, output);
    }

}