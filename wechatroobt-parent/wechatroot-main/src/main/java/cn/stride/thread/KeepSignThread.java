package cn.stride.thread;

import cn.zhouyafeng.itchat4j.Wechat;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.core.MsgCenter;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.SleepUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hero on 2017/5/28 0028.
 */
public class KeepSignThread {
    private static final Logger LOG = LoggerFactory.getLogger(KeepSignThread.class);
    private Core core = Core.getInstance();

    public void start() {
        LOG.info("+++++++++++++++++++开始维持登录状态+++++++++++++++++++++");
        new Thread(new Runnable() {
            @Override
            public void run() {
               while (true){
                   MessageTools.sendMsgById(null, core.getUserSelf().getString("UserName"));
                   SleepUtils.sleep(600000);
               }
            }
        }).start();
    }
}
