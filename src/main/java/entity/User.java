package entity;

import java.io.*;

/**
 * 用户信息文件库
 * 一个用户对应一个文件，文件包含用户名和密码（name,password）
 * 目录（usersFile/name.txt）
 */
public class User {
    public static void main(String[] args) throws Exception {
//            new User("1","2").addUser();
//            new User("1","2").reviseName("aa");
            new User("aa","2").revisePassword("bb");
    }

    private File userFile;//用户文件
    private String name;//用户名
    private String password;//用户密码

    public User() {

    }
    /**
     * 初始化信息文件
     * @param name 用户名
     * @param password 密码
     * @throws Exception
     */
    public User(String name, String password) throws Exception {
        //检测用户名是否已存在,存在报异常，不存再则创建
        this.name = name;
        this.password = password;
        userFile = new File("usersFile/"+ name +".txt");
    }

    /**
     * 注册用户时添加用户信息文件
     * @throws IOException
     */
    public void addUser() throws Exception {
        BufferedWriter bufferedWriter = null;
        String info = name + "," +password;
        if (!userFile.exists()) {
            bufferedWriter = new BufferedWriter(new FileWriter(userFile,true));
            userFile.createNewFile();

            try {
                bufferedWriter.write(info,0,info.length());
            } catch (IOException e) {
                new Exception("用户创建失败：用户名或密码不符合");
            }
            bufferedWriter.close();
            System.out.println("用户"+name+"创建成功");
        }else {
            throw new Exception("用户名已存在");
        }
    }

    /**
     * 修改用户名
     * @param name 新的用户名
     */
    public void reviseName(String name) {
        BufferedWriter bufferedWriter = null;
        String info = name + "," +password;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(userFile));
            bufferedWriter.write(info,0,info.length());
        } catch (IOException e) {
            new Exception("用户创建失败：用户名或密码不符合");
        }finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("用户名"+name+"修改成功");

        File file = new File("usersFile/"+ name +".txt");
        userFile.renameTo(file);
    }

    /**
     * 修改用户密码
     * @param password 新的用户密码
     */
    public void revisePassword(String password) {
        BufferedWriter bufferedWriter = null;
        String info = name + "," +password;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(userFile));
            bufferedWriter.write(info,0,info.length());
        } catch (IOException e) {
            new Exception("用户创建失败：用户名或密码不符合");
        }finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("用户"+name+"密码修改成功");
    }

    public File getUsersFile() {
        return userFile;
    }
}
