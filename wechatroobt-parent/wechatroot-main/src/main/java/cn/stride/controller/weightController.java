package cn.stride.controller;

import cn.stride.bean.GoalBean;
import cn.stride.bean.NowweightBean;
import cn.stride.dbutil.ConnectDb;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hspcadmin on 2017/5/27.
 */
public class weightController {
    public static String  addGoalweight(String text){
        String result="";
        String a[] = text.split("\\|");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(d);
        Connection conn = ConnectDb.Connect();
        //创建SQL执行工具
        QueryRunner qRunner = new QueryRunner();
        //执行SQL插入
        int n = 0;
        try {
            n = qRunner.update(conn, "insert into goal(name,goalweight,time) values('"+a[1]+"','"+a[2]+"','"+date+"')");
        } catch (SQLException e) {
            e.printStackTrace();
            result ="添加体重目标失败！";
            //MessageTools.sendMsgById(result, userId);
            return result;
        }
        finally {
            //关闭数据库连接
            DbUtils.closeQuietly(conn);
        }
        if(n!=0){
            result ="成功添加体重目标！";
        }
        //MessageTools.sendMsgById(result, userId);
        return result;
    }
    public static String addWeight(String text){
        String result ="";
        String a[] = text.split("\\|");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(d);
        Connection conn = ConnectDb.Connect();
        //创建SQL执行工具
        QueryRunner qRunner = new QueryRunner();
        //执行SQL插入
        int n = 0;
        try {
            n = qRunner.update(conn, "insert into nowweight(userid,weight,time) values('1','"+a[1]+"','"+date+"')");
        } catch (SQLException e) {
            e.printStackTrace();
            result ="添加体重失败！";
            //MessageTools.sendMsgById(result, userId);
            return result;
        }
        finally {
            //关闭数据库连接
            DbUtils.closeQuietly(conn);
        }
        if(n!=0){
            result ="成功添加体重！";
        }
        //MessageTools.sendMsgById(result, userId);
        return result;

    }
    public static String getWeight(){
        String result ="";
        Connection conn = ConnectDb.Connect();
        //创建SQL执行工具
        QueryRunner qRunner = new QueryRunner();
        try {
            NowweightBean weight =  qRunner.query(conn, "select weight,time from nowweight order by id desc", new BeanHandler<NowweightBean>(NowweightBean.class));
            result="目前我的体重是"+weight.getWeight()+"斤";
            GoalBean user =  qRunner.query(conn, "select name,goalweight,time from goal order by id desc", new BeanHandler<GoalBean>(GoalBean.class));
            Float sum =Float.parseFloat(user.getGoalweight())-Float.parseFloat(weight.getWeight());
            if(sum<=0){
                result=result+",我的目标体重是"+user.getGoalweight()+"斤，已经达到体重目标啦！";
            }
            else {
                result=result+",我的目标体重是"+user.getGoalweight()+"斤，距离目标体重还差"+sum+"斤";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result="查询体重出现问题啦！请重试！";
            //MessageTools.sendMsgById(result, userId);
            return result;
        }
        finally {
            //关闭数据库连接
            DbUtils.closeQuietly(conn);
        }
        //MessageTools.sendMsgById(result, userId);
        return result;
    }
}
