package view;

import util.RobotTool;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by YuRacle on 2018/5/29.
 */
public class RobotPanel extends JPanel {

    public JTextArea textArea = new JTextArea();
    public JScrollPane scrollPane;
    public JPanel jPanel = new JPanel();
    public ArrayList<JLabel> picLabel = new ArrayList<JLabel>();
    public ArrayList<JLabel> textLabel = new ArrayList<JLabel>();
    public JButton button1 = new JButton("清空");
    public JButton button2 = new JButton("发送");
    public JLabel text1;
    public JLabel text2;
    public JLabel texts;

    public int x1 = 80,x2 = 410;
    public int y1 = 120,y2 = 50;

    public RobotPanel() {

        this.setLayout(null);

        jPanel.setLayout(null);

        textArea.setBounds(400,440,700,160);
        textArea.setFont(new Font("微软雅黑",1,20));
        textArea.setBackground(new Color(240,159,49));

        button1.setBounds(910,560,80,30);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setDocument(new DefaultStyledDocument()); //清空文档
                updateUI();
            }
        });
        button2.setBounds(1010,560,80,30);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTalk(textArea.getText());
                textArea.setDocument(new DefaultStyledDocument()); //清空文档
                jPanel.repaint();
                updateUI();
            }
        });

        scrollPane = new JScrollPane(jPanel);
        scrollPane.setOpaque(false);
        scrollPane.doLayout();
        scrollPane.setBounds(400,50,700,370);
//        jPanel.setPreferredSize(new Dimension(680,370));
        jPanel.setBounds(400,50,700,370);
        jPanel.setOpaque(false);
        scrollPane.setBackground(Color.red);
        //滚动条自动出现
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //滚动条一直出现
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        JLabel heads = new JLabel();
        JLabel pics = new JLabel();
        texts = new JLabel("我叫大白,很高兴认识你!");
        texts.setFont(new Font("微软雅黑",1,15));
        ImageIcon images = new ImageIcon("images/baymaxhead.jpg");
        images.setImage(images.getImage().getScaledInstance(300/6,300/6,Image.SCALE_DEFAULT));
        heads.setIcon(images);
        heads.setBounds(x1-60,y2,50,50);

        ImageIcon image2 = new ImageIcon("images/say2.png");
        image2.setImage(image2.getImage().getScaledInstance(848/4,282/6,Image.SCALE_DEFAULT));
        pics.setBounds(x1,y2,850/4,285/6);
        texts.setBounds(x1+35,y2-7,180,60);
        pics.setIcon(image2);

        texts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(RobotPanel.this, texts.getText());
            }
        });

        jPanel.add(heads);
        jPanel.add(texts);
        jPanel.add(pics);

        this.add(button1);
        this.add(button2);
        this.add(scrollPane);
        this.add(textArea);

        y1 += 70;
        y2 += 70;
        setVisible(true);
    }

    public void addTalk(String s) {
        JLabel head1 = new JLabel();
        JLabel head2 = new JLabel();
        JLabel pic1 = new JLabel();
        JLabel pic2 = new JLabel();
        text1 = new JLabel(s);
        text2 = new JLabel();
        text1.setFont(new Font("微软雅黑",1,15));
        text2.setFont(new Font("微软雅黑",1,15));

        text2.setText(new RobotTool().send(s));
        System.out.println(text2.getText());

        ImageIcon image3 = new ImageIcon("images/my.png");
        image3.setImage(image3.getImage().getScaledInstance(567 * 50/567,567 * 50/567,Image.SCALE_DEFAULT));
        head1.setIcon(image3);
        head1.setBounds(x2+220,y2,50,50);

        ImageIcon image4 = new ImageIcon("images/baymaxhead.jpg");
        image4.setImage(image4.getImage().getScaledInstance(300/6,300/6,Image.SCALE_DEFAULT));
        head2.setIcon(image4);
        head2.setBounds(x1-60,y1,50,50);

        ImageIcon image1 = new ImageIcon("images/say1.png");
        image1.setImage(image1.getImage().getScaledInstance(848/4,282/6,Image.SCALE_DEFAULT));
        pic1.setBounds(x2,y2,850/4,285/6);
        text1.setBounds(x2+30,y2-7,180,60);

        ImageIcon image2 = new ImageIcon("images/say2.png");
        image2.setImage(image2.getImage().getScaledInstance(848/4,282/6,Image.SCALE_DEFAULT));
        pic2.setBounds(x1,y1,850/4,285/6);
        text2.setBounds(x1+30,y1-7,180,60);

        pic1.setIcon(image1);
        pic2.setIcon(image2);

        jPanel.add(text1);
        jPanel.add(text2);
        jPanel.add(pic1);
        jPanel.add(pic2);
        jPanel.add(head1);
        jPanel.add(head2);
        y1 += 140;
        y2 += 140;

        jPanel.setPreferredSize(new Dimension(680,y1+70));
        jPanel.validate();
        JScrollBar jscrollBar = scrollPane.getVerticalScrollBar();
        jscrollBar.setValue(jscrollBar.getMaximum());
        jscrollBar.updateUI();

        text1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(RobotPanel.this, text1.getText());
            }
        });
        text2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(RobotPanel.this, text2.getText());
            }
        });
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //绘制一张背景图片  2.jpg是图片的路径  自己设定为自己想要添加的图片
        Image image = new ImageIcon("images/Baymax.jpg").getImage();
        g.drawImage(image, 0, 0, 1920 * 5/7,1080 * 5/7,this);
    }

}
