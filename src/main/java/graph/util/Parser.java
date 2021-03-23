package graph.util;

public interface Parser<T> {

    /**
     * <p>
     * Parses a data array to a graph object
     * </p>
     *
     * @param data data to be parsed
     * @return the object from the data
     */
   T parse(String[] data);

}
