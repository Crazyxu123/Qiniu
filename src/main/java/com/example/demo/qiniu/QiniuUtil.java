package com.example.demo.qiniu;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import com.qiniu.util.Auth;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @author Crazyxu
 * @Create 2020-18-2020/9/2-22:18
 */
public class QiniuUtil {
    private final String ak = "biEAlFRI3WiebPeMr8Nz-3a6SXHSN3-s3tLnjXF6";
    private final String sk = "P5yJLNrnaKuQsfI_FiRpXMyJWKBEkiZRNBiYBmOa";

    private Auth auth = Auth.create(ak,sk);
    public String getDownloadUrl(String targetUrl) {
        String downloadUrl = auth.privateDownloadUrl(targetUrl);
        return downloadUrl;
    }

    /**
     * 下载
     */
    public void download(String targetUrl) {
        //获取downloadUrl
        String downloadUrl = getDownloadUrl(targetUrl);
        //本地保存路径
        String filePath = "D:/temp/picture/";
        download(downloadUrl, filePath);
    }


    /**
     * 通过发送http get 请求获取文件资源
     * @param url
     * @param filepath
     * @return
     */
    private static void download(String url, String filepath) {
        OkHttpClient client = new OkHttpClient();
        System.out.println(url);
        Request req = new Request.Builder().url(url).build();
        Response resp = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if(resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                byte[] data = readInputStream(is);
                File imgFile = new File(filepath + "1.png");          //下载到本地的图片命名
                File parentFile = imgFile.getParentFile();
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }
                if(!imgFile.exists()){
                    imgFile.createNewFile();
                }
                FileOutputStream fops = new FileOutputStream(imgFile);
                fops.write(data);
                System.out.println(1);
                fops.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        }
    }

    /**
     * 读取字节输入流内容
     * @param is
     * @return
     */
    private static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
            while((len = is.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }

    public static void main(String[] args) {
        QiniuUtil qiniuUtil = new QiniuUtil();
        String url = "http://qfsfrvt8c.hn-bkt.clouddn.com/Crazyxu:http://tmp/wx15bcb6f7b8910aa9.o6zAJszto_8uXfKOfbiXMsudEXuo.MF3G6jRlig8o88ab6b539854e648907bcc158e5bb44b.jpg";

        qiniuUtil.download(url);
    }

}
