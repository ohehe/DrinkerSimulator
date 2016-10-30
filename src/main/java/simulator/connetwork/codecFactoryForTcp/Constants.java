package simulator.connetwork.codecFactoryForTcp;/**
 * Created by Administrator on 2016/10/23.
 */

/**
 * 关于解包装包的静态值
 *
 * @author Administrator
 * @create 2016-10-23 15:43
 */
public class Constants {
    public static final int PREFIX_LEN = 1;
    public static final byte F_PREFIX = 0x02;
    public static final int LENCOUNT_LEN = 2;
    public static final int MSG_TYPE_LEN = 2;
    public static final int RESULT_OK = 0;
    public static final int RESULT_ERROR = 1;
    public static final int MACHINE_NUM_LEN = 14 ;
    public static final int MACHINE_ROOM_TEMPTURE = 25  ;
    public static final int MACHINE_MAX_TEMPTURE = 100  ;
    public static int MAX_MSG_LEN = 0xff;//最大正文长度。

    private Constants() {
    }
}
