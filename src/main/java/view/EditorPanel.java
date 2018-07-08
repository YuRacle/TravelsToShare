package view;

import entity.StoryFile;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

import static view.IndexView.p2;

/**
 * 故事创建页面
 */
public class EditorPanel extends JPanel{
    private JTextPane textPane = new JTextPane(); //文本窗格，编辑窗口
    private JLabel statusBar = new JLabel(); //状态栏
    private JFileChooser filechooser = new JFileChooser(); //文件选择器
    private JLabel t = new JLabel("标题: ");
    private JTextArea title = new JTextArea();//题目
    private JToolBar jToolBar;
    private Font tFont = new Font("微软雅黑",1,30);
    private Font sFont = new Font("微软雅黑",1,20);

    public EditorPanel() {
        //Action数组,各种操作命令
        Action[] actions = {
                new OpenAction(),
                new SaveAction(),
                new NewAction(),
                new CutAction(),
                new CopyAction(),
                new PasteAction()
        };

        this.setLayout(null);
        jToolBar = createJToolBar(actions);
        jToolBar.setSize(1200,30);
        t.setBounds(400,30,100,50);
        t.setFont(tFont);
        title.setBounds(480,35,300,50);
        title.setFont(tFont);
        textPane.setBounds(0,80,1200,590);
        textPane.setFont(sFont);

        title.setBackground(new Color(248,248,255));
        textPane.setBackground(new Color(210,224,165));
        title.setOpaque(false);
//        textPane.setOpaque(false);

        this.add(t);
        this.add(jToolBar); //增加工具栏
        this.add(title);
        this.add(textPane); //增加文本窗格
        this.add(statusBar); //增加状态栏

        setVisible(true);  //设置窗口可视
    }

    //创建工具条
    private JToolBar createJToolBar(Action[] actions) {
        JToolBar toolBar = new JToolBar(); //实例化工具条
        for (int i = 0; i < actions.length; i++) {
            JButton bt = new JButton(actions[i]); //实例化新的按钮
            bt.setRequestFocusEnabled(false); //设置不需要焦点
            toolBar.add(bt); //增加按钮到工具栏
        }
        return toolBar;  //返回工具栏
    }

    //清空文件命令
    class NewAction extends AbstractAction {
        public NewAction() {
            super("清空");
        }
        public void actionPerformed(ActionEvent e) {
            textPane.setDocument(new DefaultStyledDocument()); //清空文档
            title.setDocument(new DefaultStyledDocument());
        }
    }

    //打开文件命令
    class OpenAction extends AbstractAction {
        public OpenAction() {
            super("打开");
        }
        public void actionPerformed(ActionEvent e) {
            int i = filechooser.showOpenDialog(EditorPanel.this); //显示打开文件对话框
            if (i == JFileChooser.APPROVE_OPTION) { //点击对话框中打开选项
                File f = filechooser.getSelectedFile(); //得到选择的文件
                try {
                    InputStream is = new FileInputStream(f); //得到文件输入流
                    textPane.read(is, "d"); //读入文件到文本窗格
                } catch (Exception ex) {
                    ex.printStackTrace();  //输出出错信息
                }
            }
        }
    }

    //提交命令
    class SaveAction extends AbstractAction {
        public SaveAction() {
            super("提交");
        }
        public void actionPerformed(ActionEvent e) {
            if (title.getText().length() <= 1) {
                JOptionPane.showMessageDialog(EditorPanel.this, "故事提交失败,请注意:故事标题字数不能少于2!");

            }else if(textPane.getText().length() < 5) {
                JOptionPane.showMessageDialog(EditorPanel.this, "故事提交失败,请注意:故事正文字数不能少于5!");

            }else {
                StoryFile storyFile = new StoryFile();
                storyFile.setTitle(title.getText()).addStory(textPane.getText());
                try {
                    storyFile.writeStoryClass(storyFile);
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(EditorPanel.this, "故事提交失败,请注意:故事标题不能为空!");
                    e1.printStackTrace();
                }

                JOptionPane.showMessageDialog(EditorPanel.this, "提交成功");
                //TODO 更新故事
                p2.updataStoryList();
                p2.updataStory(title.getText());
            }
        }
    }

    //剪切命令
    class CutAction extends AbstractAction {
        public CutAction() {
            super("剪切");
        }
        public void actionPerformed(ActionEvent e) {
            textPane.cut();  //调用文本窗格的剪切命令
        }
    }
    //拷贝命令
    class CopyAction extends AbstractAction {
        public CopyAction() {
            super("拷贝");
        }
        public void actionPerformed(ActionEvent e) {
            textPane.copy();  //调用文本窗格的拷贝命令
        }
    }
    //粘贴命令
    class PasteAction extends AbstractAction {
        public PasteAction() {
            super("粘贴");
        }
        public void actionPerformed(ActionEvent e) {
            textPane.paste();  //调用文本窗格的粘贴命令
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //绘制一张背景图片  2.jpg是图片的路径  自己设定为自己想要添加的图片
        Image image = new ImageIcon("images/editorBg.jpg").getImage();
        g.drawImage(image, 0, 0, 1920,1200,this);
    }
}
