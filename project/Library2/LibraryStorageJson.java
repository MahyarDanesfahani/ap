package project.Library2;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class LibraryStorageJson implements LibraryStorage {
    private final String filePath;

    public LibraryStorageJson(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveLibrary(Library library) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filePath)){
            gson.toJson(library,writer);
        }
    }

    @Override
    public Library loadLibrary() throws IOException {
        File file = new File(filePath);
        if (!file.exists()){
            return new Library();
        }
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)){
            Type type = new TypeToken<Library>() {}.getType();
            return gson.fromJson(reader,type);
        }
    }
}
