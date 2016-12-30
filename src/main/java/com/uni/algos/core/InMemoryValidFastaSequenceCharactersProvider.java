package com.uni.algos.core;

import java.util.Arrays;
import java.util.List;

public class InMemoryValidFastaSequenceCharactersProvider implements ValidFastaSequenceCharactersProvider {

    private final List<String> validCharacters;

    public InMemoryValidFastaSequenceCharactersProvider() {
        validCharacters = AddValidCharacters();
    }

    private List<String> AddValidCharacters() {
        String[] characters = {
                "Q", "q",
                "W", "w",
                "E", "e",
                "R", "r",
                "T", "t",
                "Y", "y",
                "U", "u",
                "I", "i",
                "O", "o",
                "P", "p",
                "A", "a",
                "S", "s",
                "D", "D",
                "F", "f",
                "G", "g",
                "H", "h",
                "J", "j",
                "K", "k",
                "L", "l",
                "Z", "z",
                "X", "x",
                "C", "c",
                "V", "v",
                "B", "b",
                "N", "n",
                "M", "m",
                "-", "*"};

        return Arrays.asList(characters);
    }

    @Override
    public List<String> getCharacters() {
        return validCharacters;
    }
}

