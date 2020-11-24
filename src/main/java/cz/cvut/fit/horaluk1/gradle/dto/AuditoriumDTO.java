package cz.cvut.fit.horaluk1.gradle.dto;

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

}
