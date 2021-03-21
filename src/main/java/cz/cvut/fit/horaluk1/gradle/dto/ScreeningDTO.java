package cz.cvut.fit.horaluk1.gradle.dto;


import cz.cvut.fit.horaluk1.gradle.entity.Auditorium;
import cz.cvut.fit.horaluk1.gradle.entity.Movie;

import java.util.Date;
import java.util.Objects;

public class ScreeningDTO {

    private final int id;
    private final Date time;
    private final boolean _3D;
    private final Integer auditoriumId;
    private final Integer movieId;

    public ScreeningDTO(int id, Date time, boolean is3D, Integer auditoriumId, Integer movieId) {
        this.id = id;
        this.time = time;
        this._3D = is3D;
        this.auditoriumId = auditoriumId;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public boolean is_3D() {
        return _3D;
    }

    public Integer getAuditoriumId() { return auditoriumId; }

    public Integer getMovieId() { return movieId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScreeningDTO)) return false;
        ScreeningDTO dto = (ScreeningDTO) o;
        return id == dto.id &&
                _3D == dto._3D &&
                time.equals(dto.time) &&
                auditoriumId.equals(dto.auditoriumId) &&
                movieId.equals(dto.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, _3D, auditoriumId, movieId);
    }
}