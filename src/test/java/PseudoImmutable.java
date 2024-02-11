public record PseudoImmutable(String name, Mutable id) {
    // Zu beachten, dass record tatsächlich immutable

    public PseudoImmutable(final String name, final Mutable id) {
        this.name = name;
        this.id = new Mutable(id.getId());
    }

    public Mutable id() {
        return new Mutable(this.id.getId());
    }
}
