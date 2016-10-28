package simulator.connetwork.codecFactoryForTcp;/**
 * Created by Administrator on 2016/10/23.
 */


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulator.entity.enumtype.SendMsgType;
import simulator.entity.tcpwarpper.FrameEntity;

/**
 * 抽象帧类编码类
 *
 * @author Administrator
 * @create 2016-10-23 14:52
 */
public abstract class AbstractFrameEntityEncoder<T extends FrameEntity> implements MessageEncoder<T> {
    public Logger log = LoggerFactory.getLogger(this.getClass()); //日志记录

    protected AbstractFrameEntityEncoder() {
    }

    ;
    protected SendMsgType msgType;

    /**
     * method:encode
     *
     * @param ioSession, message, protocolEncoderOutput
     * @return void
     * @throws
     * @description 分配空间，编码网络帧（实质上编码的工作在FrameEntity中）
     * @author Administrator
     * @date 2016/10/23
     */
    public void encode(IoSession ioSession, T message, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        IoBuffer buf = IoBuffer.allocate(5);
        buf.setAutoExpand(true);//开启缓冲区自动拓展
        buf.setAutoShrink(true); //开启缓冲区自动缩减
        encodeBody(ioSession, message, buf);//具体的其他处理

    }

    //对消息体的处理 暂且为空
    protected abstract void encodeBody(IoSession session, T message, IoBuffer buf);
}
