package util;

import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by YuRacle on 2018/5/29.
 */
public class RobotTool {
    //测试
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String question;
        while (true) {
            question = sc.nextLine();
            System.out.println("你:"+question);
            System.out.println("robot:"+send(question));
        }
    }

    public static String send(String cmd) {
        //图灵网站上的secret
//      String secret = "请输入你自己的secret";
        //图灵网站上的apiKey
        String apiKey = "64da73587a394acb8ad727f64960036f";
        //json数据
        String info = cmd;
        //用户名
        String userid = "00001";

        //封装请求参数
        JSONObject json = new JSONObject();
        json.put("key", apiKey);
        json.put("info", info);
        json.put("userid", userid);
        //请求图灵api
        String result = util.PostServer.SendPost(json.toString(), "http://www.tuling123.com/openapi/api");
        JSONObject retJson = JSONObject.fromObject(result);
        return retJson.getString("text");
    }
}
