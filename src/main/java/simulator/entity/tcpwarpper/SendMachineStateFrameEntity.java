package simulator.entity.tcpwarpper;/**
 * Created by Administrator on 2016/8/8.
 */

import simulator.entity.enumtype.SendMsgType;
import simulator.utils.StringHandleUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 发送饮水机状态信息。
 *
 * @author Administrator
 * @create 2016-08-08 12:20
 */
public class SendMachineStateFrameEntity extends FrameEntity {
    //以下是成员变量的声明。
    private String machineNum;//机器号
    private Date nowTime;//当前时间
    private String waterState;//水龙头状态。
    private int temperature;//水温。
    public SendMsgType type = SendMsgType.MACHINE_STATE;//买水请求。
//////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
    //以下是成员函数的定义。

    public String getMachineNum() {
        return machineNum;
    }

    public void setMachineNum(String machineNum) {
        this.machineNum = machineNum;
    }

    public Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(Date nowTime) {
        this.nowTime = nowTime;
    }

    public String getWaterState() {
        return waterState;
    }

    public void setWaterState(String waterState) {
        this.waterState = waterState;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public SendMsgType getType() {
        return type;
    }

    public void setType(SendMsgType type) {
        this.type = type;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public SendMachineStateFrameEntity(String machineNum, String waterState, int temperature, Date now) throws UnsupportedEncodingException {
        super();
        this.machineNum = machineNum;
        this.waterState = waterState;
        this.temperature = temperature;
        this.nowTime = now;
    }

    public void init() throws UnsupportedEncodingException {
        byte[] content = new byte[14 + 4 + 2 + 11];//以下变量字符串的长度总和。
        byte[] machineNum = StringHandleUtils.bytesFillZero(this.machineNum, 14);
        byte[] waterState = StringHandleUtils.bytesFillZero(this.waterState, 4);
        byte[] temperature = StringHandleUtils.convertInt2HEXByteArr(2, this.temperature);
        byte[] date = StringHandleUtils.convertInt2HEXByteArr(11, nowTime.getTime());

        //machineNum->waterTapNum->limitedQuantity->buyQuantity->phone
        System.arraycopy(machineNum, 0, content, 0, machineNum.length);
        System.arraycopy(waterState, 0, content, machineNum.length, waterState.length);
        System.arraycopy(temperature, 0, content, machineNum.length + waterState.length, temperature.length);
        System.arraycopy(date, 0, content, machineNum.length + waterState.length + temperature.length, date.length);

        super.frameInit(33, SendMsgType.MACHINE_STATE, content);//注意加上功能号类型的两位，则为33。
    }
}
