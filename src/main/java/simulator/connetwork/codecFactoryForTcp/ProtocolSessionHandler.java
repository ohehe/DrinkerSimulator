package simulator.connetwork.codecFactoryForTcp;/**
 * Created by Administrator on 2016/10/29.
 */

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulator.entity.enumtype.SendMsgType;
import simulator.entity.tcpwarpper.FrameEntity;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 协议处理器
 *
 * @author Administrator
 * @create 2016-10-29 14:27
 */
public class ProtocolSessionHandler extends IoHandlerAdapter{

    private final Logger logger  = LoggerFactory.getLogger(this.getClass()) ;

    private boolean finish = false ;
    private ConcurrentLinkedQueue<FrameEntity> recvQueue ;

    private String machineID = null ;
    private long timeDelay = 0;
    public ProtocolSessionHandler(String machineID , long sendTimeDelay ,ConcurrentLinkedQueue<FrameEntity> queue) {
        super();
        this.recvQueue = queue ;
        this.machineID = machineID ;
        this.timeDelay = sendTimeDelay ;
    }

    public boolean isFinish(){return finish ; }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public FrameEntity getProtocolMsgByMsgType(SendMsgType sendMsgType) {
        boolean isFinded = false;
        for (FrameEntity f : recvQueue) {
            if (f.getF_msg_type() == sendMsgType) {
                if (!recvQueue.remove(f)) {
                    logger.error(this.getClass().getName() + " : 接收队列移除元素失败");
                    return null;
                } else {
                    isFinded = true;
                    return f;
                }
            }
        }
        return null;

    }
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        long id = session.getId() ;
        long creationTime = session.getCreationTime() ;
        //上一条发送的时间 属性 long
        session.setAttribute("lastSending",0) ;
        //上一条的接收时间 属性 long
        session.setAttribute("lastRecving",0) ;

        //绑定机器号属性
        session.setAttribute("machineId",machineID) ;

        logger.debug(this.getClass().getName()+
                " : 打开一个session成功\n"+
                "id:"+id+
                " creationTime:"+creationTime);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session,message);
        Thread.sleep(this.timeDelay);
        session.setAttribute("lastSending",new Date().getTime()) ;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        //super.messageReceived(session, message);
        if(recvQueue.contains(message)) return ;
        recvQueue.offer((FrameEntity) message) ;
        Thread.sleep(this.timeDelay);
        session.setAttribute("lastRecving",new Date().getTime()) ;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        logger.error(this.getClass().getName()+" : ProtocolSessionHandler捕获异常成功",cause);
    }
}
