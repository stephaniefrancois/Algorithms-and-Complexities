package com.uni.algos.app;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class DashboardUI {

    private final Scanner userInput;
    private final PrintStream out;
    private final SearchUI searchUi;
    private List<MenuChoice> menuChoices;

    public DashboardUI(InputStream in, PrintStream out, SearchUI searchUi) throws InvalidArgumentException {

        if (in == null) {
            throw new InvalidArgumentException(new String[]{"'in' must be supplied"});
        }
        if (out == null) {
            throw new InvalidArgumentException(new String[]{"'out' must be supplied"});
        }
        if (searchUi == null) {
            throw new InvalidArgumentException(new String[]{"'searchUi' must be supplied"});
        }

        this.userInput = new Scanner(System.in);
        this.out = out;
        menuChoices = PopulateMenuChoices();
        this.searchUi = searchUi;
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private List<MenuChoice> PopulateMenuChoices() {
        List<MenuChoice> menu = new ArrayList<>();
        menu.add(new MenuChoice(1, "Search for a sequence"));
        menu.add(new MenuChoice(2, "Export all Sequences with minimum length to a FASTA file"));
        menu.add(new MenuChoice(3, "Sort all sequences by OS and SwissProtId and export to a CSV file"));
        menu.add(new MenuChoice(4, "To exit the application"));
        return menu;
    }

    public void displayMainMenu() throws IOException {

        out.println("Please type in NUMBER for the menu item you wish to select:");
        for (MenuChoice menuChoice : menuChoices) {
            out.println("   " + menuChoice.getNumber() + ". " + menuChoice.getDescription());
        }

        String userInput = this.userInput.nextLine();
        if (validMenuSelectionMade(userInput)) {
            MenuChoice selectedMenuItem = getSelectedMenuItemIndex(userInput);
            executeActionForMenuSelection(selectedMenuItem);
        } else {
            out.println("'" + userInput + "' is NOT a valid menu item! Please try again.");
        }
        displayMainMenu();
    }

    private void executeActionForMenuSelection(MenuChoice selectedMenuItem) throws IOException {
        switch (selectedMenuItem.getNumber()) {
            case 1: {
                searchUi.displaySearch();
                break;
            }
            case 2: {
                exportSequencesToFastaFile();
                break;
            }
            case 3: {
                exportSortedSequencesToCsv();
                break;
            }
            case 4: {
                out.println("You have selected to exit the application. Bye bye.");
                System.exit(0);
                break;
            }
        }
    }

    private MenuChoice getSelectedMenuItemIndex(String userInput) {
        List<MenuChoice> matchingChoices = findMatchingMenuChoices(userInput);
        return matchingChoices.get(0);
    }

    private boolean validMenuSelectionMade(String menuSelection) {
        if (isNumeric(menuSelection)) {
            List<MenuChoice> matchingChoices = findMatchingMenuChoices(menuSelection);

            if (matchingChoices.size() > 0) {
                return true;
            }
        }
        return false;
    }

    private List<MenuChoice> findMatchingMenuChoices(String menuSelection) {
        int userSelection = Integer.parseInt(menuSelection);
        return menuChoices.stream()
                .filter(c -> c.getNumber() == userSelection)
                .collect(Collectors.toList());
    }

    private void exportSequencesToFastaFile() {
        out.println("Fasta");
    }

    private void exportSortedSequencesToCsv() {
        out.println("CSV");
    }

    private class MenuChoice {
        private final int number;
        private final String description;

        public MenuChoice(int number, String description) {
            this.number = number;
            this.description = description;
        }

        public int getNumber() {
            return number;
        }

        public String getDescription() {
            return description;
        }
    }
}
