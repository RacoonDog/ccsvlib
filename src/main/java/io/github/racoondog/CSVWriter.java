package io.github.racoondog;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public interface CSVWriter extends Closeable, Flushable {
    Writer getWriter();
    char getSeparatorChar();
    char getQuoteChar();
    char getEscapeChar();
    String getLineEnd();

    boolean hasException();
    IOException getException();

    static CSVWriterBuilder builder() {
        return new CSVWriterBuilder();
    }
}
