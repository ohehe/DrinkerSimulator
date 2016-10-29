package simulator.drinkerComponent;/**
 * Created by Administrator on 2016/10/28.
 */


import java.util.Arrays;

/**
 * 通用水龙头组件
 *
 * @author Administrator
 * @create 2016-10-28 10:31
 */
public abstract class ComWaterTap {//定义抽象类
    //状态变量
  protected   State tempState = State.OFF;
    //龙头打开状态保持时间
    protected long keepOnMillisecond = 0;

    public abstract boolean turnOff();//定义抽象方法。

    public abstract boolean turnOn();

    public abstract void dunringDrawOff();

    public abstract boolean checkTemperature();

    //设置故障
    //设为需要的状态 和异常状态的保持时间
    public abstract void causeMalfunction(State state, long keepMillisecond);

    protected enum State {
        ON("开状态", new byte[]{1, 1, 1}),
        OFF("关状态", new byte[]{1, 1, 0}),
        ERROR_KEEPOFF("无法打开", new byte[]{0, 0, 0}),
        ERROR_KEEPON("无法关闭", new byte[]{0, 1, 1}),
        TEMPERATURE_MISS("水温不足状态", new byte[]{0, 1, 0});
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
