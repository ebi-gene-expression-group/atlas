package uk.ac.ebi.atlas.model.resource;

import com.google.common.base.Function;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class ExternalImage extends ExternalResource<Function<OutputStream, Void>> {

    //I can go in a constructor if you need me to be something different
    private static final String formatName = "png";

    ExternalImage(ResourceType type, Path path, String uri) {
        super(type,path, uri);
    }

    @Override
    public Function<OutputStream, Void> get() {
        return new Function<OutputStream, Void>() {
            @Nullable
            @Override
            public Void apply(@Nullable OutputStream outputStream) {
                try(InputStream inputStream = Files.newInputStream(path)){
                    ImageIO.write(ImageIO.read(inputStream), formatName, outputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        };
    }
}
