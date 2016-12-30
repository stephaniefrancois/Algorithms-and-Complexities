package com.uni.algos.core.domain;

public final class FastaSequence {
    private final String sequenceId;
    private final String description;
    private final String sequence;

    public FastaSequence(String sequenceId, String description, String sequence) {
        this.sequenceId = sequenceId;
        this.description = description;
        this.sequence = sequence;
    }

    public String getSequenceId() {
        return sequenceId;
    }
    public String getDescription() {
        return description;
    }
    public String getSequence() {
        return sequence;
    }
}
