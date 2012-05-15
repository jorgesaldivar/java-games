/**
 * Clase GameMain
 *
 * JUEGO APOLLO12
 *
 * Permite generar un juego de destruir asteroides con un avion.
 * Utiliza Paneles y States.
 * JFRAME
 *
 * @author Jorge Saldivar
 * @author Carlos Guillermo Elizondo Ancer
 * @version 1.00 03/08/2010
 */
 

import java.awt.Dimension;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Toolkit;
import java.awt.Container;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameMain extends JFrame
{
	private static final long serialVersionUID = 1L;
	// Se declaran las variables.
	
	private static final int WIDTH = 1100;    //Ancho del JFrame
	private static final int HEIGHT = 700;    //Altura del JFrame
	
	
	private long currentTime;  //Tiempo en este instante
	private long elapsedTime;   //Tiempo transcurrido
	
	
	
	private Asteroides[] asteroide;   //Arreglo de asteroides
	private Cometas[] cometa ;	//Arreglo de cometas 
	private Nave jugador;    //Objeto nave
	private Nave jugadorPos2; //Objeto nave
	private Balas[] bala;    //Objeto balas
	private Mapa mapa;
	
	private boolean arriba;  //Represente si existe una colision con el JFrame arriba
	private boolean abajo;  //Represente si existe una colision con el JFrame arriba
	private boolean izquierda;  //Represente si existe una colision con el JFrame arriba
	private boolean derecha;  //Represente si existe una colision con el JFrame arriba
	private boolean giro;  //Representa si esa girada la nave
	private boolean sonidoBoleano; //Representa un boleano para llamar a la musica o pararla
	private boolean golpe; //Representa si existe una colision entre la nave y los objetos
	
	private Animacion animCometa;  //Animacion de la Cesta
	private Animacion animAsteroide;	//Animacion de la Moneda
	 
    private long currTimeParaAnimacion;		//Registra el tiempo

	private String paraTiempo; //Despliega el tiempo 
	
	private Sequencer maintheme; //Sonido de menu
	private Sequencer ending; //Sonido de final
	private SoundClip gameover;  //Sonido si pierde
	private SoundClip explosion; //Sonido si existe colision
	private SoundClip musica; //Sonido de cancion del juego

	private int direccion;			// Direccion de la nave

	private int cuentaClick;  //Cuenta las veces que se da enter para llamar a cierto arreglo
	private int contadorParaQuitarGolpe;   //Contador para que el golpe se pueda ver visible al usuario
	private int tiempo;   //Registra los minutos
	private int seleccionMenu;  //Representa la seleccion del menu
	private int sonidoOn;   //Representa On y Off del sonido
	private int cuentaGiro;  //Representa la posicion de la nave dependiendo de la letra A tecleada

	private int score;   //El score del juego
	private int randomN;   //un numero random para simplificar lineas de codigo
	
	private Image dbImage;	// Imagen a proyectar	
	private Graphics dbg;	// Objeto grafico
	private Image imagenCometa[];  //imagen arreglo de cometas
	private Image imagenAsteroide[]; //imagen arreglo de asteroides
	private Image imagenMenu[]; //imagen arreglo de menu
	private Image instrucciones; //imagen instrucciones
	private Image opciones; //imagen opciones
	private Image salir; //imagen salir
	private Image nave1Golpe; //imagen de la nave golpeada
	private Image nave2Golpe; //imagen de la nave golpeada con otra posicion
	private Image perdiste; //imagen al perder
	private Image ganaste; //imagen al ganar
	private Image imagenMenuPrincipal;  //imagen del menu
	
	//se crean los estados
    static enum State {
        INITIALIZED, MENU, INTRUCTIONS, PLAYING, OPTIONS,  GAMEOVER, WIN, DESTROYED
    }
    
    /* Current state of the Game */
    static State state;

    // Handle for the custom drawing panel
    private GamePanel gamePanel;
    // Handle for the custom drawing panel
	private Container container;
    
	/** 
     * Metodo  constructor<I>GameMain</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializa el juego y manda llamar a los demás metodos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     * 
     */
    public GameMain() {
        // Initialize the game objects
        gameInit();
        
        //se crea un canvas
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // UI components
    
        container = this.getContentPane();
        container.add(gamePanel);//Se agrega el panel incial al container.

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Para que se cierre cuando se cierra la ventana.
        this.pack();
        this.setTitle("Asteroides");//Se incializa el titulo de la ventana.
        this.setVisible(true);
    
       
        // Start the game.
        gameStart();
    }
    /** 
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     * 
     */
    public void gameInit(){
    	score=0;   //el score empieza en 0
    	cuentaClick=0;  //el cuentaClick empieza en 0
    	sonidoBoleano=false; //boleano para sonido
    	sonidoOn=1; //representa el ON del sonido
    	cuentaGiro=1; //representa posicion 1 de la nave
    	tiempo=0; //los minutos empiezan en 0
    	paraTiempo="0:00"; //formato del tiempo
    	elapsedTime=0; //se inicializa el elapsedtime
    	arriba=false;   //no ai colison arriba 
    	abajo=false; //no ai colison abajo
    	izquierda=false; //no ai colison izquierda
    	derecha=false; //no ai colison derecha
    	giro=false; //no ai giro
    	seleccionMenu=1; //primera seleccion del menu
    golpe=false; //no ai golpe
    contadorParaQuitarGolpe=0;   //empieza en cero debido a que no hay golpe
    
    	setBackground(Color.black); //background temporalmente
    	
    	int posX = (int) (WIDTH / 16);    // posicion en x 
		int posY = (int) (HEIGHT / 2);    // posicion en y 
		jugador = new Nave(posX,posY,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/Spaceship1.png")); //se inicializa la imagen de la nave
		jugador.setPosY(HEIGHT-(jugador.getAlto()*2));
		jugadorPos2=new Nave(posX,posY,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/Spaceship2.png")); //se inicializa la imagen de la nave

		
		bala = new Balas[30];
		for(int i=0;i<bala.length;i++){
		bala[i] = new Balas(jugador.getPosX()+(jugador.getAncho()/2),jugador.getPosY()+(jugador.getAlto()/2),Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/balas.png"),false,false);//se inicializa la imagen de la bala
		}
        
		mapa = new Mapa(0,0,Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/mapa.png")); //se inicializa la imagen del mapa
		
        imagenCometa = new Image[12];
        imagenAsteroide = new Image[12];  
          	  for (int i = 1; 13 > i; i++) {
                    imagenAsteroide[i-1] = Toolkit.getDefaultToolkit().getImage("imagenes/Asteroide/asteroide" + i + ".png"); //se inicializa la imagen del asteroide
                    imagenCometa[i-1] = Toolkit.getDefaultToolkit().getImage("imagenes/Cometa/cometa" + i + ".png"); //se inicializa la imagen del cometa
          	  }
          	  
          	 //Se inicializan las animaciones
              animCometa = new Animacion();
              for (int i = 0; imagenCometa.length > i; i++) {
                  animCometa.addFrame(imagenCometa[i], 150);
              }

              animAsteroide = new Animacion();
              for (int i = 0; imagenAsteroide.length > i; i++) {
                  animAsteroide.addFrame(imagenAsteroide[i], 150);
              }

              //se inicializan los objetos de asteroides y cometas
            asteroide = new Asteroides[10];
      		cometa = new Cometas[10];   
		
		for(int i=0;i<cometa.length;i++){   //se crean todos los asteroides y cometas
			cometa[i] = new Cometas(WIDTH,HEIGHT,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/cometa.png")); //imagen del cometa
			asteroide[i] = new Asteroides(WIDTH,HEIGHT,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/asteroide.png")); //imagen del asteroide
			randomN=(int) (Math.random()*35);
			//posiciones
			cometa[i].setPosX((int)(Math.random()*(WIDTH-cometa[i].getAncho())));  
			cometa[i].setPosY(getY()-(cometa[i].getAlto()*randomN)); 
			asteroide[i].setPosX(WIDTH+(randomN*asteroide[i].getAncho()));
			asteroide[i].setPosY((HEIGHT-asteroide[i].getAlto())-(int)(Math.random()*(HEIGHT-asteroide[i].getAlto())));
		}

		 //sonidos en wav	
	     gameover = new SoundClip("sonidos/gameover.wav");  //sonidos cuando se acaba el juego. 
	     explosion = new SoundClip("sonidos/explosion.wav"); //sonido cuando choca la nave.
	     musica = new SoundClip("sonidos/music.wav"); //sonido de fondo
		 musica.setLooping(true);//Para que siempre se escuche el sonido de fondo.
		    
			//imagenes de fondo y de la nave con golpe
			imagenMenu=new Image[4];
			imagenMenu[0]=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/MenuSelect.png");
			imagenMenu[1]=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/MenuJuegoNuevo.png");
			imagenMenu[2]=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/MenuOpciones.png");
			imagenMenu[3]=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/MenuSalir.png");
			imagenMenuPrincipal=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/Menu.png");
			instrucciones=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/Instrucciones.png");
			opciones=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/Opciones.png");
			salir=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/Creditos.png");
			perdiste=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/Perdio.png");
			ganaste=Toolkit.getDefaultToolkit().getImage ("imagenes/Fondos/Gano.png");
			nave1Golpe=Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/Spaceship1Golpe.png");
			nave2Golpe=Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/Spaceship2Golpe.png");
			
		//se inicializa un estado
		state = State.INITIALIZED;
		 
		//sonidos en MIDI
		 try {
		        // From file
		        Sequence sequence = MidiSystem.getSequence(new File("sonidos/maintheme.mid"));
		        Sequence sequenceEnding = MidiSystem.getSequence(new File("sonidos/ending.mid"));

		    
		        // Create a sequencer for the sequence
		        maintheme = MidiSystem.getSequencer();
		        maintheme.open();
		        maintheme.setSequence(sequence);
		    
		        // Create a sequencer for the sequence
		        ending = MidiSystem.getSequencer();
		        ending.open();
		        ending.setSequence(sequenceEnding);
		    
		        
		    } catch (IOException e) {
		    } catch (MidiUnavailableException e) {
		    } catch (InvalidMidiDataException e) {
		    }

    }
     /**
	  * Metodo <I>gameStart</I> 
	  * En este metodo lo que hace es inicializar el hilo del juego
	  */
    public void gameStart() {
        // Create a new thread
        Thread gameThread = new Thread() {
            // Override run() to provide the running behavior of this thread.

            public void run() {
                gameLoop();
            }
        };
        // Start the thread. start() calls run(), which in turn calls gameLoop().
        gameThread.start();
    }
	
	

	/** 
	 * Metodo <I>GameLoop</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
     * la posicion en x o y de manera alazar, finalmente 
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     * 
     */
	public void gameLoop () {
		
		 if (state == State.INITIALIZED) {
	            // Genera el MENU.
	            state = State.MENU;
	        }
		
		 //se quita o se pone el sonido principal y aun no empieza el juego
		 while (state != State.PLAYING) {
			 if(sonidoBoleano){
				 maintheme.stop();}//Para poder parar la musica cuando lo decide el usuario.
			 else {
				 maintheme.start();//Para poder incializar la musica cuando lo decide el usuario.
			 }
		}
		 
		 
		maintheme.stop();
    	currentTime=System.currentTimeMillis()/1000; //se inicializa el tiempo en este momento
    	if(!sonidoBoleano)
		musica.play();//Se comienza la musica.
    	
		while (true) {
	    	
			if(state==State.PLAYING){
			elapsedTime=System.currentTimeMillis()/1000-currentTime;
			paraTiempo=tiempo+":"+elapsedTime;
			
			if(cuentaClick>bala.length-1)   //sirve para seleccion de menu
				cuentaClick=0;//Se incializa en 0 para que primero este en instrucciones. 
			
			gameUpdate();  //actualiza el juego
			checaColision();  //checa si existe colision
			
			//formato para el tiempo
			if(elapsedTime<10) 
				paraTiempo=tiempo+":0"+elapsedTime;
			if(elapsedTime>59){
				tiempo++;
				currentTime=System.currentTimeMillis()/1000;
				paraTiempo=(tiempo)+":"+elapsedTime;
			}
			
			//condicion para el gameover
			if(tiempo>=1&&score<=0){
				state=State.GAMEOVER;
				repaint();
				break;
			}
			
			//condicion para ganar
			if(tiempo>=5){
				state=State.WIN;
				repaint();
				break;
				}
			}
			
			//condicion para salida en caso de que se cambie el estado a menu
			if(state==State.MENU){
				break;
			}
			
			repaint();    // Se actualiza el <code>JFrame</code> repintando el contenido.

			
			try	{
				// El thread se duerme.
				Thread.sleep (20);
			}
			catch (InterruptedException ex)	{
				System.out.println("Error en " + ex.toString());
			}
	}
		
		musica.stop();//Para para la musica incial.
		
		//condicion para que suene el gameover
		if(!sonidoBoleano&&state==State.GAMEOVER)
		gameover.play();
		
		//condicion para que suene el ending en caso de ganar
		if(!sonidoBoleano&&state==State.WIN)
		ending.start();
		
		//condicion para que se permanezca en gameover o en win
		while(state==State.GAMEOVER||state==State.WIN){	
		}
		
		ending.stop();
		gameInit();  //se incializa el juego
		repaint();
		gameLoop();  //se empieza el juego
		
	}
	
	/** 
     * Metodo<I>GameUpdate</I> sobrescrito de la clase <code>Applet</code>
     * que actualiza la posicion de la nave en caso de estar en es
     * estado PLAYING. Sino solamente encarga de actualizar la pantalla en el MENU. 
     */
	public void gameUpdate() {
	    //Dependiendo de la direccion de la nave es hacia donde se mueve.
		
	     long elapsedTimeParaAnimacion =
             System.currentTimeMillis() - currTimeParaAnimacion;
     currTimeParaAnimacion += elapsedTimeParaAnimacion;//Se maneja el tiempo. 
     animCometa.update(elapsedTimeParaAnimacion);
     animAsteroide.update(elapsedTimeParaAnimacion);
		
		switch(direccion) {
			//Dependiendo de la direccion es como se mueve la bala y el jugador.
		case 1: {
				jugador.setPosY(jugador.getPosY()-10); 
				for(int i=0;i<bala.length;i++){
					if( bala[i].getBoolean()==false&&bala[i].getBoolean2()==false){
				bala[i].setPosY(bala[i].getPosY()-10); 
					}
				}
				break;    //se mueve hacia arriba
			}
			
			case 2: {
				jugador.setPosY(jugador.getPosY()+10); 
				for(int i=0;i<bala.length;i++){
					if( bala[i].getBoolean()==false&&bala[i].getBoolean2()==false){
					bala[i].setPosY(bala[i].getPosY()+10); }
				}
				break;    //se mueve hacia abajo
			}
			
			case 3: {
				jugador.setPosX(jugador.getPosX()-10); 
				for(int i=0;i<bala.length;i++){
					if( bala[i].getBoolean()==false&&bala[i].getBoolean2()==false){
					bala[i].setPosX(bala[i].getPosX()-10); 
					}
				}
				break;    //se mueve hacia izquierda
			}
			
			case 4: {
				jugador.setPosX(jugador.getPosX()+10);
				for(int i=0;i<bala.length;i++){
					if( bala[i].getBoolean()==false&&bala[i].getBoolean2()==false){
					bala[i].setPosX(bala[i].getPosX()+10); 
					}

				}
				break;    //se mueve hacia derecha	
			}
			
			case 5:{
				break;  //se detiene
			}
		}
		
		//movimiento de la bala en Y
		for(int i=0;i<bala.length;i++){
		if(bala[i].getBoolean()==true&&bala[i].getBoolean2()==false)
			bala[i].setPosY(bala[i].getPosY()-20);
		}
			
		//movimiento de la bala en X
		for(int i=0;i<bala.length;i++){
		if(bala[i].getBoolean()==false&&bala[i].getBoolean2()==true)
			bala[i].setPosX(bala[i].getPosX()+20);
		}
		
		
		//Acutalizo la posicion del asteroide y de los cometas
		for(int i=0;i<cometa.length;i++)	{
				cometa[i].setPosY(cometa[i].getPosY()+(3));
				asteroide[i].setPosX(asteroide[i].getPosX()-(3));
		}
		
		//condicion para mantener de otro color la nave
		if(golpe)
			contadorParaQuitarGolpe++;
		
		//condicion para quitar el color del golpe de la nave con un planetoide.
		if(contadorParaQuitarGolpe>5){
			golpe=false;
		contadorParaQuitarGolpe=0;}
		
		//movimiento del fondo
		mapa.setPosX(mapa.getPosX()-1);
		
		
		 checaColision();//Revisa la colision sin importar en que estado se encuentre.
	}
	

	
	/**
	 * Metodo <code>ChecaColision</code>.  que checa la colision del objeto 
	 * cesta al colisionar con 
	 * las orillas del <code>Applet</code>.
	 */
	public void checaColision() {
		
		 //Revisa colision cuando sube
				if (jugador.getPosY() < 0) {
					direccion = 5;
					arriba=true;
				
				}
				 //Revisa colision cuando baja
				if (jugador.getPosY() + jugador.getAlto() > getHeight()) {
					direccion = 5;
					abajo=true;
					

				}
				 //Revisa colision cuando va izquierda.
				if (jugador.getPosX() < 0) {
					direccion = 5;
					izquierda=true;

				}
				 //Revisa colision cuando va derecha.
				if (jugador.getPosX() + jugador.getAncho() > getWidth()) {
					direccion = 5;
					derecha=true;
				
				}
				
				//revisa colision del mapa a la izquierda
				if(mapa.getPosX()<=getX()-mapa.getAncho()/2)
					mapa.setPosX(0);
				
				//se repocisionan las cometas y asteroides cuando tocan el fondo
				for(int i=0;i<cometa.length;i++){
				if(cometa[i].getPosY() + cometa[i].getAlto() > getHeight()){
					
					int posrX = WIDTH;    //posision en x 
					int posrY = HEIGHT ;    //posision en y
					cometa[i] = new Cometas(posrX,posrY,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/cometa.png"));
					randomN=(int) (Math.random()*35);
					
					cometa[i].setPosX((int)(Math.random()*(WIDTH-cometa[i].getAncho())));
					cometa[i].setPosY(getY()-(cometa[i].getAlto()*randomN)); //posicion en y arriba del applet de manera random 
		
				}
				
				//cuando toca la izquierda
				if (asteroide[i].getPosX() < 0) {
					int posrX = WIDTH;    //posision en x 
					int posrY = HEIGHT;    //posision en y
					asteroide[i] = new Asteroides(posrX,posrY,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/asteroide.png"));
					asteroide[i].setPosX(WIDTH+(randomN*asteroide[i].getAncho()));
					asteroide[i].setPosY((HEIGHT-asteroide[i].getAlto())-(int)(Math.random()*(HEIGHT-asteroide[i].getAlto())));
				}
				
				//colison entre jugador y cometa
				if(jugador.intersecta(cometa[i])){
					golpe=true;
					if(!sonidoBoleano)
						explosion.play();
					
					int posrX = WIDTH;    //posision en x 
					int posrY = HEIGHT;    //posision en y
					cometa[i] = new Cometas(posrX,posrY,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/cometa.png"));
					randomN=(int) (Math.random()*35);
					
					cometa[i].setPosX((int)(Math.random()*(WIDTH-cometa[i].getAncho())));
					cometa[i].setPosY(getY()-(cometa[i].getAlto()*randomN)); //posicion en y arriba del applet de manera random 
				score-=10;
				}
		
				//colison entre jugador y asteroide
				if(jugador.intersecta(asteroide[i])){
					golpe=true;
					if(!sonidoBoleano)
						explosion.play();
					
					int posrX = WIDTH;    //posision en x 
					int posrY = HEIGHT;    //posision en y
					asteroide[i] = new Asteroides(posrX,posrY,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/asteroide.png"));
					asteroide[i].setPosX(WIDTH+(randomN*asteroide[i].getAncho()));
					asteroide[i].setPosY((HEIGHT-asteroide[i].getAlto())-(int)(Math.random()*(HEIGHT-asteroide[i].getAlto())));
					score-=40;
				}
				
				//para colisones entre la bala y los objetos
				for(int c=0;c<bala.length;c++){
					//colison entre bala y cometa
					if(bala[c].intersecta(cometa[i])){
				
						if(!sonidoBoleano)
							explosion.play();
					
					int posrX = WIDTH;    //posision en x 
					int posrY = HEIGHT;    //posision en y
					cometa[i] = new Cometas(posrX,posrY,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/cometa.png"));
					randomN=(int) (Math.random()*35);	
					cometa[i].setPosX((int)(Math.random()*(WIDTH-cometa[i].getAncho())));
					cometa[i].setPosY(getY()-(cometa[i].getAlto()*randomN)); //posicion en y arriba del applet de manera random 
					bala[c] = new Balas(jugador.getPosX()+(jugador.getAncho()/2),jugador.getPosY()+(jugador.getAlto()/2),Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/balas.png"),false,false);//se inicializa la imagen de la cesta
				score+=5;
				}
					//colison entre bala y asteroide
					if(bala[c].intersecta(asteroide[i])){
					
						if(!sonidoBoleano)
							explosion.play();
						
						int posrX = WIDTH;    //posision en x 
						int posrY = HEIGHT;    //posision en y
						asteroide[i] = new Asteroides(posrX,posrY,Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/asteroide.png"));
						asteroide[i].setPosX(WIDTH+(randomN*asteroide[i].getAncho()));
						asteroide[i].setPosY((HEIGHT-asteroide[i].getAlto())-(int)(Math.random()*(HEIGHT-asteroide[i].getAlto())));
						
						bala[c] = new Balas(jugador.getPosX()+(jugador.getAncho()/2),jugador.getPosY()+(jugador.getAlto()/2),Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/balas.png"),false,false);//se inicializa la imagen de la cesta
					score+=20;
					}
				
					//colison entre bala y fin del Jframe por arriba
				if (bala[c].getPosY() < 0){
					bala[c] = new Balas(jugador.getPosX()+(jugador.getAncho()/2),jugador.getPosY()+(jugador.getAlto()/2),Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/balas.png"),false,false);//se inicializa la imagen de la cesta
				}
				
				//colison entre bala fin del Jframe por derecha
				if (bala[c].getPosX() + bala[c].getAncho() > getWidth()) {
					bala[c] = new Balas(jugador.getPosX()+(jugador.getAncho()/2),jugador.getPosY()+(jugador.getAlto()/2),Toolkit.getDefaultToolkit().getImage ("imagenes/Juego/balas.png"),false,false);//se inicializa la imagen de la cesta
				}
				
				}
				}
				
			
	}
		
	
		
	
	/**
	 * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
	 * heredado de la clase Container.<P>
	 * En este metodo lo que hace es actualizar el contenedor
	 * @param g es el <code>objeto grafico</code> usado para dibujar.
	 */
	public void update(Graphics g) {
		// Inicializan el DoubleBuffer
		if (dbImage == null) {
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
		}

		// Actualiza la imagen de fondo.
		dbg.setColor(getBackground ());
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

		// Actualiza el Foreground.
		dbg.setColor(getForeground());
		gameDraw(dbg);

		// Dibuja la imagen actualizada
		g.drawImage(dbImage, 0, 0, this);
	}
    
 	 /**
	  * Metodo <I>paint</I> 
	  * Metodo que permite actualizar el gameDraw.
	  */
	public void paint(Graphics g) {
		update(g);
	}
	
	 /**
	  * Metodo <I>gameDraw</I> 
	  * Este metodo contiene las instrucciones para dibujar
	  * cualquiera de los paneles del juego. Todo dependiendo del estado.
	  */
	public void gameDraw(Graphics g){
	switch(state){
	case MENU:
		g.drawImage(imagenMenuPrincipal,0,0,this);
		switch(seleccionMenu){//Se pinta la imagen según la selección del menu que haga el usuario.
		case 1:
		g.drawImage(imagenMenu[0],0,0,this);//Se pinta con intrucciones resaltado.
		break;
		case 2:
			g.drawImage(imagenMenu[1],0,0,this);//Se pinta con Juego Nuevo resaltado.
			break;
		case 3:
			g.drawImage(imagenMenu[2],0,0,this);//Se pinta con Opciones resaltado.
		break;
		case 4:
			g.drawImage(imagenMenu[3],0,0,this);//Se pinta con Salir resaltado.
		break;
		}
		break;
		
	case INTRUCTIONS:
		g.drawImage(instrucciones,0,0,this);//Dibuja la imagen que contiene las instrucciones.
		break;
		
	case PLAYING:
		if (jugador != null&&bala!=null) {
			
			//Dibuja la imagen en la posicion actualizada
			g.drawImage(mapa.getImagenI(),mapa.getPosX(),mapa.getPosY(),this);
			
			for(int i=0;i<bala.length;i++)
			g.drawImage(bala[i].getImagenI(), bala[i].getPosX(), bala[i].getPosY(), this);

			if(!giro){
				if(!golpe){
			g.drawImage(jugador.getImagenI(), jugador.getPosX(), jugador.getPosY(), this);
				}	else{
			g.drawImage(nave1Golpe, jugador.getPosX(), jugador.getPosY(), this);
				}
			}else if(!golpe){
			g.drawImage(jugadorPos2.getImagenI(), jugador.getPosX(), jugador.getPosY(), this);
			
			}
			else{
				g.drawImage(nave2Golpe, jugador.getPosX(), jugador.getPosY(), this);

			}
			

			
			//dibuja el cuadro junto con el texto de las vidas
			g.setFont(new Font("Verdana", Font.BOLD, 30));
	        g.setColor(Color.white);
	        g.drawString("Score = " + score, WIDTH/16, HEIGHT / 8);
			
			//dibuja el cuadro junto con el texto del score
			g.setFont(new Font("Verdana", Font.BOLD, 30));
	        g.setColor(Color.white);
	        g.drawString("Tiempo = " + paraTiempo, WIDTH/2+WIDTH/5, HEIGHT / 8);
			

			
			for (int i=0;i<cometa.length;i++) {
				
				//Dibuja la imagen en la posicion actualizada
				g.drawImage(animAsteroide.getImage(), asteroide[i].getPosX(), asteroide[i].getPosY(), this);
    			g.drawImage(animCometa.getImage(), cometa[i].getPosX(), cometa[i].getPosY(), this);
				
			}
			}
				else {
			//Da un mensaje mientras se carga el dibujo	
			g.drawString("Estoy cargando..", 10, 10);
		}
		break;
		
		//Se dibuja el texto para dar a conocer las opciones.
	case OPTIONS:
		g.drawImage(opciones,0,0,this);
		if(!sonidoBoleano){
		g.setFont(new Font("Verdana", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString("ON", WIDTH/4, HEIGHT / 2);
		}
		else{
			g.setFont(new Font("Verdana", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString("OFF", WIDTH/4, HEIGHT / 2);
		}
		break;
		
		//Se dibuja la imagen de que se perdio.
	case GAMEOVER:
		g.drawImage(perdiste,0,0,this);
		break;
		
		//Dibuja la imagen de ganar.
	case WIN:
		g.drawImage(ganaste,0,0,this);	
	break;
	
		//Cuando se decide salir del juego se despliega una imagen con los creditos.
	case DESTROYED:
		g.drawImage(salir,0,0,this);
		break;
		}
	}	
	

	/**
	 * Metodo <I>gamekeyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
	 * @param e es el <code>evento</code> generado al presionar las teclas.
	 */
	public void gameKeyPressed(int keyCode) {
		switch(state){//Se revisa la tecla utilizada según el estado.
		case MENU:
			if (keyCode == KeyEvent.VK_DOWN){//Se mueve LA SELECCION en el menu hacia abajo.
				seleccionMenu++;
				if(seleccionMenu>=5)
					seleccionMenu=1;
				repaint();
			}
			
		else if (keyCode == KeyEvent.VK_UP){//Se mueve LA SELECCION en el menu hacia arriba.
				seleccionMenu--;
				if(seleccionMenu<=0)
					seleccionMenu=4;
				repaint();
			
			}
		else	if (keyCode == KeyEvent.VK_ENTER){//Cuando se decide que hacer en el MENU.
				if (seleccionMenu==1){
					state=State.INTRUCTIONS;//Se mueve al estado requerido.
				repaint();
				}
				else if (seleccionMenu==2){
					state=State.PLAYING;//Se mueve al estado requerido.
				repaint();
				}
				else if (seleccionMenu==3){
					state=State.OPTIONS;//Se mueve al estado requerido.
				repaint();
				}
				else if (seleccionMenu==4){
					state=State.DESTROYED;//Se mueve al estado requerido.
				repaint();
				}
				}
			break;
			
		case INTRUCTIONS:
			if (keyCode == KeyEvent.VK_ENTER){//Hace que se regrese al MENU
				state=State.MENU;
				repaint();
			}
		break;
		
		case PLAYING:
			if (keyCode == KeyEvent.VK_UP&&(!arriba)) {    //Presiono flecha arriba
				direccion = 1;
				abajo=false;
			} else if (keyCode == KeyEvent.VK_DOWN&&(!abajo)) {    //Presiono flecha abajo
				direccion = 2;
				arriba=false;
			} else if (keyCode == KeyEvent.VK_LEFT&&(!izquierda)) {    //Presiono flecha izquierda
				direccion = 3;
				derecha=false;
			} else if (keyCode == KeyEvent.VK_RIGHT&&(!derecha)) {    //Presiono flecha derecha
				direccion = 4;
				izquierda=false;
			}
			else if (keyCode == KeyEvent.VK_ESCAPE){//Hace que se regrese al MENU con ESC.
				state=State.MENU;
			}
			
			else if (keyCode == KeyEvent.VK_ENTER){//Hace que se disparen las balas con ENTER.
				
				if(cuentaGiro==1)//La direccion de la bala depende del giro de la nave.
				bala[cuentaClick].setBoolean(true);
				
				else if(cuentaGiro==2)
					bala[cuentaClick].setBoolean2(true);
				
				cuentaClick++;	
			}
			
			else if (keyCode == KeyEvent.VK_A&&(cuentaGiro==1)){//Hace que la nave se gire con la tecla A.
				giro=true;
				cuentaGiro=2;
			}
		
			else if (keyCode == KeyEvent.VK_A&&(cuentaGiro==2)){//Hace que la nave se gire con la tecla A.
				giro=false;
				cuentaGiro=1;
			}
			break;
			
		case OPTIONS:
			if (keyCode == KeyEvent.VK_O&&(sonidoOn==1)){//Para dar la opción de quitar el sonido con la tecla O.
				sonidoBoleano=true;
				sonidoOn=2;
				repaint();
			}
		
			else if (keyCode == KeyEvent.VK_O&&(sonidoOn==2)){//Para regresar el sonido con la tecla O.
				sonidoBoleano=false;
				sonidoOn=1;
			repaint();
			}	
			else if (keyCode == KeyEvent.VK_ENTER){//Para regresar al MENU con ENTER.
					state=State.MENU;//Se mueve al estado requerido.
					repaint();
				}
			break;
			
		case GAMEOVER:
			if (keyCode == KeyEvent.VK_ENTER){//Para regresar al MENU con Enter.
				state=State.MENU;//Se mueve al estado requerido.
				repaint();
		}
			break;
			
		case WIN:
			if (keyCode == KeyEvent.VK_ENTER){//Para regresar al MENU con enter.
				state=State.MENU;//Se mueve al estado requerido.
				repaint();
		}
			break;
			
		case DESTROYED:
			break;
			}
		

			}
			


    
    /**
	 * Metodo <I>gamekeyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
	 * @param e es el <code>evento</code> que se genera en al presionar las teclas.
	 */
    public void gameKeyTyped(int keyCode) {
   
	
    	
    	
    }
    
    /**
	 * Metodo <I>gamekeyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al soltar la tecla presionada.
	 * @param e es el <code>evento</code> que se genera en al soltar las teclas.
	 */
    public void gameKeyReleased(int keyCode) {
    	direccion=5;
    }
    
    /**
     * Metodo principal
     *
     * @param args es un arreglo de tipo <code>String</code> de linea de comandos
     */
    public static void main(String[] args) {

    	GameMain juego = new GameMain();
    	juego.setSize(WIDTH, HEIGHT);
    	juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	juego.setVisible(true);
    }
    
    /**
     *  Clase GamePanel
     *
     * Esta clase contiene las instrucciones para manajar el panel del juego
     * y redireccionar las instrucciones al JFrame
     */
    public class GamePanel extends JPanel implements KeyListener {
        // Se declaran las variables.

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image dbImage;	// Imagen a proyectar
        private Graphics dbg;	// Objeto grafico

        /**
         * Constructor <I>GamePanel</I>
         */
        public GamePanel() {
        	setFocusable(true);
            setBackground(Color.black);
            //Se habilita el teclado
            addKeyListener(this);
    
        }

        /**
         * Metodo <I>update</I>
         * En este metodo lo que hace es actualizar el contenedor
         * @param g es el <code>objeto grafico</code> usado para dibujar.
         */
        public void update(Graphics g) {
            // Inicializan el DoubleBuffer
            if (dbImage == null) {
                dbImage = createImage(this.getSize().width, this.getSize().height);
                dbg = dbImage.getGraphics();
            }

            // Actualiza la imagen de fondo.
            dbg.setColor(getBackground());
            dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

            // Actualiza el Foreground.
            dbg.setColor(getForeground());
            paint(dbg);

            // Dibuja la imagen actualizada
            g.drawImage(dbImage, 0, 0, this);
        }

        /**
         * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
         * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
         * @param e es el <code>evento</code> generado al presionar las teclas.
         */
        public void keyPressed(KeyEvent e) {
            gameKeyPressed(e.getKeyCode());
        }

        /**
         * Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
         * En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
         * @param e es el <code>evento</code> que se genera en al presionar las teclas.
         */
        public void keyTyped(KeyEvent e) {
    		gameKeyTyped(e.getKeyCode());

        }

        /**
         * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
         * En este metodo maneja el evento que se genera al soltar la tecla presionada.
         * @param e es el <code>evento</code> que se genera en al soltar las teclas.
         */
        public void keyReleased(KeyEvent e) {
            gameKeyReleased(e.getKeyCode());

        }
        /**
         *  metodo paintComponent
         *
         *  redirecciona al metodo gameDraw para que ahi se redibuje
         */
        public void paint(Graphics g) {
            super.paint(g);   // paint background
            //setBackground(COLOR_MAIN);  // may use an image for background

            // Draw the game objects
            gameDraw(g);
            requestFocus();
        }
    }


	}
    


     
    

	

