package project;


import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.AbstractButton;
import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;
import java.awt.Scrollbar;

public class registertestpage extends JFrame {

   private JPanel contentPane;
   private JTextField ProjectNameTxt;
   private JScrollPane scrollPane;
   private JLabel imagelabel;
   private JTextField TargetPriceTxt;
   JFileChooser chooser;
  	String filePath;

   
   /**
    * Launch the application.
    */
  	/*
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
            	registertestpage frame = new registertestpage();
               	frame.setVisible(true);
               	frame.setResizable(false);
       			frame.setLocationRelativeTo(null);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }
   */

   /**
    * Create the frame.
    */
  	// [2차 추가]
  	public JPanel viewRegister() { 
        JPanel Register_contentPanel  = new JPanel();
        Register_contentPanel.setBounds(193, 23, 1073, 660);
        contentPane.add(Register_contentPanel);
        Register_contentPanel.setLayout(null);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 31, 1073, 79);
        headerPanel.setBackground(new Color(110, 89, 222));
        Register_contentPanel.add(headerPanel);
        headerPanel.setLayout(null);
        
        JLabel headerText = new JLabel("Project Register");
        headerText.setBounds(40, 10, 238, 57);
        headerText.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        headerText.setForeground(Color.WHITE);
        headerPanel.add(headerText);
        
        JPanel mpanel = new JPanel();
        mpanel.setBounds(0, 108, 1073, 552);
        Register_contentPanel.add(mpanel);
        mpanel.setLayout(null);
        
        ProjectNameTxt = new JTextField();
        ProjectNameTxt.setFont(new Font("굴림", Font.PLAIN, 13));
        ProjectNameTxt.setBounds(171, 74, 684, 34);
        mpanel.add(ProjectNameTxt);
        ProjectNameTxt.setColumns(10);
        
        JLabel imagelabel = new JLabel("Not Attached ");
        imagelabel.setHorizontalAlignment(SwingConstants.RIGHT);
        imagelabel.setBounds(516, 118, 335, 34);
        mpanel.add(imagelabel);
        
        JButton btn_Attached = new JButton("");
        btn_Attached.addActionListener(new OpenActionListener(imagelabel));
        btn_Attached.setIcon(new ImageIcon("C:\\test\\FS.jpg"));
        btn_Attached.setBounds(875, 114, 43, 34);
        mpanel.add(btn_Attached);
        
        
        
        JTextPane txtpnTitle = new JTextPane();
        txtpnTitle.setBackground(SystemColor.control);
        txtpnTitle.setText("Project Title");
        txtpnTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtpnTitle.setBounds(33, 74, 111, 34);
        mpanel.add(txtpnTitle);
        
        JTextPane txtpnContent = new JTextPane();
        txtpnContent.setBackground(SystemColor.control);
        txtpnContent.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtpnContent.setText("Description");
        txtpnContent.setBounds(33, 161, 131, 34);
        mpanel.add(txtpnContent);
        
