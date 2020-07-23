package project;

public class emailRegex {
	public static void main(String[] args) {
		String regExp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

		String emailAddr1 = "testMail1004@james.com";
		String emailAddr2 = "testMail-1004@james.com";
		String emailAddr3 = "testMail-1004@james-test.com";
		String emailAddr4 = "test.Mail-1004@james-test.co.kr";

		System.out.println("email address 1 테스트 결과 ==> " + emailAddr1.matches(regExp));
		System.out.println("email address 2 테스트 결과 ==> " + emailAddr2.matches(regExp));
		System.out.println("email address 3 테스트 결과 ==> " + emailAddr3.matches(regExp));
		System.out.println("email address 4 테스트 결과 ==> " + emailAddr4.matches(regExp));
	}
}
