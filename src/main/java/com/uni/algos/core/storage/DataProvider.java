package com.uni.algos.core.storage;

import java.io.IOException;
import java.util.List;

public interface DataProvider {
    List<String> getData() throws IOException, DataFileNotFoundException;
}
