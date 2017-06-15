package uk.ac.ebi.atlas.model.resource;

import com.esotericsoftware.kryo.io.UnsafeInput;
import com.esotericsoftware.kryo.io.UnsafeOutput;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.text.MessageFormat;
import java.util.Set;

/*
We're never closing the input and output streams.
I think it doesn't matter - e.g. InputStream's .close() does nothing.
 */
public class KryoFile extends AtlasResource<KryoFile.Handle> {
    static final String template = "/serialized_expression/{0}.{1}.kryo";

    public KryoFile(String dataFilesLocation, String experimentAccession, ProfileStreamOptions<?> profileStreamOptions){
        super(Paths.get(dataFilesLocation, MessageFormat.format(template, experimentAccession, profileStreamOptions.serializationShortString())));
    }

    @Override
    public Handle get() {
        return new Handle();
    }

    public class Handle {
        public UnsafeInput read() {
            try {
                return new UnsafeInput(Files.newInputStream(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public UnsafeOutput write() {
            try {
                return new UnsafeOutput(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void addPermissionsAfterWrite(){
            try {
                Set<PosixFilePermission> perms = Files.getPosixFilePermissions(path);
                perms.add(PosixFilePermission.GROUP_WRITE);
                Files.setPosixFilePermissions(path, perms);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }

        public boolean delete() {
            File f = path.toFile();
            if (f.exists()) {
                return f.delete();
            } else {
                return false;
            }
        }
    }
}
