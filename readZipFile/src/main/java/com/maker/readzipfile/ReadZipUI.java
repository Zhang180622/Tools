package com.maker.readzipfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ReadZipUI implements ActionListener {
    //ZIP文件及输出TXT信息
    String FileSource;
    String FileIntoTxt;

    JFrame jFrame;
    JPanel jPanel1,jPanel2,jPanel3;
    JLabel jLabel1,jLabel2;
    JTextField jTextField1,jTextField2;
    JButton jButton;

    //程序入口
    public static void main(String[] args) {
        ReadZipUI readZipUI = new ReadZipUI();
        readZipUI.go();

    }

    //UI处理逻辑
    public void go(){

        jPanel1 = new JPanel();
        jLabel1 = new JLabel("填写ZIP信息：");
        jPanel1.add(jLabel1);
        jTextField1 = new JTextField(31);
        jPanel1.add(jTextField1);

        jPanel2 = new JPanel();
        jLabel2 = new JLabel("接收ZIP信息：");
        jPanel2.add(jLabel2);
        jTextField2 = new JTextField(31);
        jPanel2.add(jTextField2);

        jPanel3 = new JPanel();
        jButton = new JButton("确认输出");
        jButton.addActionListener(this);
        jButton.setActionCommand("dealZipFile");
        jPanel3.add(jButton);

        jFrame = new JFrame("测试");
        jFrame.add(jPanel1, BorderLayout.NORTH);
        jFrame.add(jPanel2, BorderLayout.CENTER);
        jFrame.add(jPanel3, BorderLayout.SOUTH);

        jFrame.setLocation(450,250);
        jFrame.setSize(600,200);
        jFrame.setResizable(false);
        jFrame.setTitle("ZIP文件详情小工具");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.setVisible(true);

    }

    //优化
    public void success(){
        System.out.println("新增一个导出zip信息成功后的提示");
    }


    //最终处理程序的调用
    //按钮事件被监听后，需要调用对应方法解析源文件并生成目标TXT
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().contentEquals("dealZipFile")){
            FileSource  = jTextField1.getText();//.getInputContext().toString();
            FileIntoTxt = jTextField2.getText();//.getInputContext().toString();
            File Input = new File(FileSource);
            File Output = new File(FileIntoTxt);
            new ReadZipFileContent(Input,Output).run();
        }else{
            System.out.println("Nothing to do!");
        }
    }
}
