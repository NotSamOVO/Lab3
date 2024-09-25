package org.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            if ("quit".equals(country)) {
                break;
            }
            String language = promptForLanguage(translator, country);
            if ("quit".equals(language)) {
                break;
            }
            System.out.println(country + " in " + language + " is " + translator.translate(country, language));
            System.out.println("Press enter to continue or type 'quit' to exit.");
            Scanner s = new Scanner(System.in);
            if ("quit".equals(s.nextLine())) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        List<String> sortedCountries = new ArrayList<>(countries);
        for (int i = 0; i < sortedCountries.size() - 1; i++) {
            for (int j = 0; j < sortedCountries.size() - i - 1; j++) {
                if (sortedCountries.get(j).compareTo(sortedCountries.get(j + 1)) > 0) {
                    String temp = sortedCountries.get(j);
                    sortedCountries.set(j, sortedCountries.get(j + 1));
                    sortedCountries.set(j + 1, temp);
                }
            }
        }
        for (String country : sortedCountries) {
            System.out.println(country);
        }

        System.out.println("Select a country from above:");
        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        List<String> languages = translator.getCountryLanguages(country);
        List<String> sortedLanguages = new ArrayList<>(languages);
        for (int i = 0; i < sortedLanguages.size() - 1; i++) {
            for (int j = 0; j < sortedLanguages.size() - i - 1; j++) {
                if (sortedLanguages.get(j).compareTo(sortedLanguages.get(j + 1)) > 0) {
                    String temp = sortedLanguages.get(j);
                    sortedLanguages.set(j, sortedLanguages.get(j + 1));
                    sortedLanguages.set(j + 1, temp);
                }
            }
        }
        for (String language : sortedLanguages) {
            System.out.println(language);
        }

        System.out.println("Select a language from above:");
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
