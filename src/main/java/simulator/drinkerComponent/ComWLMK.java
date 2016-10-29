package simulator.drinkerComponent;/**
 * Created by Administrator on 2016/10/28.
 */

import simulator.connetwork.codecFactoryForTcp.TCPDataTransmitter;
import simulator.entity.enumtype.SendMsgType;
import simulator.entity.tcpwarpper.FrameEntity;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 通用网络模块
 *
 * @author Administrator
 * @create 2016-10-28 15:37
 */
public abstract class ComWLMK {
    //状态变量
   protected State tempState = State.OFF;
    //网络传输对象
   protected TCPDataTransmitter tcpTransmitter = null;
    //接收缓冲队列
    //人为设定平均TCP传输时延(毫秒)
   protected long transmitDelay = 100;
    //人为设定超时的TCP传输递增时延(毫秒)
   protected long transmitDelayAdd = 1000;
    //发送包计数
   protected long countOfSendPack = 0;
    //接收包计数
   protected long countOfRecvPack = 0;
    //故障记录
   protected LinkedHashMap<String, Exception> errorsRecord = new LinkedHashMap<String, Exception>();
    ConcurrentLinkedQueue<FrameEntity> BufferingQueue = new ConcurrentLinkedQueue<FrameEntity>();

    //计数+1
  public   abstract  void countSendingOnePack();

    //计数+1
    public   abstract void countRecvingOnePack();

    //发送一条数据
    public   abstract boolean sendProtocolMsg(byte[] msgPacket);

    //接收到缓冲队列
    public   abstract void fill2BufferQueue();

    //清理缓冲队列中的
    public   abstract boolean cleanBufferQueue() ;

    //清空缓冲队列
    public   abstract void emptyBufferQueue() ;

    //从队列中找到接收到的协议包(目前只允许缓冲队列中存在不相同的协议包)
    public   abstract FrameEntity chooseOnePakcet(SendMsgType type);

    protected enum State {
        ON("开状态", new byte[]{1, 1, 1}),
        OFF("关状态", new byte[]{1, 1, 0}),
        ERROR_KEEPOFF("无法打开", new byte[]{0, 0, 0}),
        ERROR_PACKET_LOSS("丢包严重", new byte[]{0, 1, 1}),
        TRANSMISSION_DELAY("网络时延过大", new byte[]{0, 1, 0});
        private String stateStr;
        private byte stateCode[];

        State(String stateStr, byte[] stateCode) {
            this.stateCode = stateCode;
            this.stateStr = stateStr;
        }

        public String getStateStr() {
            return stateStr;
        }

        public void setStateStr(String stateStr) {
            this.stateStr = stateStr;
        }

        public byte[] getStateCode() {
            return stateCode;
        }

        public void setStateCode(byte[] stateCode) {
            this.stateCode = stateCode;
        }

        @Override
        public String toString() {
            return "state{" +
                    "stateStr='" + stateStr + '\'' +
                    ", stateCode=" + Arrays.toString(stateCode) +
                    '}';
        }
    }
}
