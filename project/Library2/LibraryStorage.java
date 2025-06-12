package project.Library2;

import java.io.IOException;

public interface LibraryStorage {
    void saveLibrary(Library library) throws IOException;
    Library loadLibrary() throws IOException;
}
