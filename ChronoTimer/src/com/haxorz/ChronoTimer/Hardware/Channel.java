package com.haxorz.ChronoTimer.Hardware;

import java.time.LocalTime;

public class Channel implements HardwareEventListener {

    private int channelNum;

    public Channel(int channelNum) {

        this.channelNum = channelNum;
    }


    private boolean enabled = false;

    public static ChannelListener ChannelListener = null;

    public boolean ToggleChannel(){
        enabled = !enabled;
        return enabled;
    }

    public void Trigger(LocalTime timeStamp){
        if(enabled && ChannelListener != null)
        {
            ChannelListener.channelTriggered(channelNum, timeStamp);
        }
    }



    @Override
    public void hwEventTriggered(HWEventType type, LocalTime timeStamp) {
        if(type == HWEventType.InputSensorTriggered){
            Trigger(timeStamp);
        }
    }
}