package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    private static final String REGEX = "\\b[a-zA-Z][a-zA-Z0-9\\/.-]*\\b";

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented -> done
        String changedText = text.replaceAll("\\\\n", "\n").toLowerCase();
        TreeMap<String, Integer> treeMap = countWords (changedText);
        return printResult(treeMap);
    }

    private TreeMap<String, Integer> countWords(@Nonnull String text) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(text);

        TreeMap <String, Integer> wordsMap = new TreeMap<>();
        while (m.find()) {
            String word = m.group();
            if (wordsMap.containsKey(word)) {
                wordsMap.put(word, (wordsMap.get(word)+1));
            }
            else {
                wordsMap.put(word, 1);
            }
        }
        return wordsMap;
    }

    private String printResult(TreeMap<String, Integer> treeMap) {
        return treeMap.toString()
                .replaceAll(", ", "\n")
                .replaceAll("=", " ")
                .replaceAll("[{}]", "");
    }
}
