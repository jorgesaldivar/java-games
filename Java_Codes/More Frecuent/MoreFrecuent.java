//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************

	//MoreFrecuent.java

	//Compiled in CMD Promt using "javac MoreFrecuent.java"
	//C:\mywork> set path=%path%;C:\Program Files\Java\jdk1.7.0_04\bin
	//jdk1.7.0_04 = java version installed on computer

	//JDK needed found at:
	//http://www.oracle.com/technetwork/java/javase/downloads/index.html

	//Alternative way to compile. Use eclipse found at:
	//http://www.eclipse.org/downloads/

	//Run in cmd promt using "java MoreFrecuent"

	//Created by Jorge Saldivar on May 15 2012.

//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************

	public class MoreFrecuent {


		public static void main(String[] args) {
		
			String s="";	//Initialize string
			
			int arr[]=new int [50];		//initialize int array of 50 
			
			//Put random numbers to array
			for(int k=0;k<arr.length;k++){
				arr[k]=(int) (Math.random()*31);
				s+=arr[k]+" ";
			}
			
			//print array
			System.out.println(s);
			
			directInsert(arr);
			
			//Count repeated numbers in array
			countRepeated(arr);
			
			
		}
		
		public static void directInsert(int [] a){
			for ( int i = 0; i < a.length-1; i++ ){
				int aux = a[i];
				int j = i - 1;
				while ( j >= 0 && aux < a[j] ) {
					a[j + 1] = a[j];
					j--;
					}
				a[j + 1] = aux;
			} 
		}

		
		public static void countRepeated( int []a ){
			int counter=1;
			int moreTimesRepeated=a[0];
			int moreTimes=counter;
			
			System.out.println("Los números que se repiten son:");

			
			for(int k=0;k<a.length-1;k++){
				if(a[k]!=a[k+1]){
					if(counter>1){
					System.out.println("\tEl numero "+a[k]+" se repite "+counter+" veces");}
					
						if(moreTimes<counter){
							moreTimes=counter;
							moreTimesRepeated=a[k];}
					counter=1;
				}	
				else
					counter++;
				
			}
			if(counter>1)
			System.out.println("\tEl numero "+a[a.length-1]+" se repite "+counter+" veces");
		
			System.out.println("\tEl número que se repite más veces es el "+moreTimesRepeated);
		}


	}
