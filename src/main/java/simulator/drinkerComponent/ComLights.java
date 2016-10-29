package simulator.drinkerComponent;/**
 * Created by Administrator on 2016/10/28.
 */

import java.util.Arrays;

/**
 * 通用的指示灯接口
 *
 * @author Administrator
 * @create 2016-10-28 10:02
 */
public interface ComLights {
    //状态
    State tempState = State.OFF;
    //指示灯保持时间
    long keepMilliseconds = 0;

    public boolean turnOff();

    public boolean turnOn();

    //放水中途
    public void dunringDrawOff();

    //设置故障
    //设为需要的状态 和保持的时间
    public void causeMalfunction(State state, long keepMillisecond);

    enum State {
        ON("常开状态", new byte[]{1, 1}),
        OFF("关闭状态", new byte[]{0, 1}),
        ERROR("异常状态", new byte[]{0, 0}),
        BLINK("闪烁状态", new byte[]{1, 0});
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
