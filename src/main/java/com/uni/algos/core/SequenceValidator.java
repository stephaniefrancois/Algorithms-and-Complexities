package com.uni.algos.core;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.FastaDataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SequenceValidator implements FastaDataProvider {

    private final FastaDataProvider fastaDataProvider;
    private final ValidFastaSequenceCharactersProvider validCharactersProvider;

    public SequenceValidator(FastaDataProvider fastaDataProvider,
                             ValidFastaSequenceCharactersProvider validCharactersProvider) throws InvalidArgumentException {
        if (fastaDataProvider == null) {
            throw new InvalidArgumentException(new String[]{"'fastaDataProvider' must be supplied"});
        }
        if (validCharactersProvider == null) {
            throw new InvalidArgumentException(new String[]{"'validCharactersProvider' must be supplied"});
        }
        this.fastaDataProvider = fastaDataProvider;
        this.validCharactersProvider = validCharactersProvider;
    }

    @Override
    public List<FastaSequence> getSequences() throws IOException,
            DataFileNotFoundException,
            SequenceIdNotFoundException,
            InvalidSequenceIdException,
            InvalidSequenceException {

        List<String> errors = new ArrayList<>();
        List<FastaSequence> sequencesToValidate = fastaDataProvider.getSequences();
        List<String> validCharacters = validCharactersProvider.getCharacters();

        for (FastaSequence fastaSequence : sequencesToValidate) {
            ValidateSequence(errors, validCharacters, fastaSequence);
        }

        if (ErrorsFound(errors)) {
            throw new InvalidSequenceException(errors);
        }

        return fastaDataProvider.getSequences();
    }

    private void ValidateSequence(List<String> errors, List<String> validCharacters,
                                  FastaSequence fastaSequence) {
        String sequence = fastaSequence.getSequence();

        if (SequenceIsEmpty(sequence)) {
            errors.add("Sequence with ID: '" + fastaSequence.getSequenceId() +
                    "' empty! That is NOT a valid sequence.");
        } else {
            List<String> invalidCharacters = FindInvalidCharacters(validCharacters, sequence);

            if (ErrorsFound(invalidCharacters)) {
                errors.add("Sequence with ID: '" + fastaSequence.getSequenceId() +
                        "' have '" + invalidCharacters.size() + "' invalid characters: " +
                        String.join(",", invalidCharacters) + ".");
            }
        }
    }

    private boolean SequenceIsEmpty(String sequence) {
        return sequence == null || sequence.isEmpty();
    }

    private List<String> FindInvalidCharacters(List<String> validCharacters, String sequence) {
        List<String> invalidCharacters = new ArrayList<>();

        for (char character : sequence.toCharArray()) {
            String characterToValidate = Character.toString(character);
            if (!validCharacters.contains(characterToValidate)) {
                invalidCharacters.add(characterToValidate);
            }
        }
        return invalidCharacters;
    }

    private boolean ErrorsFound(List<String> errors) {
        return errors.size() > 0;
    }
}
