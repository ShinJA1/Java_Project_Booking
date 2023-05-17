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
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dbConn.util.ConnectionHelper;

public class BookController4 extends JFrame implements ActionListener{

	JPanel panWest, panSouth;
	JPanel p1, p2, p3, p4, p5, p6, p7, p8;
	
	JTextField txtRtNum, txtName, txtPhone, txtDate, txtTime, txtInwon, txtBNum, txtType;
	   JButton btnTotal, btnAdd, btnDel, btnSearch, btnUpdate, btnCancel, btnType, btnTime, btnBook ;

	   JTable table;
	   private static final int NONE = 0;
	   private static final int UPDATE = 1;
	   private static final int TOTAL = 2;
	   private static final int SEARCH = 3;//////////
	   
	   int cmd = NONE;
	   MyModel model;
	   
	   
	   Connection conn;
		Statement stmt;
		PreparedStatement pstmtInsert, pstmtDelete, pstmtUpdate;
		PreparedStatement pstmtTotal, pstmtTotalb,  pstmtTotalScroll;
		PreparedStatement pstmtSearch, pstmtSearchScroll;
		Scanner sc = new Scanner(System.in);
		ResultSet rs ;
	   
	
		private String sqlSelect = "SELECT * FROM rt";
		private String sqlTotal2 = "SELECT * FROM book";
		private String sqlUpdate = "UPDATE BOOK SET B_RT_NUM=?, B_NAME=?, B_PHONE=?, B_DATE=?, B_TIME=?, B_INWON=? WHERE B_NUM=?";
		private String sqlSearch = "SELECT * FROM RT WHERE RT_TYPE = ?";
		
		public void connect() {
			try {
				conn = ConnectionHelper.getConnection("oracle");
				pstmtTotal = conn.prepareStatement(sqlSelect); //식당 전체보기
				pstmtUpdate = conn.prepareStatement(sqlUpdate);
				pstmtTotalb = conn.prepareStatement(sqlTotal2); //예약확인
					pstmtSearch = conn.prepareStatement(sqlSearch); // 식당 카테고리 검색
				pstmtTotalScroll = conn.prepareStatement(sqlSelect,
						ResultSet.TYPE_SCROLL_SENSITIVE , //커서 이동을 자유롭게하고 업데이트 내용을 반영함
						ResultSet.CONCUR_UPDATABLE	);  //resultset object 변경이 가능<==> consur_read_only(읽기 전용, 변경 불가)
			
				
			} catch (Exception e) {  e.printStackTrace(); }
		}
		
		public BookController4() throws HeadlessException {
		     
			
		      panWest = new JPanel(new GridLayout(5, 0));
		      p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		      p1.add(new JLabel("식당 번호"));
		      p1.add(txtRtNum = new JTextField(12));
		      panWest.add(p1);
		      
		      p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		      p2.add(new JLabel("희망 예약일"));
		      p2.add(txtDate = new JTextField(12));
		      panWest.add(p2);
	
		      p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));  
		      txtTime = new JTextField(12); // Add this line to initialize txtTime
		      p3.add(new JLabel("시간 입력"));
		      p3.add(txtTime);
		      panWest.add(p3);
		   
		      p4 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		      p4.add(new JLabel("인원 입력"));
		      p4.add(txtInwon = new JTextField(12));
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
		      
		      p8 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		      p8.add(new JLabel("식당 카테고리"));
		      p8.add(txtType = new JTextField(12));
		      panWest.add(p8);

		      
		      add(panWest, "West");
		      
		      //button 화면 아래 등록
		      panSouth = new JPanel();
		      panSouth.add(btnTotal= new JButton("식당 전체보기")); 
		      panSouth.add(btnUpdate = new JButton("수     정"));
		      panSouth.add(btnCancel= new JButton("취     소"));
		      panSouth.add(btnBook= new JButton("예약 전체 보기"));
		      panSouth.add(btnSearch = new JButton("카테고리 검색"));////////// 검색
		      add(panSouth, "South");
		      
		      //event 처리
		      btnTotal.addActionListener(this);
		      btnCancel.addActionListener(this);
		      btnUpdate.addActionListener(this);
		      btnBook.addActionListener(this);
		      btnSearch.addActionListener(this);/////// 검색
		      
		      
		      connect();
		      //테이블 객체 생성
		      add(new JScrollPane(table = new JTable()), "Center");
		      //close
		      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      //메인 창 출력
		      setBounds(100, 100, 1000, 500); //setSize(W,H);   pack(); bounds:모서리 둥글, (x,y 좌표 위치, 너비 높이)
		      setVisible(true);   
		      
		    //  dbConnect();
		      
		      
		}
		@Override
		public void actionPerformed(ActionEvent e) {  //버튼 이벤트 처리
			Object obj = e.getSource();
	
			// 수정
			if( obj == btnUpdate ){
				System.out.println("btnUpdate 버튼누름");
				if( cmd != UPDATE ){
					setText(UPDATE);  //user method
					return;
				} //if in
				setTitle(e.getActionCommand());
				update();  //수정
			}else if( obj == btnTotal ){
				System.out.println("btnTotal");
				setTitle(e.getActionCommand());
				total();  //전체보기
			}else if( obj == btnBook){
				System.out.println("btnBook");
				setTitle(e.getActionCommand());
				totalBook();  //예약 전체보기
			}else if( obj == btnSearch){
				System.out.println("btnSearch");
				setTitle(e.getActionCommand());
				search();  //검색 ////////////
			}
			setText(NONE);
			init(); //초기화 메소드, user method
		}// actionPerformed end

	
