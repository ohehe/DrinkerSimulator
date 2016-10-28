package simulator.connetwork.codecFactoryForTcp;/**
 * Created by Administrator on 2016/10/23.
 */

import org.apache.commons.lang3.ArrayUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulator.connetwork.codecFactoryForTcp.Constants;
import simulator.entity.enumtype.SendMsgType;
import simulator.entity.tcpwarpper.FrameEntity;
import simulator.utils.StringHandleUtils;

import java.util.Arrays;

/**
 * 帧类解析器抽象类
 *
 * @author Administrator
 * @create 2016-10-23 20:10
 */
public abstract class AbstractFrameEntityDecoder implements MessageDecoder {
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    protected SendMsgType nowHandleMsgType;
    protected long read_f_len = 0;
    protected boolean isMsgHeadReaded = false;

    public MessageDecoderResult decodable(IoSession ioSession, IoBuffer ioBuffer) {
        //如果协议头没有完全读完可以直接返回NEED_DATA
        if (ioBuffer.remaining() < Constants.F_PREFIX + Constants.LENCOUNT_LEN) {
            return MessageDecoderResult.NEED_DATA;
        }

        byte[] temp = new byte[Constants.PREFIX_LEN + Constants.LENCOUNT_LEN];
        Arrays.fill(temp, (byte) 0x00);
        //装填协议头和长度
        ioBuffer.get(temp);
        //判断是否协议头正确
        if (temp[0] == Constants.F_PREFIX) {//协议头正确
            //获取协议长度
            long f_len = StringHandleUtils.unpackHexByteArr2Long(temp, 1, 2);
            this.read_f_len = f_len;
            if (f_len < Constants.MAX_MSG_LEN && f_len > 0) {//协议长度正确
                return MessageDecoderResult.OK;
            }
        }

        return MessageDecoderResult.NOT_OK;
    }

    //上面对流的操作不会造成影响，只是用来判断可读性，结束后会复原
    public MessageDecoderResult decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        byte[] discardHead = new byte[3];
        byte[] tempForFType = new byte[2];//获得帧类型
        Arrays.fill(tempForFType, (byte) 0x00);
        //跳过前面以用来判断的协议头等单元
        if (!isMsgHeadReaded) {//判断是否需要跳过头
            ioBuffer.get(discardHead);//跳过协议头+协议长度
            isMsgHeadReaded = true;
        }

        ioBuffer.get(tempForFType);//获取协议类型e
        //判断是否在枚举数组中
        SendMsgType[] typeArr = SendMsgType.values();
        boolean isMatched = false;
        for (SendMsgType sendMsgType : typeArr) {
            if (Arrays.equals(sendMsgType.getPrefix(), tempForFType)) {
                nowHandleMsgType = sendMsgType;
                isMatched = true;
            }
        }
        //获得协议消息类型成功
        if (isMatched) {
            //类型匹配成功
            //解析装载包体
            if (read_f_len > 0 && read_f_len <= Constants.MAX_MSG_LEN) {
                if (ioBuffer.remaining() >= read_f_len - Constants.MSG_TYPE_LEN) {
                    byte[] header = new byte[(int) (Constants.F_PREFIX + Constants.LENCOUNT_LEN)];
                    byte[] content = new byte[(int) (read_f_len)];
                    //已读部分的复制
                    System.arraycopy(header, 0, discardHead, 0, 3);
                    System.arraycopy(header, 0, tempForFType, 3, 2);

                    //读取流中剩余帧内容部分,前面已经校验流中剩余足够字节
                    ioBuffer.get(content);

                    //自己帧解析那边写得反人类 现在作死了
                    FrameEntity frameEntity = decodeBody(ioSession, ioBuffer, ArrayUtils.addAll(header, content));

                    protocolDecoderOutput.write(frameEntity);
                } else {
                    return MessageDecoderResult.NEED_DATA;
                }
            }
        } else {
            //匹配失败
            logger.error(this.getClass().getName() + "协议消息类型匹配失败");
            return MessageDecoderResult.NOT_OK;
        }
        return MessageDecoderResult.OK;
    }

    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        //没卵用嘛···
        //除非后面加上了对是否跳到下一帧的判断
    }

    protected abstract FrameEntity decodeBody(IoSession session,
                                              IoBuffer in, byte[] packAll);
}
