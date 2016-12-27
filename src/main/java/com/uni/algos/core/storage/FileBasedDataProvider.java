package com.uni.algos.core.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileBasedDataProvider implements DataProvider {

    private final String path;

    public FileBasedDataProvider(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("'PATH' must always be supplied!");
        }
        this.path = path;
    }

    @Override
    public List<String> getData() throws IOException, DataFileNotFoundException {
        final File file = new File(path);
        final ArrayList<String> data = new ArrayList<String>();

        if (!file.exists() || !file.canRead() || !file.isFile()) {
            throw new DataFileNotFoundException("Unable to find readable file: " + file.getAbsolutePath());
        }

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(path));

            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.close();
        }

        return data;
    }
}
