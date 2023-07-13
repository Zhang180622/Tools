package com.maker.its;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/* *
 * @Author: zhangsp
 * @Date: 2023/6/13 20:03
 * @Version: 1.0
 * @comment: txt 转 excel；忽略第一行；数据行分隔符支持1F和7C；
 */
public class ConvertTxt2Excel {
    //是否横向打印
    private static final boolean isLine = true ;
    private static final String charSet = "UTF-8" ;
    private final static File TXTFILENAME = new File("E:/myTxt2.txt") ;
    private final static File EXCELFILENAME = new File("E:/myTxt2.xls") ;

    private static int lineNum ;
    private static int colNum ;
    private static char splitChar ;
    private static final List<ColumnObject> columnObjectArrayList = new ArrayList<ColumnObject>() ;


    static {
        try {
            if(!TXTFILENAME.exists()){
                throw new FileNotFoundException("文件" + TXTFILENAME + "不存在, 请检查！");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(TXTFILENAME), charSet));
            br.readLine();
            String line = br.readLine();
            String[] arr;
            if (line.contains("|")) {
                splitChar = '|';
                arr = line.split("\\|");
            } else {
                splitChar = (char) 0x1F;
                arr = line.split("\\x1F");
            }
            colNum = arr.length;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(TXTFILENAME), charSet));
            br.readLine();
            while ( br.readLine() != null) {
                lineNum += 1;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getTxt();
        txtToExcel();
    }

    private static void txtToExcel() {
        try {
            WritableWorkbook wwk = Workbook.createWorkbook(EXCELFILENAME);
            WritableSheet ws = wwk.createSheet("Test1", 0);
            for (int i = 0; i < lineNum; i++) {
                ColumnObject p = columnObjectArrayList.get(i);
                for(int j = 0; j < colNum; j++ ){
                    if(isLine){
                        Label label = new Label(j,i, p.columnArray[j]);
                        ws.addCell(label);
                    }else{
                        Label label = new Label(i,j, p.columnArray[j]);
                        ws.addCell(label);
                    }

                }
            }
            wwk.write();
            wwk.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    private static void getTxt() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(TXTFILENAME), charSet));
            br.readLine();
            String lineStr;
            String[] colArr;
            while ((lineStr = br.readLine()) != null) {
                ColumnObject columnObject = new ColumnObject(colNum);
                if(splitChar == '|'){
                    colArr = lineStr.split("\\|");
                }
                else{
                    colArr = lineStr.split("\\x1F");
                }
                columnObject.columnArray = colArr;
                columnObjectArrayList.add(columnObject);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ColumnObject {

        public String[] columnArray;

        public ColumnObject(int columnNum) {
            columnArray = new String[columnNum];
        }
    }

}