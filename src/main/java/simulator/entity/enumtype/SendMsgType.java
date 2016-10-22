package simulator.entity.enumtype;/**
 * Created by Administrator on 2016/6/30.
 */

/**
 * 发送消息种类
 *
 * @author Administrator
 * @create 2016-06-30 21:16
 */
public enum SendMsgType {

    MACHINE_STATE(new byte[]{'0', 'B'}, "饮水机设备当前状态"),//上传饮水机设备状态的信息定义。
    RECEIVE_COMMAND(new byte[]{'0', 'C'}, "饮水机接收监控服务器发来的命令"),
    REWARD_COMMAND(new byte[]{'0', 'C'}, "饮水机回应命令");//当饮水机完成放水后，再向监控服务器发送相关回应命令。

    /*
    以下两个变量四上面三个函数分别对应的两个参数。比如说，prefix 就是指new byte[]{'0','B'},
    而typeStr 则是指后面的信息解释说明部分。
     */
    private byte[] prefix;
    private String typeStr;

    SendMsgType(byte[] prefix, String typeStr) {
        this.prefix = prefix;
        this.typeStr = typeStr;
    }

    public byte[] getPrefix() {
        return prefix;
    }

    public void setPrefix(byte[] prefix) {
        this.prefix = prefix;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }


}
