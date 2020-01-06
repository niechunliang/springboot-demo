package com.cn.niecl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Collection;

/**
* <p>类名: FileTest
* <p>描述:
* <p>版权:
* <p>日期: 2019/12/19 13:50
* <p>作者: niecl
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class FileTest {

    private final static String BASE_DIR = "E:/IdeaWorkspace/development/sbf-app-database/标准版";

    /**
     * @Description: 遍历指定目录下文件,筛选指定文件
     * @author niecl
     * @date 2019/12/19 13:50
     * @param
     * @return
     */
    @Test
    public void queryFiles() throws Exception {
        String NEW_DIR = "E:/IdeaWorkspace/development/sbf-app-database/NEW";
        //文件格式
        String[] extensions = new String[]{"sql","SQL"};
        Collection<File> filesList = FileUtils.listFiles(new File(BASE_DIR), extensions, true);
        for (File file : filesList) {
            //指定文件
            if (file.getName().toUpperCase().contains("XT_XTCSPZ")) {
                //输出到指定目录
                FileUtils.copyFileToDirectory(file, new File(NEW_DIR));
            }
        }
    }

    /**
     * @Description:
     * 查找指定目录下的(所有)文件
     * 注:既能查找出有后缀名的文件，又能查找出无后缀名的文件
     * public static Collection<File> listFiles(final File directory, final String[] extensions, final boolean recursive)
     * 方法的三个参数分别是:
     * File directory: 指定文件夹目录
     * String[] extensions:筛选出指定后缀名的文件，若为null,表示不作筛选，直接返回查到的所有文件
     * boolean recursive:是否递归
     * @author niecl
     * @date 2019/12/19 14:39
     * @param
     * @return
     */
    @Test
    public void getListFilesForExtensions(){
        // 筛选出指定后缀名的文件
        String[] extensions = new String[]{"java", "xml", "txt"};
        // null表示不作筛选，FileUtils.listFiles会直接返回所有的文件
        // String[] extensions = null;
        // 遍历查找指定目录下的所有文件
        Collection<File> filesList = FileUtils.listFiles(new File(BASE_DIR ), extensions, true);
        for (File file: filesList) {
            System.out.println(file.getAbsolutePath());
        }
    }

    /**
     * @Description:
     * 查找指定目录下的(所有)文件
     * 注:既能查找出有后缀名的文件，又能查找出无后缀名的文件
     * public static Collection<File> listFiles(final File directory, final IOFileFilter fileFilter, final IOFileFilter dirFilter)
     * @author niecl
     * @date 2019/12/19 14:54
     * @param
     * @return
     */
    @Test
    public void getListFilesForFilter() {
         // (递归)遍历查找指定目录下的所有文件
         // Collection<File> filesList = FileUtils.listFiles(new File(BASE_DIR ), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        /*
         * (递归)遍历查找指定目录下的所有文件，返回以【m.xml】结尾的文件
         * 如果筛选条件有多个的话，可以使用FileFilterUtils.and()、FileFilterUtils.or()来实现
         */
        Collection<File> filesList = FileUtils.listFiles(new File(BASE_DIR ),  FileFilterUtils.suffixFileFilter("m.xml"), TrueFileFilter.INSTANCE);
        for (File file: filesList) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
