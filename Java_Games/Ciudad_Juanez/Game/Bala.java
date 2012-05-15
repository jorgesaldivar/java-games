
import java.awt.Image;
import java.awt.Toolkit;
/**
* Clase Bala, hija de Sprite, esta clase es la encargada 
* de controlar las balas del jugador y enemigos, su impresion
* en el mapa, movimiento y daño.
*/  
public class Bala extends Sprite{
	private int damage=1;
	private float posicion; //Una posicion X dentro del mapa
	private float inicial; //La posicion inicial de la bala
	private int tipoDisparo; //varible para saber si la bala es del jugador o del enemigo
	private Animation animDispJugador; //Animacion de las balas del jugador
	private Animation animDispEnemigo; //Animacion de las balas del Enemigo
	
	/**
	 * Metodo constructor usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 */
	public Bala(int posX, int posY, Image image) {
		super(posX,posY,image);	
		inicial=0;
		cargaImagenes();
	}

	/**
	 * Metodo constructor usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 * @param velocidad es la <code>velocidad</code> que la bala tendra.
	 */
	public Bala(int posX, int posY, Image image, float velocidad) {
		super(posX,posY,image);	
		setVelocidadX(velocidad);
		dx = velocidad;
		inicial=0;
		cargaImagenes();	
	}
	
	/**
	 * Metodo constructor usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 * @param velocidadX es la <code>velocidadX</code> que la bala tendra en el eje X.
	 * @param velocidadY es la <code>velocidadY</code> que la bala tendra en el eje Y.
	 */
	public Bala(int posX, int posY, Image image, float velocidadX, float velocidadY) {
		super(posX,posY,image);	
		setVelocidadX(velocidadX);
		setVelocidadY(velocidadY);
		inicial=0;
		cargaImagenes();
	}
	

	/**
	* Metodo <I>getDamage</I> 
	* @return Devuelve el daño realizado por la bala 
	*/	
	public int getDamage(){
		return damage;
	}
	
	/**
	* Metodo <I>setDamage</I> 
	* Da un nuevo valor al daño de la bala
	*/	
	public void setDamage ( int num){
		this.damage = num;
	}
	
	/**
	* Metodo <I>actualizaPosicionI</I> 
	* usado para actualizar la posicion en X de la Bala
	*/
	public void actualizaPosicionI() {
		posicion = this.getPosX();
	}
	/**
	* Metodo <I>getPosicionF</I> 
	* Devuelve la posicion en X de la Bala
	*/	
	public float getPosicionF() {
		return posicion;
	}

	/**
	* Metodo <I>getInicial</I> 
	* Devuelve la distancia recorrida por la Bala
	*/		
	public float getInicial(){
		return inicial;
	}
	/**
	* Metodo <I>cambiaInicial</I> 
	* Actualiza la distancia recorrida por la Bala
	*/		
	public void cambiaInicial(float num){
		inicial=num;
		
	}
	
	/*
	* Metodo <I>setTipoDisparo</I> 
	* Permite cambiar el tipo de disparo
	* Enemigo o del jugador
	* Dependiendo del numero que haya sido el parametro
	*/	
	public void setTipoDisparo(int num){
		tipoDisparo = num;
	}
	
	/*
	* Metodo <I>cambiaInicial</I> 
	* Regresa el tipo de disparo
	* Enemigo o del jugador
	* Dependiendo del numero regresado
	*/	
	public int getTipoDisparo(){
		return tipoDisparo;
	}
	
	/*
	* Metodo <I>updateAnim()</I> <P>
	* cambia la animacion del Player.
	* Si ha pasado cierto tiempo desde que comenzo la animacion,
	* se cambia de animacion y se cambia el Estado.
	*/
	public void updateAnim(long elapsedTime){
		if(tipoDisparo == 1)
		{
			animDispJugador.update(elapsedTime);
			setImageIcon(animDispJugador.getImage());		
		}
			 else if (tipoDisparo == 4)
			 {
				animDispEnemigo.update(elapsedTime);
				setImageIcon(animDispEnemigo.getImage());
		     } 
	}	
	/**
	* Metodo <I>cargaImagenes()</I> <P>
	* Carga todas las imagenes que se usaran por el Player
	*/    	
    public void cargaImagenes(){
    	// Imagenes del disparo Jugador
    	Image a1 =  Toolkit.getDefaultToolkit().getImage("images/disparocyan1.png");
        Image a2 =  Toolkit.getDefaultToolkit().getImage("images/disparocyan2.png");           
        Image a3 =  Toolkit.getDefaultToolkit().getImage("images/disparocyan3.png");

 		//Imagenes de los disparos de enemigos
      	Image d1 =  Toolkit.getDefaultToolkit().getImage("images/d1.png");
      	Image d2 =  Toolkit.getDefaultToolkit().getImage("images/d2.png");
      	Image d3 =  Toolkit.getDefaultToolkit().getImage("images/d3.png");
      	Image d4 =  Toolkit.getDefaultToolkit().getImage("images/d4.png");
      	Image d5 =  Toolkit.getDefaultToolkit().getImage("images/d5.png");
      	Image d6 =  Toolkit.getDefaultToolkit().getImage("images/d6.png");
      	Image d7 =  Toolkit.getDefaultToolkit().getImage("images/d7.png");
      	Image d8 =  Toolkit.getDefaultToolkit().getImage("images/d8.png");
      	Image d9 =  Toolkit.getDefaultToolkit().getImage("images/d9.png");
      	Image d10 =  Toolkit.getDefaultToolkit().getImage("images/d10.png");
      	Image d11 =  Toolkit.getDefaultToolkit().getImage("images/d11.png");
      	Image d12 =  Toolkit.getDefaultToolkit().getImage("images/d12.png");
      	Image d13 =  Toolkit.getDefaultToolkit().getImage("images/d13.png");
    
        // Animacion de Disparo Jugador     	    
		animDispJugador = new Animation();
		animDispJugador.addFrame(a1, 100);
		animDispJugador.addFrame(a2, 100);
		animDispJugador.addFrame(a3, 100);

	    // Animacion de Disparo Enemigo    	    
		animDispEnemigo = new Animation();
		animDispEnemigo.addFrame(d1, 100);
		animDispEnemigo.addFrame(d2, 100);
		animDispEnemigo.addFrame(d3, 100);
		animDispEnemigo.addFrame(d4, 100);
		animDispEnemigo.addFrame(d5, 100);
		animDispEnemigo.addFrame(d6, 100);
		animDispEnemigo.addFrame(d7, 100);
		animDispEnemigo.addFrame(d8, 100);
		animDispEnemigo.addFrame(d9, 100);		
		animDispEnemigo.addFrame(d10, 100);
		animDispEnemigo.addFrame(d11, 100);
		animDispEnemigo.addFrame(d12, 100);	
		animDispEnemigo.addFrame(d13, 100);
    }	
}
    
    