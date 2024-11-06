package edu.uga.cs.statecapitalsquiz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class History {

    private long id;
    private Integer date;
    private Integer score;

    public History() {
        this.id = -1;
        this.date = null;
        this.score = null;
    }

    public History (int date, int score) {
        this.id = -1;
        this.date = date;
        this.score = score;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public Integer getDate() {return date;}

    public void setDate(int date) {this.date = date;}

    public Integer getScore() {return score;}

    public void setScore(int score) {this.score = score;}

    public String toString() {
        Date dateDate = new Date((long) date * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(date);

        return id + ", " + formattedDate + ", " + score;
    }
}
