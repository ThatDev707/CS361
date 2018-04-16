package com.haxorz.ChronoTimer.Races;

import com.haxorz.ChronoTimer.Commands.*;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

public class GrpRace extends Race {

    private Queue<Athlete> _finished;
    private Queue<Athlete> _runStore;

    private LocalTime _startTime = null;
    private int _athleteNum = 0;

    public GrpRace() {
        _runStore = new LinkedList<>();
        _finished = new LinkedList<>();
    }

    @Override
    public RaceType getRaceType() {
        return RaceType.GRP;
    }

    @Override
    public void executeCmd(CTCommand cmd) {
        Athlete athlete;
        switch (cmd.CMDType){
            case DNF:
                //No Function in this race type
                break;
            case NEWRUN:
            case ENDRUN:
                //discard unassigned runtimes
                _runStore.clear();
                _finished.clear();
                RunRepository.EndCurrentRun(Race.RunNumber);
                _startTime = null;
                _athleteNum = 0;

                if(cmd.CMDType == CmdType.ENDRUN)
                    break;

                Race.RunNumber++;
                break;
            case NUM:
                NumCmd numCmd = (NumCmd)cmd;

                if(COMPETITORS.containsKey(numCmd.Number)){
                    athlete = COMPETITORS.get(numCmd.Number);
                }
                else {
                    athlete = new Athlete(numCmd.Number);
                    COMPETITORS.put(numCmd.Number, athlete);
                }

                athlete.registerForRace(Race.RunNumber);

                if(_startTime != null){
                    athlete.getTimeTracker(Race.RunNumber).setStartTime(_startTime);
                    Athlete temp = _runStore.poll();

                    if(temp != null){
                        athlete.getTimeTracker(Race.RunNumber).setEndTime(temp.getTimeTracker(Race.RunNumber).getEndTime());
                        _finished.add(athlete);
                    }

                }
                break;
            case CANCEL:
                //NO Cancel
                break;
            case SWAP:
                //NO FUNCTION IN THIS RACE
                break;
            case CLR:
                //No FUNCTION IN THIS RACE
                break;
        }
    }


    @Override
    public void channelTriggered(int channelNum, LocalTime timeStamp) {
        if(channelNum == 1)
        {
            //addresss multiple TRIG CHannel 1
            //ignores follow up triggers
            if(_startTime != null)
                return;

            _startTime = timeStamp;

            RunRepository.addToCurrentRun("GRP Race Start TRIG Channel 1\n");
        }
        else if(channelNum == 2){
            Athlete athlete = new Athlete(++_athleteNum);

            if(_startTime != null){
                athlete.getTimeTracker(Race.RunNumber).setStartTime(_startTime);
                athlete.getTimeTracker(Race.RunNumber).setEndTime(timeStamp);
                RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " TRIG Channel 2\n");
                RunRepository.addToCurrentRun("Athlete " + athlete.getNumber() + " ELAPSED " + athlete.getTimeTracker(Race.RunNumber).toStringMinutes() + "\n");
                _runStore.add(athlete);
            }
            else
                RunRepository.addToCurrentRun("GRP Race TRIG Channel 2 Before Start\n");

        }
    }
}