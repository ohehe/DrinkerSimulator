package simulator.drinkerComponent.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulator.connetwork.codecFactoryForTcp.TCPDataTransmitter;
import simulator.drinkerComponent.ComWLMK;
import simulator.entity.enumtype.SendMsgType;
import simulator.entity.tcpwarpper.FrameEntity;


/**
 * Created by Administrator on 2016/10/29.
 */
public class ImplWLMK extends ComWLMK{

    private final Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    //构造函数。
    public  ImplWLMK(String hostName,int port, String machineNum) throws Exception {
        super();
        this.tcpTransmitter = new TCPDataTransmitter(hostName,port,machineNum,this.transmitDelay,this.bufferingQueue);
        if(!tcpTransmitter.connectionEstablishment()){
            tcpTransmitter.closeConnect();
            throw new Exception("tcp网络模块初始化失败") ;
        }
    }
    @Override
    public void countSendingOnePack() {
        this.countOfSendPack++;
    }

    @Override
    public void countRecvingOnePack() {
       this.countOfRecvPack++;
    }

    @Override
    public boolean sendProtocolMsg(byte[] msgPacket) {
        if(tcpTransmitter!=null){
            if (!tcpTransmitter.sendProtocolMsg(msgPacket)){
                logger.info(this.getClass().getName()+" : 发送包失败");
                return false ;
            }
            countSendingOnePack();
            return true ;
        }
        logger.error(this.getClass().getName()+" : 调用了未初始化的tcp网络连接模块");
        return false;
    }

    @Override
    public void fill2BufferQueue() {


    }

    @Override
    public boolean cleanBufferQueue() {

        return true;
    }

    @Override
    public void emptyBufferQueue() {
        if(!this.bufferingQueue.isEmpty()){
            bufferingQueue.clear();
        }
    }

    @Override
    public FrameEntity chooseOnePakcet(SendMsgType type) {
        this.countRecvingOnePack();
        return tcpTransmitter.getProtocolMsgByMsgType(type) ;

    }

    ///

}
