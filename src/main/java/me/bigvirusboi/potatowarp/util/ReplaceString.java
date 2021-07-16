package me.bigvirusboi.potatowarp.util;

public class ReplaceString {
    private final String regex;
    private final Object replacement;

    public ReplaceString(String regex, Object replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    public String getRegex() {
        return regex;
    }

    public Object getReplacement() {
        return replacement;
    }
}
