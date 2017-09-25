package com.rbao.east.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;


/**
 * @version 创建时间：2015-12-7 下午2:37:05
 * 
 */
public class GetDatabaseConn {
	 /** 
     * 连接数据库 
     */  
	
	private static Properties properties = new Properties();
    public static Connection getConnection(){  
         Connection conn = null;  
         try {  
            Class.forName("com.mysql.jdbc.Driver") ;
            InputStream in = GetDatabaseConn.class.getResourceAsStream("/jdbc.properties");
            try {
				properties.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			} 
            String url = properties.getProperty("master.jdbc.url").trim();
            String name = properties.getProperty("master.jdbc.username").trim();
            String pwd = properties.getProperty("master.jdbc.password").trim();
            //conn = DriverManager.getConnection("jdbc:mysql://192.168.1.32:3306/p2p_rongtengonline?characterEncoding=UTF-8","root","123456") ;
            conn = DriverManager.getConnection(url,name,pwd) ;
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return conn;  
    }  
    public static void main(String[] args) throws SQLException {  
         
    		String[] recommendSubLevl = getRecommendSubLevl("2117","1");
    	
    	System.out.println(recommendSubLevl);
    	
    } 
    
    public static  String[] getRecommendSubLevl(String recommendUserId,String levl) {
	    	Connection con=GetDatabaseConn.getConnection();  
	        
	        try {
	        	CallableStatement cs=(CallableStatement) con.prepareCall("{?=call getRbRecommendLevl(?,?)}");  
				cs.registerOutParameter(1, Types.VARCHAR);
				cs.setString(2, recommendUserId);      
		     	cs.setString(3, levl);   
		     	cs.execute();
		     	String subLevls= cs.getString(1).trim();
		     	String[] split = new String[]{};
		     	boolean flag = subLevls.contains("$,");
		     	
		     	if(flag==true){
		     		subLevls= subLevls.substring(2);
		     		split = subLevls.split(",");
		     	}
		     	boolean flag2 = subLevls.contains("$");
		     	if(flag2==true){
		     		subLevls= subLevls.substring(1);
		     	}
		     	
		     	 
		    	return split;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
	     	
    	return null;
    }
    
}
