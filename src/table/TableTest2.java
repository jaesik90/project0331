/*
 * swing의 컴포넌트 중 데이터베이스의 결과집합을
 * 시각화 하기에 최적화된 컴포넌트가 있는데
 * JTable 이다!!
 * 
 * 레코드의 갯수에 따라 배열의 크기를 지정해서
 * 개발해 보자!!
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
	ResultSet rs; //select문일 경무만 필요함
						//왜? 결과를 담아야 하므로...
	
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
	
	//레코드 채워넣기!!
	//테이블을 생성하기 전에, mariadb 연동하여
	//member테이블의 레코드를 이차원배열에
	//담아놓자!!
	//왜?? JTable의 생성자의 인수로 이차원 배열이
	//사용되니깐!!
	public void loadData(){
		/*
		 * 1단계 - 드라이버 로드
		 * 2단계 - 접속
		 * 3단계 - 원하는 쿼리문 수행
		 * 4단계 - 디비를 닫기
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
				
				//rs를 last()로 보내고, 위치를 묻자
				rs.last();
				int total=rs.getRow();
				//rs 원상복구
				//first()는 첫번째 레코드를 가리키는 상태이다...
				//beforeFirst()는 첫 레코드보다 위에 아무것도
				//가리키지 않는 상태이다..
				rs.beforeFirst();
				//이차원 배열 생성
				data=new String[total][column.length];
				int index=0; //층수에 대한 변수
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


