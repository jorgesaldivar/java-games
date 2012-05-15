import java.awt.Image;
import java.awt.Toolkit;

/**
 *La clase Jugador se encargara de manejar las animaciones del jugador,
 *validar si ha collisionado, las vidas del personaje y la velocidad con la que 
 *se desplazara el jugador.
*/
public class Player extends Creature {
	
	private int vida;
	//Velocidad al saltar. Negativa para que el jugador se mueva hacia arriba
    private static  float JUMP_SPEED = -.95f; 
    
   	private int pared=0;	//Si esta collisionando con una pared.
   	
	private final int MAXVIDA = 15; //Las vidas del jugador, maxima para evitar que se pase de estas
	private float MaxSpeed = 0.5f; //Velocidad maxima del jugador
    private boolean onGround; //Si el jugador esta actualmente en el suelo
    
    //animaciones del jugador
    private Animation anim;
    private Animation animParado;
    private Animation animDisparo;
    private boolean IzqDer;
    private float gravity = 0.002f;	
	private long tiempoDisparo;
    
	//Estados de movimiento del jugador
   	static enum Estado{
   		 CAMINANDO,SALTANDO,PARADO, CORRIENDO
   	}
   	
   	//Estados de ataque del jugador
   	static enum Ataque{
   		NOATK, ATK
   	}
   	
   
   	static Estado estado = Estado.CAMINANDO;   // Estado actual del jugador (movimiento)
   	static Ataque ataque = Ataque.NOATK;		// Estado actual de ataque
   	
   	
 	/**
	 * Metodo constructor de Player usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 */  	
    public Player(int posX, int posY, Image imagen)
    {	
        super(posX, posY, imagen);	// Llama al super constructor
        vida = MAXVIDA;				// Llena la vida al maximo
        cargaImagenes();			// carga las imagenes de las animaciones
        setVidaMax(MAXVIDA);
        iniciaVida();
   }
 
 	/*
	* Metodo <I>setGravity</I> que cambia la gravedad del Jugador.<P>
	*/       
    public void setGravity(float grav){
    	gravity = grav;				// Le asigna al jugador cierta gravedad
    }
    
	/*
	* Metodo <I>getGravity</I> que devuelve la gravedad del Jugador.<P>
	*/     
    public float getGravity(){
    	return gravity;				// Regresa la gravedad
    }

	/*
	* Metodo <I>estaSaltando</I> que indica si el Player salta o no.<P>
	* @return true si esta saltando, false si no esta saltando
	*/ 
   public boolean estaSaltado(){
   		if (estado == Estado.SALTANDO) {
   			return true;			
   		}
   		return false;				
   }
   
	/*
	* Metodo <I>estaAtacando</I> que indica si el Player ataca o no.<P>
	* @return // Devuelve true si el jugador esta atacando, False si no esta atacando
	*/
	public boolean estaAtacando(){
		if (ataque != Ataque.NOATK)	//checha que el estado no sea no atacar	
			return true;			
		return false;			
	}
	/*
	* Metodo <I>collideVertical</I> que frena horizontalmente a Player.<P>
	*/
    public void collideHorizontal() { //Se manda llamar cuando hay colision con un objeto, para que el personaje ya no avance en X
        setVelocidadX(0);			// Reduce la velocidad a 0
    }

	/*
	* Metodo <I>collideVertical</I> que frena verticalmente a Player.<P>
	*/	
    public void collideVertical() { //Si hay colision con un piso (hacia abajo), se pone la velocidad en Y en 0.
        if (getVelocidadY() > 0) {
            onGround = true; 		//Al haber colision, el personaje esta en el piso.
            JUMP_SPEED = -.95f;		// Se cambia la velocidad de salto
        }
        if(estado != Estado.CORRIENDO)//Si estaba Corriendo, sigue asi
 			estado = Estado.CAMINANDO;
 		
 		if (estado == Estado.CORRIENDO && (getVelocidadX() == 0)) { 	//Se revisa que el jugador este quieto
 			if(onGround){
 				estado = Estado.CAMINANDO;	// Si esta en el piso, el estado es caminando
 			} else if(!onGround) {
 				estado = Estado.SALTANDO;	// Si esta inmovil y se despega del suelo, es un salto
 			}
 		}
        setVelocidadY(0); 
    }

	/*
	* Metodo <I>setPosY</I> que establece la posicion en Y del Player.<P>
	*/
    public void setPosY(float y) {
        // Se revisa si el jugador esta cayendo
        if (Math.round(y) > Math.round(getPosY())) {
            onGround = false;							//Si se intenta modificar la posicion en Y del jugador a algo mas bajo de lo actual, quiere decir que esta cayendo
        }
        super.setPosY(y);					// Se cambia la posicion en Y del jugador
    }

