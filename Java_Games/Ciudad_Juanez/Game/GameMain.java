/**
 * Clase GameMain
 *
 * JUEGO La Batalla de Ciudad Juanes
 *
 * Utiliza Game Programming
 * Es un shooter simple. 
 * @author Samuel Heaney
 * @author Carlos Guillermo Elizondo Ancer
 * @author Jorge Saldivar
 * @author Hector de la Garza
 * @version 1.00 14/04/2010
 */
 

import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Vector;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Point;
import java.awt.SplashScreen;

public class GameMain extends JFrame {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Estados del juego.
   	static enum State {
   		M_JUEGO, M_INS, M_OP, M_PUN, M_SALIR, INSTRUCCIONES, OPCIONES, PUNTAJE, JUEGO, PAUSA, GAMEOVER, WIN, LOSE
   	}
   	
    // Constantes de juego
   	static State state; 
   

   	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final int WIDTH= 800;
	private static final int HEIGHT= 600;
	
    public static final float GRAVITY = 0.002f;	
   	public static final float VELOCIDADBALA = .7f;
   	public static final float VELOCIDADCAIDA = 7;
    private Point pointCache = new Point();
	private Image dbImage;   					
	private Graphics dbg;						
	
	//Se inicializan las imagenes del menu
	private ImageIcon M_INSz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/menu0002.png"));
	private ImageIcon M_JUEGOz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/menu0001.png"));
	private ImageIcon M_OPz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/menu0003.png"));
	private ImageIcon M_SALIRz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/menu0004.png"));
	private ImageIcon M_PUNz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/menu0007.png"));
	private ImageIcon instruccionesz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/menu0005.png"));
	private ImageIcon opcionesz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/menu0006.png"));
	private ImageIcon puntajez = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/menu0008.png"));
	private ImageIcon pausaz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/pausa.png"));
	private ImageIcon winz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/ganaste.png"));
	private ImageIcon perderz = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/perdiste.png"));

	//Se llaman las imagenes del menu que se inicializaron
	private Image M_INS = M_INSz.getImage();	
	private Image M_JUEGO = M_JUEGOz.getImage();
	private Image M_OP = M_OPz.getImage();	
	private Image M_SALIR = M_SALIRz.getImage();
	private Image instrucciones = instruccionesz.getImage();
	private Image opciones = opcionesz.getImage();
	private Image puntaje = puntajez.getImage();
	private Image M_PUN = M_PUNz.getImage();
	private Image pausa = pausaz.getImage();
	private Image win = winz.getImage();
	private Image perder = perderz.getImage();
	
	//elapsedTime sirve para mantener el tiempo del juego
 	private long elapsedTime; 	
  
	
	private TileMap mapa;
	private int vidas;
	private int puntos;

	
	@SuppressWarnings("unused")
	private boolean izq,der,arriba,abajo,space;
	private TileMapRenderer render;
	private boolean attack;
	private Vector<Sprite> otrasImagenes; 
	private Player jugador;		
	private Vector <Bala> balas;	
	@SuppressWarnings("unused")
	private Vector <Bala> balasEnemigos;
	private boolean sueltaBotonBala;
	
	//Se llaman las clases
    private Menu menu;
    private Juego juego;
	
	//variables para mantener un orden en los sonidos
	private int prendeSonidoTemporal;
	private int intParaPausa;
	private int seleccionOpciones;
	private int seleccionMusicaOn=0;
	private int seleccionMusica=0;
	private int nivelMapa=0;
	//Variables utilizadas para el Puntaje
	private Vector<Puntaje> vec;  		// Objeto vector para agregar el puntaje
	private String nombreArchivo;  //Nombre del archivo	
	private String[] arr;    	//Arreglo del archivo divido

	//Variables para el sonido en general
	private SoundClip []musica;
	private SoundClip sonidoDisparo;
	private SoundClip sonidoDisparoMalo;
	private SoundClip sonidoPausa;
	


	/** 
     * Constructor de la clase.
     */
	public GameMain() throws IOException {
		
		 sonidoDisparo = new SoundClip("sonidos/laser.wav");
		    sonidoDisparoMalo = new SoundClip("sonidos/lasermalo.wav");
		    sonidoPausa = new SoundClip("sonidos/Pause.wav");
		    
		    //se inicalizan las canciones en un arreglo de SoundClip
		    musica = new SoundClip[3];
		    
		    musica[0] = new SoundClip("sonidos/rock.wav"); //sonido de fondo
			musica[0].setLooping(true);//Para que siempre se escuche el sonido de fondo.
			
			musica[1] = new SoundClip("sonidos/hiphop.wav"); //sonido de fondo
			musica[1].setLooping(true);//Para que siempre se escuche el sonido de fondo.
				
		    musica[2] = new SoundClip("sonidos/electronica.wav"); //sonido de fondo
			musica[2].setLooping(true);//Para que siempre se escuche el sonido de fondo.
		
		//Se inicalizan las variables
		gameInit();	
      
		
		
     	juego = new Juego();
     	menu = new Menu();
	 	//options = new Options();
    	menu.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		juego.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		//options.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    	this.setContentPane(menu);
    	validate();
    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	this.pack();
    	this.setTitle("Batalla de Cd. Juanez");
    	this.setVisible(true);
      

		gameStart();							
   }


