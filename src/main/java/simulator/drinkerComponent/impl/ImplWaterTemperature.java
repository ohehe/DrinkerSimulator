package simulator.drinkerComponent.impl;

import simulator.drinkerComponent.ComWaterTemperature;

/**
 * Created by Administrator on 2016/10/29.
 */
public  class ImplWaterTemperature extends ComWaterTemperature {

    //实现抽象类中的方法。
    @Override
    public boolean turnOff() {
        this.tempState=State.OFF;
        return true;
    }

    @Override
    public void causeMalfunction(State state, long HeatMillisecond) {
          this.tempState=state;
          this.HeatMillisecond=HeatMillisecond;
    }

    @Override
    public boolean turnOn() {
        this.tempState=State.ON;
        return true;
    }

    //////////////////////////////////////////////////////////////////

    public long getTemperature() {
        return temperature;
    }

    public void setTemperature(long temperature) {
        this.temperature = temperature;
    }

    public State getTempState() {
        return tempState;
    }

    public void setTempState(State tempState) {
        this.tempState = tempState;
    }

    public long getKeepOnMillisecond() {
        return HeatMillisecond;
    }

    public void setKeepOnMillisecond(long keepOnMillisecond) {
        this.HeatMillisecond = keepOnMillisecond;
    }

}
