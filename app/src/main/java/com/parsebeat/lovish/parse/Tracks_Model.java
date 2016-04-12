package com.parsebeat.lovish.parse;

/**
 * Created by Lovish-THINKPAD on 25-02-2016.
 */
public class Tracks_Model {
    private String foo;
    private String tname;
    private String plays;
    private String duration;
    private String hearts;
    private String genre;
    private String username;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getplays() {
        return plays;
    }

    public void setPlays(String plays) {
        this.plays = plays;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHearts() {
        return hearts;
    }

    public void setHearts(String hearts) {
        this.hearts = hearts;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setUser(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
}
