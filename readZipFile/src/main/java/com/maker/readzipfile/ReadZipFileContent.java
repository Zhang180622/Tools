package com.maker.readzipfile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


//读取zip类型的压缩文件，并输出到数据库和txt文档中
public class ReadZipFileContent implements Runnable{
    //参数
    private File zipFileInput;
    private File txtFileOutput;

    //构造方法，参数为两个文件对象
    public ReadZipFileContent(File zipFileInput, File txtFileOutput){
        this.zipFileInput = zipFileInput;
        this.txtFileOutput = txtFileOutput;
    }

    public void run(){
        FileInputStream fileInputStream = null;
        ZipInputStream zipInputStream = null;
        JDBCUtils jdbcUtils = new JDBCUtils();;
        try {
            fileInputStream = new FileInputStream(zipFileInput);
            zipInputStream = new ZipInputStream(fileInputStream);
            FileOutputStream fileOutputStream = new FileOutputStream(txtFileOutput);
            ZipEntry fentry;
            int i = 1;

            while ((fentry = zipInputStream.getNextEntry()) != null) {
                fentry.isDirectory();
                fileOutputStream.write((fentry.getName() + "\n").getBytes());
                jdbcUtils.insert("asort", i, fentry.getName());
                i+=1;
            }
            System.out.println(i-1);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(fileInputStream != null){
                    fileInputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                if(zipInputStream != null){
                    zipInputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                if(jdbcUtils != null){
                    jdbcUtils.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}