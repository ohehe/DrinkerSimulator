package simulator.connetwork.codecFactoryForTcp;/**
 * Created by Administrator on 2016/10/26.
 */

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulator.entity.enumtype.SendMsgType;
import simulator.entity.tcpwarpper.FrameEntity;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 协议数据发送和封装模块
 *
 * @author Administrator
 * @create 2016-10-26 20:48
 */
public class TCPDataTransmitter {
    private static final long CONNECT_TIMEOUT = 30 * 1000L; //30秒
    protected String machineID  = null;
    protected ProtocolSessionHandler protocolSessionHandler ;
    private String hostName = "localhost";
    private int port = 8080;
    private NioSocketConnector connector = null;
    private IoSession session = null ;
    private Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    public TCPDataTransmitter(String hostName, int port, String machineID ,long sendTimeDelay,ConcurrentLinkedQueue<FrameEntity> queue) {

        this.hostName = hostName ;
        this.port = port ;
        this.machineID = machineID ;
        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
        //过滤器链设置
        connector.getFilterChain().addLast(
                "TCPDataTransmitFilter",
                new ProtocolCodecFilter(new FrameEntityProtocolCodecFactory()));
        connector.getFilterChain().addLast("executorFilter",new ExecutorFilter());
        connector.getFilterChain().addLast("loggerFilter", new LoggingFilter());

        protocolSessionHandler = new ProtocolSessionHandler(machineID,sendTimeDelay,queue) ;

        //设置匹配的处理器


    }
    //连接建立
    public boolean connectionEstablishment(){
        //最多允许重试12次
        for(int i = 0 ; i < 12 ; ++i){
            try{
                if(connector!=null){
                    //获取连接future
                    ConnectFuture future = connector.connect(new InetSocketAddress(hostName,port)) ;
                    future.awaitUninterruptibly() ;
                    session = future.getSession() ;
                    return true ;
                }else{
                    throw new Exception("connect未初始化") ;
                }
            }catch (RuntimeIoException e){
                logger.error(this.getClass().getName()+": 连接失败",e);
            }catch (Exception e){
                logger.error(this.getClass().getName()+": 连接前connetct对象未初始化");
            }
        }
        return false ;
    }
    //关闭连接
    public void closeConnect(){
        if(session!=null){
            session.getCloseFuture().awaitUninterruptibly() ;
            connector.dispose();
        }
    }
    //发送一条协议消息以byte数组形式
    public boolean sendProtocolMsg(byte[] arr){
        WriteFuture writeFuture = session.write(arr) ;
        writeFuture.awaitUninterruptibly() ;
        return true ;
    }

    public FrameEntity getProtocolMsgByMsgType(SendMsgType sendMsgType){
        return protocolSessionHandler.getProtocolMsgByMsgType(sendMsgType) ;
    }
}
