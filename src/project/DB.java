package project;

import java.sql.*;

public class DB {
	final static String driver = "oracle.jdbc.driver.OracleDriver";
	final static String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	final static String DB_USER = "c##swingdb";
	final static String DB_PASSWORD = "12345";

	private String inputName;
	private String inputId;
	private String inputPassword;
	private String inputEmail;
	private String inputPhone;
	private String inputAddress;

	private boolean isIdMatch;
	private boolean isPasswordMatch;

	public DB() {
	}

	// SignIn 생성자
	public DB(String inputId, String inputPassword) {
		this.inputId = inputId;
		this.inputPassword = inputPassword;
	}

	// SignUp 생성자
	public DB(String inputName, String inputId, String inputPassword, String inputEmail, String inputPhone,
			String inputAddress) {
		this.inputName = inputName;
		this.inputId = inputId;
		this.inputPassword = inputPassword;
		this.inputEmail = inputEmail;
		this.inputPhone = inputPhone;
		this.inputAddress = inputAddress;
	}

	// 로그인 시 회원의 세션을 확인하기 위해 'C_USER_SESSION' 컬럼의 값을 1로 수정
	public void sessionStart() {
		String query = "update TB_USER_INFO set C_USER_SESSION = '1' where S_USER_ID = '" + inputId + "'";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	// 로그아웃, 프로그램 종료 시(소스 구현은 프로그램이 실행 될 때 해당 메소드 사용) 모든 회원의 'C_USER_SESSION' 컬럼의 값을 0으로 수정
	public void sessionEnd() {
		String query = "update TB_USER_INFO set C_USER_SESSION = '0'";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	// 회원가입 시 중복데이터 검사 [구현 예정]
	public boolean hasDB() {
		Validation check = new Validation();
		
		// db에 있으면 true, 없으면 false
		return false;
	}
	
	public boolean isMatch() {
		String query = "select * from TB_USER_INFO";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);) 
		{
			while (rs.next()) {
				String S_USER_ID = null;
				String S_USER_PASSWORD = null;
				String S_USER_TMP_PASSWORD = rs.getString(10);
				
				/**
			 	회원가입 시 회원 DB의 임시비밀번호가 생성되어 저장 됨
				회원 가입 시 작성한 이메일로 임시비밀번호가 발송되며, 해당 임시비밀번호로 로그인 시도해야 함 
				최초 로그인 직후, 마이페이지 화면에서 비밀번호 수정 시 임시비밀번호는 1값으로 수정 됨
				 */
				
				// 임시 비밀번호가 1 일 경우 ( >> 비밀번호 수정 이후 )
				if(S_USER_TMP_PASSWORD.equals("1")) {
					S_USER_ID = rs.getString(2);
					S_USER_PASSWORD = rs.getString(3);
					
					isIdMatch = hasIdCheck(S_USER_ID);
					isPasswordMatch = hasPasswordCheck(S_USER_PASSWORD);
					if (isIdMatch && isPasswordMatch) {
						return true;
					}		
				}else {
					S_USER_ID = rs.getString(2);
					S_USER_TMP_PASSWORD = rs.getString(10);
					
					isIdMatch = hasIdCheck(S_USER_ID);
					isPasswordMatch = hasPasswordCheck(S_USER_TMP_PASSWORD);
					if (isIdMatch && isPasswordMatch) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			e.getMessage();
		}

		return false;
	}

	// 이메일 인증화면에 표시될 데이터
	public String[] getEmailAuthViewData() {
		
		// 가장 마지막에 삽입 된 데이터 ( >> 회원 가입 직후 보여질 화면 )
		String getDataQuery = "select S_USER_NAME, S_USER_ID, S_USER_EMAIL from tb_user_info "
				+ "where N_USER_NO like (select Max(N_USER_NO) from tb_user_info)";
		
		String[] getData = new String[3];

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(getDataQuery);) {
			while (rs.next()) {
				getData[0] = rs.getString(1);
				getData[1] = rs.getString(2);
				getData[2] = rs.getString(3);

				return getData;
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return getData;
	}

	// 회원가입 정보 DB에 삽입
	public void inputUserData() {
		String date = null;
		String userNo = null;
		StringBuffer tmpPassword = null;
		String getDateQuery = "SELECT TO_CHAR(SYSDATE) FROM DUAL";
		String getUserCountQuery = "select count(*) from TB_USER_INFO";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Oracle 현재시간 얻어오기
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(getDateQuery);) {
			while (rs.next()) {
				date = rs.getString(1);
				System.out.println(date);
			}
		} catch (Exception e) {
			e.getMessage();
		}

		// DB N_USER_NUMBER 얻어와서 +1
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(getUserCountQuery);) {
			while (rs.next()) {
				userNo = rs.getString(1);
				int tmp = Integer.parseInt(userNo) + 1;
				userNo = Integer.toString(tmp);
			}
		} catch (Exception e) {
			e.getMessage();
		}

		// mvc로 바꾸게 되면 RandomPassword 클래스를 상속받고 익명객체로 바꾸자  ( 이틀 뒤 읽으니 뭔말인지 모르겠음.. )
		RandomPassword rpwd = new RandomPassword();
		tmpPassword = rpwd.RandomPasswordCreate();

		String inputDBQuery = "INSERT INTO TB_USER_INFO(" + "N_USER_NO," + "S_USER_ID, " + "S_USER_PASSWORD,"
				+ "C_ADMIN_CHECK," + "S_USER_NAME," + "S_USER_EMAIL," + "S_USER_PHONE," + "S_USER_ADDRESS,"
				+ "D_USER_CREATED," + "S_USER_TMP_PASSWORD," + "C_USER_SESSION)" + "VALUES ( '" + userNo + "', '" + inputId + "', '"
				+ inputPassword + "', '0', '" + inputName + "', '" + inputEmail + "', '" + inputPhone + "', " + "'"
				+ inputAddress + "', '" + date + "', '" + tmpPassword + "', '0')";

		// 회원가입 정보 DB에 입력
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				PreparedStatement stmt = conn.prepareStatement(inputDBQuery);) {
			stmt.executeUpdate(inputDBQuery);

			// 인증메일 보내기
			SendMail sendMail = new SendMail(inputName, tmpPassword, inputEmail);
			sendMail.authMail();

			// 나중에 없애자
			System.out.println("회원가입 성공");
		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	public String findUserID(String inputName, String inputEmail) {

		String userID = null;
		
		String findIdQuery = "select s_user_id from tb_user_info where s_user_name = '" + inputName + "' "
							+ "AND s_user_email = '" + inputEmail + "'";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(findIdQuery);) {
			while (rs.next()) {
				userID = rs.getString(1);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		
		return userID;
	}
	
	public String findUserPassword(String inputId, String inputEmail) {
		String userPassword = null;
		String userTmpPassword = null;
		
		String findPasswordQuery = "select * from tb_user_info where s_user_id = '" + inputId + "' "
							+ "AND s_user_email = '" + inputEmail + "'";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(findPasswordQuery);) {
			while (rs.next()) {
				userPassword = rs.getString(3);
				userTmpPassword = rs.getString(10);
				if(userTmpPassword.equals("1")) {
					return userPassword;
				}else {
					return userTmpPassword;
				}
				
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return null;

	}
	
	public boolean hasIdCheck(String S_USER_ID) {
		if (inputId.equals(S_USER_ID)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasPasswordCheck(String dbPassword) {
		if (inputPassword.equals(dbPassword)) {
			return true;
		} else {
			return false;
		}
	}

}