	/**
	* Metodo <I>jump()</I> <P>
	* Si el personaje esta en el piso o se le obliga a saltar, su velocidad en Y es JUMP_SPEED.
	* Si esta en una pared, se le permite saltar de nuevo
	*/
    public void jump(boolean forceJump) {
        if (onGround || forceJump || (pared == 1)) {
        	pared = 0;
            onGround = false; 		//El personaje ya no esta en el piso
            setVelocidadY(JUMP_SPEED);//Su velocidad es la velocidad de salto
            estado = Estado.SALTANDO;	// El estado cambia a Saltando
        }
    }
    
	/**
	* Metodo <I>updateAnim()</I> <P>
	* cambia la animacion del Player.
	* Si ha pasado cierto tiempo desde que comenzo la animacion,
	* se cambia de animacion y se cambia el Estado.
	*/
	public void updateAnim(long elapsedTime){
		setGravity(0.002f);
		if(estado == Estado.SALTANDO){
				setImageIcon((Toolkit.getDefaultToolkit().getImage("images/salta09.png")).getScaledInstance(64, 130, java.awt.Image.SCALE_SMOOTH));				
		} else if ((estado == Estado.CAMINANDO) && (getVelocidadX() != 0)){ //EN movimiento
			anim.update(elapsedTime);
			setImageIcon((anim.getImage()).getScaledInstance(64, 130, java.awt.Image.SCALE_SMOOTH));	//60,130
		} else if ((estado == Estado.CAMINANDO) && (getVelocidadX() == 0)) { // Parado

			if (ataque == Ataque.ATK) {		// Cargando el arma
				long tiempoDesdeAtk = System.currentTimeMillis() - tiempoDisparo;
				if (tiempoDesdeAtk < 150) {
					animDisparo.update(elapsedTime);	
					setImageIcon((animDisparo.getImage()).getScaledInstance(72, 130, java.awt.Image.SCALE_SMOOTH));				
				} else {				// Se dejo de cargar
					ataque = Ataque.NOATK;
	    			animDisparo.start();	
				}		
			} else {
				animParado.update(elapsedTime);	// Animacion de parado
				setImageIcon((animParado.getImage()).getScaledInstance(64, 130, java.awt.Image.SCALE_SMOOTH));					
			}
		} else if ((estado == Estado.CORRIENDO) || (MaxSpeed == 1)) {	// Corriendo
			setImageIcon((Toolkit.getDefaultToolkit().getImage("images/Slide1.png")).getScaledInstance(64, 130, java.awt.Image.SCALE_SMOOTH));	
		}		
	}		
	
	/**
	* Metodo <I>getMaxSpeed()</I> <P>
	* Devuelve la Maxima velocidad posible del Player
	*/
    public float getMaxSpeed() {
        return MaxSpeed;		// Devuelve la maxima velocidad posible del jugador
    }

	
	/**
	* Metodo <I>disparo()</I> <P>
	* Cambia el estado de Ataque a ATK
	*/     
    public void disparo(){
    	ataque = Ataque.ATK;
		tiempoDisparo = System.currentTimeMillis();
    }
      

 	/**
	* Metodo <I>cambiaVida()</I> <P>
	* Reduce o aumenta la vida del jugador.
	* @param int <code> cuanto</code> es la cantida de vida a modificar.	
	*/       
    public void cambiaVida(int cuanto){
    	if (vida >= 0 && vida <= MAXVIDA) {
    		vida += cuanto;
    		if (vida > MAXVIDA){
    			vida = MAXVIDA;	// Se asegura de no aumentar la vida mas alla del limite
    		}	
    		if (vida < 0){
    			vida =0;		// Se encarga de no reducir la vida mas alla de 0
    		}	
    	}	
    }

	/**
	* Metodo <I>getVida()</I> <P>
	* Devuelve la vida actual del jugador
	*/      
    public int getVida(){
    	return vida;
    }
    

	/**
	* Metodo <I>getMaxVida()</I> <P>
	* Regresa la vida Maxima posible del jugador
	*/      
    public int getMaxVida(){
    	return MAXVIDA;
    }
    
