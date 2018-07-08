package dao;

import entity.Site;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuRacle on 2018/5/21.
 */
public class SiteDao {

    public static SiteDao siteDao;
    //所有景点集合
    private ArrayList<Site> sites = new ArrayList<Site>();
    //所有景点图片集合
    private ArrayList<Image> sitesimages;
    //所有景点名称集合
    private ArrayList<String> sitesName = new ArrayList<String>();

    public static SiteDao instance() {
        if (siteDao == null)
            siteDao = new SiteDao();
        return siteDao;
    }

    //得到所有景点的名称
    public ArrayList<String> getSiteName() {
        sitesName.clear();
        File file = new File("siteFile");
        for (String s : file.list()) {
            String[] strings = s.split("\\.");
            sitesName.add(strings[0]);
        }
        return sitesName;
    }


}
