package simulator.entity.tcpwarpper;

/**
 * Created by Administrator on 2016/10/20.
 */

import junit.framework.TestCase;

import java.util.Date;

public class AcceptCommandFrameEntityTest extends TestCase {
    public void testInit() throws Exception {
        byte[] a = new byte[]{
                0x02,//包头
                '2', '6',//正文长度。
                '0', '1',
                '1', '2', '1', '2', '1', '2', '1', '2', '1', '2', '1', '2', '1', '2',//机器号
                '1',
                '6', '5',
                '4', '3', '1', '1',
                '1', '1', '1', '1'
                , '1', '2', '1', '2', '1', '2', '1', '2', '1', '2', '1'
        };

        AcceptCommandFrameEntity acceptCommandFrameEntity = new AcceptCommandFrameEntity(a, 41);


    }
}

