package Domain;

import java.util.Objects;

public class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }


    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Pair)) return false;

        Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(first, other.first) && Objects.equals(second, other.second);
    }
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
