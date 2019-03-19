package com.book;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.vo.BookVO;

import com.vo.YesCate1VO;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class BookDao {
	//���������� ������ �ִ� ����Ŭ ������ �����ϱ�
	Connection con = null;
	//����� �Ŀ� �� ��θ� ���ؼ� DML������ ������ ����
	PreparedStatement pstmt = null;
	CallableStatement cstmt = null;
	OracleCallableStatement ocstmt = null;
	//select�� ��츸 �ʿ��� �������̽�
	//����:����Ŭ�� Ŀ���� �ڹ� �ڵ�� ������ �� �ֵ��� �޼ҵ带 ����.
	//Front-end(Ŭ���̾�Ʈ��-UI-����-html,css,js) , Back-end(������-��-java,jsp,spring)
	//VO(Value Object)-���������� , DTO(Data Transfer Object)
	//DAO(Data Access Object)-����������
	ResultSet rs = null;
	DBTest dbTest = DBTest.getInstance();
	public int bookDelete(String goods_num) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM yes_goods");
		sql.append(" WHERE goods_num= ?");
		try {
			con = dbTest.getconnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, goods_num);
			//select�� ��� ����Ŭ ������ ��û�� �� executeQuery()���
			//insert|update|delete�� ��� ����Ŭ ������ ��û�� �� executeUpdate()���
			//result 1:��������, 0:���� ����
			result = pstmt.executeUpdate();
		} catch (Exception e) {//try..catch���̿� ����� �ڵ尡 ���ܹ߻����ɼ��� �ִ� �ڵ�
			System.out.println(e.toString());//���ܰ� �߻����� �� ��Ʈ�� ��� ����
		} 
		return result;
	}
	/******************************************************************
	 * ����:refcursorȰ���Ͽ� ��ȸ��� ������
	 * 
	 ******************************************************************/
	public List<YesCate1VO> getCategory(){
	//List - Interface - ��� - ���յ� ���ߴ� �ڵ�, ������ ��� ����
    //       �ܵ����δ� �ν��Ͻ�ȭ�� �Ұ��ϴ�. �ݵ�� ����ü Ŭ������ �־����.
	//       ������(����������)�� ���� �� �ִ�.	
	//�ν��Ͻ�ȭ �� �� ����ο� �������� �̸��� �ٸ� �� �ִ�.	
		//List<YesCate1VO> cateList = null;
		List<YesCate1VO> cateList = new ArrayList<YesCate1VO>();
		try {
			con = dbTest.getconnection();//�ν��Ͻ�ȭ-�������̽���..
			cstmt = con.prepareCall("{call proc_cate1List(?)}");//�ν��Ͻ�ȭ
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			//����Ŭ �������� ���� ��û�ϱ�
			boolean isOK = cstmt.execute();
			ocstmt = (OracleCallableStatement)cstmt;
			ResultSet cursor = ocstmt.getCursor(1);
			YesCate1VO yvo = null;
			while(cursor.next()) {
				//cateList = new ArrayList<YesCate1VO>();
				yvo = new YesCate1VO();
				yvo.setCate_num(cursor.getString("cate_num"));
				yvo.setCate_num(cursor.getString("cate_bunryu"));
				cateList.add(yvo);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return cateList;
	}
	/*****************************************************************8
	 * ���� ��� ��ü ��ȸ ����
	 * @param bvo - ���� �˻��� ��츸 ����ؿ�.
	 * @return - BookVO[] - why?:�˻��� ����� n�� �ο��̴ϱ�.....
	 ******************************************************************/
	public BookVO[] getBookList(BookVO pbvo) {
		BookVO[] bVOS = null;//��(BookVO[]) �ȿ� ��(BookVO)�ִ�.
		BookVO rbVO = null;
		//DB�� ����� ������ �ο��� ������ �˾Ƴ��� ���ؼ� �����.
		//SELECT count(*) FROM yes_goods
		//�� Vector�� ����߳�?
		//��ȸ �Ǽ��� n�� �϶� n���� ��ü�迭�� ��ƾ� ��. - �� �� Vector����ϸ� �ڹ� API�� ����ؼ� ����.
		Vector<BookVO> v = new Vector<BookVO>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT                                                     ");
	    sql.append("       goods_num, goods_name, goods_paia                   ");
	    sql.append("      ,goods_ea||'��' goods_ea                             ");
	    sql.append("      ,DECODE(goods_use,'NEW','��å','1','�߰���','����') goods_use");             
	    sql.append("      ,goods_date, book_maker, goods_memo                   ");
	    sql.append("  FROM yes_goods                                           ");
	    //�� Ȥ�� ����ȸ�� ����?
	    //NumberFormatException �����Ұ�.
	    System.out.println("������ȣ:"+pbvo.getGoods_num());
	    System.out.println("�޺��ڽ� ��:"+pbvo.getSearch());
	    if(Integer.parseInt(pbvo.getGoods_num())>0) {
	    	sql.append(" WHERE goods_num = ?");
	    }
	    else if("����".equals(pbvo.getSearch())) {
	    	sql.append("  WHERE goods_name LIKE ?||'%'");
	    }
	    else if("����".equals(pbvo.getSearch())) {
	    	sql.append("  WHERE book_maker LIKE ?||'%'");
	    }
	    else if("����".equals(pbvo.getSearch())) {
	    	sql.append("  WHERE goods_memo LIKE '%'||?||'%'");
	    }
	    sql.append(" order by goods_num");
	    //insert here - ������ ����
	    try {
	    	con = dbTest.getconnection();
	    	//
	    	pstmt = con.prepareStatement(sql.toString());
		    if(Integer.parseInt(pbvo.getGoods_num())>0) {
		    	//?�ڸ��� �� ���� �ʱ�ȭ ����.
		    	pstmt.setString(1, pbvo.getGoods_num());
		    }
		    //����ڰ� ���ǰ˻��⿡�� ���� Ȥ�� ���� Ȥ�� ������ �������� �� - ���ǰ˻� ����
		    else if("����".equals(pbvo.getSearch())
		    	  ||"����".equals(pbvo.getSearch()) 
		    	  ||"����".equals(pbvo.getSearch())) {
		    	pstmt.setString(1, pbvo.getKeyword());
		    }
		    //�ڹٵ������ eclipse�� ����Ŭ ������� Toad����...
		    System.out.println("[[query]]"+sql.toString());
	    	rs = pstmt.executeQuery();
	    	while(rs.next()) {
	    		//insert here - ��������
	    		//��ȸ �Ǽ��� n���̹Ƿ� 1�Ǿ� BookVO��  ��������� �������
	    		rbVO = new BookVO(rs.getString("goods_num")
	    				         ,rs.getString("goods_name")
	    				         ,rs.getString("goods_paia")
	    				         ,rs.getString("goods_memo")
	    				         ,rs.getString("goods_ea")
	    				         ,rs.getString("goods_use")
	    		                 ,rs.getString("goods_date")
	    		                 ,rs.getString("book_maker"));//�ּҹ����� 927���� ���ܿ�
	    		//setter�޼ҵ带 ���ؼ� ��������� ��ȸ�� ����� ����.
	    		//�̰��� �����ڸ� ���ؼ��� �� �� �־��.
	    		//�Ķ���Ͱ� ���� ����Ʈ �����ڴ� JVM�� ������ �ڵ�������.
	    		//�Ķ���Ͱ� �ִ� ��� �����ڸ��� ������ �ٸ� �� �����ϱ� ���� �Ұ�.
/*	    		rbVO.setGoods_num(rs.getString("goods_num"));
	    		rbVO.setGoods_name(rs.getString("goods_name"));
	    		rbVO.setGoods_paia(rs.getInt("goods_paia"));
	    		rbVO.setGoods_ea(rs.getString("goods_ea"));
	    		rbVO.setGoods_use(rs.getString("goods_use"));*/
	    		v.add(rbVO);//927
	    	}
	    	System.out.println("v.size():"+v.size());
	    	bVOS = new BookVO[v.size()];
	    	v.copyInto(bVOS);
/*	    	System.out.println(bVOS[1].getGoods_name());
	    	System.out.println(bVOS[2].getGoods_name());
	    	System.out.println(bVOS[4].getGoods_name());*/
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return bVOS;
	}
	//�Է�ó��
	//��� ������ �ʿ��� sql�� �̸� Toad���� �׽�Ʋ �մϴ�.
	//�׷��� SQLException�� ���� �� �ִ�.
	//����Ŭ������ Toad�� ��Ʈ�� ��°� ����.- ORA-XXXXXX����� ������ִϱ�
	//���̺��� �÷���� BookVO�� ����������� ȭ���� ������Ʈ�̸��� ����
	//����Ģ�� ���ؼ� ����ؾ� �� �� ����.
	public String getGoodsNum() {
		String goods_num = null;
		//������ �ڿ�(������,���߱���)�� �������� �����ؾ���  ��
		//���ڿ��� ó���ϴ� Ŭ�������� String, StringBuffer(��Ƽ���������-�ӵ� ������), StringBuilder(�̱۽��������-�ӵ������)
		//String�� ����� ����-�����ֱ�, ������ �ȹٲ�(������)- ��ȿ�����̴�.
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(goods_num)+1 as goods_num FROM yes_goods");
		sql.append(" ORDER BY goods_num desc               ");
		try {
			con = dbTest.getconnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			rs.next();
			goods_num = rs.getString("goods_num");
		} catch (Exception e) {
			System.out.println(e.toString());//��Ʈ�� ���, �����, �������� �ĺ���,�䳪 ���̺��� �������� �ʽ��ϴ�.
		}
		return goods_num;
	}
	public int bookInsert(BookVO pbVO) {
		StringBuilder sql = new StringBuilder();
		int result = 0;
		sql.append("INSERT INTO yes_goods(goods_num, goods_name, goods_paia");
        sql.append("                     ,goods_ea,goods_date, book_maker");
        sql.append("                     ,cate_num,goods_use, goods_memo)");
        sql.append(" VALUES(?,?,?,?,?,?,?,?,?)");
		try {                                                              
			con = dbTest.getconnection();
			pstmt = con.prepareStatement(sql.toString());
			System.out.println("11");
			int i=1;
			pstmt.setString(i++, pbVO.getGoods_num());
			pstmt.setString(i++, pbVO.getGoods_name());
			pstmt.setInt(i++, Integer.parseInt(pbVO.getGoods_paia()));
			System.out.println("11");
			pstmt.setString(i++, pbVO.getGoods_ea());
			pstmt.setString(i++, pbVO.getGoods_date());
			pstmt.setString(i++, pbVO.getBook_maker());
			pstmt.setString(i++, pbVO.getCate_num());
			pstmt.setString(i++, pbVO.getGoods_use());
			pstmt.setString(i++, pbVO.getGoods_memo());
			System.out.println("11");
			result = pstmt.executeUpdate();
			System.out.println("result:"+result);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;//Ŭ������ �������� ������ ������ �� �����Ұ�.
	}
}





