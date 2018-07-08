package view;

import dao.SiteDao;
import entity.Site;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static view.IndexView.p1;

/**
 * 景点推荐页面
 */
public class RecommendPanel extends JPanel{

    public static void main(String[] args) {
    }

    private JSplitPane textSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JSplitPane imageSplitPane = new JSplitPane();

    private ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
    private JList siteList = new JList();
    private DefaultListModel listModel = new DefaultListModel();
    private JList siteRoute = new JList();
    private DefaultListModel routeModel = new DefaultListModel();
    private JLabel sitePicture = new JLabel();

    public int index=0;

    private SiteDao siteDao ;
    private Site site;

    private boolean flag = false;

    public RecommendPanel() {

        siteRoute.setFixedCellHeight(50);
        siteRoute.setBorder(BorderFactory.createTitledBorder("推荐路线:"));
        //单选
        siteRoute.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        updataSiteRoute("default");

        updataSiteList();
        siteList.setFixedCellHeight(20);
        siteList.setBorder(BorderFactory.createTitledBorder("景点列表:"));
        //单选
        siteList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //设置监听

        updataSiteImage("default");

        this.setLayout(new BorderLayout());
        textSplitPane.setDividerLocation(100);
        textSplitPane.setDividerSize(5);
        textSplitPane.setLeftComponent(siteList);
        textSplitPane.setRightComponent(siteRoute);

        imageSplitPane.setDividerLocation(100);
        imageSplitPane.setDividerSize(8);
        imageSplitPane.setLeftComponent(textSplitPane);
        imageSplitPane.setRightComponent(sitePicture);
        this.add(imageSplitPane, BorderLayout.CENTER);

        //景点图片轮播
        Timer timer = new Timer(1500,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (index == images.size()) {
                    index = 0;
                }
                ImageIcon image = images.get(index);
                image.setImage(image.getImage().getScaledInstance(1100,666,Image.SCALE_DEFAULT));
                sitePicture.setIcon(image);
                index++;
            }
        });
        timer.start();

        siteList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                if(!e.getValueIsAdjusting()) {
                    Object obj=((JList)e.getSource()).getSelectedValue();
                    flag = true;
                    p1.updataSiteRoute((String) obj);
                    flag = false;
                    p1.updataSiteImage((String) obj);
                }
            }
        });

        siteRoute.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                if(siteRoute.getValueIsAdjusting() && flag == false) {
//                    int temp = siteRoute.getSelectedIndex();
//                    String t = (String) siteRoute.getModel().getElementAt(temp);
                    Object obj=((JList)e.getSource()).getSelectedValue();
                    JOptionPane.showMessageDialog(RecommendPanel.this, obj);
                }
            }
        });
        this.setVisible(true);
    }

    /**
     * 更新相应景点的推荐路线
     * @param t 景点名
     */
    public void updataSiteRoute(String t) {
        Site site = null;
        try {
            site = new Site().getSiteClass(t);
            routeModel.removeAllElements();
            routeModel.addElement(site.getSiteRoute());
            siteRoute.setModel(routeModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新轮播图片
     * @param t 相应图片景点的名称
     */
    public void updataSiteImage(String t) {

        Site site = null;
        try {
            site = new Site().getSiteClass(t);
            images = site.getSitePicture();
            //设置景点图片
            ImageIcon image = images.get(0);
            image.setImage(image.getImage().getScaledInstance(1100,666,Image.SCALE_DEFAULT));
            sitePicture.setIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新景点名称列表
     */
    public void updataSiteList() {
        ArrayList<String> strings = siteDao.instance().getSiteName();
        System.out.println(strings.size());
        listModel.removeAllElements();
        for (String s : strings) {
            listModel.addElement(s);
        }
        siteList.setModel(listModel);
    }
}
