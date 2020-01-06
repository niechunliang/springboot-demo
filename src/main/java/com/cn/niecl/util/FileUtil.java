package com.cn.niecl.util;

import com.google.common.base.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

/**
 * <p>类名: FileUtil
 * <p>描述:
 * <p>版权:
 * <p>日期: 2019/12/19 15:56
 * <p>作者: niecl
 */
public class FileUtil {

    /**
     * @param
     * @return
     * @Description: 遍历指定目录下文件, 筛选指定文件
     * @author niecl
     * @date 2019/12/19 13:50
     */
    public void queryFiles() throws Exception {
        String NEW_DIR = "E:/IdeaWorkspace/development/sbf-app-database/NEW";
        String BASE_DIR = "E:/IdeaWorkspace/development/sbf-app-database/标准版";

        //文件格式
        String[] extensions = new String[]{"sql", "SQL"};
        Collection<File> filesList = FileUtils.listFiles(new File(BASE_DIR), extensions, true);
        for (File file : filesList) {
            //指定文件
            if (file.getName().toUpperCase().contains("XT_XTCSPZ")) {
                //输出到指定目录
                FileUtils.copyFileToDirectory(file, new File(NEW_DIR));
            }
        }
    }

    //---------------------------------------------遍历目录文件---------------------------------------------

    /**
     * @param srcDirPath
     * @param fileFilterTypes
     * @return
     * @Description: 查找指定目录下的(所有)文件
     * 注:既能查找出有后缀名的文件，又能查找出无后缀名的文件
     * public static Collection<File> listFiles(final File directory, final String[] extensions, final boolean recursive)
     * 方法的三个参数分别是:
     * File directory: 指定文件夹目录
     * String[] extensions:筛选出指定后缀名的文件，若为null,表示不作筛选，直接返回查到的所有文件
     * boolean recursive:是否递归
     * @author niecl
     * @date 2019/12/19 14:39
     */
    public static void getListFilesForExtensions(String srcDirPath, String... fileFilterTypes) {
        // 筛选出指定后缀名的文件
        String[] extensions = new String[fileFilterTypes.length];
        for (int i = 0; i < fileFilterTypes.length; ++i) {
            extensions[i] = fileFilterTypes[i];
        }
        // null表示不作筛选，FileUtils.listFiles会直接返回所有的文件
        // String[] extensions = null;
        // 遍历查找指定目录下的所有文件
        Collection<File> filesList = FileUtils.listFiles(new File(srcDirPath), extensions, true);
        for (File file : filesList) {
            System.out.println(file.getAbsolutePath());
        }
    }

    /**
     * @param srcDirPath
     * @param fileFilterType
     * @return
     * @Description: 查找指定目录下的(所有)文件
     * 注:既能查找出有后缀名的文件，又能查找出无后缀名的文件
     * public static Collection<File> listFiles(final File directory, final IOFileFilter fileFilter, final IOFileFilter dirFilter)
     * @author niecl
     * @date 2019/12/19 14:54
     */
    public static void getListFilesForFilter(String srcDirPath, String fileFilterType) {
        // (递归)遍历查找指定目录下的所有文件
        // Collection<File> filesList = FileUtils.listFiles(new File(BASE_DIR ), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        /*
         * (递归)遍历查找指定目录下的所有文件，返回以【m.xml】结尾的文件
         * 如果筛选条件有多个的话，可以使用FileFilterUtils.and()、FileFilterUtils.or()来实现
         */
        Collection<File> filesList = FileUtils.listFiles(new File(srcDirPath), FileFilterUtils.suffixFileFilter(fileFilterType), TrueFileFilter.INSTANCE);
        for (File file : filesList) {
            System.out.println(file.getAbsolutePath());
        }
    }

    //---------------------------------------------拷贝目录文件---------------------------------------------
    // 注:若这个新的目录不存在，那么会自动创建
    // 注:若目的文件夹下存在相同名字的文件、文件夹，那么会进行覆盖
    // 注:其有一个比较实用的重载方法copyDirectory(final File srcDir, final File destDir,final FileFilter filter)，
    // 该方法能对源文件、文件夹进行过滤，只有满足FileFilter的文件/文件件，才会被拷贝进 目的文件夹下

    /**
     * @param srcDirPath
     * @param destDirPath
     * @throws IOException
     * @Description: 将指定目录下的所有内容(文件 、 文件夹)，拷贝到一个新的目录下,只拷贝源目录下的内容，源目录不会被拷贝
     * @author niecl
     * @date 2019/12/20 15:12
     */
    public static void copyFilesForNotSourceDirectory(String srcDirPath, String destDirPath) throws IOException {
        FileUtils.copyDirectory(new File(srcDirPath), new File(destDirPath));
    }

