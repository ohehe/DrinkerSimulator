package simulator.entity.tcpwarpper;/**
 * Created by Administrator on 2016/6/30.
 */

import simulator.entity.enumtype.SendMsgType;
import simulator.utils.StringHandleUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 基本帧
 *
 * @author Administrator
 * @create 2016-06-30 21:39
 */
public class FrameEntity {
    //以下是饮水机设备和通讯服务器协议的定义。
    protected byte[] F_PREFIX = {0x02};//包头。
    protected byte[] F_LENCOUNT;//正文长度。
    protected byte[] F_MSG_TYPE;//功能号。
    protected static int MAX_MSG_LEN = 0xff;//最大正文长度。
    protected static int STATIC_PREFIXHEAD_LEN = 5;//包头、正文长度以及功能号的字节长度的总和。

    public byte[] getSendMsgPackage() {
        return sendMsgPackage;
    }//

    public void setSendMsgPackage(byte[] sendMsgPackage) {
        this.sendMsgPackage = sendMsgPackage;
    }

    protected byte[] sendMsgPackage;//发送消息包。

    private int f_lencount;//数据包长度
    private SendMsgType f_msg_type;

    public SendMsgType getF_msg_type() {
        return f_msg_type;
    }

    public void setF_msg_type(SendMsgType f_msg_type) {
        this.f_msg_type = f_msg_type;
    }

    public int getF_lencount() {
        return f_lencount;
    }

    public void setF_lencount(int f_lencount) {
        this.f_lencount = f_lencount;
    }


    //解析包类的构造函数定义。
    public FrameEntity() {

        F_LENCOUNT = new byte[2];
        F_MSG_TYPE = new byte[2];

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //对打包发送信息进行初始化
    //count 是从功能号之后长度和包括功能号
    public void frameInit(int count, SendMsgType sendMsgType, byte[] content) throws UnsupportedEncodingException {

        if (count > MAX_MSG_LEN) { //count 是包括功能号。当包体长度超过最大长度时，就抛出错误提示信息。
            throw new IllegalArgumentException(this.getClass().getName() + ":打包消息体长度错误,长度过长");
        }
        sendMsgPackage = new byte[count + F_LENCOUNT.length + F_PREFIX.length];
        if (count - F_MSG_TYPE.length != content.length) {//打包时，若长度不符合包的定义要求，则抛出异常。
            throw new IllegalArgumentException(this.getClass().getName() + ":消息打包失败，消息体长度错误");
        }

        F_LENCOUNT = StringHandleUtils.convertInt2HEXByteArr(2, count);
        F_MSG_TYPE = sendMsgType.getPrefix();//枚举类。

        //拼接消息体
        sendMsgPackage[0] = F_PREFIX[0];

        //以下三个for循环语句将消息装订成帧。
        int i;
        for (i = F_PREFIX.length; i < F_PREFIX.length + F_LENCOUNT.length; i++) {
            sendMsgPackage[i] = F_LENCOUNT[i - F_PREFIX.length];
        }
        int mid = F_PREFIX.length + F_LENCOUNT.length;
        for (i = mid; i < STATIC_PREFIXHEAD_LEN; ++i) {
            sendMsgPackage[i] = F_MSG_TYPE[i - mid];
        }

        for (i = STATIC_PREFIXHEAD_LEN; i < sendMsgPackage.length; ++i) {
            sendMsgPackage[i] = content[i - STATIC_PREFIXHEAD_LEN];
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    //对接收装帧信息进行初始化
    //函数返回未解析内容段
    public byte[] unpack2Frame(byte[] packAll, int needContenLen) {
        //&&packAll.length==StringHandleUtils.resolveLongFromByteArr(F_LENCOUNT)+F_MSG_TYPE.length+F_PREFIX.length
        if (needContenLen == packAll.length) {            //包长度校验正确
            //拆解包头
            if (this.F_PREFIX[0] != packAll[0]) {//包头开始位错误
                throw new IllegalArgumentException(this.getClass().getName() + ":回返消息包包头错误");
            }
            //拆解长度单元
            f_lencount = (int) StringHandleUtils.unpackHexByteArr2Long(packAll, 1, 2);
            //StringHandleUtils.unpackHexByteArr2Long(packAll,3,2) ;
            //拆解类型单元
            for (SendMsgType sendMsgType : SendMsgType.values()) {
                if (Arrays.equals(sendMsgType.getPrefix(), Arrays.copyOfRange(packAll, 3, 5))) {
                    f_msg_type = sendMsgType;
                }
            }

            //剩下content信息
            return Arrays.copyOfRange(packAll, 5, packAll.length);
        }
        return null;
    }
}
