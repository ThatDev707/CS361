package com.haxorz.server;

public class AthleteJson implements Comparable<AthleteJson> {
    public int AthleteNumber;

    public String Status;

    public String Time;

    public String State;

    public String TimeStamp;

    public int Place;

    @Override
    public int compareTo(AthleteJson o) {
        return Integer.compare(this.Place, o.Place);
    }
}