        JTextArea ProjectDescriptionTxt = new JTextArea();
        scrollPane = new JScrollPane(ProjectDescriptionTxt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(170, 161, 748, 297);
        mpanel.add(scrollPane);
        
        
        
        JButton btn_Back = new JButton("Back");
        btn_Back.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
        btn_Back.setForeground(Color.WHITE);
        btn_Back.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btn_Back.setBackground(new Color(102, 205, 170));
        btn_Back.setBounds(465, 475, 74, 34);
        mpanel.add(btn_Back);
        
        
        
        JLabel lblNewLabel = new JLabel(" (Open)");
        lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNewLabel.setBounds(867, 70, 74, 34);
        mpanel.add(lblNewLabel);
        
        
        
        
        
        JTextPane txtpnTarget = new JTextPane();
        txtpnTarget.setText("Target Price");
        txtpnTarget.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtpnTarget.setBackground(SystemColor.menu);
        txtpnTarget.setBounds(33, 114, 131, 34);
        mpanel.add(txtpnTarget);
        
        TargetPriceTxt = new JTextField();
        TargetPriceTxt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        TargetPriceTxt.setHorizontalAlignment(SwingConstants.RIGHT);
        TargetPriceTxt.setColumns(10);
        TargetPriceTxt.setBounds(171, 118, 119, 34);
        mpanel.add(TargetPriceTxt);
        
        JButton btn_Post = new JButton("Post");
        btn_Post.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		/* [주석]
        		DB projectpost = new DB(ProjectNameTxt.getText(), ProjectDescriptionTxt.getText(), TargetPriceTxt.getText(), filePath );
        		projectpost.inputProjectDB();
        		*/
      		//팝업 //Okbtn 이벤트 수정필요
        		ProjectPostDialog dialog2 = new ProjectPostDialog();
        		dialog2.setVisible(true);
        		
        		
        	}
        });
        
        btn_Post.setForeground(Color.WHITE);
        btn_Post.setBackground(new Color(102, 205, 170));
        btn_Post.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn_Post.setBounds(563, 475, 74, 34);
        mpanel.add(btn_Post);
        
        
        
        JLabel lblNewLabel_1 = new JLabel("\\");
        lblNewLabel_1.setFont(new Font("돋움", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(295, 118, 29, 34);
        mpanel.add(lblNewLabel_1);
        
        return Register_contentPanel; 
  	}
  	
   public registertestpage() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 1280, 720);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JPanel navPanel = new JPanel();
      navPanel.setBackground(new Color(54, 33, 89));
      navPanel.setBounds(12, 23, 181, 660);
      contentPane.add(navPanel);
      navPanel.setLayout(null);
      
      JButton BrandColumn = new JButton("BRAND");
      BrandColumn.setForeground(Color.WHITE);
      BrandColumn.setFont(new Font("굴림", Font.BOLD, 25));
      BrandColumn.setBackground(new Color(54, 33, 89));
      BrandColumn.setIcon(null);
      BrandColumn.setBounds(0, 32, 185, 76);
      BrandColumn.setFocusPainted(false);
      BrandColumn.setBorderPainted(false);
      navPanel.add(BrandColumn);
      
      JButton column1 = new JButton("Project Register");
      
      column1.setForeground(Color.WHITE);
      column1.setFont(new Font("Segoe UI", Font.BOLD, 12));
      column1.setBackground(new Color(85, 65, 118));
      column1.setBounds(0, 158, 185, 44);
      column1.setBorderPainted(false);
      column1.setFocusPainted(false);
      
      navPanel.add(column1);
      
      JButton column2 = new JButton("Review");
      column2.setForeground(Color.WHITE);
      column2.setFont(new Font("Segoe UI", Font.BOLD, 12));
      column2.setFocusPainted(false);
      column2.setBorderPainted(false);
      column2.setBackground(new Color(85, 65, 118));
      column2.setBounds(0, 224, 185, 44);
      navPanel.add(column2);
      
      JButton column3 = new JButton("My page");
      column3.setForeground(Color.WHITE);
      column3.setFont(new Font("Segoe UI", Font.BOLD, 12));
      column3.setFocusPainted(false);
      column3.setBorderPainted(false);
      column3.setBackground(new Color(85, 65, 118));
      column3.setBounds(0, 292, 185, 44);
      navPanel.add(column3);
      
      JPanel Register_contentPanel  = new JPanel();
      Register_contentPanel.setBounds(193, 23, 1073, 660);
      contentPane.add(Register_contentPanel);
      Register_contentPanel.setLayout(null);
      
      JPanel headerPanel = new JPanel();
      headerPanel.setBounds(0, 31, 1073, 79);
      headerPanel.setBackground(new Color(110, 89, 222));
      Register_contentPanel.add(headerPanel);
      headerPanel.setLayout(null);
      
      JLabel headerText = new JLabel("Project Register");
      headerText.setBounds(40, 10, 238, 57);
      headerText.setFont(new Font("Segoe UI", Font.PLAIN, 30));
      headerText.setForeground(Color.WHITE);
      headerPanel.add(headerText);
      
      JPanel mpanel = new JPanel();
      mpanel.setBounds(0, 108, 1073, 552);
      Register_contentPanel.add(mpanel);
      mpanel.setLayout(null);
      
      ProjectNameTxt = new JTextField();
      ProjectNameTxt.setFont(new Font("굴림", Font.PLAIN, 13));
      ProjectNameTxt.setBounds(171, 74, 684, 34);
      mpanel.add(ProjectNameTxt);
      ProjectNameTxt.setColumns(10);
      
      JLabel imagelabel = new JLabel("Not Attached ");
      imagelabel.setHorizontalAlignment(SwingConstants.RIGHT);
      imagelabel.setBounds(516, 118, 335, 34);
      mpanel.add(imagelabel);
      
      JButton btn_Attached = new JButton("");
      btn_Attached.addActionListener(new OpenActionListener(imagelabel));
      btn_Attached.setIcon(new ImageIcon("C:\\test\\FS.jpg"));
      btn_Attached.setBounds(875, 114, 43, 34);
      mpanel.add(btn_Attached);
      
      
      
      JTextPane txtpnTitle = new JTextPane();
      txtpnTitle.setBackground(SystemColor.control);
      txtpnTitle.setText("Project Title");
      txtpnTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      txtpnTitle.setBounds(33, 74, 111, 34);
      mpanel.add(txtpnTitle);
      
      JTextPane txtpnContent = new JTextPane();
      txtpnContent.setBackground(SystemColor.control);
      txtpnContent.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      txtpnContent.setText("Description");
      txtpnContent.setBounds(33, 161, 131, 34);
      mpanel.add(txtpnContent);
      
      JTextArea ProjectDescriptionTxt = new JTextArea();
      scrollPane = new JScrollPane(ProjectDescriptionTxt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.setBounds(170, 161, 748, 297);
      mpanel.add(scrollPane);
      
      
      
      JButton btn_Back = new JButton("Back");
      btn_Back.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		
      	}
      });
      btn_Back.setForeground(Color.WHITE);
      btn_Back.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      btn_Back.setBackground(new Color(102, 205, 170));
      btn_Back.setBounds(465, 475, 74, 34);
      mpanel.add(btn_Back);
      
      
      
      JLabel lblNewLabel = new JLabel(" (Open)");
      lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      lblNewLabel.setBounds(867, 70, 74, 34);
      mpanel.add(lblNewLabel);
      
      
      
      
      
      JTextPane txtpnTarget = new JTextPane();
      txtpnTarget.setText("Target Price");
      txtpnTarget.setFont(new Font("Segoe UI", Font.PLAIN, 18));
      txtpnTarget.setBackground(SystemColor.menu);
      txtpnTarget.setBounds(33, 114, 131, 34);
      mpanel.add(txtpnTarget);
      
      TargetPriceTxt = new JTextField();
      TargetPriceTxt.setFont(new Font("Segoe UI", Font.PLAIN, 15));
      TargetPriceTxt.setHorizontalAlignment(SwingConstants.RIGHT);
      TargetPriceTxt.setColumns(10);
      TargetPriceTxt.setBounds(171, 118, 119, 34);
      mpanel.add(TargetPriceTxt);
      
      JButton btn_Post = new JButton("Post");
      btn_Post.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		/* [주석]
      		DB projectpost = new DB(ProjectNameTxt.getText(), ProjectDescriptionTxt.getText(), TargetPriceTxt.getText(), filePath );
      		projectpost.inputProjectDB();
      		*/
    		//팝업 //Okbtn 이벤트 수정필요
      		ProjectPostDialog dialog2 = new ProjectPostDialog();
      		dialog2.setVisible(true);
      		
      		
      	}
      });
      
      btn_Post.setForeground(Color.WHITE);
      btn_Post.setBackground(new Color(102, 205, 170));
      btn_Post.setFont(new Font("Segoe UI", Font.BOLD, 15));
      btn_Post.setBounds(563, 475, 74, 34);
      mpanel.add(btn_Post);
      
      
      
      JLabel lblNewLabel_1 = new JLabel("\\");
      lblNewLabel_1.setFont(new Font("돋움", Font.PLAIN, 15));
      lblNewLabel_1.setBounds(295, 118, 29, 34);
      mpanel.add(lblNewLabel_1);
      

      JPanel mainPanel = new JPanel();
      mainPanel.setBounds(0, 0, 1395, 800);
      contentPane.add(mainPanel);
   }
   
   
 //OpenBtn이 선택되면 호출되는 Action 리스너
   class OpenActionListener implements ActionListener{

   	 OpenActionListener(JLabel l) {
   		 imagelabel = l;
            chooser= new JFileChooser(); // 파일 다이얼로그 생성

        }
   	
   	@Override
   	 public void actionPerformed(ActionEvent e) {
           FileNameExtensionFilter filter = new FileNameExtensionFilter(
                      "JPG & PNG", // 파일 이름에 창에 출력될 문자열
                      "jpg", "png"); // 파일 필터로 사용되는 확장자.
           chooser.setFileFilter(filter); // 파일 다이얼로그에 파일 필터 설정
           chooser.setMultiSelectionEnabled(false); //멀티선택 불가
          // 파일 다이얼로그 출력
          int ret = chooser.showOpenDialog(null);
          if(ret != JFileChooser.APPROVE_OPTION) { 
       	   // 사용자가  창을 강제로 닫았거나 취소 버튼을 누른 경우
              JOptionPane.showMessageDialog(null,"파일을 선택하지 않았습니다", "경고",JOptionPane.WARNING_MESSAGE);
              return;
          }
          
          // 사용자가 파일을 선택하고 "열기" 버튼을 누른 경우
          filePath = chooser.getSelectedFile().getPath(); // 파일 경로명을 알아온다.
          imagelabel.setText("열기한 파일 : "+chooser.getSelectedFile().getName()); // 파일을 로딩하여  레이블에 파일이름 출력한다.
         
          //image.setIcon
//          imagelabel.setText("열기 경로 : " + chooser.getSelectedFile().toString());
          //pack(); // 이미지의 크기에 맞추어 프레임의 크기 조절
   
          System.out.println(chooser.getSelectedFile().toString());
          

   	}
   	
   	

   	
   }
}
class ProjectPostDialog extends JDialog{
	JLabel pcl = new JLabel("등록되었습니다.");
	
	JButton Okbtn = new JButton("OK");
	
	ProjectPostDialog(){
		
		setLayout(new FlowLayout());
		add(pcl);
		add(Okbtn);
		setSize(200,100);
		setLocationRelativeTo(null);
		
		pcl.setFont(new Font("굴림", Font.PLAIN, 18));
		
		//Ok버튼 리스너
		Okbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

				
				
			}
		});
	}
	

}