	/** 
    * Metodo <I>gameInit</I> <P>
    * En este metodo se inizializan las variables o se crean los objetos
    * a usarse en el juego.
    */
   public void gameInit() throws IOException {
	    vidas = 1; 												// Numero de vidas iniciales
	    state = State.M_JUEGO;									// Empieza en el canvas de Menu
		balas = new Vector <Bala>();							// Vector de balas
		
		nombreArchivo = "Puntaje.txt"; 	 //nombre del archivo	
    	vec = new Vector<Puntaje>();	//vector para puntaje
		prendeSonidoTemporal=1;  //sonidoTemporal prende el sonido
		seleccionOpciones=0;   //primera seleccion en opciones
		intParaPausa=1;   //con intParaPausa es 1, no hay pausa
		
		balasEnemigos = new Vector  <Bala>(); 					// Vector de balas de enemigos
		sueltaBotonBala = true;									// El boton de disparo esta suelto
								
		puntos = 0;
		der=false;
		arriba=false;
		abajo=false;
		izq=false;
		space=false;
	    mapa = new TileMap(10,10);						
	    mapa = mapa.loadMap(nivelMapa);					
	    render = new TileMapRenderer();								
	    ImageIcon back = new ImageIcon(Toolkit.getDefaultToolkit().getImage("images/backgroundMejorado.png"));
	    Image backback = back.getImage();
	    render.setBackground(backback); 
	

	
	}
   
	/** 
    * Metodo <I>gameShutdown</I> <P>
    * Metodo utilizado para cerrar la pantalla y terminar el juego. 
    */
   public void gameShutdown() {
      System.exit(0);	//Cierra el juego, termina la aplicacion
   }

	/** 
    * Metodo <I>gameStart</I> <P>
    * En este metodo se crea e inicializa el hilo para la animacion.
    */
   public void gameStart() { 
     
      Thread t = new Thread() {
         // Override run() to provide the running behavior of this thread.
         public void run() {
            gameLoop();
         }
      };
      // Se inicia el hilo que a su vez llama al gameLoop
      t.start();
   }
   
