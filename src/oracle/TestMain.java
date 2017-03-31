/*
 * 우리가 사용중인 데이터베이스 제품은
 * 모두 DBMS이다!!
 * DB(저장소)MS(관리시스템) - 네트워크기반
 * 이라서, 원격 접속이 가능하다!!
 * 
 * 데이터베이스 접속 클라이언트
 * -SQLPLUS(콘솔기반)
 * -Toad
 * 
 * 현재 사용중인 네트워크 프로토콜은 TCP/IP
 * 기반이므로, 원격지의 호스트를 접속하려면
 * 그 호스트의 주소를 알아야 하는데, 기반이
 * TCP/IP 인지라 IP 주소를 알아야 한다
 * user 계정정보까지 알아야 한다!!!
 * 
 * 프로젝트내에 lib이라는 일반 폴더 생성
 * --> 이안에 ojdbc6을 넣어둔다...
 * 선택한 프로젝트 --> build path 선택-->
 * configure build path --> add library
 * --> user libray선택
 * */
package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestMain {

	public static void main(String[] args) {
		//1단계 오라클을 자바가 제어할 수 있는
		//코드가 들어있는 jar파일을 메모리에
		//로드해야 한다.. 이런 데이터베이스 제어
		//jar 파일을 자바에서는 드라이버라 한다
		//드라이버는 db 제조사에서 제공한다..
		//oracle --> 오라클사
		//mysql --> 오라클사
		//mssql --> MS
		
		
	
		Connection con=null;//닫기 위해서 try문 위에 지역변수로 두었다...
		PreparedStatement pstmt=null; //닫기 위해서 위로 올렸다...
		try {
			//드라이버 클래스 로드!!
			//신기하게 String형으로 넣으면 됨
			//db라이브러리-->ojdbc6-->oracle.jdbc.driver-->OracleDriver.class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
			
			//2단계 오라클에 접속하자!!
			//접속하는 메서드는 암기사항이다...
			//"jdbc:oracle:thin:@localhost:1521", "batman", "1234"은
			//jdbc방식으로 oracle또는 mariadb를 접근해서 thin 타입으로
			//@를 쓰고 네트워크 표시를 한후 호스트(ip)를 쓰고 1521이라는 포트번호를 적는다...
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521", "batman", "1234");
			//sql에 접속했는지 보기위해서는 getConnection의 반환형인 Connection을 생성해서
			//이 객체가 null값인지를 체크해서 확인한다...
			if(con!=null){
				System.out.println("접속 성공");
				
				//현재 유저가 보유한 테이블에 insert시도
				String sql="insert into company(company_id, brand) values(seq_company.nextval, '나이키')";
				//쿼리문 수행을 위해서는 쿼리문을 전담
				//하는 객체를 이용해야 하는데, 이 객체가 바로
				//PreparedStatement인터페이스 이다
				pstmt = con.prepareStatement(sql);
				int result=pstmt.executeUpdate();//쿼리문 실행 메서드
				//이 쿼리문 수행에 의해 반영된 레코드의 수를 반환해준다!!
				if(result==1){
					System.out.println("입력성공");
				}else{
					System.out.println("입력실패");
				}
				
			}else{
				System.out.println("접속 실패");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//스트림과 DB 연동작업 후엔 반드시
			//닫는 처리를 해야한다!!
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
