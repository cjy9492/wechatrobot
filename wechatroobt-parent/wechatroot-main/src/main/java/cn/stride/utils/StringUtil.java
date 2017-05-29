package cn.stride.utils;

import cn.zhouyafeng.itchat4j.api.WechatTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hspcadmin on 2017/5/26.
 */
public class StringUtil {
    //从配置文件读取带|的信息
    public static List getList(String name) {
        List list =new ArrayList();
        if(name!=null){
        if(name.indexOf("|")==-1){
            list.add(name);
        }
        else {
            String a[] = name.split("\\|");
            for (int i = 0; i <a.length ; i++) {
                list.add(a[i]);
            }
        }
        }
        return list;
    }
    //根据微信好友昵称获取到本次登录的用户名
    public static List getUserNameByNickName(List name) {
        List list =new ArrayList();
        if(name!=null) {
            for (int i = 0; i < name.size(); i++) {
                if (WechatTools.getUserNameByNickName(name.get(i).toString()) != null) {
                    list.add(WechatTools.getUserNameByNickName(name.get(i).toString()));
                }
            }
        }
        return list;
    }
    //判断群消息中是否有关键词
    public static Boolean isgroupcall(List name,String text) {
        for (int i = 0; i < name.size(); i++) {
            if(text.contains(name.get(i).toString())){
                return true;
            }
        }
        return false;
    }

    public static boolean isexceptname(List exceptid,String userId, boolean exceptname){
        for (int i = 0; i < exceptid.size(); i++) {
            if(exceptid.get(i).toString().equals(userId)){
                exceptname=true;
            }
        }
        return exceptname;
    }
    public static boolean isCache(Map groupreply,String touserId, boolean grouprep){
        if(groupreply.containsKey(touserId)){
            if(!"0".equals(groupreply.get(touserId).toString())){
                grouprep=true;
            }
            else {
                grouprep=false;
            }
        }else{
            grouprep=false;
        }
        return grouprep;
    }
    public static void countGroupreply(Map groupreply,String touserId, List groupname,String text){
        //在群消息中当前消息的用户如果存在缓存中
        if(groupreply.containsKey(touserId)){
            //就将自动响应次数-1
            int a=Integer.parseInt(groupreply.get(touserId).toString());
            a=a-1;
            groupreply.put(touserId,a);
            if(StringUtil.isgroupcall(groupname,text)){//如果语句中包含了关键词，那么再次重置为3
                groupreply.put(touserId,3);
            }
        }else {
            groupreply.put(touserId,3);//如果不在缓存中，能进入到这步，那么可以肯定包含了关键词，所以重置为3
        }
    }

    public static boolean isFirstreply(Map firstreply,String userId){
        if(firstreply.containsKey(userId))
        {
            return false;
        }
        return true;
    }
}
