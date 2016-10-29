package simulator.connetwork.codecFactoryForTcp;/**
 * Created by Administrator on 2016/10/26.
 */

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * 协议数据发送和封装模块
 *
 * @author Administrator
 * @create 2016-10-26 20:48
 */
public class TCPDataTransmitter {
    private static final long CONNECT_TIMEOUT = 30 * 1000L; //30秒
    private String hostName = "localhost";
    private int port = 8080;
    private NioSocketConnector connector;


    public TCPDataTransmitter(String hostName, int post) {

        connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
        //过滤器链设置
        connector.getFilterChain().addLast(
                "TCPDataTransmitFilter",
                new ProtocolCodecFilter(new FrameEntityProtocolCodecFactory()));
        connector.getFilterChain().addLast("loggerFilter", new LoggingFilter());

        //设置匹配的处理器


    }
    //连接建立

    //发送一条
}
