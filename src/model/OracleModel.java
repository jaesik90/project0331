/*
 * 이 객체는 JTable의 생성자에서 요구하는
 * 컨트롤러 객체이다.
 * 이 객체의 역활은 디자인과 로직을 분리시켜
 * 주는 중간자 역활....
 * */
package model;

import javax.swing.table.AbstractTableModel;

public class OracleModel extends AbstractTableModel{
	String driver="oracle.jdbc.OracleDriver";
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="batman";
	String password="1234";
	String[][] data=new String[3][2];
	
	public OracleModel(){
		data[0][0]="짜장면";
		data[0][1]="북경반점";
		data[1][0]="샌드위치";
		data[1][1]="서브웨이";
		data[2][0]="핫크리스피 치킨";
		data[2][1]="KFC";
	}
	
	
	//컬럼의 갯수를 반환
	public int getColumnCount() {
		return 2;
	}

	//레코드의 갯수를 반환
	public int getRowCount() {
		return 3;
	}

	//특정 위치의 값을 반환
	public Object getValueAt(int row, int col) {
		System.out.println("row="+row+",col="+col+"에 뭘 넣어야 해요?");
		return data[row][col];
	}

}
