package utils;

public record Pair<T>(T first, T second) {
    public static <T> Pair<T> of(T first, T second) {
        return new Pair<>(first, second);
    }

    public boolean contains(T value) {
        return first.equals(value) || second.equals(value);
    }
}
