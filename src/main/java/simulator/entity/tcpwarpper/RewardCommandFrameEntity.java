package simulator.entity.tcpwarpper;

/**
 * Created by Administrator on 2016/10/20.
 */

import simulator.entity.enumtype.SendMsgType;
import simulator.utils.StringHandleUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

public class RewardCommandFrameEntity extends FrameEntity {
    private String serialNum;//序列号
    private String machineNum;//机器号
    private String waterTapNum;//水龙头号。
    private int commandCode;//命令码。
    private int waterAmount;//水量。
    private Date date;//时间戳。
    public SendMsgType type = SendMsgType.REWARD_COMMAND;

    /////////////////////////////////////////////////////////////////////////////////////////
    public RewardCommandFrameEntity(String serialNum, String machineNum, String waterTapNum, int commandCode, int waterAmount, Date now) throws UnsupportedEncodingException {
        super();
        this.serialNum = serialNum;
        this.machineNum = machineNum;
        this.waterTapNum = waterTapNum;
        this.commandCode = commandCode;
        this.waterAmount = waterAmount;
        this.date = now;
    }

    public void init() throws UnsupportedEncodingException {
        byte[] content = new byte[8 + 14 + 1 + 2 + 4 + 11];//以下变量字符串的长度总和。
        byte[] serialNum = StringHandleUtils.bytesFillZero(this.serialNum, 8);
        byte[] machineNum = StringHandleUtils.bytesFillZero(this.machineNum, 14);
        byte[] waterTapNum = StringHandleUtils.bytesFillZero(this.waterTapNum, 1);
        byte[] commandCode = StringHandleUtils.convertInt2HEXByteArr(2, this.commandCode);
        byte[] waterAmount = StringHandleUtils.convertInt2HEXByteArr(4, this.waterAmount);
        byte[] datetime = StringHandleUtils.convertInt2HEXByteArr(11, this.getDate().getTime());

        //machineNum->waterTapNum->limitedQuantity->buyQuantity->phone
        System.arraycopy(serialNum, 0, content, 0, serialNum.length);
        System.arraycopy(machineNum, 0, content, serialNum.length, machineNum.length);
        System.arraycopy(waterTapNum, 0, content, serialNum.length + machineNum.length, waterTapNum.length);
        System.arraycopy(commandCode, 0, content, serialNum.length + machineNum.length + waterTapNum.length, commandCode.length);
        System.arraycopy(waterAmount, 0, content, serialNum.length + machineNum.length + waterTapNum.length + commandCode.length, waterAmount.length);
        System.arraycopy(datetime, 0, content, serialNum.length + machineNum.length + waterTapNum.length + commandCode.length + waterAmount.length, datetime.length);

        super.frameInit(42, SendMsgType.REWARD_COMMAND, content);//注意加上类型的两位.
    }


    //以下是生成的变量的get和set方法。


    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getMachineNum() {
        return machineNum;
    }

    public void setMachineNum(String machineNum) {
        this.machineNum = machineNum;
    }

    public String getWaterTapNum() {
        return waterTapNum;
    }

    public void setWaterTapNum(String waterTapNum) {
        this.waterTapNum = waterTapNum;
    }

    public int getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(int commandCode) {
        this.commandCode = commandCode;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(int waterAmount) {
        this.waterAmount = waterAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SendMsgType getType() {
        return type;
    }

    public void setType(SendMsgType type) {
        this.type = type;
    }
}
