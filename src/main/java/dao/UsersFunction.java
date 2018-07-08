package dao;

import entity.User;

import java.io.*;

/**
 * 用户功能
 * 1.登录验证，与用户信息文件进行校队
 * 2.注册用户
 */
public class UsersFunction {

    public static void main(String[] args) {
        new UsersFunction("test","test").signUp();
        new UsersFunction("test","test").delUser();
    }
    private File userFile;//储存用户信息文件，用以验证
    private String name;//用户名
    private String password;//密码
    private boolean loginPower=false;//登录权限

    public UsersFunction(String name, String password) {
        this.name = name;
        this.password = password;
        try {
            userFile = new User(name, password).getUsersFile();
        } catch (Exception e) {
            new Exception("用户文件不存在");
        }
    }

    /**
     * 登录用户验证
     * @return loginPower 登录权限
     * @throws IOException
     */
    public boolean LoginingVerify() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(userFile));
        String infos = bufferedReader.readLine();
        String[] info = infos.split(",");
        if (name!=null && password!=null) {
            if (name.equals(info[0]) && password.equals(info[1])) {
                loginPower = true;
                System.out.println("用户验证成功：true");
                return loginPower;
            }
        }
        bufferedReader.close();
        return loginPower;
    }

    /**
     * 注册用户
     */
    public void signUp() {
        try {
            new User(name, password).addUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除用户
     */
    public void delUser() {
        userFile.delete();
    }

}
