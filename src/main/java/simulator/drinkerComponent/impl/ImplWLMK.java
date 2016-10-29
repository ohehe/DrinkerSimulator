package simulator.drinkerComponent.impl;

import simulator.drinkerComponent.ComWLMK;
import simulator.entity.enumtype.SendMsgType;
import simulator.entity.tcpwarpper.FrameEntity;

/**
 * Created by Administrator on 2016/10/29.
 */
public class ImplWLMK extends ComWLMK{
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

        return false;
    }

    @Override
    public void fill2BufferQueue() {

    }

    @Override
    public boolean cleanBufferQueue() {
        return false;
    }

    @Override
    public void emptyBufferQueue() {

    }

    @Override
    public FrameEntity chooseOnePakcet(SendMsgType type) {
        return null;
    }
}
