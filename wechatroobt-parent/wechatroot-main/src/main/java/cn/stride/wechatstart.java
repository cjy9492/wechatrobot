package cn.stride;

import cn.stride.thread.KeepSignThread;
import cn.stride.utils.PropertiesUtil;
import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.Config;
import cn.zhouyafeng.itchat4j.utils.enums.OsNameEnum;

/**
 * Created by hspcadmin on 2017/5/22.
 */
public class wechatstart {
    public static void main(String[] args) {
        String qrPath =  PropertiesUtil.readValue("locationpath"); // 保存登陆二维码图片的路径
        if(OsNameEnum.LINUX.equals(Config.getOsNameEnum())){//检测是否处于linux环境
             qrPath = "/home/pi/script/weightguard";
        }
        IMsgHandlerFace msgHandler = new MessageHandler(); // 实现IMsgHandlerFace接口的类
        Wechat wechat = new Wechat(msgHandler, qrPath); // 【注入】
        wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
        KeepSignThread kst=new KeepSignThread();
        kst.start();
    }
}