    /*
     * @Description:
     * 将指定目录以及该目录下的的所有内容(文件、文件夹)，拷贝到一个新的目录下,将源目录一起拷贝
     * @author niecl
     * @date 2019/12/20 15:12
     * @param srcDirPath
     * @param destDirPath
     * @return
     */
    public static void copyFilesForSourceDirectory(String srcDirPath, String destDirPath) throws IOException {
        FileUtils.copyDirectoryToDirectory(new File(srcDirPath), new File(destDirPath));
    }

    //---------------------------------------------拷贝文件---------------------------------------------
    /*
     * @Description:
     *拷贝一个文件，到一个新的文件
     * 注:FileUtils.copyFile方法只能拷贝文件，不能拷贝文件夹
     * 注:新文件的存放路径如果不存在，会自动创建
     * @author niecl
     * @date 2019/12/20 15:54
     * @param srcFilePath
     * @param destFilePath
     * @return void
     */
    public static void copyFile(String srcFilePath, String destFilePath) throws IOException {
        FileUtils.copyFile(new File(srcFilePath), new File(destFilePath));
    }

    /*
     * @Description:
     * 拷贝一个文件，到一个输出流
     * @author niecl
     * @date 2019/12/20 16:00
     * @param srcFilePath
     * @return
     */
    public static void copyFileToStream(String srcFilePath) throws IOException {
        OutputStream os = System.out;
        FileUtils.copyFile(new File(srcFilePath), os);
        String str = os.toString();
    }

    /*
     * @Description:
     * 拷贝一个输入流，到一个文件
     * 注:会自动创建文件
     * 注:如果路径不存在的话，会自动创建相关文件夹
     * @author niecl
     * @date 2019/12/20 16:23
     * @param srcFilePath
     * @param destFilePath
     * @return
     */
    public static void copyStreamToFile(String srcFilePath, String destFilePath) throws IOException {
        File srcFile = new File(srcFilePath);
        InputStream is = new FileInputStream(srcFile);
        try {
            // 等价于copyToFile(InputStream source, File destination)
            FileUtils.copyInputStreamToFile(is, new File(destFilePath));
        } finally {
            IOUtils.closeQuietly(is);
        }

    }

    /*
     * @Description:
     * 拷贝文件到指定目录下
     * 注:如果目标目录不存在，那么会自动创建
     * @author niecl
     * @date 2019/12/20 16:27
     * @param srcFilePath
     * @param destDirPath
     * @return
     */
    public static void copyFileToDirectory(String srcFilePath, String destDirPath) throws IOException {
        FileUtils.copyFileToDirectory(new File(srcFilePath), new File(destDirPath));
    }

    //---------------------------------------------拷贝目录---------------------------------------------
    /*
     * @Description:
     * 拷贝【文件】或【文件夹】 到 指定目录下
     * 注:如果目标目录不存在，那么会自动创建
     * 注:如果拷贝的是文件夹，那么连该文件夹下的所有东西，都会一同进行拷贝
     * @author niecl
     * @date 2019/12/20 16:30
     * @param
     * @return
     */
    public static void copyToDirectory(String srcDirPath, String destDirPath) throws IOException {
        FileUtils.copyToDirectory(new File(srcDirPath), new File(destDirPath));
    }

    //---------------------------------------------下载文件---------------------------------------------
    /*
     * @Description:
     * 拷贝URL指向的文件 到 指定文件(即:下载URL指向的文件)
     * 注:如果文件的相关目录不存在，那么会自动创建相关文件夹
     * 注:copyURLToFile还有一个重载方法copyURLToFile(URL source, File destination, int connectionTimeout, int readTimeout)
     * @author niecl
     * @date 2019/12/20 16:36
     * @param
     * @return
     */
    public static void copyURLToFile(String srcUri, String descFilePath) throws IOException {
        URL url = new URL(srcUri);
        FileUtils.copyURLToFile(url, new File(descFilePath));
    }

    //---------------------------------------------删除/清除文件、目录---------------------------------------------
    /*
     * @Description:
     * 清除目录(而不删除它)
     * @author niecl
     * @date 2019/12/20 16:42
     * @param srcDirPath
     * @return
     */
    public static void cleanDirectory(String srcDirPath) throws IOException {
        FileUtils.cleanDirectory(new File(srcDirPath));
    }

