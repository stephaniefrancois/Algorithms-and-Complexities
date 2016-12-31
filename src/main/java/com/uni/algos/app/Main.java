package com.uni.algos.app;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.parsers.InvalidSequenceIdException;
import com.uni.algos.core.parsers.SequenceIdNotFoundException;
import com.uni.algos.core.search.InvalidSearchCriteriaException;
import com.uni.algos.core.storage.DataFileNotFoundException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InvalidArgumentException,
            InvalidSearchCriteriaException,
            InvalidSequenceException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            InvalidSequenceIdException,
            IOException {
        // write your code here
        DashboardUI ui =
                CompositionRoot.createRootComponent(System.in, System.out,
                        "src/main/resources/uniprot_sprot.fasta");
//        manager.findAndDisplaySequence("Q6GZX");
//        manager.findAndDisplaySequence("A0A0U5AUY6");

        ui.displayMainMenu();
    }
}


