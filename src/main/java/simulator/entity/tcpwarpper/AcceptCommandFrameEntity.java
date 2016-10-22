package simulator.entity.tcpwarpper;/**
 * Created by Administrator on 2016/8/8.
 */

import simulator.entity.enumtype.SendMsgType;
import simulator.utils.StringHandleUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

/**
 * 接收
 *
 * @author Administrator
 * @create 2016-08-08 14:43
 */
public class AcceptCommandFrameEntity extends FrameEntity {
    private String machineNum;
    private String waterTapNum;//水龙头号。
    private long commandCode;//命名码。
    private long limitQuantity;//限量
    private long waterAmount;//水量。
    private Date date;
    public SendMsgType type = SendMsgType.RECEIVE_COMMAND;
    /////////////////////////////////////////////////////////////////////////////////////////

    //needLendth 为包数组的长度。
    public AcceptCommandFrameEntity(byte[] bytePackage, int needLength) throws UnsupportedEncodingException {
        super();
        this.setSendMsgPackage(bytePackage);

        byte[] unpackContentArr = unpack2Frame(bytePackage, needLength);
        int contenLength = getF_lencount();
        if (unpackContentArr.length != contenLength - F_MSG_TYPE.length) {//长度校验失败
            throw new IllegalArgumentException(this.getClass().getName() + ":解析tcp包失败，长度错误");
        }

        //按照协议要求拆解出属性 m 10-> w 4-> p 11-> n 11//如何对应的？
        machineNum = StringHandleUtils.resolveByteArr2Str(14, unpackContentArr, 0);
        waterTapNum = StringHandleUtils.resolveByteArr2Str(1, unpackContentArr, 14);
        waterAmount = StringHandleUtils.resolveLongFromByteArr(Arrays.copyOfRange(unpackContentArr, 21, 25));
        limitQuantity = StringHandleUtils.resolveLongFromByteArr(Arrays.copyOfRange(unpackContentArr, 17, 21));
        commandCode = StringHandleUtils.resolveLongFromByteArr(Arrays.copyOfRange(unpackContentArr, 15, 17));
        date = new Date(StringHandleUtils.resolveLongFromByteArr(Arrays.copyOfRange(unpackContentArr, 25, 36)));

    }
    //以下是生成的变量的get和set方法。


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

    public long getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(long commandCode) {
        this.commandCode = commandCode;
    }

    public long getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(long limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public long getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(long waterAmount) {
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
