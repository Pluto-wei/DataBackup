package main.Util;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

/**
 * 文件打包工具类
 * 密钥(int)与文件字节异或加密
 */
public class FilePack {
    static final int BUFFER = 8192;

    /**
     * 打包
     * @param pathName 要打包的文件列表
     * @param zipFilePath 打包后的路径
     */
    public static void pack(List<String> pathName, String zipFilePath) {
        File zipFile = new File(zipFilePath);
        ZipOutputStream out = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            out = new ZipOutputStream(cos);
            String basedir = "";
            for (int i = 0; i < pathName.size(); i++) {
                pack(new File(pathName.get(i)), out, basedir);
            }
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 打包单个文件
     * @param srcPathName 源文件
     * @param targetPathName 目标地址
     */
    public static void pack(String srcPathName, String targetPathName) {
        File zipFile = new File(targetPathName);
        File file = new File(srcPathName);
        if (!file.exists())
            throw new RuntimeException(srcPathName + "不存在！");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
                    new CRC32());
            ZipOutputStream out = new ZipOutputStream(cos);
            String basedir = "";
            pack(file, out, basedir);
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 打包
     * @param file 选中的文件
     * @param out 输出
     * @param basedir 基目录
     */
    private static void pack(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            System.out.println("打包：" + basedir + file.getName());
            packDirectory(file, out, basedir);
        } else {
            System.out.println("打包：" + basedir + file.getName());
            packFile(file, out, basedir);
        }
    }

    /**
     * 打包一个目录
     */
    private static void packDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            pack(files[i], out, basedir + dir.getName() + "/");
        }
    }

    /**
     * 打包一个文件
     */
    private static void packFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解包文件
     * @param inputFile 要解包的文件
     * @param destDirPath 目标文件
     * @throws Exception 异常
     */
    public static void tarUnpack(String inputFile,String destDirPath) throws Exception {
        File srcFile = new File(inputFile);//获取当前打包文件
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new Exception(srcFile.getPath() + "所指文件不存在");
        }
        ZipFile zipFile = new ZipFile(srcFile);//创建打包文件对象
        //开始解包
        Enumeration<?> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            // 如果是文件夹，就创建个文件夹
            if (entry.isDirectory()) {
                String dirPath = destDirPath + "/" + entry.getName();
                srcFile.mkdirs();
            } else {
                // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                File targetFile = new File(destDirPath + "/" + entry.getName());
                // 保证这个文件的父文件夹必须要存在
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.createNewFile();
                // 将打包文件内容写入到这个文件中
                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(targetFile);
                int len;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                // 关流顺序，先打开的后关闭
                fos.close();
                is.close();
            }
        }System.out.println("解包完成！");
    }
}
