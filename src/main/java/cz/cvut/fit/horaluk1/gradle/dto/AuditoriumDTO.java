package cz.cvut.fit.horaluk1.gradle.dto;

import java.util.Objects;

public class AuditoriumDTO {

    private final int id;
    private final int capacity;

    public AuditoriumDTO(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditoriumDTO)) return false;
        AuditoriumDTO that = (AuditoriumDTO) o;
        return id == that.id &&
                capacity == that.capacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity);
    }
}
