/*
 * swing�� ������Ʈ �� �����ͺ��̽��� ���������
 * �ð�ȭ �ϱ⿡ ����ȭ�� ������Ʈ�� �ִµ�
 * JTable �̴�!!
 * 
 * ���ڵ��� ������ ���� �迭�� ũ�⸦ �����ؼ�
 * ������ ����!!
 * */
package table;

import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableTest2 extends JFrame{
	JTable table;
	JScrollPane scroll;
	
	String driver="oracle.jdbc.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="batman";
	String password="1234";

	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs; //select���� �湫�� �ʿ���
						//��? ����� ��ƾ� �ϹǷ�...
	
	String[][] data;


	String[] column={"empno","ename","job","mgr","hiredate","sal","comm","deptno"};
	
	public TableTest2(){
		 loadData();
		//setLayout(new FlowLayout());
		table = new JTable(data,column);
		scroll =new JScrollPane(table);
		
		add(scroll);
		setSize(800,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	//���ڵ� ä���ֱ�!!
	//���̺��� �����ϱ� ����, mariadb �����Ͽ�
	//member���̺��� ���ڵ带 �������迭��
	//��Ƴ���!!
	//��?? JTable�� �������� �μ��� ������ �迭��
	//���Ǵϱ�!!
	public void loadData(){
		/*
		 * 1�ܰ� - ����̹� �ε�
		 * 2�ܰ� - ����
		 * 3�ܰ� - ���ϴ� ������ ����
		 * 4�ܰ� - ��� �ݱ�
		 * */
		
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				con=DriverManager.getConnection(url, user, password);
				String sql="select * from emp";
				pstmt=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs=pstmt.executeQuery();
				
				//rs�� last()�� ������, ��ġ�� ����
				rs.last();
				int total=rs.getRow();
				//rs ���󺹱�
				//first()�� ù��° ���ڵ带 ����Ű�� �����̴�...
				//beforeFirst()�� ù ���ڵ庸�� ���� �ƹ��͵�
				//����Ű�� �ʴ� �����̴�..
				rs.beforeFirst();
				//������ �迭 ����
				data=new String[total][column.length];
				int index=0; //������ ���� ����
				while(rs.next()){
					int empno=rs.getInt("empno");
					String ename=rs.getString("ename");
					String job=rs.getString("job");
					int mgr = rs.getInt("mgr");
					String hiredate = rs.getString("hiredate");
					int sal = rs.getInt("sal");
					String comm= rs.getString("comm");
					int deptno=rs.getInt("deptno");
					
					
					
					data[index][0]=Integer.toString(empno);
					data[index][1]=ename;
					data[index][2]=job;
					data[index][3]=Integer.toString(mgr);
					data[index][4]=hiredate;
					data[index][5]=Integer.toString(sal);
					data[index][6]=comm;
					data[index][7]=Integer.toString(deptno);
					index++;
					
				}
			
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new TableTest2();
	}
}


