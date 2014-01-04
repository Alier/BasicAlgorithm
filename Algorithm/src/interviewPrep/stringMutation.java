package interviewPrep;

import java.util.ArrayList;
import java.util.HashMap;

public class stringMutation {
	char[] charArray;
	char[] charArrayNoTail;
	char charTail;
	int charCount;
	int charCountTail;

	ArrayList<String> mutations = new ArrayList<String>();

	public ArrayList<String> getMutations(){
		return mutations;
	}
	
	public stringMutation(char[] cArray, int len) {
		this.charCount = len;
		this.charCountTail = len - 1;
		this.charArray = new char[charCount];
		this.charArrayNoTail = new char[charCount];
		
		for(int i=0; i< charCount;i++){
			this.charArray[i] = cArray[i];
			this.charArrayNoTail[i] = cArray[i];
		}
		this.charArrayNoTail[charCount - 1] = '\0';
	}

	public void doMutation() {
		if (this.charCount == 1) {
			this.mutations.add(Character.toString(charArray[0]));
		} else {
			this.charTail = this.charArray[this.charCount - 1];
			stringMutation newObj = new stringMutation(this.charArrayNoTail, this.charCountTail);
			newObj.doMutation();
			for (String s : newObj.mutations) {
				// for each mutation from newObj, insert tail in each possible
				// position
				// for newObj with charArray abcd, possible pos from 0 to 4,
				// totally 5;
				for (int pos = 0; pos <= this.charCountTail; pos++) {
					char[] mutationArray = s.toCharArray();
					char[] newStr = insertCharToStr(
							this.charTail, mutationArray,pos);
					this.mutations.add(new String(newStr));
				}
			}
		}
	}

	// insert Char into the n th position of str
	private char[] insertCharToStr(char ch, char[] s, int pos) {
		int newLen = s.length + 1;
		char[] result = new char[newLen];
		// copy anything before position
		for (int i = 0; i < pos; i++) {
			result[i] = s[i];
		}

		for (int j = pos; j < s.length; j++) {
			result[j + 1] = s[j];
		}

		result[pos] = ch;
		return result;

	}

	/* find the starting point of a circle .
	 * for example, for string abcedc, the circle starting point is c.
	 */
	public char findFirstDuplicate(){
		HashMap<Character, Integer> charList = new HashMap<Character, Integer>();
		
		for(int i=0;i<charArray.length; i++){
			if(charList.containsKey(charArray[i])){
				System.out.println("memory used: " + charList.size());
				return charArray[i];
			}else {
				//index in
				charList.put(charArray[i],1);
			}
		}
		return 0;
	}
}

