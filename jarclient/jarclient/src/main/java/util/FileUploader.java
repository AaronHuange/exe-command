package util;

import connect.WsClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploader {
    private static final String TAG = "UploadUtil";

    public static void upload(String uploadUrl, String uploadFile) {
        String fileName = "";
        int pos = uploadFile.lastIndexOf("/");
        if (pos >= 0) {
            fileName = uploadFile.substring(pos + 1);
        }

        String end = "\r\n";
        String Hyphens = "--";
        String boundary = "WUm4580jbtwfJhNp7zi1djFEO3wNNm";
        HttpURLConnection conn = null;
        OutputStream outputStream = null;
        DataOutputStream ds = null;
        FileInputStream fStream = null;
        InputStream is = null;
        try {
            URL url = new URL(uploadUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            outputStream = conn.getOutputStream();

            ds = new DataOutputStream(outputStream);


            ds.writeBytes(Hyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" + fileName + "\"" + end);
            ds.writeBytes(end);
            fStream = new FileInputStream(uploadFile);
            // 每次写入1024字节
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            // 将文件数据写入到缓冲区
            while ((length = fStream.read(buffer)) != -1) {
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(Hyphens + boundary + Hyphens + end);
            ds.flush();
            // 获取返回内容
            is = conn.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            ds.close();
            WsClient.get().send("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            WsClient.get().send("上传失败:" + e.getMessage());
        } finally {
            close(is);
            close(fStream);
            close(ds);
            close(outputStream);
        }
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
//                if (closeable != null) {
//                    closeable = null;
//                }
            }
        }
    }


}