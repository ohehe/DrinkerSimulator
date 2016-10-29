package simulator.drinkerComponent.impl;

import simulator.drinkerComponent.ComLights;

/**
 * Created by Administrator on 2016/10/29.
 */
public class ImplLights extends ComLights {
    //返回饮水机当前状态。
    public boolean turnOff() {

        this.tempState=State.OFF;

        return true;
    }

    public boolean turnOn() {
        this.tempState=State.ON;
        return true;
    }

    public void dunringDrawOff() {
         this.tempState=State.BLINK;
    }

    public void causeMalfunction(State state, long keepMillisecond) {

        this.tempState=state;
        this.keepMilliseconds=keepMillisecond;
    }
}
