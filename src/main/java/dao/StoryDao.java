package dao;

import entity.StoryFile;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by YuRacle on 2018/5/23.
 */
public class StoryDao {

    //所有故事文件集合
    public ArrayList<StoryFile> storyFile = new ArrayList<StoryFile>();
    //所有故事标题集合
    public ArrayList<String> storyTitle = new ArrayList<String>();
    //所有故事正文集合
    public ArrayList<StringBuilder> story = new ArrayList<StringBuilder>();
    public static StoryDao storyDao;

    public static StoryDao instance() {
        if (storyDao == null) {
            storyDao = new StoryDao();
        }
        return storyDao;
    }

    public StoryDao() {

    }

    /**
     * @return 得到所有故事标题列表
     */
    public ArrayList<String> getStoryTitle() {
        storyTitle.clear();
        File file = new File("storyFile");
        for (String s : file.list()) {
            String[] strings = s.split("\\.",2);
            storyTitle.add(strings[0]);
        }
        return storyTitle;
    }
}
