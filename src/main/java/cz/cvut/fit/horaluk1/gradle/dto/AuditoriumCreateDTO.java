package cz.cvut.fit.horaluk1.gradle.dto;

public class AuditoriumCreateDTO {

    private int capacity;

    public AuditoriumCreateDTO() {
    }

    public AuditoriumCreateDTO(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
