package com.uni.algos.core;

public class DescriptionBasedSequenceIdParser implements FastaSequenceIdParser {
    private final String separator = "|";
    private final int requiredLength = 6;

    @Override
    public String parseSequenceId(String description) throws SequenceIdNotFoundException, InvalidSequenceIdException {
        String id = tryExtractSequenceId(description);
        validateSequenceIdAndThrowIfNotValid(id);
        return id;
    }

    private String tryExtractSequenceId(String description) throws SequenceIdNotFoundException {
        int openingSeparator = description.indexOf(separator);
        if (openingSeparator == -1) {
            throw new SequenceIdNotFoundException(description);
        }
        int idStartsAt = openingSeparator + 1;
        int closingSeparator = description.indexOf(separator, idStartsAt);
        if (closingSeparator <= openingSeparator) {
            throw new SequenceIdNotFoundException(description);
        }
        return description.substring(idStartsAt, closingSeparator);
    }

    private void validateSequenceIdAndThrowIfNotValid(String id) throws InvalidSequenceIdException {
        if (invalidLength(id)) {
            throw new InvalidSequenceIdException(id);
        }

        for (char character : id.toCharArray()) {
            if (notSupportedCharacter(character)) {
                throw new InvalidSequenceIdException(id);
            }
        }
    }

    private boolean invalidLength(String id) {
        return id.length() != requiredLength;
    }

    private boolean notSupportedCharacter(char character) {
        return Character.isLetterOrDigit(character) == false;
    }
}
