package simulator.entity.tcpwarpper;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/19.
 */
public class SendMachineStateFrameEntityTest extends TestCase {

    public void testInit() throws Exception {
        SendMachineStateFrameEntity frameEntity = new SendMachineStateFrameEntity("01234567891234", "1111", 23, new Date());
        frameEntity.init();
        for (byte b : frameEntity.getSendMsgPackage()) {
            System.out.println(b + " ");
        }
    }
}