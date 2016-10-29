package simulator.drinkerComponent.impl;

import simulator.connetwork.codecFactoryForTcp.Constants;
import simulator.drinkerComponent.ComWaterTemperature;

/**
 * Created by Administrator on 2016/10/29.
 */
public  class ImplWaterTemperature extends ComWaterTemperature implements Runnable{

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

    @Override
    public void close() {
        isClose = true ;
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

    public void run() {
        long now = System.currentTimeMillis() ;
        while(true){
            if(tempState == State.ON){//加热状态
                temperature += heatUpSpeed  ;
                if (temperature >= Constants.MACHINE_MAX_TEMPTURE-2){
                    temperature = Constants.MACHINE_MAX_TEMPTURE ;
                }
            }else{//降温状态
                temperature -= bringDownSpeed ;
                if (temperature <= Constants.MACHINE_ROOM_TEMPTURE+2){
                    temperature = Constants.MACHINE_ROOM_TEMPTURE ;
                }

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //终止
            if(isClose){
                break ;
            }
        }
    }
}
