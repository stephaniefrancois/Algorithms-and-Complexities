package com.uni.algos.core.storage;

import com.uni.algos.core.domain.FastaSequence;

import java.util.List;

public interface DataStore {
    void saveData(List<FastaSequence> sequences);
}

