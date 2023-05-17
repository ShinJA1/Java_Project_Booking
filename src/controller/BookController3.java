package controller;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dbConn.util.ConnectionHelper;



public class BookController3 extends JFrame implements ActionListener {
	
	int size;
   
   JPanel panWest, panSouth;
   JPanel p1, p2, p3, p4, p5, p6, p7, p8;
   JTextField txtRtNum, txtName, txtPhone, txtDate, txtTime, txtInwon, txtBNum, txtType;
   JButton btnTotal, btnAdd, btnDel, btnSearch, btnUpdate, btnCancel, btnType, btnTime ;

   JTable table;
   
   public static ArrayList<String> list;
   
   private static final int NONE = 0;
   private static final int ADD = 1;
   private static final int SEARCH = 2;
   private static final int DELETE = 3;
   private static final int UPDATE = 5;
   private static final int TYPE = 6;  ////////////////////////////////////
   private static final int TIME = 7;  /////////////////////////////////////

   private static final int TOTAL = 4;

   int cmd = NONE;
   
   MyModel model;
   
	Connection conn;
	Statement stmt;
	PreparedStatement pstmtInsert, pstmtDelete, pstmtUpdate, pstmtType, pstmtTime, pstmtType2;
	PreparedStatement pstmtTotal, pstmtTotalScroll;
	PreparedStatement pstmtSearch, pstmtSearchScroll;
	Scanner sc = new Scanner(System.in);
	ResultSet rs ;
   
	private String sqlInsert = "INSERT INTO CUSTOMERS VALUES(?,?,?,?)";    //예약하기
	private String sqlDelete = "DELETE FROM CUSTOMERS WHERE CODE = ?";      //예약삭제
	private String sqlSelect = "SELECT * FROM rt";      //식당목록
	private String sqlSearch = "select * from book where B_NUM = ? ";  //예약 확인
	private String sqlUpdate ="UPDATE BOOK SET B_RT_NUM=?, B_NAME=?, B_PHONE=?, B_DATE=?, B_TIME=?, B_INWON=? WHERE B_NUM=?";    //예약수정
	private String sqlType = "Select * from rt where rt_type = ?";     //삭당 카테고리 별 목록 보기
	private String sqlType2 = "SELECT RT_TYPE FROM RT";
	private String sqlTime = "SELECT RT_TYPE FROM RT";    //예약시간 선택-가능한 식당 목록
	
	public void connect() {
		try {
			conn = ConnectionHelper.getConnection("oracle");
			pstmtInsert = conn.prepareStatement(sqlInsert);   //예약하기
			pstmtDelete = conn.prepareStatement(sqlDelete);    //예약 삭제
			pstmtTotal = conn.prepareStatement(sqlSelect);      //식당목록 전체보기
			pstmtSearch = conn.prepareStatement(sqlSearch);     //예약확인
			pstmtUpdate = conn.prepareStatement(sqlUpdate);     //예약수정
			pstmtType = conn.prepareStatement(sqlType);       //식당 카테고리
			pstmtTime = conn.prepareStatement(sqlTime);        //예약시간 선택
			pstmtType2 = conn.prepareStatement(sqlType2); 
			
			pstmtTotalScroll = conn.prepareStatement(sqlSelect,
					ResultSet.TYPE_SCROLL_SENSITIVE , //커서 이동을 자유롭게하고 업데이트 내용을 반영함
					ResultSet.CONCUR_UPDATABLE	);  //resultset object 변경이 가능<==> consur_read_only(읽기 전용, 변경 불가)
			pstmtSearchScroll = conn.prepareStatement(sqlSearch,
					ResultSet.TYPE_SCROLL_SENSITIVE , //커서 이동을 자유롭게하고 업데이트 내용을 반영함
					ResultSet.CONCUR_UPDATABLE	);  //resultset object 변경이 가능<==> consur_read_only(읽기 전용, 변경 불가)
			
//			stmt = conn.createStatement();
//			conn.setAutoCommit(true);  
			
		} catch (Exception e) {  e.printStackTrace(); }
	}

