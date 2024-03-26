package org.common.parsers;

public interface ParserInterface <T>{
    T toObject(String []line);
    String[] toStringArray(T object);
}