private void search() {///////////////// 식당 카테고리 검색
		
	try {
			System.out.println("search");
			ResultSet rs = pstmtTotal.executeQuery();
			ResultSet rsScroll = pstmtTotalScroll.executeQuery();
			
			if(model == null) model = new MyModel();
			
			model.getRowCount(rsScroll);
			model.setData(rs);
			
			table.setModel(new DefaultTableModel(model.data, model.columnName));
			table.updateUI();  

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}//total end (select)


private void totalBook() {
	try {
		System.out.println("total2");
		ResultSet rs = pstmtTotalb.executeQuery();
		ResultSet rsScroll = pstmtTotalScroll.executeQuery();
		
		if(model == null) model = new MyModel();
		
		model.getRowCount(rsScroll);
		model.setData(rs);
		
//		table.setModel(model);
		table.setModel(new DefaultTableModel(model.data, model.columnName));
		table.updateUI();  //화면 다시 그려줌 N 
	
	} catch (Exception e) {
		e.printStackTrace();
	}
			// TODO Auto-generated method stub
			
		}

//		예약 수정
		public void update() {
			try {
				System.out.println("update@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//				total();
				String strBNum = txtBNum.getText();////////////////////////////
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
						"수정하시겠습니까? ",
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
				JOptionPane.showMessageDialog(null, "수정 완료.");
			} catch (Exception e) {
				e.printStackTrace();
//				JOptionPane.showMessageDialog(null, "수정 싪패.");
				
//				total();
				init();
				
			}
		}// update()
		
		
		
		private void total() { //select 전체보기
			try {
				System.out.println("total");
				ResultSet rs = pstmtTotal.executeQuery();
				ResultSet rsScroll = pstmtTotalScroll.executeQuery();
				
				if(model == null) model = new MyModel();
				
				model.getRowCount(rsScroll);
				model.setData(rs);
				
//				table.setModel(model);
				table.setModel(new DefaultTableModel(model.data, model.columnName));
				table.updateUI();  //화면 다시 그려줌 N 
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}//total end (select)


		private void init() {  //초기화 메소드
			txtRtNum.setText("");			txtRtNum.setEditable(false);
			txtName.setText("");		txtName.setEditable(false);
			txtPhone.setText("");		txtPhone.setEditable(false);
			txtDate.setToolTipText("");		txtDate.setEditable(false);
			txtTime.setText("");		txtTime.setEditable(false);
			txtInwon.setText("");		txtInwon.setEditable(false);
			txtBNum.setText("");		txtBNum.setEditable(false);
			txtType.setToolTipText("");		txtType.setEditable(false);
		}// init() end

		private void setText(int command) {  //user method
			switch(command){
				
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
			btnUpdate.setEnabled(false);
			
			switch(command){
				
				case TOTAL :
					btnTotal.setEnabled(true);
					cmd = TOTAL;
					break;
				case UPDATE :
					btnUpdate.setEnabled(true);
					cmd = UPDATE;
					break;
				case SEARCH :///////////
					txtName.setEditable(true);
					break;
				case NONE :
					btnTotal.setEnabled(true);
					btnUpdate.setEnabled(true);
					cmd = NONE;
					break;
			}//switch end	
		}//setButton end

	   public static void main(String[] args) {
	      new BookController4();
	   }

}
