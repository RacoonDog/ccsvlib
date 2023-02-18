package io.github.racoondog.impl;

import io.github.racoondog.DynamicCSVWriter;
import io.github.racoondog.StaticCSVWriter;

import java.io.IOException;
import java.io.Writer;

public class CSVWriterImpl implements StaticCSVWriter, DynamicCSVWriter {
    private final Writer writer;
    private final char separatorChar;
    private final char quoteChar;
    private final char escapeChar;
    private final String lineEnd;

    private final boolean quoteEverything;
    private final StringBuilder sb;
    private IOException exception;

    public CSVWriterImpl(Writer writer, char separatorChar, char quoteChar, char escapeChar, String lineEnd, StringBuilder stringBuilder, boolean quoteEverything) {
        this.writer = writer;
        this.separatorChar = separatorChar;
        this.quoteChar = quoteChar;
        this.escapeChar = escapeChar;
        this.lineEnd = lineEnd;
        this.sb = stringBuilder;
        this.quoteEverything = quoteEverything;
    }

    @Override
    public Writer getWriter() {
        return writer;
    }

    @Override
    public char getSeparatorChar() {
        return separatorChar;
    }

    @Override
    public char getQuoteChar() {
        return quoteChar;
    }

    @Override
    public char getEscapeChar() {
        return escapeChar;
    }

    @Override
    public String getLineEnd() {
        return lineEnd;
    }

    @Override
    public boolean hasException() {
        return exception != null;
    }

    @Override
    public IOException getException() {
        return exception;
    }

    @Override
    public void write(Object obj) {
        write(String.valueOf(obj));
    }

    @Override
    public void write(boolean b) {
        write(Boolean.toString(b));
    }

    @Override
    public void write(int i) {
        write(Integer.toString(i));
    }

    @Override
    public void write(long l) {
        write(Long.toString(l));
    }

    @Override
    public void write(float f) {
        write(Float.toString(f));
    }

    @Override
    public void write(double d) {
        write(Double.toString(d));
    }

    @Override
    public void endLine() {
        sb.append(lineEnd);
        try {
            writer.write(sb.toString());
        } catch (IOException e) {
            exception = e;
        }

        deleteLine(); //Clear line buffer
    }

    @Override
    public void delete() {
        String separatorCharStr = Character.toString(separatorChar);
        int index = sb.lastIndexOf(separatorCharStr);

        while (index != -1 && sb.charAt(index - 1) != escapeChar) {
            index = sb.lastIndexOf(separatorCharStr, index - 1);
        }

        if (index != -1) sb.setLength(index);
    }

    @Override
    public void deleteLine() {
        sb.setLength(0);
    }

    @Override
    public void writeCharSeqs(CharSequence... charSeqs) {
        for (var charSeq : charSeqs) write(charSeq);
        endLine();
    }

    @Override
    public void writeObjects(Object... objects) {
        for (var object : objects) write(object);
        endLine();
    }

    @Override
    public void writeCharSeqs(Iterable<CharSequence> charSeqs) {
        for (var charSeq : charSeqs) write(charSeq);
        endLine();
    }

    @Override
    public void writeObjects(Iterable<Object> objects) {
        for (var object : objects) write(object);
        endLine();
    }

    @Override
    public void writeCharSeqArrItr(Iterable<CharSequence[]> lines) {
        for (var charSeqs : lines) write(charSeqs);
    }

    @Override
    public void writeObjectArrItr(Iterable<Object[]> lines) {
        for (var objects : lines) write(objects);
    }

    @Override
    public void writeCharSeqArrArr(CharSequence[]... lines) {
        for (var charSeqs : lines) write(charSeqs);
    }

    @Override
    public void writeObjectArrArr(Object[]... lines) {
        for (var objects : lines) write(objects);
    }

    @Override
    public void writeCharSeqItrItr(Iterable<Iterable<CharSequence>> lines) {
        for (var charSeqs : lines) write(charSeqs);
    }

    @Override
    public void writeObjectItrItr(Iterable<Iterable<Object>> lines) {
        for (var objects : lines) write(objects);
    }

    @Override
    public void writeCharSeqItrArr(Iterable<CharSequence>... lines) {
        for (var charSeqs : lines) write(charSeqs);
    }

    @Override
    public void writeObjectItrArr(Iterable<Object>... lines) {
        for (var objects : lines) write(objects);
    }

    /**
     * Ultimately, all writes go through this method
     */
    @Override
    public void write(CharSequence charSeq) {
        if (!sb.isEmpty()) sb.append(separatorChar);
        process(charSeq.toString());
    }

    private void process(String element) {
        int separatorIdx = element.indexOf(separatorChar);
        int quoteIdx = element.indexOf(quoteChar);
        int lineEndIdx = element.indexOf(lineEnd);
        int crIdx = element.indexOf('\r');
        int escapeIdx = element.indexOf(escapeChar);

        boolean containsSpecialCharacters = separatorIdx != -1 || quoteIdx != -1 || lineEndIdx != -1 || crIdx != -1 || escapeIdx != -1;
        boolean shouldQuote = containsSpecialCharacters || quoteEverything;

        if (!shouldQuote) {
            sb.append(element);
            return;
        }

        StringBuilder elementBuilder = new StringBuilder(element.length()).append(quoteChar);

        if (containsSpecialCharacters) {
            char[] charArr = element.toCharArray();
            for (var c : charArr) {
                if (isSpecial(c)) elementBuilder.append(escapeChar);
                elementBuilder.append(c);
            }
        }

        elementBuilder.append(quoteChar);

        sb.append(elementBuilder);
    }

    private boolean isSpecial(char c) {
        return c == quoteChar || c == escapeChar || c == separatorChar;
    }

    @Override
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override
    public void close() throws IOException {
        flush();
        this.writer.close();
    }
}
