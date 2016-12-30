package com.uni.algos.core;

import java.util.ArrayList;
import java.util.List;

public class InMemoryValidFastaSequenceCharactersProvider implements ValidFastaSequenceCharactersProvider {

    private final ArrayList<String> validCharacters = new ArrayList<String>();

    public InMemoryValidFastaSequenceCharactersProvider() {
        AddValidCharacters();
    }

    private void AddValidCharacters() {
        validCharacters.add("Q");
        validCharacters.add("W");
        validCharacters.add("E");
        validCharacters.add("R");
        validCharacters.add("T");
        validCharacters.add("Y");
        validCharacters.add("U");
        validCharacters.add("I");
        validCharacters.add("O");
        validCharacters.add("P");
        validCharacters.add("A");
        validCharacters.add("S");
        validCharacters.add("D");
        validCharacters.add("F");
        validCharacters.add("G");
        validCharacters.add("H");
        validCharacters.add("J");
        validCharacters.add("K");
        validCharacters.add("L");
        validCharacters.add("Z");
        validCharacters.add("X");
        validCharacters.add("C");
        validCharacters.add("V");
        validCharacters.add("B");
        validCharacters.add("N");
        validCharacters.add("M");
    }

    @Override
    public List<String> getCharacters() {
        return validCharacters;
    }
}
