package uk.ac.ebi.atlas.model.resource;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

public abstract class ExternalImage extends ExternalResource<Function<HttpServletResponse, Void>> {

    //I can go in a constructor if you need me to be something different
    private static final String FORMAT_NAME = "png";

    ExternalImage(ResourceType type, Path path, String uri) {
        super(type, path, uri);
    }

    @Override
    public Function<HttpServletResponse, Void> get() {
        return new Function<HttpServletResponse, Void>() {
            @Nullable
            @Override
            public Void apply(@Nullable HttpServletResponse response) {
                response.setContentType("image/" + FORMAT_NAME);
                try (InputStream inputStream = Files.newInputStream(path);
                     OutputStream out = response.getOutputStream()) {
                    ImageIO.write(ImageIO.read(inputStream), FORMAT_NAME, out);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
    }

}
