//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************
		//Tic_Tac_Toe.java

		//Compiled in CMD Promt using "javac Tic_Tac_Toe.java" 
		//C:\mywork> set path=%path%;C:\Program Files\Java\jdk1.7.0_04\bin   
		//jdk1.7.0_04 = java version installed on computer
		
		//JDK needed found at:
		//http://www.oracle.com/technetwork/java/javase/downloads/index.html
		
		//Alternative way to compile. Use eclipse found at:
		//http://www.eclipse.org/downloads/
		
		//Run in cmd promt using "java Tic_Tac_Toe"
		
		//Created by Jorge Saldivar on Apr 28 2009.

//***************** vv DOCUMENTING AND HELPFUL STUFF vv ********************
	
//Libraries used
import javax.swing.*;	
import java.awt.*;	
import java.awt.event.*;	
	
    public class Tic_Tac_Toe implements ActionListener {	
   // class constants 	
      //String [] name={"Tic_Tac_ToeConBotas.gif", "porky.gif","perroADieta.gif","Leon.gif","caballo.gif","caballoBlanco.gif"};
      private static final int WINDOW_WIDTH = 700;   // pixels	
      private static final int WINDOW_HEIGHT = 700;  // pixels	
      private static final int TEXT_WIDTH = 100;      // characters	
   	
      private JLabel cuentaTag = new JLabel("");	
      private JTextField cuentaText = new JTextField(TEXT_WIDTH);
        
   
   
      private static final GridLayout LAYOUT_STYLE =	
      new GridLayout(4,3);	
   
      private static final String LEGEND = "You are playing Tic_Tac_Toe!\nprogrammed by \nJorge Andres Saldivar Hernandez"	
      + "\nID: 1033317";	
   
   // instance variables 	
   
   // window for GUI 	
      private JFrame window = new JFrame("Jorge Saldivar");	
   	
      private JTextArea legendArea = new JTextArea(LEGEND, 1, 	
      WINDOW_WIDTH);
   	
    //Variables
      int playerCount=0;
      int computerCount=0;
      String output="0";
      String output2="0"; 
   
			
   // run button 	
      private JButton [][] m= new JButton[3][3];
   
   
	// initialized run buttons
       public void initialize()
      {
         for(int r=0, i=0; r<m.length; r++)
            for (int c=0; c<m[0].length; c++,i++)
            {
               m[r][c] = new JButton ("");	//button
               m[r][c].addActionListener(this);
               window.add(m[r][c]);
               window.setSize(800,600);
               window.setLocation(250,100);
               m[r][c].setIcon(new ImageIcon("Images/yellow.jpg")); //background            	
            }
      }
   	

   
   // Tic_Tac_Toe(): constructor 	
       public Tic_Tac_Toe() { 	
      // configure GUI 	
         window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);	
         window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
      	
         window.setLayout(LAYOUT_STYLE);	
         
         initialize();//indow.add(runButton);
            
      // display GUI 	
         window.setVisible(true);
         window.add(legendArea);
         window.add(cuentaTag);	
         window.add(cuentaText);
         cuentaText.setText("Human "+output+"-"+output2+" Cpu");
      
      }	
   
   
         
   // actionPerformed(): run button action event handler	
       public void actionPerformed(ActionEvent e) {	
      // get user's responses 	
         int r1=0;
         int c1=0;
         boolean find=false;		//space available
         boolean computerThrow=false;	//computer throws move
         boolean won=false;		//winning variable
         boolean draw=false;		//draw variable
      	
      
        
         for(int r=0;r<m.length&&!find;r++)
            for(int c=0;c<m[r].length&&!find;c++)  
               if(e.getSource()==m[r][c]){
                  r1=r;
                  c1=c;
                  find=true;
                   
                  
                  
                  if(m[r1][c1].getText().equals("")){
                     m[r1][c1].setIcon(new ImageIcon("Images/x.jpg"));
                     m[r1][c1].setText("xx");
                     computerThrow=true;
                  }
               }
            
         
         
         
         //Plays and win!
         
         int conta=0;
         if(computerThrow){
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(r==c&&m[r][r].getText().equals("xx")){	
                     conta++;}
            if (conta==3){
               won=true;
               computerThrow=false;}
         }  
         	
         if(computerThrow){
            won=true;
            
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(!m[m.length-1-r][r].getText().equals("xx"))	
                     
                     won=false;  }
         
         
         int a=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(c==0&&m[r][c].getText().equals("xx")){
                     a++;}
         if(a==3){
            won=true;
            computerThrow=false;
         }
         	
         int b=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(c==1&&m[r][c].getText().equals("xx")){
                     b++;}
         if(b==3){
            won=true;
            computerThrow=false;
         }
         	
         int d=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(c==2&&m[r][c].getText().equals("xx")){
                     d++;}
         if(d==3){
            won=true;
            computerThrow=false;
         }
         	
         int g=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(r==0&&m[r][c].getText().equals("xx")){
                     g++;}
         if(g==3){
            won=true;
            computerThrow=false;
         }
         
         int f=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(r==1&&m[r][c].getText().equals("xx")){
                     f++;}
         if(f==3){
            won=true;
            computerThrow=false;
         }
         	
         int h=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(r==2&&m[r][c].getText().equals("xx")){
                     h++;}
         if(h==3){
            won=true;
            computerThrow=false;
         }
         
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(!m[r][c].getText().equals(""))
                     draw=true;
      	
          
         if(won){
            JOptionPane.showMessageDialog(null,"You won!","Tic_Tac_Toe",-1,new ImageIcon("Images/winner.jpg"));
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++){
                  m[r][c].setText("");
                  m[r][c].setIcon(new ImageIcon("Images/yellow.jpg"));}      
            computerThrow=false;
            won=false;
            draw=false;
            playerCount++;  
            output = String.valueOf(playerCount);	
            cuentaText.setText("Human "+output+"-"+output2+" Cpu");	
            	
            
            
            
         }
         
         //Computer move
         if (computerThrow){
            find=false;
            for(int r=(int)(Math.random()*2);r<m.length;r++)
               for(int c=(int)(Math.random()*2);c<m[r].length;c++)
                  if(m[r][c].getText().equals("")&&!find){
                     find=true;
                     draw=false;
                     m[r][c].setText("0.o");
                     m[r][c].setIcon(new ImageIcon("Images/o.jpg"));
                     
                  }
            
         }

         
         //Plays and Lose!
         int pconta=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(r==c&&m[r][r].getText().equals("0.o")){	
                     pconta++;}
         if (pconta==3){
            won=true;  
            computerThrow=false;
         }
         
         
         if(computerThrow){
            won=true;
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(!m[m.length-1-r][r].getText().equals("0.o"))	
                     
                     won=false;  }
         
         
         int pa=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(c==0&&m[r][c].getText().equals("0.o")){
                     pa++;}
         if(pa==3){
            won=true;
            computerThrow=false;
         }
         	
         int pb=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(c==1&&m[r][c].getText().equals("0.o")){
                     pb++;}
         if(pb==3){
            won=true;
            computerThrow=false;
         }
         	
         int pd=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(c==2&&m[r][c].getText().equals("0.o")){
                     pd++;}
         if(pd==3){
            won=true;
            computerThrow=false;
         }
         	
         int pg=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(r==0&&m[r][c].getText().equals("0.o")){
                     pg++;}
         if(pg==3){
            won=true;
            computerThrow=false;
         }
         
         
         int pf=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(r==1&&m[r][c].getText().equals("0.o")){
                     pf++;}
         if(pf==3){
            won=true;
            computerThrow=false;
         }  		
         	
         int ph=0;
         if(computerThrow)
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++)
                  if(r==2&&m[r][c].getText().equals("0.o")){
                     ph++;}
         if(ph==3){
            won=true;
            computerThrow=false;}
         
         
          
         if(won){
            JOptionPane.showMessageDialog(null,"You Lose...","Tic_Tac_Toe",-1,new ImageIcon("Images/loser.jpg"));
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++){
                  m[r][c].setText("");
                  m[r][c].setIcon(new ImageIcon("Images/yellow.jpg"));}
            computerThrow=false;
            won=false;
            ++computerCount;
            output2 = String.valueOf(computerCount);	
            cuentaText.setText("Human "+output+"-"+output2+" Cpu");
         }
          
          
         
         //In case of draw	 
         if(draw){
            JOptionPane.showMessageDialog(null,"Draw!","Tic Tac Toe",-1);
            for(int r=0;r<m.length;r++)
               for(int c=0;c<m[r].length;c++){
                  m[r][c].setText("");
                  m[r][c].setIcon(new ImageIcon("Images/yellow.jpg"));}        
            
			//reset variables
			computerThrow=false;
            won=false;
            draw=false;
         }			
         
         
      }
   	
      
      
   // main(): application entry point	
       public static void main(String[] args) {	
         
            
         Tic_Tac_Toe gui = new Tic_Tac_Toe();
               
      }
   }