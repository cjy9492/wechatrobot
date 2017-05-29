package cn.stride;

import cn.stride.bean.GoalBean;
import cn.stride.bean.NowweightBean;
import cn.stride.controller.tulinController;
import cn.stride.controller.weightController;
import cn.stride.dbutil.ConnectDb;
import cn.stride.utils.PropertiesUtil;
import cn.stride.utils.StringUtil;
import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hspcadmin on 2017/5/22.
 */
public class MessageHandler implements IMsgHandlerFace {
    MyHttpClient myHttpClient = Core.getInstance().getMyHttpClient();
    String apiKey = PropertiesUtil.readValue("apiKey"); // 这里是我申请的图灵机器人API接口，每天只能5000次调用，建议自己去申请一个，免费的:)
    Logger logger = Logger.getLogger("TulingRobot");
    private Core core = Core.getInstance();
    private boolean opentulin=true;//是否启动图灵机器人  true启动 flash 关闭
    private boolean exceptname=false;//是否正在和屏蔽人聊天
    private boolean grouprep=false;//当前用户是否已经在群里说过关键词
    String start =PropertiesUtil.readValue("wechatstart");
    String close =PropertiesUtil.readValue("wechatclose");
    Map firstreply = new HashMap();//缓存在群中说出关键词的用户ID 格式类型{用户者id,剩余相应次数 默认为3}
    Map groupreply = new HashMap();//缓存在群中说出关键词的用户ID 格式类型{用户者id,剩余相应次数 默认为3}
    List excepttouserId = StringUtil.getList(PropertiesUtil.readValue("exceptname"));
    List groupname =StringUtil.getList(PropertiesUtil.readValue("groupname"));
    String fertilizers =PropertiesUtil.readValue("fertilizers");
    @Override
    public String textMsgHandle(JSONObject msg) {
        List exceptid =StringUtil.getUserNameByNickName(excepttouserId);
        String result = "";
        String text = msg.getString("Text");//获取消息内容
        String userId = msg.getString("FromUserName");//获取当前消息发送者的id
        String sendId = msg.getString("ToUserName");//获取当前消息接收者的id
        String touserId = msg.getString("groupSendId");//如果是群消息则获取到群消息发送者的ID
        //获取到这条消息是否是屏蔽人发送的
        exceptname=StringUtil.isexceptname(exceptid,userId,exceptname);
        if((!userId.equals(core.getUserSelf().getString("UserName")))&&!exceptname||userId.equals(sendId)) {
            if (text.contains(close)) {
                opentulin = false;
                firstreply.clear();
            }
            if (text.contains(start)) {
                opentulin = true;
            }
            //增肥计划相应接口请无视
            if("true".equals(fertilizers)){
            if(text.startsWith("添加体重目标|")){
                result= weightController.addGoalweight(text);
                return result;
            }
            if(text.startsWith("添加体重|")){
                result=weightController.addWeight(text);
                return result;
            }
            if(text.equals("获取体重")){
                result=weightController.getWeight();
                return result;
            }
            }
            //判断是否是群消息和是否开启图灵机器人
            if (!msg.getBoolean("groupMsg") && opentulin) {
                if(StringUtil.isFirstreply(firstreply,userId)){
                    String reply ="本人目前不在手机旁，无法及时回复，目前小鱼代为回复，你可以和小鱼聊会天，关闭小鱼请回复"+close+"，唤醒小鱼请回复"+start+"。小鱼目前还在成长中，有啥得罪的地方还请多多包涵。";
                    MessageTools.sendMsgById(reply,userId);
                    firstreply.put(userId,1);
                }
                String url = "http://www.tuling123.com/openapi/api";
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("key", apiKey);
                paramMap.put("info", text);
                paramMap.put("userid", userId.substring(2,6));
                String paramStr = JSON.toJSONString(paramMap);
                try {
                    HttpEntity entity = myHttpClient.doPost(url, paramStr);
                    result = EntityUtils.toString(entity, "UTF-8");
                    JSONObject obj = JSON.parseObject(result);
                    result=tulinController.msghandle(obj);
                } catch (Exception e) {
                    logger.info(e.getMessage());
                }
                //MessageTools.sendMsgById(result, userId);
                return result;

            }
            //群消息处理逻辑
            else if (opentulin) {
                String url = "http://www.tuling123.com/openapi/api";
                //判断当前消息用户是否存在缓存中
                grouprep=StringUtil.isCache(groupreply,touserId,grouprep);

                if (StringUtil.isgroupcall(groupname,text)||grouprep) {
                    //计算已经自动响应了几次
                    StringUtil.countGroupreply(groupreply,touserId,groupname,text);
                    //封装消息 发送给图灵
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("key", apiKey);
                    paramMap.put("info", text);
                    paramMap.put("userid", userId.substring(2,6));
                    String paramStr = JSON.toJSONString(paramMap);
                    try {
                        HttpEntity entity = myHttpClient.doPost(url, paramStr);
                        result = EntityUtils.toString(entity, "UTF-8");
                        JSONObject obj = JSON.parseObject(result);
                        result=tulinController.msghandle(obj);
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                    //MessageTools.sendMsgById(result, userId);
                    return result;
                }
            }

        }
        exceptname=false;
        return null;
    }
    @Override
    public String picMsgHandle(JSONObject msg) {
        return null;
    }

    @Override
    public String voiceMsgHandle(JSONObject msg) {
        return null;
    }

    @Override
    public String viedoMsgHandle(JSONObject msg) {
        return null;
    }

    @Override
    public String nameCardMsgHandle(JSONObject msg) {
        return null;
    }
}
