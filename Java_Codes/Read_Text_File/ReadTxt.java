//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************

	//ReadTxt.java

	//Compiled in CMD Promt using "javac ReadTxt.java"
	//C:\mywork> set path=%path%;C:\Program Files\Java\jdk1.7.0_04\bin
	//jdk1.7.0_04 = java version installed on computer

	//JDK needed found at:
	//http://www.oracle.com/technetwork/java/javase/downloads/index.html

	//Alternative way to compile. Use eclipse found at:
	//http://www.eclipse.org/downloads/

	//Run in cmd promt using "java ReadTxt"

	//Created by Jorge Saldivar on May 15 2012.

//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************

//Libraries used
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


	public class ReadTxt {
		
		//Start the class for read from keyboard
		public static BufferedReader keyboardRead = new BufferedReader(new InputStreamReader(System.in));
		
		public static void main(String[] args)throws IOException {
			
			System.out.println("Name of txt file with extension: ");	//Ask for name of file with extension -> .txt
			//Read from keyboard
			String y = keyboardRead.readLine();
			boolean cycle = false; //While false, remain on cycle
			
		//If not read, remain on cycle
		while(!cycle){
			try{
				//Read file
				BufferedReader fileIn = new BufferedReader( new FileReader (y));
				cycle=true;
			}
			
			catch(IOException e){
				System.out.println("File not found");
				System.out.println("\nName of txt file with extension: ");
				//Read from keyboard
				y = keyboardRead.readLine();}
			
			
		}
		
		//Casos(fileIn);
		System.out.println("File read successfully");
		}

	}


