package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class App {
	JFrame frame = new JFrame("세바기");

	static private JPanel contentPane;
	static private JPanel currentRightPanel;
	
	static private JTextField inputIdSignIn;
	static private JPasswordField inputPasswordSignIn;
	
	static private JTextField inputNameSignUp;
	static private JTextField inputIdSignUp;
	static private JTextField inputPasswordSignUp;
	static private JTextField inputEmailSignUp;
	static private JTextField inputPhoneSignUp;
	static private JTextField inputAddressSignUp;
	
	static private JTextField inputNameFindId;
	static private JTextField inputEmailFindId;
	
	static private JTextField inputIdFindPassword;
	static private JTextField inputEmailFindPassword;
	
	
	App(){
		DB sessionInitialize = new DB();
		sessionInitialize.sessionEnd();
		Initialize();
	}
	
	public void Initialize() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
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
		BrandColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Initialize();
			}
		});
		navPanel.add(BrandColumn);

		RightPanel(viewSignIn());
		

		frame.setVisible(true);
		frame.setLocationRelativeTo(null); // 중앙 배치
		frame.setResizable(false); // 사이즈 고정

	}
	
	public void RightPanel(JPanel rightPanel) {
		currentRightPanel = rightPanel;
		contentPane.add(currentRightPanel);
	}	

	// 로그인 화면
	public JPanel viewSignIn() {
		
		// 로그인 패널 생성
		JPanel signInPanel = new JPanel(); 
		signInPanel.setBounds(193, 23, 1073, 660);
		signInPanel.setLayout(null);

		// RegisterButton 생성
		JButton RegisterButton = new JButton();
		RegisterButton.setBounds(398, 498, 276, 11);
		RegisterButton.setBackground(SystemColor.control);
		RegisterButton.setBorderPainted(false);
		RegisterButton.setIcon(new ImageIcon("./image/RegisterButtonBefore.png"));
		RegisterButton.setSelectedIcon(new ImageIcon("./image/RegisterButtonBefore.png"));
		RegisterButton.setPressedIcon(new ImageIcon("./image/RegisterButtonAfter.png"));
		RegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signInPanel.setVisible(false);
				RightPanel(viewSignUp());
			}
		});
		signInPanel.add(RegisterButton);

		// signInFormPanel 이미지 생성
		ImagePanel signInFormPanel = new ImagePanel(
				new ImageIcon("./image/SignInForm.png").getImage());
		signInFormPanel.setBounds(400, 89, signInFormPanel.getWidth(), signInFormPanel.getHeight());
		signInPanel.add(signInFormPanel);

		inputPasswordSignIn = new JPasswordField();
		inputPasswordSignIn.setForeground(SystemColor.controlDkShadow);
		inputPasswordSignIn.setBounds(81, 219, 143, 21);
		signInFormPanel.add(inputPasswordSignIn);
		inputPasswordSignIn.setText("password");
		inputPasswordSignIn.setBorder(null);
		inputPasswordSignIn.setBackground(SystemColor.control);
		inputPasswordSignIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// inputPassword 16자 제한
				inputPasswordSignIn.setDocument(new JTextFieldLimit(16));
				inputPasswordSignIn.setText("");
			}
		});

		inputIdSignIn = new JTextField();
		inputIdSignIn.setForeground(SystemColor.controlDkShadow);
		inputIdSignIn.setBounds(81, 150, 143, 25);
		signInFormPanel.add(inputIdSignIn);
		inputIdSignIn.setBorder(null);
		inputIdSignIn.setText("아이디 입력");
		inputIdSignIn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		inputIdSignIn.setBackground(SystemColor.control);
		inputIdSignIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// inputId 10자 제한
				inputIdSignIn.setDocument(new JTextFieldLimit(10));
				inputIdSignIn.setText("");
			}
		});
		
		// LogInButton 생성
		JButton LogInButton = new JButton();
		LogInButton.setBounds(35, 274, 201, 42);
		LogInButton.setBorderPainted(false);
		LogInButton.setIcon(new ImageIcon("./image/LogInButtonBefore.png"));
		LogInButton.setPressedIcon(
				new ImageIcon("./image/LogInButtonAfter.png"));
		LogInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// DB Class 생성자 호출 시 이용자가 입력한 ID, Password를 매개변수로 대입
				// isMatch() 메서드에서 boolean값을 리턴받음
				DB inputData = new DB(inputIdSignIn.getText(), String.valueOf(inputPasswordSignIn.getPassword()));
				if (inputData.isMatch()) {
					JOptionPane.showMessageDialog(null, "You have logged in successfully");
					
					signInPanel.setVisible(false);
					inputData.sessionStart();

					// 메인페이지(선정님) 호출
					ProjectMainTest Seonjeong = new ProjectMainTest();		
					frame.setContentPane(Seonjeong.getContentPane());

				} else {
					JOptionPane.showMessageDialog(null, "You failed to log in");
				}
			}
		});
		signInFormPanel.add(LogInButton);

		// FindAccountButton 생성
		JButton FindAccountButton = new JButton();
		FindAccountButton.setBounds(46, 336, 178, 16);
		FindAccountButton.setBorderPainted(false);
		FindAccountButton.setIcon(new ImageIcon("./image/FindAccountBefore.png"));
		FindAccountButton.setPressedIcon(new ImageIcon("./image/FindAccountAfter.png"));
		FindAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signInPanel.setVisible(false);
				RightPanel(viewFindAccount());
			}
		});
		signInFormPanel.add(FindAccountButton);
		
		return signInPanel;
	}
	
	
	// 계정찾기 화면
	public JPanel viewFindAccount() {
		
		// 계정찾기 패널 생성
		JPanel findAccountPanel = new JPanel(); 
		findAccountPanel.setBounds(193, 23, 1073, 660);
		findAccountPanel.setLayout(null);
		
		// findIdFormPanel 이미지 생성
		ImagePanel findIdFormPanel = new ImagePanel(
				new ImageIcon("./image/FindIdForm.png").getImage());
		findIdFormPanel.setBounds(270, 233, 537, 264);
		findAccountPanel.add(findIdFormPanel);
		findIdFormPanel.setLayout(null);
		
		JButton ConfirmIdButton = new JButton();
		ConfirmIdButton.setIcon(new ImageIcon("./image/FindAccountButtonBefore.png"));
		ConfirmIdButton.setPressedIcon(new ImageIcon("./image/FindAccountButtonAfter.png"));
		ConfirmIdButton.setBounds(205, 171, 128, 45);
		ConfirmIdButton.setBorderPainted(false);
		ConfirmIdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = null;
				DB search = new DB();
				userId = search.findUserID(inputNameFindId.getText(), inputEmailFindId.getText());
				if(userId == null) {
					JOptionPane.showMessageDialog(null, "입력 정보를 다시 확인해 주세요");
				}else {
					JOptionPane.showMessageDialog(null, "아이디 확인: " + userId);
				}			
			}
		});
		findIdFormPanel.add(ConfirmIdButton);
		
		inputNameFindId = new JTextField();
		inputNameFindId.setForeground(Color.DARK_GRAY);
		inputNameFindId.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 16));
		inputNameFindId.setBorder(null);
		inputNameFindId.setText("회원가입 된 이름 ");
		inputNameFindId.setBounds(200, 55, 224, 38);
		inputNameFindId.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputNameFindId.setDocument(new JTextFieldLimit(10));
				inputNameFindId.setText("");
			}
		});
		findIdFormPanel.add(inputNameFindId);
		inputNameFindId.setColumns(10);
		
		inputEmailFindId = new JTextField();
		inputEmailFindId.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 16));
		inputEmailFindId.setBorder(null);
		inputEmailFindId.setText("회원가입 된 이메일주소");
		inputEmailFindId.setForeground(Color.DARK_GRAY);
		inputEmailFindId.setColumns(10);
		inputEmailFindId.setBounds(200, 109, 224, 38);
		inputEmailFindId.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputEmailFindId.setDocument(new JTextFieldLimit(20));
				inputEmailFindId.setText("");
			}
		});
		findIdFormPanel.add(inputEmailFindId);
		
		
		// findPasswordFormPanel 이미지 생성
		ImagePanel findPasswordFormPanel = new ImagePanel(
				new ImageIcon("./image/FindPasswordForm.png").getImage());
		findPasswordFormPanel.setBounds(270, 233, 537, 264);
		findAccountPanel.add(findPasswordFormPanel);
		findPasswordFormPanel.setLayout(null);
		
		JButton ConfirmPasswordButton = new JButton();
		ConfirmPasswordButton.setIcon(new ImageIcon("./image/FindAccountButtonBefore.png"));
		ConfirmPasswordButton.setPressedIcon(new ImageIcon("./image/FindAccountButtonAfter.png"));
		ConfirmPasswordButton.setBounds(205, 171, 128, 45);
		ConfirmPasswordButton.setBorderPainted(false);
		ConfirmPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userPassword = null;
				DB search = new DB();
				userPassword = search.findUserPassword(inputIdFindPassword.getText(), inputEmailFindPassword.getText());
				if(userPassword == null) {
					JOptionPane.showMessageDialog(null, "입력 정보를 다시 확인해 주세요");
				}else {
					JOptionPane.showMessageDialog(null, "비밀번호 확인: " + userPassword);
				}			
			}
		});
		findPasswordFormPanel.add(ConfirmPasswordButton);
		
		inputIdFindPassword = new JTextField();
		inputIdFindPassword.setForeground(Color.DARK_GRAY);
		inputIdFindPassword.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 16));
		inputIdFindPassword.setBorder(null);
		inputIdFindPassword.setText("회원가입 된 아이디");
		inputIdFindPassword.setBounds(200, 55, 224, 38);
		inputIdFindPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputIdFindPassword.setDocument(new JTextFieldLimit(10));
				inputIdFindPassword.setText("");
			}
		});
		findPasswordFormPanel.add(inputIdFindPassword);
		inputIdFindPassword.setColumns(10);
		
		inputEmailFindPassword = new JTextField();
		inputEmailFindPassword.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 16));
		inputEmailFindPassword.setBorder(null);
		inputEmailFindPassword.setText("회원가입 된 이메일주소");
		inputEmailFindPassword.setForeground(Color.DARK_GRAY);
		inputEmailFindPassword.setColumns(10);
		inputEmailFindPassword.setBounds(200, 109, 224, 38);
		inputEmailFindPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputEmailFindPassword.setDocument(new JTextFieldLimit(20));
				inputEmailFindPassword.setText("");
			}
		});
		findPasswordFormPanel.add(inputEmailFindPassword);
		
		findPasswordFormPanel.setVisible(false);
		

		JButton FindIdButton = new JButton();
		JButton FindPasswordButton = new JButton();
		
		FindIdButton.setIcon(new ImageIcon("./image/FindIdButtonAfter.png"));
		FindIdButton.setBounds(272, 180, 266, 52);
		FindIdButton.setBorderPainted(false);
		FindIdButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findPasswordFormPanel.setVisible(false);
				findIdFormPanel.setVisible(true);
				FindIdButton.setIcon(new ImageIcon("./image/FindIdButtonAfter.png"));
				FindPasswordButton.setIcon(new ImageIcon("./image/FindPasswordButtonBefore.png"));
				
				inputIdFindPassword.setText("회원가입 된 아이디");
				inputEmailFindPassword.setText("회원가입 된 이메일주소");
			}
		});
		findAccountPanel.add(FindIdButton);
		
		
		FindPasswordButton.setIcon(new ImageIcon("./image/FindPasswordButtonBefore.png"));
		FindPasswordButton.setBounds(539, 180, 266, 52);
		FindPasswordButton.setBorderPainted(false);
		FindPasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findIdFormPanel.setVisible(false);
				findPasswordFormPanel.setVisible(true);
				FindPasswordButton.setIcon(new ImageIcon("./image/FindPasswordButtonAfter.png"));
				FindIdButton.setIcon(new ImageIcon("./image/FindIdButtonBefore.png"));
				
				inputNameFindId.setText("회원가입 된 이름");
				inputEmailFindId.setText("회원가입 된 이메일주소");
			}
		});
		findAccountPanel.add(FindPasswordButton);

		return findAccountPanel;
	}

	// 회원가입 화면
	public JPanel viewSignUp() {
		
		// 회원가입 패널 생성
		JPanel signUpPanel = new JPanel();
		signUpPanel.setBounds(193, 23, 1073, 660);
		signUpPanel.setLayout(null);

		// signUpFormPanel 이미지 생성
		ImagePanel signUpFormPanel = new ImagePanel(new ImageIcon("./image/SignUpForm.png").getImage());
		signUpFormPanel.setBounds(306, 5, 459, 647);
		signUpPanel.add(signUpFormPanel);
		signUpFormPanel.setLayout(null);

		// SearchAddressButton 생성
		JButton SearchAddressButton = new JButton();
		SearchAddressButton.setBounds(340, 477, 22, 22);
		SearchAddressButton.setBackground(SystemColor.control);
		SearchAddressButton.setBorderPainted(false);
		SearchAddressButton.setIcon(new ImageIcon("./image/SearchButtonBefore.png"));
		SearchAddressButton.setSelectedIcon(new ImageIcon("./image/SearchButtonBefore.png"));
		SearchAddressButton.setPressedIcon(new ImageIcon("./image/SearchButtonAfter.png"));
		SearchAddressButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("SearchAddressButton.. 시간 없으면 없앨 예정");
			}
		});
		signUpFormPanel.add(SearchAddressButton);

		// CreateAccountButton 생성
		JButton CreateAccountButton = new JButton();
		CreateAccountButton.setBounds(138, 553, 200, 41);
		CreateAccountButton.setBorderPainted(false);
		CreateAccountButton.setIcon(new ImageIcon("./image/CreateAccountButtonBefore.png"));
		CreateAccountButton.setSelectedIcon(new ImageIcon("./image/CreateAccountButtonBefore.png"));
		CreateAccountButton.setPressedIcon(new ImageIcon("./image/CreateAccountButtonAfter.png"));
		CreateAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("CreateAccountButton");
				DB data = new DB(inputNameSignUp.getText(), inputIdSignUp.getText(), inputPasswordSignUp.getText(),
						inputEmailSignUp.getText(), inputPhoneSignUp.getText(), inputAddressSignUp.getText());
				EmailRegex regexCheck = new EmailRegex(inputEmailSignUp.getText());
				
				if(regexCheck.isCorrectRegex()) {
					if (data.hasDB() == false) {
						try {
							data.inputUserData();
							signUpPanel.setVisible(false);
							RightPanel(viewEmailAuth());
						} catch (Exception e1) {
							e1.getMessage();
						}
					} else {
						JOptionPane.showMessageDialog(null, "기존 회원정보가 있습니다");
					}
				}else {
					JOptionPane.showMessageDialog(null, "이메일을 올바르게 입력하세요");
				}
				


			}
		});
		signUpFormPanel.add(CreateAccountButton);

		inputNameSignUp = new JTextField();
		inputNameSignUp.setForeground(SystemColor.controlDkShadow);
		inputNameSignUp.setText("이름을 입력하세요");
		inputNameSignUp.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 12));
		inputNameSignUp.setBackground(SystemColor.menu);
		inputNameSignUp.setColumns(10);
		inputNameSignUp.setBorder(null);
		inputNameSignUp.setBackground(SystemColor.menu);
		inputNameSignUp.setBounds(138, 152, 224, 21);
		inputNameSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputNameSignUp.setDocument(new JTextFieldLimit(3));
				inputNameSignUp.setText("");
			}
		});
		signUpFormPanel.add(inputNameSignUp);

		inputIdSignUp = new JTextField();
		inputIdSignUp.setForeground(SystemColor.controlDkShadow);
		inputIdSignUp.setText("아이디를 입력하세요");
		inputIdSignUp.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 12));
		inputIdSignUp.setColumns(10);
		inputIdSignUp.setBorder(null);
		inputIdSignUp.setBackground(SystemColor.menu);
		inputIdSignUp.setBounds(138, 217, 224, 21);
		inputIdSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputIdSignUp.setDocument(new JTextFieldLimit(12));
				inputIdSignUp.setText("");
			}
		});
		signUpFormPanel.add(inputIdSignUp);

		inputPasswordSignUp = new JTextField();
		inputPasswordSignUp.setForeground(SystemColor.controlDkShadow);
		inputPasswordSignUp.setText("비밀번호를 입력하세요");
		inputPasswordSignUp.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 12));
		inputPasswordSignUp.setColumns(10);
		inputPasswordSignUp.setBorder(null);
		inputPasswordSignUp.setBackground(SystemColor.menu);
		inputPasswordSignUp.setBounds(138, 281, 224, 21);
		inputPasswordSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputPasswordSignUp.setDocument(new JTextFieldLimit(16));
				inputPasswordSignUp.setText("");
			}
		});
		signUpFormPanel.add(inputPasswordSignUp);

		inputEmailSignUp = new JTextField();
		inputEmailSignUp.setForeground(SystemColor.controlDkShadow);
		inputEmailSignUp.setText("이메일을 입력하세요");
		inputEmailSignUp.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 12));
		inputEmailSignUp.setColumns(10);
		inputEmailSignUp.setBorder(null);
		inputEmailSignUp.setBackground(SystemColor.menu);
		inputEmailSignUp.setBounds(138, 347, 224, 21);
		inputEmailSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputEmailSignUp.setDocument(new JTextFieldLimit(20));
				inputEmailSignUp.setText("");
			}
		});
		signUpFormPanel.add(inputEmailSignUp);

		inputPhoneSignUp = new JTextField();
		inputPhoneSignUp.setForeground(SystemColor.controlDkShadow);
		inputPhoneSignUp.setText("핸드폰번호를 입력하세요");
		inputPhoneSignUp.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 12));
		inputPhoneSignUp.setColumns(10);
		inputPhoneSignUp.setBorder(null);
		inputPhoneSignUp.setBackground(SystemColor.menu);
		inputPhoneSignUp.setBounds(138, 411, 224, 21);
		inputPhoneSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputPhoneSignUp.setDocument(new JTextFieldLimit(11));
				inputPhoneSignUp.setText("");
			}
		});
		signUpFormPanel.add(inputPhoneSignUp);

		inputAddressSignUp = new JTextField();
		inputAddressSignUp.setForeground(SystemColor.controlDkShadow);
		inputAddressSignUp.setText("주소를 검색하세요");
		inputAddressSignUp.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 12));
		inputAddressSignUp.setColumns(10);
		inputAddressSignUp.setBorder(null);
		inputAddressSignUp.setBackground(SystemColor.menu);
		inputAddressSignUp.setBounds(138, 476, 200, 21);
		inputAddressSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				inputAddressSignUp.setText("");
			}
		});
		signUpFormPanel.add(inputAddressSignUp);
		
		return signUpPanel;
	}

	// 이메일 인증 화면
	public JPanel viewEmailAuth() {
		
		JPanel emailAuthPanel = new JPanel();
		
		// 가입시도한 이용자 정보 얻어오기
		DB emailAuthViewData = new DB();
		String[] signUpUserData = emailAuthViewData.getEmailAuthViewData();

		emailAuthPanel.setBounds(193, 23, 1073, 660);
		//contentPane.add(emailAuthPanel);
		emailAuthPanel.setLayout(null);

		JPanel emailAuthFormPanel = new ImagePanel(
				new ImageIcon("./image/EmailAuth.png").getImage());
		emailAuthFormPanel.setBounds(304, 78, 458, 465);
		emailAuthPanel.add(emailAuthFormPanel);
		emailAuthFormPanel.setLayout(null);

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("a h시 m분");
		String nowTime = now.format(dateTimeFormatter);

		JTextPane signUpCreated = new JTextPane();
		signUpCreated.setBounds(290, 43, 118, 35);
		signUpCreated.setFont(new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 14));
		signUpCreated.setBackground(SystemColor.control);
		signUpCreated.setText(nowTime);
		emailAuthFormPanel.add(signUpCreated);

		JTextPane signUpName = new JTextPane();
		signUpName.setForeground(SystemColor.controlDkShadow);
		signUpName.setBounds(69, 172, 73, 21);
		signUpName.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		signUpName.setBackground(SystemColor.menu);
		signUpName.setText(signUpUserData[0]);
		emailAuthFormPanel.add(signUpName);

		JTextPane signUpName2 = new JTextPane();
		signUpName2.setForeground(SystemColor.controlDkShadow);
		signUpName2.setBounds(182, 299, 180, 21);
		signUpName2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		signUpName2.setBackground(SystemColor.menu);
		signUpName2.setText(signUpUserData[0]);
		emailAuthFormPanel.add(signUpName2);

		JTextPane signUpId = new JTextPane();
		signUpId.setForeground(SystemColor.controlDkShadow);
		signUpId.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		signUpId.setBounds(182, 326, 180, 21);
		signUpId.setBackground(SystemColor.menu);
		signUpId.setText(signUpUserData[1]);
		emailAuthFormPanel.add(signUpId);

		JTextPane signUpEmail = new JTextPane();
		signUpEmail.setForeground(SystemColor.controlDkShadow);
		signUpEmail.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		signUpEmail.setBounds(182, 353, 180, 21);
		signUpEmail.setBackground(SystemColor.menu);
		signUpEmail.setText(signUpUserData[2]);
		emailAuthFormPanel.add(signUpEmail);

		LocalDate currentDate = LocalDate.now();

		JTextPane signUpCreated2 = new JTextPane();
		signUpCreated2.setForeground(SystemColor.controlDkShadow);
		signUpCreated2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		signUpCreated2.setBounds(182, 380, 94, 21);
		signUpCreated2.setBackground(SystemColor.menu);
		signUpCreated2.setText(currentDate.toString());
		emailAuthFormPanel.add(signUpCreated2);
		
		return emailAuthPanel;
	}
}
