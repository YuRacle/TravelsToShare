package entity;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by YuRacle on 2018/5/19.
 */
public class Site implements Serializable{

    public static void main(String[] args) throws Exception {
        Site site = new Site("default");
        site.setSiteRoute("中国->广州");
        site.writeSiteClass();
        Site site1 = new Site().getSiteClass(site.getSiteName());
        System.out.println(site1.getSitePicture());

        Site site2 = new Site("广州大学城");
        site2.setSiteRoute("广州大学城->广东药科大学->华南理工大学");
        site2.writeSiteClass();
        Site site3 = new Site().getSiteClass(site2.getSiteName());
        System.out.println(site3.getSitePicture());
    }

    private String siteName;//景点名
    private String siteRoute;//推荐路线
    private ArrayList<ImageIcon> sitePicture = new ArrayList<ImageIcon>();//景点图片
    private File siteFile;
    //序列化与反序列化存储类与读取类，无法序列化成员（加transient）
    transient ObjectInputStream inputStream;
    transient ObjectOutputStream outputStream;

    /**
     * 通过序列号储存景点类
     * @throws IOException
     */
    public void writeSiteClass() throws IOException {

        siteFile = new File("siteFile/"+siteName+".obj");
        if (!siteFile.exists()) {
            siteFile.createNewFile();
        }
        outputStream = new ObjectOutputStream(new FileOutputStream(siteFile));

        outputStream.writeObject(this);
        outputStream.close();
    }

    /**
     * 通过反序列号获取景点类
     * @param title 景点名
     * @return 景点类
     * @throws Exception
     */
    public Site getSiteClass(String title) throws Exception {

        File site  = new File("siteFile/"+title+".obj");
        if (!site.exists()) {
            throw new Exception("景点文件不存在");
        }else {
            inputStream = new ObjectInputStream(new FileInputStream(site));
            Site mySite = (Site) inputStream.readObject();
            inputStream.close();
            return mySite;
        }
    }

    public Site() {
    }

    public Site(String siteName) {
        this.siteName = siteName;
        addSitePicture();
    }

    public void addSitePicture() {
        ImageIcon image = null;
        for (int i = 1; i <= 3; i++) {
            image = new ImageIcon("images/"+siteName+" ("+i+")"+".jpg");
            if (image != null) {
                System.out.println(image);
                sitePicture.add(image);
                System.out.println(i);
            }else {
                break;
            }
        }
        this.setSitePicture(sitePicture);
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setSiteRoute(String siteRoute) {
        this.siteRoute = siteRoute;
    }

    public void setSitePicture(ArrayList<ImageIcon> sitePicture) {
        this.sitePicture = sitePicture;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteRoute() {
        return siteRoute;
    }

    public ArrayList<ImageIcon> getSitePicture() {
        return sitePicture;
    }
}
