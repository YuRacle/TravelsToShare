package view;

import entity.StoryFile;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

import static dao.StoryDao.storyDao;
import static view.IndexView.p2;

/**
 * 故事页面
 */
public class StoryPanel extends JPanel{

    public static void main(String[] args) {
        new StoryPanel();
    }

    private Font tFont = new Font("微软雅黑",1,30);
    private Font sFont = new Font("微软雅黑",1,20);
    private Font nFont = new Font("微软雅黑",1,10);
    private JSplitPane splitPane = new JSplitPane();
    private JPanel rightPanel = new JPanel();
    private JList storyList = new JList();
    private DefaultListModel listModel = new DefaultListModel();
    //    private JLabel storyList = new JLabel("故事列表");
    private JLabel title = new JLabel("标题",JLabel.CENTER);
    private JTextPane story = new JTextPane();
    private JLabel num = new JLabel("  游览数:1000  点赞数:100 ");
    private boolean flag = false;

    public StoryPanel() {

        this.setLayout(new BorderLayout());
        this.setSize(1198,661);
        rightPanel.setLayout(null);
        title.setSize(1000,50);
        title.setFont(tFont);
        num.setBounds(10,50,1000,15);
        num.setFont(nFont);
        story.setEditable(false);
        story.setBounds(10,80,1000,300);
        story.setFont(sFont);

        //更新故事列表
        updataStoryList();
        storyList.setFixedCellHeight(30);
        storyList.setBorder(BorderFactory.createTitledBorder("故事列表:"));
        //单选
        storyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //组件透明化
        rightPanel.setOpaque(false);
        splitPane.setOpaque(false);
//        storyList.setOpaque(false);
        title.setOpaque(false);
        num.setOpaque(false);
        story.setOpaque(false);

        rightPanel.add(title);
        rightPanel.add(story);
        rightPanel.add(num);

        splitPane.setDividerLocation(100);
        splitPane.setDividerSize(2);
        splitPane.setLeftComponent(storyList);
        splitPane.setRightComponent(rightPanel);
        this.add(splitPane,BorderLayout.CENTER);

        init();

        //设置监听
        storyList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                if(!e.getValueIsAdjusting() && flag == false) {
                    Object obj=((JList)e.getSource()).getSelectedValue();
                    System.out.println("aa");
//                int temp = storyList.getSelectedIndex();
//                String t = (String) storyList.getModel().getElementAt(temp);
                    updataStory(obj.toString());
                }
            }
        });
        this.setVisible(true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //绘制一张背景图片  2.jpg是图片的路径  自己设定为自己想要添加的图片
        Image image = new ImageIcon("images/dandelion.jpg").getImage();
        g.drawImage(image, 0, 0, 19200/15,12000/15,this);
    }

    public void init() {
        try {
            StoryFile storyFile = new StoryFile().getStoryClass("广州一日游");
            title.setText(storyFile.getTitle());
            story.setText(String.valueOf(storyFile.getStory()));
            num.setText("游览: "+storyFile.getReadNum()+"  点赞: "+storyFile.getLoveNum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新故事详细页面,更新为相依的故事详细页面
     * @param t 相应故事的标题
     */
    public void updataStory(String t) {
        try {
            StoryFile storyFile = new StoryFile().getStoryClass(t);
            title.setText(storyFile.getTitle());
            story.setText(String.valueOf(storyFile.getStory()));
            num.setText("游览: "+storyFile.getReadNum()+"  点赞: "+storyFile.getLoveNum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新故事页面的故事列表
     */
    public void updataStoryList() {
        flag = true;
        ArrayList<String> strings = storyDao.instance().getStoryTitle();
        listModel.removeAllElements();
        for (String s : strings) {
            listModel.addElement(s);
        }
        storyList.setModel(listModel);
        flag = false;
    }
}
