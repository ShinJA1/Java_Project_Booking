package controller;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class BookController2 extends JFrame implements ActionListener {
   
   JPanel panWest, panSouth;
   JPanel p1, p2, p3, p4, p5, p6, p7,p8;
   JTextField txtRtNum, txtName, txtPhone, txtDate, txtTime, txtInwon, txtBNum, txtType;
   JButton btnTotal, btnAdd, btnDel, btnSearch, btnUpdate, btnCancel, btnTime, btnType;
   
   JTable table;
   
   private static final int NONE = 0;
   private static final int ADD = 1;
   private static final int SEARCH = 2;
   private static final int DELETE = 3;
   private static final int TOTAL = 4;

   int cmd = NONE;
   
   

   public BookController2() throws HeadlessException {
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
      p7.add(txtDate = new JTextField(12));
      panWest.add(p7);
      
      p8 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p8.add(new JLabel("식당 카테고리"));
      p8.add(txtDate = new JTextField(12));
      panWest.add(p8);
      
     
      add(panWest, "West");
      
      //button 화면 아래 등록
      panSouth = new JPanel();
      panSouth.add(btnTotal= new JButton("식당 전체보기")); 
      panSouth.add(btnAdd= new JButton("예     약"));
      panSouth.add(btnAdd= new JButton("예약조회"));
      panSouth.add(btnSearch = new JButton("수     정"));
      panSouth.add(btnDel= new JButton("삭     제"));
      panSouth.add(btnTime = new JButton("예약 가능한 식당 보기"));
      panSouth.add(btnType = new JButton("식당 분류 목록"));
      panSouth.add(btnCancel= new JButton("취     소"));

      add(panSouth, "South");
      add(panSouth, "South");
      
      //event 처리
//      btnTotal.addActionListener(this);
//      btnAdd.addActionListener(this);
//      btnDel.addActionListener(this);
//      btnSearch.addActionListener(this);
//      btnCancel.addActionListener(this);
//      btnUpdate.addActionListener(this);
      
      //테이블 객체 생성
      add(new JScrollPane(table = new JTable()), "Center");
      //close
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //메인 창 출력
      setBounds(100, 100, 700, 300); //setSize(W,H);   pack(); bounds:모서리 둥글, (x,y 좌표 위치, 너비 높이)
      setVisible(true);   
      
    //  dbConnect();
}

   public static void main() {
      new BookController2();
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      
   }
}