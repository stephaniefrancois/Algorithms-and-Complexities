package com.uni.algos.core.storage;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.domain.FastaSequence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



// TODO: check for valid characters ONLY
// TODO: cleanup FastaDataParserFromString
// TODO: introduce error reporting mechanism
public class FastaDataParserFromString implements FastaDataProvider {

    private final String FIRST_DESCRIPTION_ONLY_PREFIX = ";";
    private final String ANY_DESCRIPTION_PREFIX = ">";

    private final DataProvider dataProvider;

    public FastaDataParserFromString(DataProvider dataProvider) throws InvalidArgumentException {
        if (dataProvider == null) {
            throw new InvalidArgumentException(new String[]{"'dataProvider' must be supplied"});
        }
        this.dataProvider = dataProvider;
    }

    @Override
    public List<FastaSequence> getSequences() throws IOException, DataFileNotFoundException {
        ArrayList<FastaSequence> sequences = new ArrayList<FastaSequence>();
        List<String> dataToParse = dataProvider.getData();

        boolean areWeBuildingSequence = false;
        boolean firstSequence = true;
        boolean lastSequenceDescriptionIdentifierIsCorrect = false;

        String description = null;
        StringBuilder sequenceBuilder = new StringBuilder();

        for (String dataItem : dataToParse) {
            if ((firstSequence && dataItem.startsWith(FIRST_DESCRIPTION_ONLY_PREFIX)) ||
                    dataItem.startsWith(ANY_DESCRIPTION_PREFIX)) {
                lastSequenceDescriptionIdentifierIsCorrect = true;
                firstSequence = false;

                if (areWeBuildingSequence) {
                    sequences.add(new FastaSequence(description, sequenceBuilder.toString()));
                }
                sequenceBuilder = new StringBuilder();
                description = dataItem.substring(1);
                areWeBuildingSequence = true;
            } else if(!firstSequence && dataItem.startsWith(FIRST_DESCRIPTION_ONLY_PREFIX)) {
                lastSequenceDescriptionIdentifierIsCorrect = false;
            }else if (lastSequenceDescriptionIdentifierIsCorrect){
                sequenceBuilder.append(dataItem);
            }
        }

        if (areWeBuildingSequence) {
            sequences.add(new FastaSequence(description, sequenceBuilder.toString()));
        }

        return sequences;
    }
}