	/** 
    * Metodo <I>gameLoop</I> <P>
    * En este metodo se maneja el tiempo en el que corre y se actualiza el juego
    * hasta el momento en que termina bajo determinada condicion.
    */
	private void gameLoop() {
	   	
		long startTime = System.currentTimeMillis();		
		long currTime = startTime;		
		
		//mientras no exista un GameOver sigue corriendo el juego
		while (true) {
        	elapsedTime = System.currentTimeMillis() - currTime;	
            currTime += elapsedTime;
            
            //llama el puntaje para poder desplegarlo cuando el estado es igual a puntaje
            if(state == State.PUNTAJE){
            	try{
        			leeArchivo();    						//lee el contenido del archivo
        		
        		}catch(IOException e){
        			System.out.println("Error en " + e.toString());
        		}
            }
            
            //se sale del juego cuando existe esta condicion
            if (state == State.GAMEOVER) {				
                break;
            }
            
           //para prender la musica y se pueda escuchar en el fondo 
           if(prendeSonidoTemporal==1&&seleccionMusicaOn==0){
           musica[seleccionMusica].play();
           prendeSonidoTemporal=2;
            }
           
           //apaga la musica
           if(seleccionMusicaOn!=0){
        	   musica[seleccionMusica].stop();
        	   prendeSonidoTemporal=1;
           }
            
           //se mantiene en el juego mientras el estado esta en juego
	        if(state == State.JUEGO) {					
				gameUpdate();
				
				if(mapa.getPuntos()>=500&&nivelMapa==0){
					nivelMapa=1;
					puntos=mapa.getPuntos();
					try {
						mapa = mapa.loadMap(nivelMapa);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mapa.setPuntos(puntos);
					}
				
				if(mapa.getPuntos()>=1000&&nivelMapa==1){
					nivelMapa=2;
					puntos=mapa.getPuntos();
					try {
						mapa = mapa.loadMap(nivelMapa);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mapa.setPuntos(puntos);
					}
			
				if(mapa.getPuntos()>=1550&&nivelMapa==2)
					vidas=0;
				
				
			    if (vidas <= 0) {						// Si las vidas son menos o igual a 0, se llega a GameOver
			    	
			    	
			    	musica[seleccionMusica].stop();
			    	if(mapa.getPuntos()>=1550&&nivelMapa==2){
				    state = State.WIN;
			    	}
				    else{
				    state = State.LOSE;}
				   	this.setContentPane(menu);			// Se cambia de canvas
				   	
				 	menu.requestFocus();
				 	validate();
				 	vidas=1;
				 	
				 
			    	
				 	
					String nombre = JOptionPane.showInputDialog("Nombre del Jugador");
					if(nombre.length()==0){
						nombre="Jugador";
					}
					JOptionPane.showMessageDialog(null,"El puntaje de " + nombre + " es: " + mapa.getPuntos(),"PUNTAJE", JOptionPane.PLAIN_MESSAGE);
					
					//maneja el puntaje
					try{
						vec.add(new Puntaje(nombre,mapa.getPuntos()));     //Agrega el contenido del nuevo puntaje al vector.
						grabaArchivo();   						//Graba el vector en el archivo.
			
					}catch(IOException e){
						System.out.println("Error en " + e.toString());
					}
					
					switch(nivelMapa){
					case 0:
						nivelMapa=0;
					break;
					case 1:
						nivelMapa=0;
					break;
					
					case 2:
						nivelMapa=0;
					break;
					
					}
			
				 	try{
				 		gameInit();	
				 	}
				 	catch(Exception e){ 		
					}
					
					}
					
			}	
			repaint();	// Se repinta el contenido
			try	{
				// El thread se duerme.
				Thread.sleep (20);
			}
			catch (InterruptedException ex)	{
				// no hace nada.
			}
		}
		
	}		

	/** 
    * Metodo <I>gameUpdate</I> <P>
    * En este metodo se actualizan las posiciones de los personajes y objetos en el juego
    * en caso de que se presente algun tipo de accion.
    */
   @SuppressWarnings("unchecked")
public void gameUpdate() {
   		otrasImagenes = mapa.getSprites();			// Consigue el mapa
		Player jugador = mapa.getPlayer(); 			// Consigue al jugador
		
        float velocityX = 0;		
        	
		if (der) {
		    velocityX += jugador.getMaxSpeed();			// Si esta presionada la derecha, la velocidad aumenta
		}
		if (izq) {
		    velocityX -= jugador.getMaxSpeed(); 		// Si esta presionada la izquierda, la velocidad disminuye					
		}
		if (space) {
			jugador.jump(false);  						// Obliga un salto.  Envia parametro false para indicar que es salto normal (no esta rebotando)
		}	
		if (attack) {									// Si se presiono la tecla de ataque, se llama al metodo disparo()
			jugador.disparo();
			attack = false;	
			if(seleccionMusicaOn==0)
			sonidoDisparo.play();
		}
			
		//Actualiza a los enemigos. Los obliga a atacar si el jugador esta cerca
		for (int x = 0; x < otrasImagenes.size(); x++){
			Sprite sprite = (Sprite)otrasImagenes.get(x);
			sprite.actualiza(elapsedTime);			
			sprite.checaProximidad(jugador);		
			sprite.updateAnim(elapsedTime);			
			if(sprite.atacar()){
				if(seleccionMusicaOn==0)
				sonidoDisparoMalo.play();
				mapa.addBalaEnemigos(sprite.dibujaBala(sprite.volteaADerecha(), mapa));
			}
			if(sprite.puedeMorir()){
				mapa.removeSprite(sprite);
				mapa.setPuntos(mapa.getPuntos()+50);
			}
		}
		
		
        jugador.setVelocidadX(velocityX);			// Cambia al jugador la velocidad horizontal dependiendo de que tecla se presiono
		checaColision(); 							// Checa la colision del jugador con el mapa y actualiza posicion
		jugador.updateAnim(elapsedTime);			// Actualiza la animacion dle jugador
		
		//Para que el jugador reaparezca despues de caerse
		if ((jugador.getPosY() > TileMapRenderer.tilesToPixels(mapa.getHeight())) || (jugador.getVida() == 0)) {
			jugador.setPosY(80);								// Se coloca al jugador en una posicion determinada en Y
			jugador.setPosX(TileMapRenderer.tilesToPixels(3));	// Se coloca al jugador en una posicion determinada en X
			jugador.cambiaVida(15);								// Se llena la vida del jugador
			vidas --; 
		}

		Vector<Explosiones> explosiones = mapa.getExplosiones();					
	
		Enumeration e = explosiones.elements(); 
		Explosiones exp;	 
		while (e.hasMoreElements()) {							
			exp = (Explosiones) e.nextElement();
			exp.updateAnim(elapsedTime);
			if(exp.termina()){
				mapa.removeExplosion(exp);
			}
		}
		
		
		//Seccion de BALAS	
		//Actualiza balas del jugador
		balas = mapa.getBalas();								// Se consigue un vector de balas del jugador
		e = balas.elements();						
		Bala b;
		while (e.hasMoreElements()) {							// Se recorre el vector
			b = (Bala) e.nextElement();
			b.actualiza(elapsedTime);							// Se actualiza la posicion de la bala
			b.updateAnim(elapsedTime);
			b.cambiaInicial(b.getInicial() + 0.8f);				// Se incrementa un contador de distancia recorrida por la bala
		}
					
		//Se encarga de borrar balas que ya hayan durado cierto tiempo en pantalla				
		e = balas.elements();
		while (e.hasMoreElements()) {							
			b = (Bala) e.nextElement();							
			b.actualizaPosicionI();
			float oldX = b.getPosX();
			float newX = oldX ;
			if (b.getInicial() > 50f || getTileCollision(b,newX,b.getPosY())!=null) {							// Si la bala ya recorrio mas de la cantidad especificada, se quita del vector
				mapa.removeBala(b); 	
				if (getTileCollision(b,newX,b.getPosY())!=null)
					mapa.addExplosion(new Explosiones((int)b.getPosX(),(int)b.getPosY(),Toolkit.getDefaultToolkit().getImage("images/explosion1.png")));// El objeto bala es removido del vector
			}
			for (int x = 0; x < otrasImagenes.size(); x++) { 	// Si una bala toca al enemigo, ambos desaparecen.
				Sprite sprite = (Sprite)otrasImagenes.get(x);	
				if (b.intersecta(sprite)) {						
					sprite.cambiaVida(-b.getDamage());										
					mapa.removeBala(b);							
					mapa.addExplosion(new Explosiones((int)b.getPosX(),(int)b.getPosY(),Toolkit.getDefaultToolkit().getImage("images/explosion1.png")));
				}
				
			}
						
		}
		//BALAS ENEMIGOS/
		balas = mapa.getBalasEnemigos();						// Se consigue el vector de balas de Enemigos			
		e = balas.elements();	
		while (e.hasMoreElements()) {						
			b = (Bala) e.nextElement();
			b.actualiza(elapsedTime);							
			b.cambiaInicial(b.getInicial() + 0.8f);				
			b.updateAnim(elapsedTime);
			float dx = b.getVelocidadX();
			float oldX = b.getPosX();
			float newX = oldX + dx*elapsedTime;
			if(b.intersecta(jugador)){
				jugador.cambiaVida(b.getDamage()*-1);
				mapa.removeBalaEnemigos(b);
				mapa.addExplosion(new Explosiones((int)b.getPosX(),(int)b.getPosY(),Toolkit.getDefaultToolkit().getImage("images/explosion1.png")));
			}
			//Despues de cierta distancia se remueve la bala de la pantalla
			if(Math.abs(b.getInicial()) >  20f || getTileCollision(b, newX, b.getPosY()) != null){
				mapa.removeBalaEnemigos(b);
			}
		}


	}
	
	   /** 
	    * Metodo <I>checaColision</I> <P>
	    * En este metodo se manejan todo tipo de colisiones involucrando personajes.
	    */
	public void checaColision() {
		Player jugador = mapa.getPlayer(); 						// Consigue al jugador
		Vector<Sprite> otrasImagenes = mapa.getSprites();		// Consigue a los enemigos		
	 
		
		
		// Seccion Enemigos
		for(int x = 0; x < otrasImagenes.size(); x++) {			// Se recorre el vector de enemigos
			Sprite sprite = (Sprite)otrasImagenes.get(x);
			float dx = sprite.getVelocidadX();					// Se cosnigue la velcoidad actual
			float oldX = sprite.getPosX();						// Se consigue la posicion actual
			float newX = oldX + dx*elapsedTime;					// Se consigue la posicion a la que se va a mover
			
			sprite.revisarCambioDeVuelta(mapa, oldX, sprite.getPosY(), newX);	// Se revisa si esta en esquina, para obligar al alien a girar
			
			Point punto = getTileCollision(sprite, newX, sprite.getPosY());		// Se revisa si hay colision en donde se va a mover
			if(punto != null){			
				sprite.revisarCambioDeEstado(Sprite.CHOQUE_CON_PARED, newX);	// Si no hay colision, se revisa si el alien necesita cambiar de estado
				sprite.setPosX(TileMapRenderer.tilesToPixels(TileMapRenderer.pixelsToTiles(oldX))); //Se actualiza la posicion del alien
			}

			if (sprite.intersecta(jugador)){									// Se revisa si el enemigo toca al jugador
				boolean izquierda = jugador.getLado();						
				if(izquierda){													// Dependiendo del lado del jugador, este es empujado
					jugador.setPosX(jugador.getPosX() + sprite.getAncho());
				} else {
					jugador.setPosX(jugador.getPosX() - sprite.getAncho());					
				}
				jugador.cambiaVida(-1);											// Se reduce la vida del jugador
			}
				if(sprite.puedeMorir())
					mapa.removeSprite(sprite);					
		
	
	
		}
		
	 //////////////FIN DE SECCION ENEMIGOS///////////////////

	 
	 	
		float dx = jugador.getVelocidadX(); 	//Velocidad del jugador
        float oldX = jugador.getPosX(); 		//Vieja posicion 
        float newX = oldX + dx * elapsedTime;   //Nueva posicion = Vieja posicion + Velocidad*tiempo desde ultimo checaColision

        Point punto = getTileCollision(jugador, newX, jugador.getPosY()); //Checa si hubo colision o no, ver metodo getTileCollision
		
		if(punto==null){
			jugador.setPosX(newX); //Si no hubo colision, ahora si, actualiza la posicion Horizontal del jugador
		}


		jugador.setVelocidadY(jugador.getVelocidadY() + jugador.getGravity() * elapsedTime); //Velocidad vertical = VelocidadY + Gravedad por el tiempo transcurrido
	   	float dy = jugador.getVelocidadY();             			//Velocidad en Y
	   	float oldY = jugador.getPosY();								//Vieja posicion en Y
	   	float newY = oldY + dy * elapsedTime;						//Posicion a la que se quiere mover= vieja posicion + velocidad*tiempo
		punto = getTileCollision(jugador, jugador.getPosX(), newY); //Checa si hay o no colision con algun Tile de piso
        if (punto == null) {
            jugador.setPosY(newY); //Si no hay colision (actualiza la posicion en Y)
        }
        else{
			if (dy > 0) {
				jugador.setPosY(TileMapRenderer.tilesToPixels(punto.y) - jugador.getAlto());		  		
			} else if (dy < 0) {
		        jugador.setPosY(TileMapRenderer.tilesToPixels(punto.y+1));		  	
		   }        	
        	jugador.collideVertical(); //Si hay colision, cambia la velocidad del personaje a 0 y avisa que esta en el piso (ver Clase Player)
        }
	}

		
	public Point getTileCollision(Sprite sprite,float newX, float newY) //Recibe como parametros el sprite y la poscion a la que se quiere mover
    {
    	//Checa de donde a donde se quiere mover (en pixeles)
        float fromX = Math.min(sprite.getPosX(), newX);
        float fromY = Math.min(sprite.getPosY(), newY);
        float toX = Math.max(sprite.getPosX(), newX); 
        float toY = Math.max(sprite.getPosY(), newY);

        // Checa en que valor Entero estan ubicados los Tiles (que pueden o no existir)
        // Esto es, asigna a los siguientes integers la posicion que deberian tener los Tiles de dicha area
        int fromTileX = TileMapRenderer.pixelsToTiles(fromX);
        int fromTileY = TileMapRenderer.pixelsToTiles(fromY);
        int toTileX = TileMapRenderer.pixelsToTiles(toX + sprite.getAncho() - 1);
        int toTileY = TileMapRenderer.pixelsToTiles(toY + sprite.getAlto() - 1);

        // Recorre el arreglo de Tiles. Ahora si checa si hay un Tile o no en la posicion especificada
        for (int x = fromTileX; x <= toTileX; x++) {
            for (int y = fromTileY; y <= toTileY; y++) {
                if (x < 0 || x >= mapa.getWidth() || mapa.getTile(x, y) != null)
                {
                    //Si se encuentra un tile en la posicion, hay colision
                    pointCache.setLocation(x, y);
                    return pointCache;
                }
            }
        }
        //No hubo colision
        return null;
    }
	
   /**
    * Metodo <I>gameDraw</I> <P>
	* Metodo usado para dibujar en el frame de acuerdo 
	* al estado en el que se encuentra el juego.
	* @param g2d, es el objeto Graphics2D en el cual se dibuja
	*/
	private void gameDraw(Graphics2D g2d) {
		switch (state) {
			case M_JUEGO:
        		g2d.drawImage(M_JUEGO, 0,0, menu); //se dibuja la imagen de seleccion juego
            break;
            
         	case M_INS:
        	 	g2d.drawImage(M_INS, 0,0, menu); //se dibuja la imagen de seleccion instrucciones
            break;
            
			case M_OP:
        		g2d.drawImage(M_OP, 0,0, menu); //se dibuja la imagen de seleccion opciones
           	break;

         	case M_SALIR:
        		g2d.drawImage(M_SALIR, 0,0, menu);  //se dibuja la imagen de seleccion salir
            break;
            
         	case M_PUN:
         		g2d.drawImage(M_PUN, 0,0, menu); //se dibuja la imagen de seleccion puntaje
         		break;
         		
         	case INSTRUCCIONES:
        		g2d.drawImage(instrucciones, 0,0, menu);  //se dibuja la imagen de instrucciones
            break;
            
         	case OPCIONES:
        		g2d.drawImage(opciones, 0,0, menu);  //se dibuja la imagen de opciones
        		
        		switch(seleccionOpciones){  //se dibujan las seleccion de sonidos y musica
        		case 0:
        			g2d.setColor(Color.WHITE);
        			g2d.drawRect(100, 90, 100, 10);
        			break;
        		case 1:
        			
        			g2d.setColor(Color.WHITE);
        			g2d.drawRect(100, 200, 100, 10);
        			break;
        		}
        		
        		switch(seleccionMusica){  //se dibuja la seleccion de musica mediante un rectangulo
        		case 0:
        			g2d.drawRect(WIDTH/9, HEIGHT/4+HEIGHT/6, 160, 70);
        			break;
        		case 1:
        			g2d.drawRect(WIDTH/9+WIDTH/4+WIDTH/8-50, HEIGHT/4+HEIGHT/6, 160, 70);
        			break;
        		case 2:
        			g2d.drawRect(WIDTH/9+WIDTH/2+WIDTH/8-20, HEIGHT/4+HEIGHT/6, 160, 70);
        			break;
        		}
        		
        		switch(seleccionMusicaOn){  //se dibuja la seleccion de musica prendida y apagada
        		case 0:
        			g2d.drawRect(WIDTH/2, HEIGHT/16, 90, 70);
        			break;
        		case 1:
        			g2d.drawRect(WIDTH/2+WIDTH/4, HEIGHT/16, 90, 70);
        			break;
        		}
            break;
            
        	case PUNTAJE:
        		g2d.drawImage(puntaje,0,0,this);  //se dibuja la imajen de puntaje
        		int incremento=10;
        	   	for (int i=0; i<vec.size(); i++) {  //despiega el puntaje de los jugadores con los nombres de los jugadores
        	   		Puntaje x;
        	   		x = (Puntaje) vec.get(i);
        			g2d.setColor(Color.BLACK);
        			g2d.setFont(new Font("Serif", Font.BOLD, 38));  				
        			g2d.drawString(x.getNombre(),200,250+incremento);
        			g2d.drawString(""+x.getPuntaje(),450,250+incremento);
        	   		incremento+=50;
        	   	}
        		break;
            
        	case PAUSA:
        		g2d.drawImage(pausa, 0,0, menu);  	//se despiega una imagen de pausa
        		break;
            
         	case WIN:
         		g2d.drawImage(win,0,0,this); 		//se despliega la pantalla de ganar
         		break;
         		
         	case LOSE:								//se despliega la pantalla de perder
         		g2d.drawImage(perder,0,0,this);
         		break;
		
		}
	}
    
	 /**
     * Metodo <I>gameKeyPressed</I> <P>
	 * Metodo usado para manejar el evento que ocurre al presionar una
	 * tecla en cualquier panel y dependiendo del estado.
	 * @param keyCode es el <code>evento</code> que se genera en al presionar las teclas.
	 */
	public void gameKeyPressed(int keyCode) {
		switch (keyCode) {			
			case KeyEvent.VK_UP:
				//se cambian los estados dependiendo que se oprima la tecla de arriba
				if (state == State.M_INS) {
					state = State.M_JUEGO;
		  			repaint();
		   		} else if (state == State.M_PUN) {
		   			state = State.M_INS;
		  			repaint();
		   		} else if (state == State.M_OP) {
		   			state = State.M_PUN;
		  			repaint();
		   		}
		   	      else if (state == State.M_SALIR) {
		   			state = State.M_OP;
		  			repaint();
		   		}
				
				//se cambia la seleccion de opciones mientras este en el estado de opciones
		   		else if (state == State.OPCIONES) {
		   			seleccionOpciones=0;
		   		}
		   	
			break; 							// Fin del case de Arriba
			   	
			case KeyEvent.VK_DOWN:
				//se cambian los estados dependiendo que se oprima la tecla de abajo
		   		if (state == State.M_JUEGO) {
		   			state = State.M_INS;
		  			repaint();
		   		} else if (state == State.M_INS) {
		   			state = State.M_PUN;
		  			repaint();
		   		} else if (state == State.M_PUN) {
		   			state = State.M_OP;
		  			repaint();
		   		}
		  			else if (state == State.M_OP) {
			   			state = State.M_SALIR;
			  			repaint();
		   		
		   		} else if (state == State.JUEGO) {
		   			abajo=true;
		   		}
		   		
		   		//se cambia la seleccion de opciones mientras este en el estado de opciones
		   		else if (state == State.OPCIONES) {
		   			seleccionOpciones=1;
		   		}
		   		
			break;					// Fin del case de tecla Abajo
			   	
		   	case KeyEvent.VK_RIGHT:
		   		if (state == State.JUEGO) { //se mueve el jugador a la derecha
		   			der=true;  
		   		}
		   		if (state == State.OPCIONES&&seleccionOpciones==0) { //se apaga el sonido
		   			seleccionMusicaOn=1;
		   		}
		   		if (state == State.OPCIONES&&seleccionOpciones==1) { //se cambia la musica
		   			musica[seleccionMusica].stop();
		   			prendeSonidoTemporal=1;
		   			seleccionMusica++;
		   			if(seleccionMusica>=2)
		   				seleccionMusica=2;
		   		}
			break;					// Fin del case de Tecla Derecha
			   	
		   	case KeyEvent.VK_LEFT:
		   		if (state == State.JUEGO) {  //se mueve el jugador a la izquierda
		   			izq=true;
		   		}
		   		if (state == State.OPCIONES&&seleccionOpciones==0) {  //se prende el sonido
		   			seleccionMusicaOn=0;
		   		}
		   		if (state == State.OPCIONES&&seleccionOpciones==1) { //se cambia la musica
		   			musica[seleccionMusica].stop();
		   			prendeSonidoTemporal=1;
		   			seleccionMusica--;
		   			if(seleccionMusica<=0)
		   				seleccionMusica=0;
		   		}
			break;					// Fin del case de Tecla izquierda
			   	
		   	case KeyEvent.VK_S: 
		   	  if (state == State.JUEGO) {  //brinca el jugador
		   			arriba=true;
		   			space = true;
		   		}
		   	  
		   	  
			break;					
			   	
		   	case KeyEvent.VK_P:
		   		if(state==State.JUEGO) { //pausa
					state=State.PAUSA;
					repaint();
				}
		   		else if(state==State.PAUSA){  //regresa al juego en caso de pausa
		   			state=State.JUEGO;
		   		}
		   		break;
			
		   	case KeyEvent.VK_ENTER:
		   		if(state == State.WIN){
		   			puntos=0;
		   			state = State.M_JUEGO;
		   		}
		   		
		   		if (state == State.M_JUEGO) {				// Pantalla de Menu Juego
		   			state = State.JUEGO;
		   			//musicaMenu.stop();	
		   			
		   			this.setContentPane(juego);
	     			juego.requestFocus();
	     			validate();
		   		} else if (state == State.M_INS) {			// Pantalla de Instrucciones
		   			state = State.INSTRUCCIONES;
		  			repaint();
		   		} 
		   		else if (state == State.INSTRUCCIONES) {	// Pantalla de Instrucciones
		   			state = State.M_INS;
		  			repaint();
		   		}		
		   		else if (state == State.M_OP) {				// Pantalla de opciones
		   			state = State.OPCIONES;
		  			repaint();
		   		} 
		   		else if (state == State.OPCIONES) {			// Pantalla de opciones
		   			state = State.M_OP;
		  			repaint();
		   		}		
		   		
		   		else if (state == State.M_PUN) {			// Pantalla de puntaje
		   			state = State.PUNTAJE;
		  			repaint();
		   		}
		   		else if (state == State.PUNTAJE) {			// Pantalla de puntaje
		   			state = State.M_PUN;
		  			repaint();
		   		} 
		   		
		   		else if (state == State.M_SALIR) {   	//se destruye el juego
		   			System.exit(0);
		   		}  
		   		else if (state == State.GAMEOVER) {		// Pantalla de Gameover
		   			gameShutdown();						// Se cierra el juego
		   		}
			break;
			
			case KeyEvent.VK_BACK_SPACE:				// Fin del case de Tecla de Enter
			 if (state == State.INSTRUCCIONES) {		// Pantalla de Instrucciones
		   			state = State.M_INS;
		  			repaint();
		   		}
			break;   
			
		
		}
	}
   
   /** 
    * Metodo <I>gameKeyTyped</I> <P>
	* Metodo usado para manejar el evento que ocurre al hacer click una
	* tecla en cualquier panel y dependiendo del estado.
	* @param e es el <code>evento</code> que se genera al hacer click en una tecla.
	*/
	public void gameKeyTyped(KeyEvent e) {  
		if (state == State.JUEGO) {
		    if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A' ) {
				if (sueltaBotonBala) {
			   		jugador = mapa.getPlayer();
					sueltaBotonBala = false;		// Se avisa que se solto el botono de bala		   		
					Bala disparo;
					attack = true;					// Se avisa que se esta atacando
					if (jugador.getLado()) {		// Se crea un nuevo objeto de bala, con velocidad dependiendo del lado del jugador		
						disparo = new Bala((int)jugador.getPosX(), (int)jugador.getPosY() + (jugador.getAlto() / 2 - 20),
						Toolkit.getDefaultToolkit().getImage("images/disparocyan1.png"));
						disparo.setVelocidadX(-VELOCIDADBALA);
					} else {
						disparo = new Bala((int)jugador.getPosX() + jugador.getAncho(), (int)jugador.getPosY() + (jugador.getAlto() / 2 - 20),
						Toolkit.getDefaultToolkit().getImage("images/disparocyan1.png"));	
						disparo.setVelocidadX(VELOCIDADBALA);
					}
					disparo.setTipoDisparo(1);		// Es una bala de nivel 1
					mapa.addBala(disparo);	 		// Se agrega una bala al mapa  	   
				}	
			}	    
		}
	}
   
   /**
	* Metodo <I>gameKeyReleased</I> <P>
	* En este metodo maneja el evento que se genera al soltar la tecla presionada en cualquier panel
	* y dependiendo del estado.
	* @param e es el <code>evento</code> que se genera en al soltar las teclas.
	*/
	public void gameKeyReleased(KeyEvent e){
		if (state == State.JUEGO) { 	
			if(e.getKeyCode() == KeyEvent.VK_S) {
				space = false;
				arriba = false;
		   	} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		   		abajo = false;
		   	} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				der = false;   		
		   	} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	   			izq = false;
	   		} else if (e.getKeyCode() == KeyEvent.VK_D) {
	   			space = false;
	   		} else if (e.getKeyCode() == KeyEvent.VK_A) {
				jugador=mapa.getPlayer();					
	   	
	   		sueltaBotonBala = true;			// Se avisa quie se solto el boton
			}
			
	 	
	   		
		} 		   	    
	}
   
   /**
	* Clase derivada de <code>JPanel</code> en donde se definen las caracteristicas
	* del panel Menu.
	*/
	class Menu extends JPanel implements KeyListener {
		private static final long serialVersionUID = 1L;
		// Constructor
		public Menu() {
			setFocusable(true); 	// Para que sea capaz de recibir eventos
	        requestFocus();
	        addKeyListener(this);	// Se le agrega un escuchador de eventos de teclado
		}
	   
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			super.paintComponent(g2d);
			gameDraw(g2d);
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
	  	* Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	  	* En este metodo maneja el evento que se genera al soltar la tecla presionada.
	  	* @param e es el <code>evento</code> que se genera en al soltar las teclas.
	  	*/
		public void keyReleased(KeyEvent e) {
			gameKeyReleased(e);
		}
	   
	   /**
	  	* Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	  	* En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
	  	* @param e es el <code>evento</code> que se genera en al presionar las teclas.
	  	*/
		public void keyTyped(KeyEvent e) {
			gameKeyTyped(e);
		}
	}	

   /**
	* Clase derivada de <code>JPanel</code> en donde se definen las caracteristicas
	* del panel Juego.
	*/   
	class Juego extends JPanel implements KeyListener {
		private static final long serialVersionUID = 1L;

		// Constructor
	    public Juego() {
			setFocusable(true);		//Solicita el focus
	        requestFocus();
	        addKeyListener(this);	// Para habilitar el uso de teclado
	    }

	    // Llamado por el repaint().
	    public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
	        super.paintComponent(g2d);   // paint background
	        //se repintara el panel mientras haya vidas
	        if (vidas > 0&&state!=State.PAUSA) {
				// Inicializan el DoubleBuffer
	        	intParaPausa=1;
	     		if (dbImage == null){
	     			dbImage = createImage (this.getSize().width, this.getSize().height);
	     			dbg = dbImage.getGraphics ();
	     		}
		     	// Actualiza la imagen de fondo.
				dbg.setColor (getBackground ());
		     	
		     	// Actualiza el Foreground.
		     	dbg.setColor (getForeground());
			    render.draw(g2d, mapa, 1000, 600);	
			} else {
				if(intParaPausa==1){
				sonidoPausa.play();
				intParaPausa=2;
				}
	     		g2d.drawImage(pausa, 0, 0, this);  
			}
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
	    * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
		* En este metodo maneja el evento que se genera al soltar la tecla presionada.
      	* @param e es el <code>evento</code> que se genera en al soltar las teclas.
		*/
		public void keyReleased(KeyEvent e) {
			gameKeyReleased(e);
	    }
	   
	   /**
		* Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
		* En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
	  	* @param e es el <code>evento</code> que se genera en al presionar las teclas.
		*/
		public void keyTyped(KeyEvent e) {
			gameKeyTyped(e);
		}
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
	  	* Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	  	* En este metodo maneja el evento que se genera al soltar la tecla presionada.
	  	* @param e es el <code>evento</code> que se genera en al soltar las teclas.
	  	*/
		public void keyReleased(KeyEvent e) {
			gameKeyReleased(e);
		}
	   
	   /**
	  	* Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	  	* En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
	  	* @param e es el <code>evento</code> que se genera en al presionar las teclas.
	  	*/
		public void keyTyped(KeyEvent e) {
			gameKeyTyped(e);
		}	
		 
    // El main del juego
	public static void main(String[] args) throws IOException{
		SplashScreen splash = SplashScreen.getSplashScreen();
	   if (splash != null) { // Se crea un splash Screen
	      Dimension splashBounds = splash.getSize();
	      Graphics2D g2d = splash.createGraphics();
	   
	      // Simulate a progress bar
	      for (int i = 0; i < 100; i += 5) {
	         g2d.setColor(Color.BLUE);
	        // g2d.drawImage(Toolkit.getDefaultToolkit().getImage("images/corre1.png"),i,splashBounds.height -100,null);
	         g2d.fillRect(0, splashBounds.height -20, splashBounds.width * i / 100, 20);
	         splash.update();
	         try {
	            Thread.sleep(10);  // Some delays
	         } catch (Exception e) {}
	      }
	      g2d.dispose();
	      splash.close();
	   }

		new GameMain();			// Se crea un constructor
	}
	/**
     * Metodo <I>leeArchivo</I> que procesa eventos de KeyReleased que lee la 
     * informacion de un archivo y lo agrega a un vector.
     *
     * @throws IOException
     */
    public void leeArchivo() throws IOException{
    	BufferedReader fileIn = new BufferedReader(new FileReader(nombreArchivo));
    	String dato = fileIn.readLine();
    	vec = new Vector<Puntaje>();
    	while(dato != null) {
    		arr = dato.split(",");
    		int num = (Integer.parseInt(arr[0]));
    		String nom = arr[1];
    		vec.add(new Puntaje(nom, num));
    		dato = fileIn.readLine();
    	}
    	fileIn.close();
    }
    
    /**
     * Metodo <I>grabaArchivo</I> que agrega la informacion del vector al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException{
    	PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
    	Collections.sort(vec);
    	int limite=vec.size();
    	if(limite>5){ // Este es directamente el numero de tops score a desplegar,
    		limite=5;
    	}
    	for (int i=0; i<limite; i++) {
    		Puntaje x;
    		x = (Puntaje) vec.get(i);
    		fileOut.println(x.toString());
    	}
    	fileOut.close();	
    	
    } 
}
