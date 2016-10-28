package simulator.connetwork.codecFactoryForTcp;/**
 * Created by Administrator on 2016/10/23.
 */

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import simulator.entity.tcpwarpper.FrameEntity;

/**
 * 帧类协议工厂
 *
 * @author Administrator
 * @create 2016-10-23 14:07
 */
public class FrameEntityProtocolCodecFactory extends DemuxingProtocolCodecFactory {
    public FrameEntityProtocolCodecFactory() {
        super.addMessageDecoder(FrameEntityDecoder.class);
        //编码器实质上没有对帧对象的处理 在FrameEntity就可以进行该过程
        super.addMessageEncoder(FrameEntity.class, FrameEntityEncoder.class);
    }
}
