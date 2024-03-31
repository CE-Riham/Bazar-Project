package org.common.parsers;

/**
 * This is a generic interface for parsing objects of type T.
 * It provides two methods: one for converting a string array to an object of type T, and one for converting an object of type T to a string array.
 * This interface is implemented by specific parsers for each entity type in the application.
 * @param <T> The type of the object to be parsed.
 */
public interface ParserInterface <T>{

    /**
     * This method converts a string array to an object of type T.
     * The implementation of this method depends on the specific parser class.
     * @param line The string array to be converted to an object of type T.
     * @return The object of type T.
     */
    T toObject(String []line);

    /**
     * This method converts an object of type T to a string array.
     * The implementation of this method depends on the specific parser class.
     * @param object The object of type T to be converted to a string array.
     * @return The string array.
     */
    String[] toStringArray(T object);
}