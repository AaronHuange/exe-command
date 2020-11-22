package util;

import connect.WsClient;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * 下载 工具类
 *
 * @author sun
 */
public class DownloadUtils {

    /**
     * 下载文件到本地
     *
     * @param urlString
     * @param filename
     * @author sun
     * @date 2018年3月25日 上午11:01:05
     */
    public static void download(String urlString, String filename) {
        URL url = null;// 构造URL
        InputStream is = null;
        GZIPInputStream gis = null;
        OutputStream os = null;
        try {
            url = new URL(urlString);
            URLConnection con = url.openConnection();// 打开连接
            is = con.getInputStream();// 输入流
            String code = con.getHeaderField("Content-Encoding");
            if ((null != code) && code.equals("gzip")) {
                gis = new GZIPInputStream(is);
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                os = new FileOutputStream(filename);
                // 开始读取
                while ((len = gis.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            } else {
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                os = new FileOutputStream(filename);
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            WsClient.get().send("文件路径格式错误(文件路径格式有以下两种)：\n1:存在的文件'绝对路径' 和 不必需存在的文件名\n2:默认的执行目录路径 和 不必需存在的文件名");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    if (os != null) {
                        os = null;
                    }
                }
            }

            if (gis != null) {
                try {
                    gis.close();
                } catch (IOException e) {
                    if (gis != null) {
                        gis = null;
                    }
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    if (is != null) {
                        is = null;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
//        try {
//            WsClient.get().send("downfile::/XXX/XXX.txt::https://sunjs.oss-cn-beijing.aliyuncs.com/daigou/201803/07/1520425746777.jpg::/Users/MacBook/Documents/directorysystem_structure/files/personage/project/tools/listener-tool/jarclient/jarclient/test/test22.jpg");
////            download("g"File.separator + "test.jpg");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



        try {
            //String url = "http://www.baidu.com";
            String url = "http://www.csdn.net/";
            java.net.URI uri = java.net.URI.create(url);
            // 获取当前系统桌面扩展
            Desktop dp = Desktop.getDesktop();
            // 判断系统桌面是否支持要执行的功能
            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                //File file = new File("D:\\aa.txt");
                //dp.edit(file);// 　编辑文件
                dp.browse(uri);// 获取系统默认浏览器打开链接
                // dp.open(file);// 用默认方式打开文件
                // dp.print(file);// 用打印机打印文件
            }
        } catch (java.lang.NullPointerException e) {
            // 此为uri为空时抛出异常
            e.printStackTrace();
        } catch (java.io.IOException e) {
            // 此为无法获取系统默认浏览器
            e.printStackTrace();
        }

    }

}
