package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final Map<String, Map<String, String>> translations;
    private final List<String> countries;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        translations = new HashMap<>();
        countries = new ArrayList<>();

        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);
                String countryCode = countryObject.getString("countryCode");
                countries.add(countryCode);

                Map<String, String> languageMap = new HashMap<>();
                JSONObject translationsObject = countryObject.getJSONObject("translations");

                for (String languageCode : translationsObject.keySet()) {
                    String translation = translationsObject.getString(languageCode);
                    languageMap.put(languageCode, translation);
                }
                translations.put(countryCode, languageMap);
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        if (translations.containsKey(country)) {
            return new ArrayList<>(translations.get(country).keySet()); // Return mutable copy of language codes
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countries);
    }

    @Override
    public String translate(String country, String language) {
        if (translations.containsKey(country) && translations.get(country).containsKey(language)) {
            return translations.get(country).get(language);
        }
        return null;
    }
}
