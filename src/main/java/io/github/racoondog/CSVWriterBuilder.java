package io.github.racoondog;

import io.github.racoondog.impl.CSVWriterImpl;

import java.io.Writer;

public class CSVWriterBuilder {
    public static final int DEFAULT_STRINGBUILDER_SIZE = 1024;

    public char separatorChar = ',';
    public char quoteChar = '"';
    public char escapeChar = '"';
    public String lineEnd = "\n";
    public StringBuilder stringBuilder;

    public boolean quoteEverything = false;

    public CSVWriterBuilder() {}

    public static CSVWriterBuilder create() {
        return new CSVWriterBuilder();
    }

    public CSVWriterBuilder separatorChar(char newSeparatorChar) {
        this.separatorChar = newSeparatorChar;
        return this;
    }

    public CSVWriterBuilder quoteChar(char newQuoteChar) {
        this.quoteChar = newQuoteChar;
        return this;
    }

    public CSVWriterBuilder escapeChar(char newEscapeChar) {
        this.escapeChar = newEscapeChar;
        return this;
    }

    public CSVWriterBuilder lineEnd(String newLineEnd) {
        this.lineEnd = lineEnd;
        return this;
    }

    public CSVWriterBuilder quoteEverything(boolean quoteEverythingToggle) {
        this.quoteEverything = quoteEverythingToggle;
        return this;
    }

    public CSVWriterBuilder bindStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
        return this;
    }

    public DynamicCSVWriter buildDynamic(Writer writer) {
        if (stringBuilder == null) stringBuilder = new StringBuilder(DEFAULT_STRINGBUILDER_SIZE);
        return new CSVWriterImpl(writer, separatorChar, quoteChar, escapeChar, lineEnd, stringBuilder, quoteEverything);
    }

    public StaticCSVWriter buildStatic(Writer writer) {
        if (stringBuilder == null) stringBuilder = new StringBuilder(DEFAULT_STRINGBUILDER_SIZE);
        return new CSVWriterImpl(writer, separatorChar, quoteChar, escapeChar, lineEnd, stringBuilder, quoteEverything);
    }
}
