package simulator.connetwork.codecFactoryForTcp;/**
 * Created by Administrator on 2016/10/23.
 */

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import simulator.entity.tcpwarpper.FrameEntity;

/**
 * 帧类编码
 *
 * @author Administrator
 * @create 2016-10-23 14:48
 */
public class FrameEntityEncoder<T extends FrameEntity> extends AbstractFrameEntityEncoder<T> {

    public FrameEntityEncoder() {
    }

    @Override
    protected void encodeBody(IoSession session, T message, IoBuffer buf) {
        //装填
        msgType = message.getF_msg_type();
        int f_len = message.getF_lencount();
        if (f_len == message.getF_lencount() + 3) {//长度正确
            buf.put(message.getSendMsgPackage());
        } else {//长度错误
            buf.flip();
            log.error(this.getClass().getName() + ":\nline:43\nerror:发送tcp包长度错误");
            throw new RuntimeException("发送tcp包长度错误");
        }

    }
}
