package com.uni.algos.core.domain;

public final class FastaSequence {
    private final String description;
    private final String sequence;

    public FastaSequence(String description, String sequence) {
        this.description = description;
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public String getSequence() {
        return sequence;
    }
}
