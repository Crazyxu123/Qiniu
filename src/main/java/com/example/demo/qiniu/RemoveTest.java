package com.example.demo.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;

/**
 * @author Crazyxu
 * @Create 2020-07-2020/9/3-0:07
 */
public class RemoveTest {
    private String ACCESS_KEY = "biEAlFRI3WiebPeMr8Nz-3a6SXHSN3-s3tLnjXF6";
    private String SECRET_KEY = "P5yJLNrnaKuQsfI_FiRpXMyJWKBEkiZRNBiYBmOa";
    // 要上传的空间
    private String bucketname = "crazyxu";
    public int deleteFileFromQiniu(String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        String key = fileName;
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response delete = bucketManager.delete(bucketname, key);
            return delete.statusCode;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            ex.printStackTrace();
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
        return -1;
    }

    public static void main(String[] args) {
        RemoveTest test = new RemoveTest();
        test.deleteFileFromQiniu("Crazyxu:http://tmp/wx15bcb6f7b8910aa9.o6zAJszto_8uXfKOfbiXMsudEXuo.MF3G6jRlig8o88ab6b539854e648907bcc158e5bb44b.jpg");
    }
}