    /*
     * @Description:
     * 删除文件夹(及里面的内容)
     * 注:如果删除失败的话，那么会IOException抛出异常
     * @author niecl
     * @date 2019/12/20 16:42
     * @param srcDirPath
     * @return
     */
    public static void deleteDirectory(String srcDirPath) throws IOException {
        FileUtils.deleteDirectory(new File(srcDirPath));
    }

    /*
     * @Description:
     * 删除指定的文件 或 指定的文件夹(及里面的内容)
     * 注:如果删除失败的话，那么会IOException抛出异常
     * 注:此方法不会抛出异常，只会返回删除成功与否(提示:若文件/文件夹不存在，则会被视为删除失败，进而返回false)
     * @author niecl
     * @date 2019/12/20 16:42
     * @param srcDirPath
     * @return
     */
    public static boolean deleteQuietly(String srcDirPath) {
        return FileUtils.deleteQuietly(new File(srcDirPath));
    }

    //---------------------------------------------创建目录---------------------------------------------
    /*
     * @Description:
     * 根据给定的文件,创建其所在的目录
     * 注:若父目录也不存在，那么会自动创建
     * 注:还有一个相似的方法:创建给定的文件夹目录FileUtils.forceMkdir
     * @author niecl
     * @date 2019/12/20 16:42
     * @param mkdirPath
     * @return
     */
    public static void mkdir(String mkdirPath) throws IOException {
        FileUtils.forceMkdirParent(new File(mkdirPath));
    }

    //---------------------------------------------文件与流---------------------------------------------
    /*
     * @Description:
     * 根据文件，获得输入流
     * @author niecl
     * @date 2019/12/20 17:06
     * @param srcFilePath
     * @return
     */
    public static InputStream openInputStream(String srcFilePath) throws IOException {
        InputStream is = null;
        try {
            is = FileUtils.openInputStream(new File(srcFilePath));
        } finally {
            IOUtils.closeQuietly(is);
        }
        return is;
    }

    /*
     * @Description:
     * 根据文件，获得输出流(若指定位置下有源文件，那么会覆盖源文件)；
     * 注:此方法还有一个重载方法FileUtils.openOutputStream(final File file, final boolean append)
     * append参数，控制是覆盖源文件，还是在源文件后面追加(默认为覆盖)。
     * @author niecl
     * @date 2019/12/20 17:06
     * @param srcFilePath
     * @return os
     */
    public static OutputStream openOutputStream(String srcFilePath) throws IOException {
        OutputStream os = null;
        try {
            os = FileUtils.openOutputStream(new File(srcFilePath));
        } finally {
            IOUtils.closeQuietly(os);
        }
        return os;
    }

    /*
     * @Description:
     * 将文件的内容读入字节数组
     * @author niecl
     * @date 2019/12/20 17:06
     * @param srcFilePath
     * @return
     */
    public static byte[] readFileToByteArray(String srcFilePath) throws IOException {
        return FileUtils.readFileToByteArray(new File(srcFilePath));
    }

    //---------------------------------------------文件读写---------------------------------------------
    /*
     * @Description:
     * 将文件的内容按照行为划分，读取出来
     * @author niecl
     * @date 2019/12/20 17:06
     * @param srcFilePath
     * @return
     */
    public static List<String> readLines(String srcFilePath) throws IOException {
        return FileUtils.readLines(new File(srcFilePath), Charsets.UTF_8);
    }

    /*
     * @Description:
     * 将文件的内容读入字符串
     * @author niecl
     * @date 2019/12/20 17:06
     * @param srcFilePath
     * @return
     */
    public static String readFileToString(String srcFilePath) throws IOException {
        return FileUtils.readFileToString(new File(srcFilePath), Charsets.UTF_8);
    }

    /*
     * @Description:
     * 向指定文件中写入内容
     * 通过最后一个参数控制，是覆盖 还是追加
     * 注意:追加数据时，是直接追加，不会另起一行追加；如果需要换行的话，那么可以使用IOUtils.LINE_SEPARATOR
     * 注:若文件不存在(或相关文件夹不存在)，那么会自动创建
     * @author niecl
     * @date 2019/12/20 17:25
     * @param srcFilePath
     * @param appendContent
     * @return
     */
    public static void writeStringToFile(String srcFilePath, String appendContent) throws IOException {
        File targetFile = new File(srcFilePath);
        //换行拼接内容
        if (StringUtils.isNotEmpty(appendContent)) {
            FileUtils.writeStringToFile(targetFile, IOUtils.LINE_SEPARATOR + appendContent, Charset.forName("GBK"), true);
            return;
        }
        FileUtils.writeStringToFile(targetFile, StringUtils.EMPTY, Charset.forName("GBK"), true);
    }

}
