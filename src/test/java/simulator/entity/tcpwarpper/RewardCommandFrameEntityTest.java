package simulator.entity.tcpwarpper;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/21.
 */
public class RewardCommandFrameEntityTest extends TestCase {

    public void testInit() throws Exception {
        RewardCommandFrameEntity rewardFrameEntity = new RewardCommandFrameEntity("12345678", "01234567891234", "1", 0x65, 4355, new Date());
        rewardFrameEntity.init();
        for (byte b : rewardFrameEntity.getSendMsgPackage()) {
            System.out.println(b + " ");
        }
    }
}
