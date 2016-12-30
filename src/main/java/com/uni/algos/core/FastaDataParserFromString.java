package com.uni.algos.core;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.DataProvider;
import com.uni.algos.core.storage.FastaDataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FastaDataParserFromString implements FastaDataProvider {

    private final String FIRST_DESCRIPTION_ONLY_PREFIX = ";";
    private final String ANY_DESCRIPTION_PREFIX = ">";

    private final DataProvider dataProvider;
    private final FastaSequenceIdParser sequenceIdParser;

    private boolean weBuildingSequence = false;
    private boolean lastSequenceDescriptionIdentifierIsCorrect = false;
    private String description = null;
    private StringBuilder sequenceBuilder = null;

    public FastaDataParserFromString(DataProvider dataProvider, FastaSequenceIdParser sequenceIdParser) throws InvalidArgumentException {
        if (dataProvider == null) {
            throw new InvalidArgumentException(new String[]{"'dataProvider' must be supplied"});
        }
        if (sequenceIdParser == null) {
            throw new InvalidArgumentException(new String[]{"'sequenceIdParser' must be supplied"});
        }
        this.dataProvider = dataProvider;
        this.sequenceIdParser = sequenceIdParser;
    }

    @Override
    public List<FastaSequence> getSequences() throws IOException, DataFileNotFoundException, SequenceIdNotFoundException, InvalidSequenceIdException {
        ArrayList<FastaSequence> sequences = new ArrayList<FastaSequence>();
        List<String> dataToParse = dataProvider.getData();

        weBuildingSequence = false;
        lastSequenceDescriptionIdentifierIsCorrect = false;
        description = null;
        sequenceBuilder = new StringBuilder();

        Iterator<String> iterator = dataToParse.iterator();

        if (HaveData(iterator)) {
            String dataItem = iterator.next();
            if (FirstDescriptionFound(dataItem)) {
                StartSequence(dataItem);
            }
        }

        while (HaveData(iterator)) {
            String dataItem = iterator.next();

            if (DescriptionFound(dataItem)) {
                if (weBuildingSequence) {
                    FinishSequence(sequences);
                }
                StartSequence(dataItem);
            } else if (IncorrectDescriptionPrefixFound(dataItem)) {
                lastSequenceDescriptionIdentifierIsCorrect = false;
            } else if (lastSequenceDescriptionIdentifierIsCorrect) {
                sequenceBuilder.append(dataItem);
            }
        }

        if (weBuildingSequence) {
            FinishSequence(sequences);
        }

        return sequences;
    }

    private boolean IncorrectDescriptionPrefixFound(String dataItem) {
        return dataItem.startsWith(FIRST_DESCRIPTION_ONLY_PREFIX);
    }

    private void FinishSequence(ArrayList<FastaSequence> sequences) throws SequenceIdNotFoundException, InvalidSequenceIdException {
        String sequenceId = sequenceIdParser.parseSequenceId(description);
        sequences.add(new FastaSequence(sequenceId, description, sequenceBuilder.toString()));
    }

    private boolean HaveData(Iterator<String> iterator) {
        return iterator.hasNext();
    }

    private boolean DescriptionFound(String dataItem) {
        return dataItem.startsWith(ANY_DESCRIPTION_PREFIX);
    }

    private boolean FirstDescriptionFound(String dataItem) {
        return dataItem.startsWith(FIRST_DESCRIPTION_ONLY_PREFIX) ||
                DescriptionFound(dataItem);
    }

    private void StartSequence(String dataItem) {
        sequenceBuilder = new StringBuilder();
        description = dataItem.substring(1);
        weBuildingSequence = true;
        lastSequenceDescriptionIdentifierIsCorrect = true;
    }
}
