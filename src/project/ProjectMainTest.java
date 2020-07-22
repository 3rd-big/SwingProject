package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.Renderer;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ProjectMainTest extends JFrame {

	private static JPanel contentPane;
	private static JPanel Main_contentPanel;
	private static JPanel currentRightPanel; // [2차 추가]
	private JTable table;
	private JScrollPane scrollPane; // 테이블 스크롤바 자동으로 생성되게 하기

	// [수정] 실행 환경의 OracleDB 접속 정보로 수정
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	static String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	static String DB_USER = "c##swingdb";
	static String DB_PASSWORD = "12345";

	private Object[][] data = null;
	private String colNames[] = { "project image", "project name" }; // 테이블 컬럼 값들
//   private DefaultTableModel model = new DefaultTableModel(colNames, 0) {
//      @Override
//      public boolean isCellEditable(int row, int column) {
//         if (column >= 0) {
//            return false;
//         } else {
//            return true;
//         }
//      }
//	
//	
//	
//      
//      @Override
//      public Class<?> getColumnClass(int column){
//    	  switch(column) {
//    	  case 0: return ImageIcon.class;
//    	  case 1: return String.class;
//    	  default : return Object.class;
//    	  }
//      }
//      
//      
//   }; // 테이블 데이터 모델

	private DefaultTableModel model = new DefaultTableModel(data, colNames) {
		public Class getComlumnClass(int column) {
			return getValueAt(0, column).getClass();
		}
	};

	// [추가]
	public JPanel getContentPane() {
		return contentPane;
	}
	
	// [2차 추가]
	public void RightPanel(JPanel rightPanel) {
		currentRightPanel = rightPanel;
		contentPane.add(currentRightPanel);
	}

	ProjectMainTest() {

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	[삭제]
		//setBounds(100, 100, 1280, 720);					[삭제]
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);						[삭제]
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
		// [2차 추가]
		column1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("프로젝트 등록 버튼");
				Main_contentPanel.setVisible(false);
				registertestpage SeonjeongRegisterPanel = new registertestpage();
				RightPanel(SeonjeongRegisterPanel.viewRegister());
			}
		}); // 여기까지
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

		Main_contentPanel = new JPanel();
		Main_contentPanel.setBounds(193, 23, 1073, 660);
		contentPane.add(Main_contentPanel);
		Main_contentPanel.setLayout(null);

		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(new Color(110, 89, 222));
		headerPanel.setBounds(0, 31, 1073, 79);
		Main_contentPanel.add(headerPanel);
		headerPanel.setLayout(null);

		JLabel hederText = new JLabel("Project");
		hederText.setBounds(40, 10, 193, 57);
		hederText.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		hederText.setForeground(Color.WHITE);
		headerPanel.add(hederText);

		FileOutputStream fos;

//      JPanel ptablepanel = new JPanel();
//      ptablepanel.setBounds(12, 156, 1049, 494);
//      Main_contentPanel.add(ptablepanel);
//      ptablepanel.setLayout(null);

		///

		tableView();

	}

	static void tableView() {
		JPanel tableicon1 = new JPanel();
		tableicon1.setBounds(36, 120, 926, 480);
		Main_contentPanel.add(tableicon1);
		tableicon1.setLayout(null);

		String GetProjectQuery1 = "SELECT S_PROJECT_IMAGE, S_PROJECT_NAME FROM TB_PROJECT_INFO";

		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(GetProjectQuery1);

			Object[][] data = null;
			String[] columnNames = { "pic", "des" };
			DefaultTableModel model = new DefaultTableModel(data, columnNames) {
				public Class getColumnClass(int column) {
					return getValueAt(0, column).getClass();
				}
			};

			JTable table1 = new JTable(model);

			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				String imgpath = rs.getString(1);
				Icon p1 = new ImageIcon(imgpath);

				model.addRow(new Object[] { p1, rs.getString(2) });
				table1.setPreferredScrollableViewportSize(table1.getPreferredSize());

			}
			JScrollPane scrollPane = new JScrollPane(table1);
			scrollPane.setBounds(51, 25, 850, 445);
			tableicon1.add(scrollPane);

		} catch (Exception e) {
		}

	}

//table 내용 가운데 정렬 메소드
	public void tableCellCenter(JTable t) {
		// 테이블 내용 가운데 정렬하기
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
		dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로

		TableColumnModel tcm = t.getColumnModel(); // 정렬할 테이블의 컬럼모델을 가져옴

		// 전체 열에 지정
		// for(int i = 0 ; i < tcm.getColumnCount() ; i++){
		// tcm.getColumn(i).setCellRenderer(dtcr);
		// 컬럼모델에서 컬럼의 갯수만큼 컬럼을 가져와 for문을 이용하여
		// 각각의 셀렌더러를 아까 생성한 dtcr에 set해줌
		// }

		// 특정 열에 지정

		tcm.getColumn(1).setCellRenderer(dtcr);
	}

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProjectMainTest frame = new ProjectMainTest();
//					frame.setVisible(true);
//					frame.setLocationRelativeTo(null); // 중앙 배치
//					frame.setResizable(false); // 사이즈 고정
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}

class JTableMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
