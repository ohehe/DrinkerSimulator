package simulator.drinkerComponent.impl;

import simulator.drinkerComponent.ComWaterTap;
import simulator.drinkerComponent.ComWaterTemperature;

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
    public boolean checkTemperature(ComWaterTemperature comWaterTemperature,int temperature) {
        if(comWaterTemperature.getTemperature()<temperature){
            this.tempState = State.TEMPERATURE_MISS ;
            if(comWaterTemperature.getTempState() == ComWaterTemperature.State.ERROR_HEATON){
                return false ;
            }
            long cou = temperature - comWaterTemperature.getTemperature() ;
            comWaterTemperature.turnOn() ;
            while(true){
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(comWaterTemperature.getTemperature()>=temperature)
                    break ;
            }
            return true ;
        }
        return true;//水温正常。
    }

    @Override
    public void causeMalfunction(State state, long keepMillisecond) {
            this.tempState=state;
            this.keepOnMillisecond=keepMillisecond;
    }
}
