package simulator.utils;/**
 * Created by Administrator on 2016/10/24.
 */

import simulator.entity.enumtype.SendMsgType;
import simulator.entity.tcpwarpper.AcceptCommandFrameEntity;
import simulator.entity.tcpwarpper.FrameEntity;
import simulator.entity.tcpwarpper.RewardCommandFrameEntity;

import java.io.UnsupportedEncodingException;

/**
 * 帧类转换器
 *
 * @author Administrator
 * @create 2016-10-24 1:13
 */
public class FrameEntityConvertHandler {
    //自动根据类型进行装包操作
    //buffer可以为null 一般为整个的包数组
    public static FrameEntity chooseTypeEntityUnpack(SendMsgType sendMsgTypes, byte[] bufferArr) throws UnsupportedEncodingException {
        synchronized (bufferArr) {
            FrameEntity frameEntity = null;
            switch (sendMsgTypes) {
                case RECEIVE_COMMAND: {
                    frameEntity = null;
                    frameEntity = new AcceptCommandFrameEntity(bufferArr, bufferArr.length);
                }
                break;
            }
            return frameEntity;
        }
    }
}
