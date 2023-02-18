package io.github.racoondog;

public interface DynamicCSVWriter extends CSVWriter {
    void write(Object obj);
    void write(CharSequence charSeq);
    void write(boolean b);
    void write(int i);
    void write(long l);
    void write(float f);
    void write(double d);

    void endLine();

    void delete();
    void deleteLine();
}
