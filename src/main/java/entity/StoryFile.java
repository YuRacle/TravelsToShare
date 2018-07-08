package entity;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 故事+评论文件库
 */
public class StoryFile implements Serializable {

    public static void main(String[] args) throws Exception {
        StoryFile storyFile = new StoryFile();
        storyFile.setTitle("广州一日游").addStory("我在广州游了一天").addComment("2333");
        storyFile.writeStoryClass(storyFile);
        StoryFile storyFile1 = storyFile.getStoryClass("广州一日游");
        System.out.println(storyFile1.getStory());
        System.out.println(storyFile1.getComment());
    }

    private String title;//故事标题
    private String author;//作者
    private StringBuilder story = new StringBuilder();//故事正文
    private ArrayList<String> comment = new ArrayList<String>();//故事评论
    private Image image;
    private int loveNum=0;//点赞数
    private int readNum=0;//阅读数
    private int collectNum=0;//收藏数
    private int commentNum=0;//评论数
    private File storyFile;//故事文件
    private String data;//故事发布时间
    //时间格式
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    //序列化与反序列化存储类与读取类，无法序列化成员（加transient）
    transient ObjectInputStream inputStream;
    transient ObjectOutputStream outputStream;
    //private File numFile;//存放各类数量文件
    //private File commentFile;//评论文件

    public StoryFile() {
    }
    /**
     * 通过序列化存储故事对象
     * @param story 故事类
     * @throws IOException
     */
    public void writeStoryClass(StoryFile story) throws IOException {
        if (title == null) {
            throw new IOException("故事没有设置标题，无法创建故事！");
        }
        storyFile = new File("storyFile/"+title+".obj");
        if (!storyFile.exists()) {
            storyFile.createNewFile();
        }
        outputStream = new ObjectOutputStream(new FileOutputStream(storyFile));

        outputStream.writeObject(story);
        outputStream.close();

    }

    /**
     * 通过反序列化读取故事对象
     * @param title 故事标题
     * @return 故事对象
     * @throws Exception
     */
    public StoryFile getStoryClass(String title) throws Exception {

        File story  = new File("storyFile/"+title+".obj");
        if (!story.exists()) {
            throw new Exception("故事文件不存在");
        }else {
            inputStream = new ObjectInputStream(new FileInputStream(story));
            StoryFile myStory = (StoryFile) inputStream.readObject();
            inputStream.close();
            return myStory;
        }
    }

    public void upCommentNum() {
        commentNum++;
    }

    public void upCollectNum() {
        collectNum++;
    }

    public void upLoveNum() {
        loveNum++;
    }

    private void upReadNum() {
        readNum++;
    }

    public void setImage(String p) {
        image = new ImageIcon("images/"+p).getImage();
    }

    public StoryFile setTitle(String title) {
        this.title = title;
        return this;
    }

    public StoryFile setAuthor(String author) {
        this.author = author;
        return this;
    }

    public StoryFile setData() {
        this.data = dateFormat.format(new Date());
        return this;
    }

    public StoryFile addStory(String s) {
        story.append(s);
        return this;
    }

    public StoryFile addComment(String c) {
        comment.add(c);
        return this;
    }

    public Image getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public StringBuilder getStory() {
        return story;
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public int getLoveNum() {
        return loveNum;
    }

    public int getReadNum() {
        return readNum;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public String getData() {
        return data;
    }

//  IO读取文件字符串方式，太麻烦了
//    /**
//     * 添加故事，把故事写入到文件
//     * @param story 故事字符串
//     * @return 返回故事文件对象
//     * @throws IOException
//    public void writeStory2(String story) throws IOException {
//        if (!storyFile.exists()) {
//            storyFile.createNewFile();
//        }
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(storyFile,true));
//        bufferedWriter.write(story,0,story.length());
//        bufferedWriter.close();
//    }
//
//    *//**
//     * 得到故事，读入StringBuilder里
//     * @param storyFile 故事文件对象
//     * @return 故事StringBuilder
//     * @throws IOException
//     *//*
//    public StringBuilder getStory2(File storyFile) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(storyFile));
//        StringBuilder story = new StringBuilder();
//        String s=null;
//        while ((s = bufferedReader.readLine()) != null) {
//            story.append(s);
//        }
//        return story;
//    }
//
//    *//**
//     * 把评论写入文件
//     * @param comment 评论字符串
//     * @throws IOException
//     *//*
//    public void writeComment(String comment) throws IOException {
//        if (!commentFile.exists()) {
//            commentFile.createNewFile();
//        }
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(commentFile,true));
//        bufferedWriter.write("评论"+commentNum+":"+comment,0,comment.length());
//        bufferedWriter.newLine();
//    }
//
//    *//**
//     * 得到评论字符串数组
//     * @param commentFile 评论文件对象
//     * @throws IOException
//     * @return 评论字符串数组
//     *//*
//    public String[] getComment(File commentFile) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(commentFile));
//        String[] comment = new String[1024];
//        int index=0;
//        while ((comment[index]=bufferedReader.readLine()) != null) {
//            index++;
//            comment[index] = bufferedReader.readLine();
//        }
//        return comment;
//    }*/

}
