package com.example.demo.contrller;

import com.qiniu.util.StringMap;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author Crazyxu
 * @Create 2020-31-2020/9/11-23:31
*/
@Service
public class QiniuServer{

    private String ak;

    private String sk;

    public StringMap upload(String filePath,String username){
        StringMap respondedBody = new StringMap();
        File file = null;
        if(filePath  != null){
            file = new File(filePath);
        }else{
            respondedBody.put("error","文件路径有问题");
        }
        return respondedBody.putAll(this.upload(file, username));
    }

    public StringMap upload(File file,String username){

        if(file == null){

        }
        return null;
    }

    public File download(String fileName,String user){
        return null;
    }
}