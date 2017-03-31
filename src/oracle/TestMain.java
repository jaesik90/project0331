/*
 * �츮�� ������� �����ͺ��̽� ��ǰ��
 * ��� DBMS�̴�!!
 * DB(�����)MS(�����ý���) - ��Ʈ��ũ���
 * �̶�, ���� ������ �����ϴ�!!
 * 
 * �����ͺ��̽� ���� Ŭ���̾�Ʈ
 * -SQLPLUS(�ֱܼ��)
 * -Toad
 * 
 * ���� ������� ��Ʈ��ũ ���������� TCP/IP
 * ����̹Ƿ�, �������� ȣ��Ʈ�� �����Ϸ���
 * �� ȣ��Ʈ�� �ּҸ� �˾ƾ� �ϴµ�, �����
 * TCP/IP ������ IP �ּҸ� �˾ƾ� �Ѵ�
 * user ������������ �˾ƾ� �Ѵ�!!!
 * 
 * ������Ʈ���� lib�̶�� �Ϲ� ���� ����
 * --> �̾ȿ� ojdbc6�� �־�д�...
 * ������ ������Ʈ --> build path ����-->
 * configure build path --> add library
 * --> user libray����
 * */
package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestMain {

	public static void main(String[] args) {
		//1�ܰ� ����Ŭ�� �ڹٰ� ������ �� �ִ�
		//�ڵ尡 ����ִ� jar������ �޸𸮿�
		//�ε��ؾ� �Ѵ�.. �̷� �����ͺ��̽� ����
		//jar ������ �ڹٿ����� ����̹��� �Ѵ�
		//����̹��� db �����翡�� �����Ѵ�..
		//oracle --> ����Ŭ��
		//mysql --> ����Ŭ��
		//mssql --> MS
		
		
	
		Connection con=null;//�ݱ� ���ؼ� try�� ���� ���������� �ξ���...
		PreparedStatement pstmt=null; //�ݱ� ���ؼ� ���� �÷ȴ�...
		try {
			//����̹� Ŭ���� �ε�!!
			//�ű��ϰ� String������ ������ ��
			//db���̺귯��-->ojdbc6-->oracle.jdbc.driver-->OracleDriver.class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� �ε� ����");
			
			//2�ܰ� ����Ŭ�� ��������!!
			//�����ϴ� �޼���� �ϱ�����̴�...
			//"jdbc:oracle:thin:@localhost:1521", "batman", "1234"��
			//jdbc������� oracle�Ǵ� mariadb�� �����ؼ� thin Ÿ������
			//@�� ���� ��Ʈ��ũ ǥ�ø� ���� ȣ��Ʈ(ip)�� ���� 1521�̶�� ��Ʈ��ȣ�� ���´�...
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521", "batman", "1234");
			//sql�� �����ߴ��� �������ؼ��� getConnection�� ��ȯ���� Connection�� �����ؼ�
			//�� ��ü�� null�������� üũ�ؼ� Ȯ���Ѵ�...
			if(con!=null){
				System.out.println("���� ����");
				
				//���� ������ ������ ���̺� insert�õ�
				String sql="insert into company(company_id, brand) values(seq_company.nextval, '����Ű')";
				//������ ������ ���ؼ��� �������� ����
				//�ϴ� ��ü�� �̿��ؾ� �ϴµ�, �� ��ü�� �ٷ�
				//PreparedStatement�������̽� �̴�
				pstmt = con.prepareStatement(sql);
				int result=pstmt.executeUpdate();//������ ���� �޼���
				//�� ������ ���࿡ ���� �ݿ��� ���ڵ��� ���� ��ȯ���ش�!!
				if(result==1){
					System.out.println("�Է¼���");
				}else{
					System.out.println("�Է½���");
				}
				
			}else{
				System.out.println("���� ����");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��� ã�� �� �����ϴ�");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//��Ʈ���� DB �����۾� �Ŀ� �ݵ��
			//�ݴ� ó���� �ؾ��Ѵ�!!
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
				
				e.printStackTrace();
					}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
	}

}
