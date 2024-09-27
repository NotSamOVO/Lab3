package org.translation;

import java.util.Collections;
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
    private static final String QUIT_COMMAND = "quit";    /**
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
            if (QUIT_COMMAND.equalsIgnoreCase(country)) {
                break;
            }
            String language = promptForLanguage(translator, country);
            if (QUIT_COMMAND.equalsIgnoreCase(language)) {
                break;
            }
            System.out.println(country + " in " + language + " is " + translator.translate(country, language));
            System.out.println("Press enter to continue or type 'quit' to exit.");
            Scanner scanner = new Scanner(System.in);
            String textTyped = scanner.nextLine();
            if (QUIT_COMMAND.equalsIgnoreCase(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        for (int i = 0; i < countries.size(); i++) {
            String countryCode = countries.get(i);
            String countryName = translator.translate(countryCode, "en");
            if (countryName != null) {
                countries.set(i, countryName);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        for (String country : countries) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> languages = translator.getCountryLanguages(country);
        for (int i = 0; i < languages.size(); i++) {
            String languageCode = languages.get(i);
            String languageName = translator.translate(languageCode, "en");
            if (languageName != null) {
                languages.set(i, languageName);
            }
        }

        Collections.sort(languages, String.CASE_INSENSITIVE_ORDER);

        for (String language : languages) {
            System.out.println(language);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