	/**
	* Metodo <I>cargaImagenes()</I> <P>
	* Carga todas las imagenes que se usaran por el Player
	*/    	
    public void cargaImagenes(){
    	// Animacion de correr
    	Image a1 =  Toolkit.getDefaultToolkit().getImage("images/corre1.png");
        Image a2 =  Toolkit.getDefaultToolkit().getImage("images/corre2.png");           
        Image a3 =  Toolkit.getDefaultToolkit().getImage("images/corre3.png");
        Image a4 =  Toolkit.getDefaultToolkit().getImage("images/corre4.png");           
        Image a5 =  Toolkit.getDefaultToolkit().getImage("images/corre5.png");
        Image a6 =  Toolkit.getDefaultToolkit().getImage("images/corre6.png");           
        Image a7 =  Toolkit.getDefaultToolkit().getImage("images/corre7.png");
        Image a8 =  Toolkit.getDefaultToolkit().getImage("images/corre8.png");           
        Image a9 =  Toolkit.getDefaultToolkit().getImage("images/salta09.png");
        
        // Animacion de parado	
    	Image b1 =  Toolkit.getDefaultToolkit().getImage("images/parado01.png");
        Image b2 =  Toolkit.getDefaultToolkit().getImage("images/parado02.png");           
        Image b3 =  Toolkit.getDefaultToolkit().getImage("images/parado03.png");
        Image b4 =  Toolkit.getDefaultToolkit().getImage("images/parado04.png");           
        Image b5 =  Toolkit.getDefaultToolkit().getImage("images/parado05.png");
        Image b6 =  Toolkit.getDefaultToolkit().getImage("images/parado06.png");           
        Image b7 =  Toolkit.getDefaultToolkit().getImage("images/parado07.png");
        Image b8 =  Toolkit.getDefaultToolkit().getImage("images/parado08.png");           
        Image b9 =  Toolkit.getDefaultToolkit().getImage("images/parado09.png");
        Image b10 =  Toolkit.getDefaultToolkit().getImage("images/parado10.png");           
        Image b11 =  Toolkit.getDefaultToolkit().getImage("images/parado11.png");  
        Image b12 =  Toolkit.getDefaultToolkit().getImage("images/parado12.png"); 
        Image b13 =  Toolkit.getDefaultToolkit().getImage("images/parado13.png");
        Image b14 =  Toolkit.getDefaultToolkit().getImage("images/parado14.png");           
        Image b15 =  Toolkit.getDefaultToolkit().getImage("images/parado15.png");  
        Image b16 =  Toolkit.getDefaultToolkit().getImage("images/parado16.png");         	         	 
        Image b17 =  Toolkit.getDefaultToolkit().getImage("images/parado17.png"); 
 		
 		// Animacion de disparo
     	Image c1 =  Toolkit.getDefaultToolkit().getImage("images/dispara1.png");
        Image c2 =  Toolkit.getDefaultToolkit().getImage("images/dispara2.png");           
        Image c3 =  Toolkit.getDefaultToolkit().getImage("images/dispara3.png");
		 

        // Animacion de correr     	    
		anim = new Animation();
		anim.addFrame(a1, 100);
		anim.addFrame(a2, 100);
		anim.addFrame(a3, 100);
		anim.addFrame(a4, 100);
		anim.addFrame(a5, 100);
		anim.addFrame(a6, 100);
		anim.addFrame(a7, 100);
		anim.addFrame(a8, 100);
		anim.addFrame(a9, 100);
		
		// Animacion de Parado
		animParado = new Animation();
		animParado.addFrame(b1, 100);
		animParado.addFrame(b2, 100);
		animParado.addFrame(b3, 100);
		animParado.addFrame(b4, 100);
		animParado.addFrame(b5, 100);
		animParado.addFrame(b6, 100);
		animParado.addFrame(b7, 100);
		animParado.addFrame(b8, 100);		
		animParado.addFrame(b9, 100);
		animParado.addFrame(b10, 100);
		animParado.addFrame(b11, 100);    
		animParado.addFrame(b12, 100);  
		animParado.addFrame(b13, 100);
		animParado.addFrame(b14, 100);    
		animParado.addFrame(b15, 100);  
		animParado.addFrame(b16, 100);  
		animParado.addFrame(b17, 100);  

		// Animacion de Disparo
		animDisparo = new Animation();
		animDisparo.addFrame(c1, 50);
		animDisparo.addFrame(c2, 50);
		animDisparo.addFrame(c3, 50);
    }
	/**
	* Metodo <I>getLado()</I> <P>
	* Devuelve true si esta volteando a la izquierda, false si voltea a la derecha
	*/    
    public boolean getLado(){ //Devuelve True si esta volteando a la Izquierda, False si voltea a la derecha
    	if (getVelocidadX() < 0) {
    		IzqDer = true;			// Si la velocidad es negativa, voltea a la izquierda
    	} else if (getVelocidadX() > 0) { 
    		IzqDer = false;			// Si la velocidad es positiva, voltea a la derecha
    	}
    	return IzqDer;
    }
}
