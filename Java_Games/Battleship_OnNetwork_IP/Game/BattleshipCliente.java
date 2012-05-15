//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************
		//BattleshipCliente.java

		//Compiled in CMD Promt using "javac BattleshipCliente.java" 
		//C:\mywork> set path=%path%;C:\Program Files\Java\jdk1.7.0_04\bin   
		//jdk1.7.0_04 = java version installed on computer
		
		//JDK needed found at:
		//http://www.oracle.com/technetwork/java/javase/downloads/index.html
		
		//Alternative way to compile. Use eclipse found at:
		//http://www.eclipse.org/downloads/
		
		//Run in cmd promt using "java BattleshipCliente XXX.XXX.XXX.XXX"
		//XXX.XXX.XXX.XXX means IP where BattleshipServidor.java is running
		
		//Created by Jorge Saldivar and Oscar Rosales on Nov 21 2011.


//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************

//Libraries used
import java.net.*;
import java.io.*;

	//Everything was programmed in the main
    class BattleshipCliente {
       
	   public static void main(String args[]) throws Exception{
        
		//Read from keyboard
         InputStreamReader istream = new InputStreamReader(System.in);
         BufferedReader br = new BufferedReader(istream);
        
         //Creates array for ship
         int naveA[][] = new int[6][6];
        
		//Array of ship
         for(int i = 0; i < 6;i++){
            for(int j = 0; j < 6;j++){
               naveA[i][j] = 0; 
               System.out.print(naveA[i][j] + " ");
            }
            System.out.println();
         }
        
        
        //Ask player 1 for position of ship, if its going to be vertical or horizontal
         System.out.println("Escribe 0 para nave horizontal o 1 para nave vertical: ");
         String valor_posicion = br.readLine();
         int valor_de_posicion = Integer.parseInt(valor_posicion);
        
         if(valor_de_posicion > 1)
         {
            do{//Make sure player put 0 or 1, options for vertical or horizontal
               System.out.println("Escribe 0 para nave horizontal o 1 para nave vertical: "); 
               valor_posicion = br.readLine();
               valor_de_posicion = Integer.parseInt(valor_posicion);
            }while(valor_de_posicion > 1);
         }
        
		
						//Ask player 1 for row position of ship
						 System.out.println("Cordenadas Renglon del la nave 1 con 2 posiciones: ");
						 String valor_posicionx = br.readLine();
						 int valor_de_posicionx = Integer.parseInt(valor_posicionx);
						
						 if(valor_de_posicionx > 6)//Makes sure its less than 6, the array length
						 {
							do{ 
							   System.out.println("Cordenadas Renglon del la nave 1 con 2 posiciones: "); 	//prints cordinates
							   valor_posicionx = br.readLine();
							   valor_de_posicionx = Integer.parseInt(valor_posicionx);
							}while(valor_de_posicionx > 6);
						 }
					
						//Ask player 1 for column position of ship
						 System.out.println("Cordenadas Columna del la nave 1 con 2 posiciones: ");
						 String valor_posiciony = br.readLine();
						 int valor_de_posiciony = Integer.parseInt(valor_posiciony);
						
						 if(valor_de_posiciony > 6)//Makes sure its less than 6, the array length
						 {
							do{
							   System.out.println("Cordenadas Columna del la nave 1 con 2 posiciones: "); 
							   valor_posiciony = br.readLine();
							   valor_de_posiciony = Integer.parseInt(valor_posiciony);
							}while(valor_de_posiciony > 6);
						 }
        
						//Puts ship on coordinates
						 naveA[valor_de_posicionx-1][valor_de_posiciony-1] = 1;
						
						//Prints ship position
						 for(int i = 0; i < 6;i++){
							for(int j = 0; j < 6;j++){
							   
							   System.out.print(naveA[i][j] + " ");
							}
							System.out.println();
						 }
        
		//Condition to know if player choose vertical or horizontal position
        if(valor_de_posicion == 0){
            
				//Ask player for column position
				String valor_posiciony2 = "";
				int valor_de_posiciony2 = 0;
				do{
				   System.out.println("Cordenadas Columna del la nave 1 con 2 posiciones: "); 
				   valor_posiciony2 = br.readLine();
				   valor_de_posiciony2 = Integer.parseInt(valor_posiciony2);
				   if(((valor_de_posiciony+1 == valor_de_posiciony2) || (valor_de_posiciony-1 == valor_de_posiciony2))){
					 
				   }
				   else{
					  valor_de_posiciony2=10;
				   }
				
				}while(valor_de_posiciony2 > 6);//Makes sure its less than 6, array length
         
				naveA[valor_de_posicionx-1][valor_de_posiciony2-1] = 1;
         
				//Prints results
				for(int i = 0; i < 6;i++){
				   for(int j = 0; j < 6;j++){
				   
					  System.out.print(naveA[i][j] + " ");
				   }
				   System.out.println();
				}
			 
		}
		
        else {
            
				//Ask player for column position
				String valor_posicionx2 = "";
				int valor_de_posicionx2 = 0;
				
				do{ 
				   System.out.println("Cordenadas Renglon de la nave 1 con 2 posiciones: "); 
				   valor_posicionx2 = br.readLine();
				   valor_de_posicionx2 = Integer.parseInt(valor_posicionx2);
				   if(((valor_de_posicionx+1 == valor_de_posicionx2) || (valor_de_posicionx-1 == valor_de_posicionx2))){
					 
				   }
				   else{
					  valor_de_posicionx2=10;
				   }
				
				}while(valor_de_posicionx2 > 6);//Makes sure its less than 6, array length
			 
				naveA[valor_de_posicionx2-1][valor_de_posiciony-1] = 1;
			 
				//Prints results
				for(int i = 0; i < 6;i++){
				   for(int j = 0; j < 6;j++){
				   
					  System.out.print(naveA[i][j] + " ");
				   }
				   System.out.println();
				}
        }
      
          //variables for communication on the network
         DatagramSocket yo = null; //socket
         InetAddress dir = null; //ip destination
         DatagramPacket paquete; //package to send
         byte[] buffer = new byte[80];	//data buffer
         String recibido, tecleado=" "; //saves received data
         BufferedReader delTeclado = new BufferedReader(new InputStreamReader(System.in));
         int item1,item2,itemSumado;
         String aMandar;
         String[] items;
                
			 try{
				dir = InetAddress.getByName(args[0]);//Creates connection
			 } 
				 catch(UnknownHostException e){//in case not working
				   System.out.println(e.getMessage());
				   System.exit(1);
				}
			 try{
				yo = new DatagramSocket();//declares socket
			 }
				 catch(SocketException e){//failure
				   System.out.println(e.getMessage());
				   System.exit(1);
				}
           
		   
						 while(tecleado.length()!=0){
							try{
								
								//Send coordinates to attack
							   System.out.print("Cordenadas a atacar: ");
							   tecleado = delTeclado.readLine(); //data to send
							   buffer = tecleado.getBytes(); //data buffer to send in bytes
							   
							   //Creates package to send with he bytes in the buffer, length, ip destiny an port 5000
							   paquete = new DatagramPacket(buffer, buffer.length, dir, 5000);
							   yo.send(paquete);//sends package
									
							   //Receive attack result
							   buffer = new byte[80];//prepares buffer
							   paquete = new DatagramPacket(buffer, buffer.length);//declares receive package
							   yo.receive(paquete);//receives package
							   recibido = new String(paquete.getData());//Process message content of the package
							   System.out.println(">> "+recibido);//Displays message
										
							   recibido=recibido.replace("\"","");
							   recibido=recibido.trim();
							   if(recibido.equalsIgnoreCase("has salido victorioso")){//If won, terminate game
								  break;//get of of cycle
							   }          
								
							   //Receive coordinates attacked by the enemy
							   buffer = new byte[80];//Prepares buffer
							   paquete = new DatagramPacket(buffer, buffer.length);//Declares receives package
							   yo.receive(paquete);//receives package
							   recibido = new String(paquete.getData());//Process message content of the package
							   System.out.println(">> "+recibido);//Displays message
							   
							   //Manage text receives and separate it by commas
							   items = recibido.split(",");
							   System.out.println(items[0] + items[1]); 
									 
							   items[0] = items[0].replace("\"","");
							   items[0] = items[0].trim();
											
							   items[1] = items[1].replace("\"","");
							   items[1] = items[1].trim();
											
							   item1 = Integer.parseInt(items[0]);
							   item2 = Integer.parseInt(items[1]);
							  
							   //Check if hit or not
							   if(naveA[item1-1][item2-1] == 1)
							   {    naveA[item1-1][item2-1] = 0;
								  System.out.println("Han golpeado a tu nave.");//Advise that the enemy hitted your ship
								  System.out.println("");
											
								  itemSumado = 0;
								  
								  //Sums all matrix
								  for(int i = 0; i < 6;i++){
									 for(int j = 0; j < 6;j++){
										itemSumado = itemSumado + naveA[i][j];  
									 }
								  }
											
								  if(itemSumado == 0)
								  {//If lose, ends game
									 System.out.println("Has sido derrotado.");//Tells you, you lose
									 System.out.println("");
									
									 //Tells enemy he won
									 aMandar = "Has salido victorioso";
									 buffer = new byte[80];
									 buffer = aMandar.getBytes();//converts to bytes
									 paquete = new DatagramPacket(buffer, buffer.length, dir, 5000);//initialize package
									 yo.send(paquete);//sends message
									 break;//get out of cycle
															 
								  }
								  else{
									 
									 //Tells enemy that he hitted a ship
									 aMandar = "Has golpeado a la nave.";
									 buffer = new byte[80];
									 buffer = aMandar.getBytes();//converts to bytes
									 paquete = new DatagramPacket(buffer, buffer.length, dir, 5000);//initialize package
									 yo.send(paquete);//sends message
								  }
											
							   }
							   else
							   {  //tells enemy he missed
								  System.out.println("Han fallado.");//Tells that enemy missed
								  System.out.println("");
								  aMandar = "Has fallado.";//prepares message to send
								  buffer = new byte[80];
								  buffer = aMandar.getBytes();//convert to bytes
								  paquete = new DatagramPacket(buffer, buffer.length, dir, 5000);//initialize package
								  yo.send(paquete);//sends message
												
							   }
							 
							   //print result
							   for(int i = 0; i < 6;i++){
								  for(int j = 0; j < 6;j++){
									 System.out.print(naveA[i][j] + " ");
								  }
								  System.out.println();//matrix printed
							   }
							}
								catch (IOException e){//fails
								  System.out.println(e.getMessage());
								  System.exit(1);
							   }
						 }   	    	
         yo.close();//close conection
        
         }  
   }