   public BookController3() throws HeadlessException {
     
      panWest = new JPanel(new GridLayout(5, 0));
      p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));  //오른쪽정렬
      p1.add(new JLabel("식당 번호"));
      p1.add(txtRtNum = new JTextField(12));
      panWest.add(p1);
    
      p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
      p2.add(new JLabel("희망 예약일"));
      p2.add(txtRtNum = new JTextField(12));
      panWest.add(p2);
      
      p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));  
      p3.add(new JLabel("시간 입력"));
      p3.add(txtRtNum = new JTextField(12));
      panWest.add(p3);
      
      p4 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
      p4.add(new JLabel("인원 입력"));
      p4.add(txtRtNum = new JTextField(12));
      panWest.add(p4);
      
      p5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p5.add(new JLabel("예약자명"));
      p5.add(txtName = new JTextField(12));
      panWest.add(p5);
      
      p6 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p6.add(new JLabel("전화번호"));
      p6.add(txtPhone = new JTextField(12));
      panWest.add(p6);
      
      p7 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p7.add(new JLabel("예약 번호"));
      p7.add(txtBNum = new JTextField(12));
      panWest.add(p7);
      
//      try {
////		ArrayList<String> list = returnList();
//		String[] listt = new String[size];  //////////////////////////////////////////////
//		listt = returnList();
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//      p8 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//      p8.add(new JLabel("식당 카테고리"));
//      p8.add(txtType = new JComboBox<String>());
//      panWest.add(p8);
      
      p8 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p8.add(new JLabel("식당 카테고리"));
      p8.add(txtType = new JTextField(12));
      panWest.add(p8);

      
      add(panWest, "West");
      
      //button 화면 아래 등록
      panSouth = new JPanel();
      panSouth.add(btnTotal= new JButton("식당 전체보기")); 
      panSouth.add(btnAdd= new JButton("예     약"));
      panSouth.add(btnSearch= new JButton("예약조회"));
      panSouth.add(btnUpdate = new JButton("수     정"));
      panSouth.add(btnDel= new JButton("삭     제"));
      panSouth.add(btnTime = new JButton("예약 가능한 식당 보기"));
      panSouth.add(btnType = new JButton("식당 분류 목록"));
      panSouth.add(btnCancel= new JButton("취     소"));
      add(panSouth, "South");
      
      //event 처리
      btnTotal.addActionListener(this);
      btnAdd.addActionListener(this);
      btnDel.addActionListener(this);
      btnSearch.addActionListener(this);
      btnCancel.addActionListener(this);
      btnUpdate.addActionListener(this);
      btnType.addActionListener(this);
      btnTime.addActionListener(this);
      
      connect();
      
      //테이블 객체 생성
      add(new JScrollPane(table = new JTable()), "Center");
      //close
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //메인 창 출력
      setBounds(100, 100, 1000, 300); //setSize(W,H);   pack(); bounds:모서리 둥글, (x,y 좌표 위치, 너비 높이)
      setVisible(true);   
      
    //  dbConnect();
      
      
}
   
   
	@Override
	public void actionPerformed(ActionEvent e) {  //버튼 이벤트 처리
		Object obj = e.getSource();
		if( obj == btnAdd ){
			if( cmd != ADD ){
				setText(ADD);  //user method
				return;
			} //if in
			setTitle(e.getActionCommand());
//			add();  // 추가
			
			
		}else if( obj == btnDel ){
			if( cmd != DELETE ){
				setText(DELETE);  //user method
				return;
			} //if in
			setTitle(e.getActionCommand());
//			try {
//				del();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			} // 삭제
			 
		}else if( obj == btnSearch ){
			if( cmd != SEARCH ){
				setText(SEARCH);  //user method
				return;
			} //if in
			setTitle(e.getActionCommand());
//			search(); // 검색
//			try {
//				BookingController.selectByType(getName());
//			} catch (SQLException e1) {e1.printStackTrace();}
			
		}else if( obj == btnUpdate ){
			System.out.println("update        button --------------------------");
			if( cmd != UPDATE ){
				setText(UPDATE);  //user method
				return;
			} //if in
			setTitle(e.getActionCommand());
			update();  //수정
//			try {
//				BookingController.update(getName());
//			} catch (SQLException e1) {e1.printStackTrace();}   //null++++++++++++++++++++++++
		} else if( obj == btnType ){
			if( cmd != TYPE ){
				setText(TYPE);  //user method
				return;
			} //if in
			setTitle(e.getActionCommand());
			try {
				selectType();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  //type end
		}else if( obj == btnTotal ){
			System.out.println("type   type");
			setTitle(e.getActionCommand());
			total();  //전체보기
		}
		setText(NONE);
		init(); //초기화 메소드, user method
	}// actionPerformed end
   
//  예약 수정
  public void update() {
     System.out.println("update@@@@@@@@@@@@@@@@@@@@@@@@@@@");
     try {
//        total();
        String strBNum = txtBNum.getText();
        String strRtNum = txtRtNum.getText();
        String strName = txtName.getText();
        String strPhone = txtPhone.getText();
        String strDate = txtDate.getText();
        String strTime = txtTime.getText();
        String strInwon = txtInwon.getText();
  //"UPDATE BOOK SET B_RT_NUM=?, B_NAME=?, B_PHONE=?, B_DATE=?, B_TIME=?, B_INWON=? WHERE B_NUM=?";      
        System.out.println(strRtNum+", "+strName+", "+ strPhone+", "+strDate+" , " +strTime+", "+strInwon);
        
        if (strRtNum.length() < 1) {
           JOptionPane.showMessageDialog(null, "예약번호는 필수 입력 사항입니다.");
           return;
        }// if end
        
        switch (JOptionPane.showConfirmDialog(null,
              "(식당번호: "+strRtNum+", 예약자명"+strName+", 연락처"+ strPhone+", 날짜"+strDate+", 시간"+strTime+", 인원"+strInwon+")" ,
              "수정하시겠니? ",
               JOptionPane.YES_NO_OPTION ) ) {
              case 0 :  //확인
                 break;
              case 1 :  // 아니오
                 return;
           } // switch end      

        pstmtUpdate.setString(1, strRtNum); 
        pstmtUpdate.setString(2, strName);
        pstmtUpdate.setString(3, strPhone);
        pstmtUpdate.setString(4, strDate);
        pstmtUpdate.setString(5, strTime);
        pstmtUpdate.setString(6, strInwon);
        pstmtUpdate.setInt(7, Integer.parseInt(strBNum)); // 예약번호
        pstmtUpdate.executeUpdate();
        //sql = update
        
     } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
        
//        total();
        init();
        
     }
  }// update()

	//////////////////////////////////////////////////////////////////////////////////////////

	private void selectType() throws SQLException {
		
		try {
//			System.out.println("type button");
			String cat = txtType.getText();
			System.out.println("입력한 카테고리:  "+cat); 
			pstmtType.setString(1, cat);
			pstmtType.executeUpdate();
			ResultSet rs = pstmtType.executeQuery();
//			ResultSet rsScroll = pstmtTotalScroll.executeQuery();
			
			if(model == null) model = new MyModel();
			
//			model.getRowCount(rsScroll);
			model.setData(rs);
			
//			table.setModel(model);
			table.setModel(new DefaultTableModel(model.data, model.columnName));
			table.updateUI();  //화면 다시 그려줌
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String[] returnList() throws SQLException {
		
		ArrayList<String> list = new ArrayList<>();
		ResultSet rs = pstmtType2.executeQuery();
		size = rs.getFetchSize();
		String[] listt = new String[size];
//		ResultSetMetaData rsmd = rs.getMetaData();
//		while(rs.next()) {
//			list.add(rs.getString(1));
//			lis
//		}
		for(int i=0; i<size; i++) {
			listt[i] = rs.getString(i);
		}
		return listt;
		
	}

/////////////////////////////////////////////////////////////////////////////////////////////
	
	private void total() { //select 전체보기
		try {
			ResultSet rs = pstmtTotal.executeQuery();
			ResultSet rsScroll = pstmtTotalScroll.executeQuery();
			
			if(model == null) model = new MyModel();
			
			model.getRowCount(rsScroll);
			model.setData(rs);
			
//			table.setModel(model);
			table.setModel(new DefaultTableModel(model.data, model.columnName));
			table.updateUI();  //화면 다시 그려줌 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}//total end (select)


	private void init() {  //초기화 메소드
		txtRtNum.setText("");			txtRtNum.setEditable(false);
		txtName.setText("");		txtName.setEditable(false);
		txtPhone.setText("");		txtPhone.setEditable(false);
		txtDate.setText("");		txtDate.setEditable(false);
		txtTime.setText("");		txtTime.setEditable(false);
		txtInwon.setText("");		txtInwon.setEditable(false);
		txtBNum.setText("");		txtBNum.setEditable(false);
		txtType.setText("");		txtType.setEditable(false);
//		txtType.setToolTipText("카테고리");		txtType.setEditable(false);
	}// init() end

	private void setText(int command) {  //user method
		switch(command){
			case ADD :
				txtRtNum.setEditable(true);
				txtName.setEditable(true);
				txtDate.setEditable(true);
				txtPhone.setEditable(true);
				break;
			case DELETE :
				txtBNum.setEditable(true);
				break;
			case SEARCH :
				txtBNum.setEditable(true);
				break;
			case TYPE :
				txtType.setEditable(true);
				break;
			case TIME :
				txtTime.setEditable(true);
				break;
			case UPDATE :
				txtRtNum.setEditable(true);
				txtName.setEditable(true);
				txtPhone.setEditable(true);
				txtDate.setEditable(true);
				txtTime.setEditable(true);
				txtInwon.setEditable(true);
				break;
		}//switch end
		
		setButton(command);  //user method
	}// setText() end

	private void setButton(int command) {  //user method
		//cancel button 제외하고 어떤 버튼이 눌리더라도 모든 버튼이 비활성화
		btnTotal.setEnabled(false);
		btnAdd.setEnabled(false);
		btnDel.setEnabled(false);
		btnSearch.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnType.setEnabled(false);
		btnTime.setEnabled(false);
		
		switch(command){
			case ADD :
				btnAdd.setEnabled(true);
				cmd = ADD;
				break;
			case DELETE :
				btnDel.setEnabled(true);
				cmd = DELETE;
				break;
				
			case SEARCH :
				btnSearch.setEnabled(true);
				cmd = SEARCH;
				break;
			case TOTAL :
				btnTotal.setEnabled(true);
				cmd = TOTAL;
				break;
			case UPDATE :
				btnUpdate.setEnabled(true);
				cmd = UPDATE;
				break;
			case TYPE :
				btnType.setEnabled(true);
				cmd = TYPE;
				break;
			case TIME :
				btnTime.setEnabled(true);
				cmd = TIME;
				break;
				
			case NONE :
				btnTotal.setEnabled(true);
				btnAdd.setEnabled(true);
				btnDel.setEnabled(true);
				btnSearch.setEnabled(true);
				btnCancel.setEnabled(true);  //
				btnUpdate.setEnabled(true);
				btnTime.setEnabled(true);
				btnType.setEnabled(true);
				cmd = NONE;
				break;
		}//switch end	
	}//setButton end

	
	

   public static void main(String[] args) {
      new BookController3();
   }


}