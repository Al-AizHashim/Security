/*
the ones and zeros repersent sample key in binary, you can copy it to try the program
0101001001000011110001100010010001110111011101101111110101011011
 */

package a51;
import java.util.Scanner; // used for obtaining the input data from keyboard

public class A51 {

 private String key = null;  // create string varaible for key
 static final int REG_X_LENGTH = 19;    // create int varaible for the lenght of register X whiche equal 19
 static final int REG_Y_LENGTH = 22;    //create int varaible for the lenght of register Y whiche equal 22
 static final int REG_Z_LENGTH = 23;    //create int varaible for the lenght of register Z whiche equal 23
 int[] regX = new int[REG_X_LENGTH];    //declare array to repersent register X with size 19
 int[] regY = new int[REG_Y_LENGTH];    //declare array to repersent register Y with size 22
 int[] regZ = new int[REG_Z_LENGTH];    //declare array to repersent register Z with size 23
/**
	*  loadRegisters functoin is used to load the value of key to the three registers.
	*  The Integer.parseInt() method is used to parse the String key argument into an Integer object. 
	*  substring method is used to take subset of of the key. Then the Integer.parseInt() convert 
	*  the subset of key into integer and store it in index i in the array  
*/
 void loadRegisters(String key) {
	for (int i = 0; i < REG_X_LENGTH; i++)
	 regX[i] = Integer.parseInt(key.substring(i, i + 1));
	for (int i = 0; i < REG_Y_LENGTH; i++)
	 regY[i] = Integer.parseInt(key.substring(REG_X_LENGTH + i, REG_X_LENGTH + i + 1));
	for (int i = 0; i < REG_Z_LENGTH; i++)
	 regZ[i] = Integer.parseInt(key.substring(REG_X_LENGTH + REG_Y_LENGTH + i, REG_X_LENGTH +
		REG_Y_LENGTH + i + 1));
 }

 
 /**
	* -setKey function used to ensure the validity of the key and assign the value of the key to the key variable 
	* -If valid 64-bit binary key, set it and return true else just return false
	*/
 boolean setKey(String key) {
	if (key.length() == 64 && key.matches("[01]+")) {
	 this.key = key;
	 this.loadRegisters(key);
	 return true;
	}
	return false;
 }
 /**
	* - getKey function  used to get the value of the key from keyboard and store it in the variable "key"
	*/
 
 String getKey() { 
	return this.key;
 }
  /**
	* - encrypt function is used to encrypt the plaintext to produce the ciphertext
	* - 
	*/
 String encrypt(String plaintext) {
	StringBuilder s = new StringBuilder();	// create an object s from StringBuilder class 
	int[] binary = this.toBinary(plaintext);	//convert the plaintext from string type to binary
	int[] keystream = getKeystream(binary.length);	// create keystream equal the lenght of the plaintext after convert it to binary 
	for (int i = 0; i < binary.length; i++)	// for loop 
	 s.append(binary[i] ^ keystream[i]);	// append method used to append the binary values after XOR the keystream with the binary plaintext
	return s.toString();	// encrypt function return the appended value in string type
 }
 
 

 int[] getKeystream(int length) {
	int[] keystream = new int[length];
	
	for (int i = 0; i < length; i++) {	
	 
	 int maj = this.getMajority(regX[8] , regY[10] , regZ[10]);	


	 if (regX[8] == maj) {
		int newStart = regX[13] ^ regX[16] ^ regX[17] ^ regX[18];
		int[] temp = regX.clone(); 		
		for (int j = 1; j < regX.length; j++)
		 regX[j] = temp[j - 1]; 	
		regX[0] = newStart;			
	 }

	 
	 if (regY[10] == maj) {
		int newStart = regY[20] ^ regY[21];		
		int[] temp = regY.clone();		
		for (int j = 1; j < regY.length; j++)
		 regY[j] = temp[j - 1];		
		regY[0] = newStart;			
	 }

	
	 if (regZ[10] == maj) {
		int newStart = regZ[7] ^ regZ[20] ^ regZ[21] ^ regZ[22]; 
		int[] temp = regZ.clone();		
		for (int j = 1; j < regZ.length; j++)
		 regZ[j] = temp[j - 1];		
		regZ[0] = newStart;			
	 }
	 keystream[i] = regX[18] ^ regY[21] ^ regZ[22]; 
	}
	return keystream;
 }		
 private int getMajority(int x, int y, int z) {
	return x + y + z > 1 ? 1 : 0; 	
 }

 
 public int[] toBinary(String text) {		
	StringBuilder s = new StringBuilder();	
	for (int i = 0; i < text.length(); i++) {
	 String temp = Integer.toBinaryString(text.charAt(i));

	 s.append(temp);		
	}
	String binaryStr = s.toString(); 
	int[] binary = new int[binaryStr.length()];
	for (int i = 0; i < binary.length; i++)
		
	 binary[i] = Integer.parseInt(binaryStr.substring(i, i + 1)); 
	return binary;
 }		
 
 public static void main(String[] args) { 	
	A51 a51 = new A51();   						
	Scanner scanner = new Scanner(System.in);	
	System.out.println("A5/1 Stream Cipher");
	System.out.println("Input a 64-bit key: in binary value ");
	while (a51.getKey() == null) {				
		
	 if (!a51.setKey(scanner.nextLine()))		
		System.out.println("Invalid key. Make sure input is a 64-bit binary value");
	}

		String in;	
		System.out.println("Input plain text"); 	
		in = scanner.nextLine(); 				
		System.out.println("encrypt(" + in + ") = " + a51.encrypt(in)); 
	} 											
 }												
