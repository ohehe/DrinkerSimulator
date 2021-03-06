package simulator.drinkerComponent;
import simulator.connetwork.codecFactoryForTcp.Constants;

import java.util.Arrays;
/**
 * Created by Administrator on 2016/10/29.
 */
public abstract class ComWaterTemperature {

    protected long temperature = Constants.MACHINE_ROOM_TEMPTURE;//当前水温。
    protected State tempState = State.OFF;//加热或开关状态。
    protected long HeatMillisecond = 0;
    //升温速度 按秒记
    protected int heatUpSpeed = 4 ;
    //降温速度
    protected  int bringDownSpeed = 1 ;

    protected  boolean isClose = false ;

    public abstract boolean turnOff();//定义抽象方法关闭冷却。

    public abstract void causeMalfunction(State state, long keepMillisecond);

    public abstract boolean turnOn();//定义抽象方法打开加热。

    public long getTemperature() {
        return temperature;
    }

    public State getTempState() {
        return tempState;
    }

    //终止标志
    public abstract void close() ;

    //////////////////////////////////////////////////////

    //以下定义水温状态的枚举类型。
    public enum State {
        ON("加热状态", new byte[]{1, 1, 1}),
        OFF("冷却状态", new byte[]{0, 0, 0}),
        ERROR_HEATOFF("无法加热", new byte[]{0, 0, 1}),
        ERROR_HEATON("无法冷却", new byte[]{0, 1, 1});
        ;
        //以下是定义枚举类型设置状态信息函数。
        private String stateStr;
        private byte stateCode[];

        State(String stateStr, byte[] stateCode) {
            this.stateCode = stateCode;
            this.stateStr = stateStr;
        }

        public String getStateStr() {
            return stateStr;
        }

        public void setStateStr(String stateStr) {
            this.stateStr = stateStr;
        }

        public byte[] getStateCode() {
            return stateCode;
        }

        public void setStateCode(byte[] stateCode) {
            this.stateCode = stateCode;
        }


        @Override
        public String toString() {
            return "state{" +
                    "stateStr='" + stateStr + '\'' +
                    ", stateCode=" + Arrays.toString(stateCode) +
                    '}';
        }
    }
}