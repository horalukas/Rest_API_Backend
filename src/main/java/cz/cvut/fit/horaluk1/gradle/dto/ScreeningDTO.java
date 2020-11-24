package cz.cvut.fit.horaluk1.gradle.dto;


import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;

import java.util.Date;

public class ScreeningDTO {

    private final int id;
    private final Date time;
    private final boolean is3D;
    private final Integer auditoriumId;
    private final Integer movieId;

    public ScreeningDTO(int id, Date time, boolean is3D, Integer auditoriumId, Integer movieId) {
        this.id = id;
        this.time = time;
        this.is3D = is3D;
        this.auditoriumId = auditoriumId;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public boolean isIs3D() {
        return is3D;
    }

    public Integer getAuditorium() { return auditoriumId; }

    public Integer getMovie() { return movieId; }

}