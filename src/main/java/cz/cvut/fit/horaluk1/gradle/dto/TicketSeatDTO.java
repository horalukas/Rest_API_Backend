package cz.cvut.fit.horaluk1.gradle.dto;

import java.util.Objects;

public class TicketSeatDTO {

    private final long id;
    private final int number;
    private final boolean taken;
    private final Integer ownerId;
    private final Integer screeningId;

    public TicketSeatDTO(long id, int number, boolean taken, Integer ownerId, Integer screeningId) {
        this.id = id;
        this.number = number;
        this.taken = taken;
        this.ownerId = ownerId;
        this.screeningId = screeningId;
    }

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public boolean isTaken() {
        return taken;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public Integer getScreeningId() { return screeningId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketSeatDTO)) return false;
        TicketSeatDTO that = (TicketSeatDTO) o;
        return id == that.id &&
                number == that.number &&
                taken == that.taken &&
                Objects.equals(ownerId, that.ownerId) &&
                screeningId.equals(that.screeningId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, taken, ownerId, screeningId);
    }
}
