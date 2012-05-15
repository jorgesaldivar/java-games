//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************
		//BattleshipServidor.java

		//Compiled in CMD Promt using "javac BattleshipServidor.java" 
		//C:\mywork> set path=%path%;C:\Program Files\Java\jdk1.7.0_04\bin   
		//jdk1.7.0_04 = java version installed on computer
		
		//JDK needed found at:
		//http://www.oracle.com/technetwork/java/javase/downloads/index.html
		
		//Alternative way to compile. Use eclipse found at:
		//http://www.eclipse.org/downloads/
		
		//Run in cmd promt using "java BattleshipServidor"
		
		//Created by Jorge Saldivar and Oscar Rosales on Nov 21 2011.


//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************

//Libraries used
import java.net.*;
import java.io.*;
 
 	//Everything was programmed in the main
	class BattleshipServidor {
		public static void main(String args[]) throws Exception{
			
			//Read from keyboard
			InputStreamReader istream = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(istream);
			
			//Creates array for ship
			int naveA[][] = new int[6][6];
				
				//Put 0 on all positions of the array
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
			
			//If player wrote something different from 0 or 1 it ask again
			if(valor_de_posicion > 1)
			{
				do{ 
					System.out.println("Escribe 0 para nave horizontal o 1 para nave vertical: "); 
						valor_posicion = br.readLine();
						valor_de_posicion = Integer.parseInt(valor_posicion);
				}	while(valor_de_posicion > 1);
			}
			
						//Ask player 1 for row position of ship
						System.out.println("Cordenadas de renglon del la nave 1 con 2 posiciones: ");
							String valor_posicionx = br.readLine();
							int valor_de_posicionx = Integer.parseInt(valor_posicionx);
						
						//If player wrote something greater than 6 it ask again
						if(valor_de_posicionx > 6)
						{
							do{ 
								System.out.println("Cordenadas de renglon del la nave 1 con 2 posiciones: "); 
									valor_posicionx = br.readLine();
									valor_de_posicionx = Integer.parseInt(valor_posicionx);
							}	while(valor_de_posicionx > 6);
						}
						
						//Ask player for column position of ship
						System.out.println("Cordenadas de columna del la nave 1 con 2 posiciones: ");
							String valor_posiciony = br.readLine();
							int valor_de_posiciony = Integer.parseInt(valor_posiciony);
						
						//If player wrote something greater than 6 it ask again
						if(valor_de_posiciony > 6)
						{
							do{ 
								System.out.println("Cordenadas Columna del la nave 1 con 2 posiciones: "); 
									valor_posiciony = br.readLine();
									valor_de_posiciony = Integer.parseInt(valor_posiciony);
							}	while(valor_de_posiciony > 6);
						}
						
						//Puts ship on the position the player said
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
					
				String valor_posiciony2 = "";
				int valor_de_posiciony2 = 0;
		
				//Ask player for column position
				do{ 
					System.out.println("Cordenadas de columna del la nave 1 con 2 posiciones: "); 
						valor_posiciony2 = br.readLine();
						valor_de_posiciony2 = Integer.parseInt(valor_posiciony2);
					 
					 //Verifies that the ship position can be putted beside the other position
					 if(((valor_de_posiciony+1 == valor_de_posiciony2) || (valor_de_posiciony-1 == valor_de_posiciony2)))
					 {
					 } 
					 
					 //If it cant be places, the values sums so it can remain inside the while
					 else
					 {
						valor_de_posiciony2=10;
					 }

				}	while(valor_de_posiciony2 > 6);
			
			//Puts the ship in position
			naveA[valor_de_posicionx-1][valor_de_posiciony2-1] = 1;
			
			//Prints ship matrix
			for(int i = 0; i < 6;i++){
				for(int j = 0; j < 6;j++){
				   
					System.out.print(naveA[i][j] + " ");
				}
				System.out.println();
			}
			 
			}
			else
			{        
				
				String valor_posicionx2 = "";
				int valor_de_posicionx2 = 0;
				
				//Ask player for position of row position 
				do
				{ 
					System.out.println("Cordenadas de renglon del la nave 1 con 2 posiciones: "); 
					valor_posicionx2 = br.readLine();
					 valor_de_posicionx2 = Integer.parseInt(valor_posicionx2);
					 
					 //It is verified that the spacecraft will be placed in a proper position so the position adjacent to previous
					 if(((valor_de_posicionx+1 == valor_de_posicionx2) || (valor_de_posicionx-1 == valor_de_posicionx2))){
					 
					 }
					 //If the ship can be placed in that position, the value of position increases so you can stay inside while
					 else
					 {
						 valor_de_posicionx2=10;
					 }

				}	while(valor_de_posicionx2 > 6);
			
			//Puts the ship in position
			naveA[valor_de_posicionx2-1][valor_de_posiciony-1] = 1;
			
			//Prints ship matrix
			for(int i = 0; i < 6;i++){
				for(int j = 0; j < 6;j++){
				   
					System.out.print(naveA[i][j] + " ");
				}
				System.out.println();
			}
			
				
			}

			//variables for communication on the network
			DatagramSocket yo = null; //socket
			DatagramPacket paquete; //package to send
			InetAddress dirCliente = null;  //ip destination
			int puertoCliente,item1,item2,itemSumado;
			byte[] buffer = new byte[80]; //data buffer
			String aMandar, recibido, dato, dato_a_mandar,dato_a_calar; //saves data to send
 			String[] items;
			
			try{
				
				//Initialize socket on port 5000
				yo = new DatagramSocket(5000);
				System.out.println("Socket escuchando en puerto 5000");
				
				while(true){    
					//Receive client data
					buffer = new byte[80];
					paquete = new DatagramPacket(buffer, buffer.length);
					yo.receive(paquete); 
					recibido = new String(paquete.getData());
					dirCliente = paquete.getAddress(); //It captures the enemy address
					System.out.println(dirCliente.toString()+" me mando : "+recibido); //Client message is displayed
					//aMandar = new String(recibido.toUpperCase());
						 
		   
							//It handles the received text and separated by commas
							items = recibido.split(",");
							//System.out.println(items[0] + items[1]); 
						 
							items[0] = items[0].replace("\"","");
							items[0] = items[0].trim();
								
							items[1] = items[1].replace("\"","");
							items[1] = items[1].trim();
								
							item1 = Integer.parseInt(items[0]);
							item2 = Integer.parseInt(items[1]);

							//Check if the opponent hit the ship
							if(naveA[item1-1][item2-1] == 1)
							{    
								naveA[item1-1][item2-1] = 0;
								System.out.println();
								System.out.println("Han golpeado tu nave");
								System.out.println();
								
								//Variable itemSumado serves to verify the sum of the matrix
								itemSumado = 0;
								
								//Add the entire array
								for(int i = 0; i < 6;i++){
									for(int j = 0; j < 6;j++){
												itemSumado = itemSumado + naveA[i][j];  
									}
								}
											//If the itemSumado is zero, it means that there are no ships, so you've been defeated and the game closes
											if(itemSumado == 0)
											{
												System.out.println();
												System.out.println("Has sido derrotado");
												System.out.println();
												 
												//The coordinates are sent to the client
												aMandar = "Has salido victorioso"; 
												buffer = new byte[80];
												buffer = aMandar.getBytes();
												puertoCliente = paquete.getPort();
												paquete = new DatagramPacket(buffer,buffer.length, dirCliente, puertoCliente);
												yo.send(paquete);
												break;
													
											}
											
											else
											{
											   
												System.out.println();
												
												//The coordinates are sent to the client
												aMandar = "Has golpeado la nave";
												buffer = new byte[80];			    
												buffer = aMandar.getBytes();
												puertoCliente = paquete.getPort();
												paquete = new DatagramPacket(buffer,buffer.length, dirCliente, puertoCliente);
												yo.send(paquete);
											}
								
								}
								else
								{
								   System.out.println();
								   System.out.println("Han fallado");
								   System.out.println(); 
									
									//The coordinates are sent to the client
									aMandar = "Has fallado";
									buffer = new byte[80];
									buffer = aMandar.getBytes();
									puertoCliente = paquete.getPort();
									paquete = new DatagramPacket(buffer,buffer.length, dirCliente, puertoCliente);
									yo.send(paquete);
									
								}
								
								
								
								
								
								 //Prints result
								for(int i = 0; i < 6;i++){
									for(int j = 0; j < 6;j++){

										System.out.print(naveA[i][j] + " ");
									}
									System.out.println();
								}
								
								
								//He asks the player for the coordinates to attack
								System.out.println("Cordenadas a atacar?: ");
								dato = br.readLine();
								 
								//The coordinates are sent to the client
								buffer = new byte[80];
								buffer = dato.getBytes();
								puertoCliente = paquete.getPort();
								paquete = new DatagramPacket(buffer,buffer.length, dirCliente, puertoCliente);
								yo.send(paquete);
									
								//Data is received from client
								buffer = new byte[80];
								paquete = new DatagramPacket(buffer, buffer.length);
								yo.receive(paquete);
								recibido = new String(paquete.getData());
								dirCliente = paquete.getAddress(); //It captures the customer's address
								System.out.println(dirCliente.toString()+" me mando : "+recibido); //Client message is displayed
							
							
								recibido = recibido.replace("\"","");
								recibido  = recibido.trim();
						   
								//It closes the game when the juegador has been victorious
								if(recibido.equalsIgnoreCase("Has salido victorioso")){
									break;
								}
							
							
							}
							//Close connection
							yo.close();
							
							} catch (IOException e){
								System.out.println(e.getMessage());
								System.exit(1);
							}
			  
		}

	}