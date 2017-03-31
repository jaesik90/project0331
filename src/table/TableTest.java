/*
 * swing의 컴포넌트 중 데이터베이스의 결과집합을
 * 시각화 하기에 최적화된 컴포넌트가 있는데
 * JTable 이다!!
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

public class TableTest extends JFrame{
	JTable table;
	JScrollPane scroll;
	
	String driver="org.mariadb.jdbc.Driver";
	String url="jdbc:mariadb://localhost:3306/db0331";
	String user="root";
	String password="";

	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs; //select문일 경무만 필요함
						//왜? 결과를 담아야 하므로...
	
	String[][] data;


	String[] column={"member_id","name","age"};
	
	public TableTest(){
		 loadData();
		//setLayout(new FlowLayout());
		table = new JTable(data,column);
		scroll =new JScrollPane(table);
		
		add(scroll);
		setSize(500,150);
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
				String sql="select * from member";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				//이차원 배열 생성
				data=new String[3][3];
				int index=0; //층수에 대한 변수
				while(rs.next()){
					
					int member_id=rs.getInt("member_id");//primary key 접근
					String name=rs.getString("name");
					int age=rs.getInt("age");
					System.out.println(member_id+","+name+","+age);
					
					data[index][0]=Integer.toString(member_id);
					data[index][1]=name;
					data[index][2]=Integer.toString(age);
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
		new TableTest();
	}
}


