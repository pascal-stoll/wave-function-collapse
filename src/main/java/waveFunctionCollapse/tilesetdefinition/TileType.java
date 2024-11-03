package waveFunctionCollapse.tilesetdefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public record TileType(String fileName, int[] rotations, EdgeType[] edges) implements Comparable<TileType> {

    public TileType {
        assert (edges.length == 4) : "Not exactly 4 edges provided for tile type of file '" + fileName + "'.";
        assert (rotations.length <= 4) : "Too many rotations provided for tile type of file '" + fileName + "'.";
    }

    /*
    public TileType(final String fileName, final EdgeType[] edges) {
        this(fileName, TileType.getRotations(edges), edges);
    }

    private static int[] getRotations(final EdgeType[] edges) {
        List<Integer> rotations = new ArrayList<>(4);
        rotations.add(0);

        for (int i=1; i<4; i++) {
            List<EdgeType> edgesListCopy = new ArrayList<>(Arrays.asList(edges));
            Collections.rotate(edgesListCopy, i);

            for (int j=0; j<4; j++) {
                if (!edges[j].equals(edgesListCopy.get(j))) {
                    rotations.add(i);
                    break;
                }
            }
        }

        return rotations.stream()
                .mapToInt(Integer::valueOf)
                .toArray();
    }
    */

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(TileType o) {
        return this.fileName.compareTo(o.fileName);
    }

    @Override
    public int hashCode() {
        return fileName.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (! (other instanceof TileType)) {
            return false;
        }
        return fileName.equals(((TileType) other).fileName);
    }
}
