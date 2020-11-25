package cz.cvut.fit.horaluk1.gradle.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TicketSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private int number;

    @NotNull
    private boolean taken;

    @ManyToOne
    @JoinColumn(name = "moviegoer_id")
    private MovieGoer owner;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "screening_id")
    private Screening screening;

    public TicketSeat() {
    }

    public TicketSeat(int number, boolean taken, MovieGoer owner, Screening screening) {
        this.number = number;
        this.taken = taken;
        this.owner = owner;
        this.screening = screening;
    }

    public long getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public MovieGoer getOwner() {
        return owner;
    }

    public void setOwner(MovieGoer owner) {
        this.owner = owner;
    }

    public Screening getScreening() { return screening; }

    public void setScreening(Screening screening) { this.screening = screening; }
}
