package simulator.connetwork.codecFactoryForTcp;/**
 * Created by Administrator on 2016/10/23.
 */

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import simulator.entity.tcpwarpper.FrameEntity;
import simulator.utils.FrameEntityConvertHandler;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 帧类解析
 *
 * @author Administrator
 * @create 2016-10-23 14:47
 */
public class FrameEntityDecoder extends AbstractFrameEntityDecoder {

    @Override
    protected FrameEntity decodeBody(IoSession session, IoBuffer in, byte[] packAll) {

        FrameEntity frameEntity = null;
        try {
            frameEntity = FrameEntityConvertHandler.chooseTypeEntityUnpack(nowHandleMsgType, packAll);
        } catch (UnsupportedEncodingException e) {
            logger.error(this.getClass().getName() + ": 解帧过程中发生了不支持编码的错误");
            e.printStackTrace();
        }
        if (frameEntity != null) return frameEntity;
        else {
            logger.error(this.getClass().getName() + " : FrameEntity中解析包失败");
            return null;
        }
    }
}
