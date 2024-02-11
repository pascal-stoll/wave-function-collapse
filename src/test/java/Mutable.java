public class Mutable {
    private int id;

    public Mutable(final int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[id=" + this.id + "]";
    }
}
