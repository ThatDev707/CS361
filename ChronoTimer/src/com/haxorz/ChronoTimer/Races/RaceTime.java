package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.SystemClock;

import java.time.Duration;
import java.time.LocalTime;

/**
 * an object that is abe to keep track of the time an athlete
 * started and finished, and if they even finished at all
 *
 * times are local so this makes the assumption that the race
 * takes place in one time zone. If the race crosses time zones,
 * we recommend setting the system time to UTC to avoid confusion
 */
public class RaceTime {

	private LocalTime _startTime;
	private LocalTime _endTime;
	private boolean _DNF = false;

	private Athlete _athlete;


	public RaceTime(Athlete athlete) {
		_athlete = athlete;
	}

	public LocalTime getStartTime() { return _startTime; }
	public void setStartTime(LocalTime startTime) { _startTime = startTime; }

	public LocalTime getEndTime() { return _endTime;}
	public void setEndTime(LocalTime endTime) {	_endTime = endTime;	}

	public Duration getDuration(){
		if(_startTime != null && _endTime != null){
			return Duration.between(_startTime, _endTime);
		}
		else if(_startTime != null && _endTime == null){
			return Duration.between(_startTime, SystemClock.now());
		}
		throw new IllegalStateException("Start and/or End time not set");
	}

	public boolean isDNF() { return _DNF; }
	public void setDNF(boolean DNF) { this._DNF = DNF; }
	public void setStartNow(){
		_startTime = SystemClock.now();
	}
	public void setEndNow(){
		_endTime = SystemClock.now();
	}

	/**
	 * @return a string with the race time with hours, minutes, seconds and hundredths of a second
	 */
	public String toStringHours(){
		String str = "";

		Duration d = this.getDuration();

		long hours = d.getSeconds() / 3600;
		str += hours;
		str += ':';

		long mins = (d.getSeconds() / 60) % 60;
		if(mins < 10) str += '0';
		str += mins;
		str += ':';

		long secs = d.getSeconds() % 60;
		if(secs < 10) str += '0';
		str += secs;
		str += '.';

		int hund = d.getNano() / 10000000; //10^7
		if(hund < 10) str += '0';
		str += hund;

		return str;
	}

	/**
	 * @return string with minutes, seconds and hundredths of a secons
	 */
	public String toStringMinutes(){
		if(isDNF()){
			return "DNF";
		}

		if(_startTime == null){
			return "Waiting to Race";
		}

		String str = "";

		Duration d = this.getDuration();

		long mins = (d.getSeconds() / 60);
		if(mins < 10) str += '0';
		str += mins;
		str += ':';

		long secs = d.getSeconds() % 60;
		if(secs < 10) str += '0';
		str += secs;
		str += '.';

		int hund = d.getNano() / 10000000; //10^7
		if(hund < 10) str += '0';
		str += hund;

		return str;
	}

	/**
	 * @return string with minutes, seconds and hundredths of a seconds or DNF
	 */
	public String toString(){
		if(isDNF()){
			return "Athlete " + _athlete.getNumber() + " DNF";
		}

		return "Athlete " + _athlete.getNumber() + " ELAPSED " + toStringMinutes();
	}

	public long toMillis() {
		if(isDNF()){
			return 0;
		}

		if(_startTime == null){
			return 0;
		}

		Duration d = this.getDuration();

		return d.toMillis();
	}
}
