package cn.stride.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by hspcadmin on 2017/5/27.
 */
//图灵机器人消息处理类
public class tulinController {
    public static String msghandle(JSONObject obj){
        String result ="";
        if (obj.getString("code").equals("100000")) {
            result = obj.getString("text");
        }
        else if (obj.getString("code").equals("200000")) {
            result = obj.getString("text");
            result=result+"\n";
            result = result+ obj.getString("url");
        }
        else if (obj.getString("code").equals("302000")) {
            result = obj.getString("text");
            JSONArray jsonArray = obj.getJSONArray("list");  //取出json对象里面“root”数组，并转换成JSONArray对象
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj1= (JSONObject) jsonArray.get(i);
                result=result+"\n";
                result=result+obj1.getString("article");
                result=result+obj1.getString("detailurl");
            }
        }
        else if (obj.getString("code").equals("308000")) {
            result = obj.getString("text");
            JSONArray jsonArray = obj.getJSONArray("list");  //取出json对象里面“root”数组，并转换成JSONArray对象
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj1= (JSONObject) jsonArray.get(i);
                result=result+"\n";
                result=result+obj1.getString("icon");
                result=result+"\n";
                result=result+obj1.getString("name");
                result=result+"\n";
                result=result+obj1.getString("info");
                result=result+"\n";
                result=result+obj1.getString("detailurl");
            }
        }
        else {
            result = "处理有误";
        }
    return result;
    }
}
