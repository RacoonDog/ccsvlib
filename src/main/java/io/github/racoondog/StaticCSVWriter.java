package io.github.racoondog;

public interface StaticCSVWriter extends CSVWriter {
    void writeCharSeqs(CharSequence... charSeqs);
    void writeObjects(Object... objects);
    void writeCharSeqs(Iterable<CharSequence> charSeqs);
    void writeObjects(Iterable<Object> objects);

    void writeCharSeqArrItr(Iterable<CharSequence[]> lines);
    void writeObjectArrItr(Iterable<Object[]> lines);
    void writeCharSeqArrArr(CharSequence[]... lines);
    void writeObjectArrArr(Object[]... lines);
    void writeCharSeqItrItr(Iterable<Iterable<CharSequence>> lines);
    void writeObjectItrItr(Iterable<Iterable<Object>> lines);
    void writeCharSeqItrArr(Iterable<CharSequence>... lines);
    void writeObjectItrArr(Iterable<Object>... lines);
}
