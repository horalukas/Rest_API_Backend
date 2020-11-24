package cz.cvut.fit.horaluk1.gradle.dto;


import java.util.Date;

public class ScreeningCreateDTO {

    private Date time;
    private boolean is3D;
    private Integer auditoriumId;
    private Integer movieId;

    public ScreeningCreateDTO() {
    }

    public ScreeningCreateDTO(Date time, boolean is3D, Integer auditoriumId, Integer movieId) {
        this.time = time;
        this.is3D = is3D;
        this.auditoriumId = auditoriumId;
        this.movieId = movieId;
    }

    public Date getTime() {
        return time;
    }

    public boolean isIs3D() {
        return is3D;
    }

    public Integer getAuditoriumId() { return auditoriumId; }

    public Integer getMovieId() { return movieId; }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setIs3D(boolean is3D) {
        this.is3D = is3D;
    }

    public void setAuditoriumId(Integer auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}