package com.book;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {
	
	public final String _DRIVER = "oracle.jdbc.driver.OracleDriver";
	public final String _URL ="jdbc:oracle:thin:@192.168.0.6:1521:orcl11";
	public final String _USER = "scott";
	public final String _PW = "tiger";
	Connection con = null;
	//��ü ������ ���ִ� �޼ҵ带 ���� �����غ���.
	//Ŭ���� �޼ҵ��̴ϱ� �ν��Ͻ�ȭ ���� �ٷ� ȣ�Ⱑ��.
	public static DBTest getInstance() {
		DBTest dbtest = null;
		if(dbtest==null) {
			dbtest = new DBTest();
			
		}
		return dbtest;
	}
    //����Ŭ������ ����̹� Ŭ������ �޸𸮿� ���������� �ε� �Ǵ��� üũ.
    public boolean driverTest() {
     boolean isok= false;
     try {
		Class.forName(_DRIVER);
		isok = true;
	} catch (ClassNotFoundException ce) {
		System.out.println("����̹� Ŭ������ ã���� �����ϴ�.");
		isok = false;
	}
    	return isok;
    }
    //���������� �������ִ� ������������� Ȯ�� �Ǿ����� üũ.
    public boolean connectTest() {
        boolean isok= false;
    	try {
			
			Class.forName(_DRIVER);
			con = DriverManager.getConnection(_URL, _USER, _PW);
			if(con!=null) {
			isok =true;}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    	return isok;
    }
    public Connection getconnection() {//Connection �� �������� �޼ҵ�
      
    	try {
			
			Class.forName(_DRIVER);
			con = DriverManager.getConnection(_URL, _USER, _PW);	
		} catch (Exception e) {
			System.out.println(e.toString());
		}
    	return con;
    }
    public static void main(String args[]) {
   	DBTest DB =new DBTest();
   	BookDao bDao = new BookDao();
   	int result = bDao.bookDelete("");
   	System.out.println("ó�������=="+result);
//    	 boolean isok =DB.driverTest();
//    	 boolean isok2 =DB.connectTest();
//    	 
//    	System.out.println(isok);
//    	System.out.println(isok2);
    	
      }	
}
