/* 
 * Professor/Programmer: Clinton Rogers
 *
 * Any documents, source code, or work you create/modify as a result of this project is the 
 * property of the University of Massachusetts Dartmouth.  This document and any and all source 
 * code cannot be shared with anyone except: University of Massachusetts Dartmouth faculty 
 * (including TA�s), and in a private digital portfolio (public access online is prohibited) 
 * with the intention of applying to jobs and internships. These exceptions are non-transferable. 
 * Failure to comply is, at the very least, an academic infraction that could result in dismissal 
 * from the university. 
 * 
 * Student Name:Aiden Hochwarter
 * Date:02/08/2024
 * q1: All of the files that hold an encrypted text in it are all the same size, they are all 128 bytes.
 * q2: Yes the size of the file we encrypt has to be able to fit into the the size of the file the encrypted message will go in.
 * q3: Yes it seems as if anything under 117 characters will be placed into a 128 byte file, anything over 117 needs a bigger
 * space to fit in.
 * q4: We could divide up some of the message into smaller pieces and encrypt them individually with one key and then combine them 
 * to create the one big encrypted message.
 */

import java.util.*;
import java.io.*;
import java.security.*;

public class SimpleAsymKeyEncryptApp {

	public static void main(String[] args) {

		//Other implementations other than RSA do not work without messing with Eclipse's settings
		PublicKeyEncryption pke1 = new PublicKeyEncryption(PublicKeyEncryption.AlgorithmTypes.RSA);

		//Keyboard input.
		Scanner kbInput = new Scanner(System.in);

		//Used to get the users choice from the 
		int choice;

		String bufferData;
		byte encryptedData[], decryptedData[];
		PublicKey publicK;
		PrivateKey privateK;

		System.out.println("=======================================================");
		System.out.println("Welcome to the Simple Asymmetric Key Encryption Program");
		System.out.println("=======================================================");

		do { 
		System.out.println("Please choose from the following options:");
		System.out.println("1. Save my public key to a binary file");
		System.out.println("2. Save my private key to a binary file");
		System.out.println("3. Generate a new set of my keys.");
		System.out.println("4. Encrypt using my key");
		System.out.println("5. Decrypt using my key");
		System.out.println("6. Encrypt using a key from a file");
		System.out.println("7. Decrypt using a key from a file");
		System.out.println("8. Quit the program");

		//get the users choice
		choice = kbInput.nextInt();
		kbInput.nextLine();//buffer out the end of line character.
		
	
			
		switch(choice) { 
		case 1://Save my public key to a binary file.
		//ask them what they want to name the file(using scanner)
		//save it to a string
		//Name the key file the scanner choice
			   String pubName;
			   System.out.println("What would you like to name the public file you are saving to?");
			   pubName = kbInput.nextLine(); 
			   saveByteArrayToFile(pke1.getPublicKeyAsByteArray(), pubName);
			   System.out.println("Save Complete");
		
		       break;
		case 2://Save my private key to a binary file.
			   String privName;
			   System.out.println("What would you like to name the private file you are saving to?");
			   privName = kbInput.nextLine();
			   saveByteArrayToFile(pke1.getPrivateKeyAsByteArray(), privName);
			   System.out.println("Save Complete");
			   
			   break;

		case 3: //Generate a new set of my keys.
				
				pke1.generateNewPair();
				
				break;
		case 4://Encrypt using my key
				
				String encrypExamp;
				System.out.println("What would you like to encrypt? ");
				encrypExamp = kbInput.nextLine();
				
				bufferData = getTextFileAsString(encrypExamp);
				
				encryptedData = pke1.encrypt(bufferData.getBytes());
				String encrypt;
				System.out.println("What would you like to name the file that has your encrypted text stored in it?");
				encrypt = kbInput.nextLine();
				saveByteArrayToFile(encryptedData, encrypt);

				System.out.println("Text from message file has been encrypted using your public key");
				System.out.println("and saved to the file you created");
				System.out.println("Please press enter to continue.");
				kbInput.nextLine();
					
				break;
		case 5://Decrypt using my key
				String decrypt;
				System.out.println("What file is would you like to decrypt? ");
				decrypt = kbInput.nextLine();
				encryptedData = loadByteArrayFromFile(decrypt);
				decryptedData = pke1.decrypt(encryptedData);

				System.out.println("Decrypted Text:");
				System.out.println(new String(decryptedData));
				System.out.println("Please press enter to continue.");
			
				kbInput.nextLine();
						
				break;
		case 6: //Encrypt using a key from a file
				String origMess;
				System.out.println("What file would you like to encrypt? ");
				origMess = kbInput.nextLine();
				bufferData = getTextFileAsString(origMess);
				String encrKey;
				System.out.println("What file is the key coming from? ");
				encrKey = kbInput.nextLine();
				
				publicK = pke1.convertBytesToPublicKey(loadByteArrayFromFile(encrKey));
				encryptedData = pke1.encrypt(bufferData.getBytes(), publicK);
				String encrypMess;
				System.out.println("What would you like to name the file you are saving too? ");
				encrypMess = kbInput.nextLine();
				saveByteArrayToFile(encryptedData, encrypMess);

				System.out.println("Text from SampleDataToEncrypt.txt has been encrypted using your public key");
				System.out.println("and saved to the file you created");
				System.out.println("Please press enter to continue.");
				kbInput.nextLine();
				
				break;
		case 7://Decrypt using a key from a file
				String encrypMess2;
				System.out.println("What file has the message you want encrypted? ");
				encrypMess2 = kbInput.nextLine();
				encryptedData = loadByteArrayFromFile(encrypMess2);
				String decrypKey;
				System.out.println("What file has your key? ");
				decrypKey = kbInput.nextLine();
				privateK = pke1.convertBytesToPrivateKey(loadByteArrayFromFile(decrypKey));

				decryptedData = pke1.decrypt(encryptedData,privateK);
				String decrypTxt;
				System.out.println("What file would you like your decrypted text to be saved to? ");
				decrypTxt = kbInput.nextLine();
				
				try{
					PrintWriter fileOutput = new PrintWriter("decrypted_"+decrypTxt);
					fileOutput.println(new String(decryptedData));
					
					fileOutput.close();
					
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				System.out.println("Please press enter to continue.");
				kbInput.nextLine();
			
				break;
			
			
		case 8:
			     System.out.println("Thank you for using this program, good bye!");
		       
			     break;
		       
		       
		default:
								//invalid choice
		         System.out.println("Invalid Choice, press enter to continue.");
		         kbInput.nextLine();
		}}
		
	while(choice != 8);
		}
	
		


	private static void saveByteArrayToFile(byte arrayData[], String fileName)
	{
		//Just dump the whole thing to a binary file.
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(arrayData);
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static byte[] loadByteArrayFromFile(String fileName)
	{
		//Just dump the whole thing to a binary file.
		try {
			FileInputStream fis = new FileInputStream(fileName);
			byte buffer[] = fis.readAllBytes();
			fis.close();

			return buffer;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getTextFileAsString(String filename)
	{
		File f = new File(filename);

		String bufferString = "";

		Scanner fileInput;
		try {
			fileInput = new Scanner(f);
			
			int x = 0;
			while(fileInput.hasNext())
			{
				bufferString+=fileInput.nextLine() + "\n";
				x++;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bufferString;
	}

}
