package project;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.SystemColor;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.Renderer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class boardtestpage extends JFrame {

	private JPanel contentPane;

	private JTable table;
	private JScrollPane scrollPane; // 테이블 스크롤바 자동으로 생성되게 하기

	private String driver = "oracle.jdbc.driver.OracleDriver";
	String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	String DB_USER = "c##ora_user";
	String DB_PASSWORD = "jang";

	private String colNames[] = { "Reviews" }; // 테이블 컬럼 값들
	private DefaultTableModel model = new DefaultTableModel(colNames, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			if (column >= 0) {
				return false;
			} else {
				return true;
			}
		}
	}; // 테이블 데이터 모델

	public JPanel viewBoard() {
		JPanel Board_contentPanel = new JPanel();
		Board_contentPanel.setBounds(193, 23, 1073, 660);
		contentPane.add(Board_contentPanel);
		Board_contentPanel.setLayout(null);

		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(110, 89, 222));
		headerPanel.setBounds(0, 31, 1073, 79);
		Board_contentPanel.add(headerPanel);
		headerPanel.setLayout(null);

		JLabel hederText = new JLabel("BoardList");
		hederText.setBounds(40, 10, 193, 57);
		hederText.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		hederText.setForeground(Color.WHITE);
		headerPanel.add(hederText);

		FileOutputStream fos;

		JPanel ptablepanel = new JPanel();
		ptablepanel.setBounds(0, 304, 1049, 333);
		Board_contentPanel.add(ptablepanel);
		ptablepanel.setLayout(null);
		table = new JTable(model); // 테이블에 모델객체 삽입
		// table.addMouseListener(new JTableMouseListener()); // 테이블에 마우스리스너 연결
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(57, 21, 944, 290);
		ptablepanel.add(scrollPane);

		JLabel lblNewLabel = new JLabel("Review");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		lblNewLabel.setBounds(53, 192, 74, 30);
		Board_contentPanel.add(lblNewLabel);

		JScrollPane WritescrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		WritescrollPane.setBounds(143, 151, 800, 112);
		Board_contentPanel.add(WritescrollPane);

		JTextArea BoardContentTxtArea = new JTextArea();
		WritescrollPane.setViewportView(BoardContentTxtArea);

		JButton btn_Post = new JButton("Post");
		btn_Post.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("post되었습니다.");
				/*
				 * [주석] DB boardpost = new DB(BoardContentTxtArea.getText());
				 * boardpost.inputReviewDB();
				 */
				// 팝업
				BoardPostDialog dialog = new BoardPostDialog();
				dialog.setVisible(true);
				// 쓴 리뷰내용 테이블에 추가
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new String[] { BoardContentTxtArea.getText() });
				// textarea 초기화
				BoardContentTxtArea.setText("");

			}
		});
		btn_Post.setForeground(Color.WHITE);
		btn_Post.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btn_Post.setBackground(new Color(102, 205, 170));
		btn_Post.setBounds(964, 229, 74, 34);
		Board_contentPanel.add(btn_Post);

		JButton btn_Back = new JButton("Back");
		btn_Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btn_Back.setForeground(Color.WHITE);
		btn_Back.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btn_Back.setBackground(new Color(102, 205, 170));
		btn_Back.setBounds(964, 153, 74, 34);
		Board_contentPanel.add(btn_Back);

		table.getTableHeader().setReorderingAllowed(false); // 이동 불가
		table.getTableHeader().setResizingAllowed(false); // 크기 조절 불가

		
		return Board_contentPanel;

	}

	boardtestpage() {

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
		BrandColumn.setFont(new Font("Segoe UI", Font.BOLD, 25));
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

//		JPanel Board_contentPanel = new JPanel();
//		Board_contentPanel.setBounds(193, 23, 1073, 660);
//		contentPane.add(Board_contentPanel);
//		Board_contentPanel.setLayout(null);
//
//		JPanel headerPanel = new JPanel();
//		headerPanel.setBackground(new Color(110, 89, 222));
//		headerPanel.setBounds(0, 31, 1073, 79);
//		Board_contentPanel.add(headerPanel);
//		headerPanel.setLayout(null);
//
//		JLabel hederText = new JLabel("BoardList");
//		hederText.setBounds(40, 10, 193, 57);
//		hederText.setFont(new Font("Segoe UI", Font.PLAIN, 30));
//		hederText.setForeground(Color.WHITE);
//		headerPanel.add(hederText);
//
//		FileOutputStream fos;
//
//		JPanel ptablepanel = new JPanel();
//		ptablepanel.setBounds(0, 304, 1049, 333);
//		Board_contentPanel.add(ptablepanel);
//		ptablepanel.setLayout(null);
//		table = new JTable(model); // 테이블에 모델객체 삽입
//		// table.addMouseListener(new JTableMouseListener()); // 테이블에 마우스리스너 연결
//		scrollPane = new JScrollPane(table);
//		scrollPane.setBounds(57, 21, 944, 290);
//		ptablepanel.add(scrollPane);
//
//		JLabel lblNewLabel = new JLabel("Review");
//		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
//		lblNewLabel.setBounds(53, 192, 74, 30);
//		Board_contentPanel.add(lblNewLabel);
//
//		JScrollPane WritescrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
//				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		WritescrollPane.setBounds(143, 151, 800, 112);
//		Board_contentPanel.add(WritescrollPane);
//
//		JTextArea BoardContentTxtArea = new JTextArea();
//		WritescrollPane.setViewportView(BoardContentTxtArea);
//
//		JButton btn_Post = new JButton("Post");
//		btn_Post.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				System.out.println("post되었습니다.");
//				/*
//				 * [주석] DB boardpost = new DB(BoardContentTxtArea.getText());
//				 * boardpost.inputReviewDB();
//				 */
//				// 팝업
//				BoardPostDialog dialog = new BoardPostDialog();
//				dialog.setVisible(true);
//				// 쓴 리뷰내용 테이블에 추가
//				DefaultTableModel model = (DefaultTableModel) table.getModel();
//				model.addRow(new String[] { BoardContentTxtArea.getText() });
//				// textarea 초기화
//				BoardContentTxtArea.setText("");
//
//			}
//		});
//		btn_Post.setForeground(Color.WHITE);
//		btn_Post.setFont(new Font("Segoe UI", Font.BOLD, 15));
//		btn_Post.setBackground(new Color(102, 205, 170));
//		btn_Post.setBounds(964, 229, 74, 34);
//		Board_contentPanel.add(btn_Post);
//
//		JButton btn_Back = new JButton("Back");
//		btn_Back.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//			}
//		});
//		btn_Back.setForeground(Color.WHITE);
//		btn_Back.setFont(new Font("Segoe UI", Font.PLAIN, 15));
//		btn_Back.setBackground(new Color(102, 205, 170));
//		btn_Back.setBounds(964, 153, 74, 34);
//		Board_contentPanel.add(btn_Back);
//
//		table.getTableHeader().setReorderingAllowed(false); // 이동 불가
//		table.getTableHeader().setResizingAllowed(false); // 크기 조절 불가

		BoardTableSelect();
		getContentPane().setLayout(null); // 레이아웃 배치관리자 삭제

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 1395, 800);
		contentPane.add(mainPanel);

	}

	private void BoardTableSelect() {

		String query = "SELECT L_BOARD_CONTENT FROM TB_BOARD";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stat = conn.createStatement();
				ResultSet rs = stat.executeQuery(query);) {

			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] { rs.getString("L_BOARD_CONTENT") });

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// 최근 한줄만 추가
	private void BoardContenAdd() {
		// 최근 입력된 리뷰내용의 boardno검색
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		String CntQuery = "select count(*) from TB_BOARD";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			stat = conn.createStatement();
			rs = stat.executeQuery(CntQuery);

			String boardno = rs.getString(1);
			try {
				String query = "SELECT L_BOARD_CONTENT FROM TB_BOARD WHERE N_BOARD_NO=5";
				Class.forName(driver);
				conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				stat = conn.createStatement();
				rs = stat.executeQuery(query);

				model.addRow(new Object[] { rs.getString("L_BOARD_CONTENT") });

			} catch (Exception e) {
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				conn.close();
				stat.close();
				rs.close();
			} catch (Exception e2) {
				System.out.println("12");
			}
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					boardtestpage frame = new boardtestpage();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null); // 중앙 배치
					frame.setResizable(false); // 사이즈 고정
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("11");
				}
			}
		});
	}
}

class BoardPostDialog extends JDialog {
	JLabel pcl = new JLabel("등록되었습니다.");

	JButton Okbtn = new JButton("OK");

	BoardPostDialog() {

		setLayout(new FlowLayout());
		add(pcl);
		add(Okbtn);
		setSize(200, 100);
		setLocationRelativeTo(null);

		pcl.setFont(new Font("굴림", Font.PLAIN, 18));

		// Ok버튼 리스너
		Okbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				/*
				 * [주석] DB refresh = new DB(); refresh.add();
				 */
//				
			}
		});
	}

}
