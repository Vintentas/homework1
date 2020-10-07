package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {

    private static final String REGEX = "\\b[a-zA-Z][a-zA-Z0-9\\/.-]*\\b";

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: NotImplemented -> done
        String changedText = text.replaceAll("\\\\n", "\n").toLowerCase();
        return countLines(changedText) + ";" + countWords(changedText) + ";" + countLetters(changedText);
    }

    private String countLines(@Nonnull String text) {
        int count = text.split("\n").length;
        return Integer.toString(count);
    }

    private String countWords(@Nonnull String text) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(text);

        int count = 0;
        while (m.find()) {
            count++;
        }
        return Integer.toString(count);
    }

    private String countLetters(@Nonnull String text) {
        int count = text.length();
        return Integer.toString(count);
    }
}
