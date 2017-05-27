package cn.stride;

import com.alibaba.fastjson.JSONObject;

/**
 * Hello world!
 *
 */
//没啥用的类
public class App
{
    public static void main( String[] args )
    {JSONObject msg = new JSONObject();
        System.out.println( msg.getString("FromUserName").contains("@@") );
    }
}
