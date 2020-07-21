package project;

import java.util.Random;

public class RandomPassword {
	public StringBuffer RandomPasswordCreate() {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 16; i++) {
		    int rIndex = rnd.nextInt(4);
		    switch (rIndex) {
		    case 0:
		        // a-z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		        temp.append((rnd.nextInt(10)));
		        break;
		    case 3:
		    	// !"#$'()*+`-./
		    	temp.append((char) ((int)(rnd.nextInt(15)) + 33));
		    	break;
		    }
		}
		return temp;
	}
}
