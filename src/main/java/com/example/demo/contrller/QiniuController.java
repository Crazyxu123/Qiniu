package com.example.demo.contrller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.sun.el.parser.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Crazyxu
 * @Create 2020-17-2020/8/29-21:17
 */
@RestController
public class QiniuController {

    private final String ak = "biEAlFRI3WiebPeMr8Nz-3a6SXHSN3-s3tLnjXF6";
    private final String sk = "P5yJLNrnaKuQsfI_FiRpXMyJWKBEkiZRNBiYBmOa";
    private final String bucket = "crazyxu";
    p
    private int num = 0;

    @GetMapping("/qiniu/up/token")
    public ResponseEntity<Object> getToken(){
        Auth auth = Auth.create(ak,sk);
        String upToken = auth.uploadToken("crazyxu");
        Map<String,String> map = new HashMap<>();
        map.put("token", upToken);
        map.put("key", "key"+num++);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @GetMapping("/qiniu/up/upload")
    public  ResponseEntity<Object> uploadFile(){

//        Zone zone = new Zone.Builder(Zone.zone0())
//                .upHttp("http://upload.qiniup.com")
//                .upHttps("http://upload.qiniup.com")
//                .upBackupHttp("http://upload.qiniup.com")
//                .upBackupHttps("http://upload.qiniup.com")
//                .rsHttp("http://rs.qiniu.com")
//                .rsfHttp("http://rsf.qiniu.com")
//                .apiHttp("http://api.qiniu.com")
//                .iovipHttp("http://iovip.qbox.me").build();
//        Configuration cfg = new Configuration(zone);
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        String filePath = "C:\\Users\\23241\\Pictures\\Saved Pictures\\yyds.jpg";
        StringMap params = new StringMap();
        String key = "crazyxu:" + filePath;
        Auth auth = Auth.create(ak, sk);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }
}
