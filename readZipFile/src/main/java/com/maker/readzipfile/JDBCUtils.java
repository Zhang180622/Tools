package com.maker.readzipfile;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {

    //以下参数只需本类的properties对象读取即可，不需其它类访问，所以用private修饰
    //以下参数要在静态代码块中使用，所以用static修饰的
    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    public PreparedStatement ppstat;
    public Connection conn;


    // 静态代码块，随着类的加载，直接执行读取
    static {
        try {
            // 1 使用Properties处理流
            // 使用load()方法加载指定的流
            Properties props = new Properties();
            Reader is = new FileReader("db.properties");
            props.load(is);
            driver = props.getProperty("driver");
            url = props.getProperty("url");
            user = props.getProperty("user");
            password = props.getProperty("password");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //连接数据库
    public Connection getConnection() {
        try {
            // 1 注册驱动；2 获得连接
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //增加
    public void insert(String tablename, int Order, String content){
        try{
            //1,获取连接对象
            conn = new JDBCUtils().getConnection();
            //2,指定要执行的SQL语句
            String sql = "INSERT INTO " +tablename+" VALUES(?,?)";
            //3，获取SQL语句的执行对象 PreparedStatement
            ppstat = conn.prepareStatement(sql);
            //4,设置SQL语句需要的参数，对应索引和参数名
            ppstat.setInt(1,Order);
            ppstat.setString(2,content);
            int line = ppstat.executeUpdate();
            //6,处理结果集
            System.out.println(line+"行新增");
            //7,关闭连接
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //删除
    public void delete(String tablename, String express, int sid){
        try{
            //1,获取连接对象
            conn = new JDBCUtils().getConnection();
            //2,指定要执行的SQL语句
            String sql = "DELETE FROM " +tablename+" WHERE ? = ? ";
            //3，获取SQL语句的执行对象 PreparedStatement
            ppstat = conn.prepareStatement(sql);
            //4,设置SQL语句需要的参数，对应索引和参数名
            ppstat.setString(1,express);
            ppstat.setInt(2,sid);
            int line = ppstat.executeUpdate();
            //6,处理结果集
            System.out.println(line+"行被删除");
            //7,关闭连接
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //更改
    public void update(String tablename, String express){
        try{
            //1,获取连接对象
            conn = new JDBCUtils().getConnection();
            //2,指定要执行的SQL语句
            String sql = "UPDATE " +tablename+" SET WHERE ?";
            //3，获取SQL语句的执行对象 PreparedStatement
            ppstat = conn.prepareStatement(sql);
            //4,设置SQL语句需要的参数，对应索引和参数名
            ppstat.setString(1,express);
            int line = ppstat.executeUpdate();
            //6,处理结果集
            System.out.println(line+"行被修改");
            //7,关闭连接
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    //关闭数据库连接
    public void close(){
        if(ppstat != null){
            try{
                ppstat.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
    }


    public static void main(String[] args) {

        JDBCUtils jdbcUtils = new JDBCUtils();
        jdbcUtils.getConnection();
        jdbcUtils.delete("asort","SID",2);
    }
}
