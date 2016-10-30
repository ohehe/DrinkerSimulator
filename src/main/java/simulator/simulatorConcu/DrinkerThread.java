package simulator.simulatorConcu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simulator.connetwork.codecFactoryForTcp.Constants;
import simulator.drinkerComponent.ComLights;
import simulator.drinkerComponent.ComWLMK;
import simulator.drinkerComponent.ComWaterTap;
import simulator.drinkerComponent.ComWaterTemperature;
import simulator.drinkerComponent.impl.ImplLights;
import simulator.drinkerComponent.impl.ImplWLMK;
import simulator.drinkerComponent.impl.ImplWaterTap;
import simulator.drinkerComponent.impl.ImplWaterTemperature;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/29.
 */
public class DrinkerThread extends  Thread {
    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    //组件
    private ComLights[] comLights;
    private ComWaterTemperature comWaterTemperature ;
    private ComWaterTap[] comWaterTap ;
    private ComWLMK comWLMK ;


    //开关
    private boolean isON = false;
    //机器编号
    private String machineNum ;
    //开机时间
    private long creationTime = 0;


    /*过程状态*/
    //初始化是否成功
    private boolean isInitialSucced  = true ;


   //一个饮水机一个单独的线程传输。
    //因此将饮水机类和运行自己的线程绑定在一起。
    //构造函数的定义。
    public DrinkerThread(String hostName,int port, String machineNum) throws Exception {
        if(machineNum.getBytes().length!= Constants.MACHINE_NUM_LEN){
            logger.error(this.getClass().getName()+" : 机器编号参数设置错误");
            isON = false ;
            isInitialSucced = false ;
            return ;
        }
        this.machineNum = machineNum ;
        comLights = new ComLights[4] ;
        comWaterTap = new ComWaterTap[4] ;
        for(ComLights c : comLights){
            c = new ImplLights() ;
        }
        for(ComWaterTap c : comWaterTap){
            c = new ImplWaterTap() ;
        }
        comWaterTemperature = new ImplWaterTemperature() ;
        comWLMK = new ImplWLMK(hostName,port,machineNum) ;
        creationTime = new Date().getTime() ;
        isON = true ;
        isInitialSucced = true ;
    }



    //线程方法run()的实现。
    public void run(){
        while(true){
            //1.判断开关状态
            if(){}
            //2.1.关--结束线程 2.2.开--继续循环
            //3.检查网络模块是否正常
            //4.1正常--发送机器状态 并延时5秒  4.2不正常--每隔1min尝试重新发送或者continue
            //5.检查网络模块是否接收到买水请求
            //6.有--水龙头check···检查温度 出水 灯开闪烁  返回买水命令响应
            //7.根据流速判断时间 直到放完水
            //8.返回买水命令响应
            //9.放完水 灯回常开状态 水龙头关闭 温度关

        }
    }
}
