package simulator.drinkerComponent.impl;

import simulator.drinkerComponent.ComWaterTap;

/**
 * Created by Administrator on 2016/10/29.
 */
public class ImplWaterTap extends ComWaterTap {
    @Override
    public boolean turnOff() {
        this.tempState=State.OFF;
        return true;
    }

    @Override
    public boolean turnOn() {
        this.tempState=State.ON;
        return true;
    }

    @Override
    public void dunringDrawOff() {

    }

    @Override
    public boolean checkTemperature() {
        if(this.tempState==State.TEMPERATURE_MISS)return false;
        else
        return true;//水温正常。
    }

    @Override
    public void causeMalfunction(State state, long keepMillisecond) {
            this.tempState=state;
            this.keepOnMillisecond=keepMillisecond;
    }
}
