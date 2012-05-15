import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
	
	/**
	 * Clase Sprite, es la clase encargada de darle los aspectos
	 * fisico (caida libres), la posicion (movimiento) y la
	 * posibilidad de atacar a los enemigos y jugador
	 */  


public class Sprite {
	
   	private Animation anim; //Animacion del enemigo o jugador
	private ImageIcon icono;    //icono.    
    
    private float posX;// posicion en el eje X
    private float posY;  // posicion en el eje Y 
  
    protected float dx; //cambio de la posicion referente a una posicion inicial en X
    protected float dy; //cambio de la posicion referente a una posicion inicial en Y
    
    private int vidaMax = 3;//Le da al enemigo o jugador un numero maximo de vidas
    protected int vida; //Las vidas que se necesitan quitar para destruir el personaje

	private String estadoActual; //el estado en que esta el Sprite
	int lado; //Lado hacia donde apunta el Sprite
	
	//Los estados que se puede estar el Sprite
	public static final String PROXIMA_CAIDA_LIBRE = "ProximaCaidaLibre";
	public static final String CHOQUE_CON_PARED = "ChoqueConPared";
	public static final String ESTA_CERCA = "EstaCerca";
	public static final String DERECHA_ATACANDO = "paradoYAtacando";
	public static final String IZQUIERDA_ATACANDO="paradoYAtacandoD";
	public static final String NO_ESTA_CERCA = "noEstaCerca";
	protected boolean matenme = false;
 


	/**
	* Constructor Sprite para crear un objeto de tipo Sprite.
	* @param posX posicion en el eje X del Sprite
	* @param posY posicion en el eje Y del Sprite
	* @param image la imagen del objeto que funcionara para reconocer
	* si es el jugador o un enemigo
	*/
    public Sprite(int posX, int posY ,Image image) {
		this.posX=posX;
		this.posY=posY;
		icono = new ImageIcon(image);
    }
	/**
	* Constructor Sprite para crear un objeto de tipo Sprite.
	* @param posX posicion en el eje X del Sprite
	* @param posY posicion en el eje Y del Sprite
	* @param Animacion el conjunto de imagenes del objeto que
	* funcionara para reconocer si es el jugador o un enemigo
	*/
	public Sprite(int posX, int posY , Animation anim) {
		this.anim = anim;
		this.posX=posX;
		this.posY=posY;
    }
    
    /**
	* Metodo Sprite se encarga de asignar una imagen al objeto Sprite
	* @param image, la imagen a desplegar en el Sprite
	*/
	public Sprite(ImageIcon image){
		icono = image;
	}
	
	/**
	* Metodo actualiza se encarga de calcular la caida libre del Sprite
	* @param tiempo, tiempo usado para calcular la caida del Sprite
	*/
    public void actualiza(long tiempo) {
		posX += dx * tiempo; //movimiento tipo caida libre en eje x
       	posY += dy * tiempo; //movimiento tipo caida libre en eje y
       	if(getVida() <= 0)  //si las vidas se acaban el Sprite se elima
			matenme = true;
		
    }
    /**
	* Metodo atacar encargado de hacer que los enemigos ataquen.
	* Se usa en conjunto con otro metodo para saber si debe atacar
	* @return true, si el enemigo no estaba atacando lo forza a atacar
	* @return false, si el enemigo atacaba y el jugador no esta cerca
	* lo forza a detenerse
	*/
	public boolean atacar(){
		return false;
	}
	
	/**
	* Metodo bala encargado de desplegar las balas de los enemigos
	* @param derecha, determina a que lado el Sprite de la bala se vera
	* @param mapa, si el enemigo atacaba y el jugador no esta cerca
	* @return null, al terminar de agregar la bala la saca del TileMap
	*/
	public Bala dibujaBala(boolean derecha, TileMap mapa){
		return null;
	}

	

	/**
	* Metodo checaProximidad para determinar si hay un objeto cerca del Sprie
	* Si hay un objeto cercano, el Sprite cambia de estado.
	* @param otroSprite, sera el Sprite a comparar si esta cerca o no
	*/
	public void checaProximidad(Sprite otroSprite) //Esto es usado para checar proximidad de un Enemigo con el jugador
		{
	//Determina si el enemigo esta a una distancia, comparando sus ejes en X y Y y mientras su estado no haya sido ESTA_CERCA lo pone en este.
		if(Math.abs(this.getPosX() - otroSprite.getPosX()) < 500 && this.getPosY() < otroSprite.getPosY() + 50 
		&& this.getPosY() + this.getAlto() + 50 > otroSprite.getPosY() && estadoActual != ESTA_CERCA)
			revisarCambioDeEstado(Sprite.ESTA_CERCA, 0); //asigna el estado
			
		//Si no esta cerca determina si el usuario esta alejado una cierta distancia para asignarle el estado de NO_ESTA_CERCA
			else if(Math.abs(this.getPosX() - otroSprite.getPosX()) > 500 || this.getPosY() > otroSprite.getPosY() + 50 
			&& this.getPosY() + this.getAlto() + 50 < otroSprite.getPosY())
			revisarCambioDeEstado(Sprite.NO_ESTA_CERCA, 0); //Asignacion del estado
			
			//Si el Jugador esta cerca se necesita saber si el enemigo cambiara de lado
				else if(getCurrState() == ESTA_CERCA) 
				{
					if(this.getPosX() - otroSprite.getPosX()>0) //Checa en que lado esta
					{
						dx = Math.abs(dx)*-1; //checa si esta a la derecha
						setLado(-1); //cambia de lado apuntando hacia la derecha
					}
					else{
						dx = Math.abs(dx); //checa si esta a la izquierda
						setLado(1); //cambia de lado apuntando hacia la izquierda
						}
				}
	}

	/**
	* Metodo puedoMorir para determinar si un objeto puede ser destruido.
	* @return true, si el objeto puede ser destruido
	* @return false, si el objeto no se puede destruir
	*/
	public boolean puedeMorir(){
		return matenme;
	}
	
	/**
	* Metodo setLado para determinar el lado hacia donde apuntara el Sprite
	* @param lado, entero que indica hacia donde apuntara el Sprite
	* 1 izquierda, -1 derecha
	*/
	public void setLado(int lado){
		this.lado = lado; //asignacion del lado
	}
	
	/**
	* Metodo volteaADerecha para determinar el lado hacia donde debe voltear el Sprite
	* @return true, si esta orientado a la izquierda = 1, 
	* @return false, si esta orientado a la derecha  = -1
	*/
	public boolean volteaADerecha(){
		if(lado >0)
			return true;
		return false;
	}
		
	
	/**
	* Metodo revisarcambioDeVueltapara revisar si el enemigo llega a una esquina.
	* Si llega a una esquina, el Sprite gira y cambia de direccion.
	* @param mapa es el TileMap.
	* @param posX es el float de la posicion en X del Sprite.
	* @param posY es el float de la posicion en Y del Sprite.
	* @param newX es el float de la futura posicion en X del Sprite.
	*/
	public void revisarCambioDeVuelta(TileMap mapa, float posX, float posY, float newX){
		if(dx>0) newX = newX + getWidth();
			for(int x = 0; x<mapa.getWidth(); x++)
			{
				if(mapa.getTile(TileMapRenderer.pixelsToTiles(posX + TileMapRenderer.TILE_SIZE*x), 
				TileMapRenderer.pixelsToTiles(posY + getAlto() + TileMapRenderer.TILE_SIZE/2)) == null)
					if(Math.abs(TileMapRenderer.tilesToPixels(TileMapRenderer.pixelsToTiles(posX + 
					TileMapRenderer.TILE_SIZE*x))) - newX < TileMapRenderer.TILE_SIZE/8)
					{
						setPosX(posX);
						revisarCambioDeEstado(Sprite.PROXIMA_CAIDA_LIBRE, newX);
					}
			}
	}
	
	/**
	* Metodo <I>getPosX</I> que devuelve la posicion en X del Sprite.<P>
	*/
    public float getPosX() {
        return posX;
    }
    
	/**
	* Metodo <I>getPosY</I> que devuelve la posicion en Y del Sprite.<P>
	*/
    public float getPosY() {
        return posY;
    }

	/**
	* Metodo <I>setPosX</I> que establece la posicion en X del Sprite.<P>
	* @param posX, la nueva posicion para el sprite
	*/
    public void setPosX(float posX) {
        this.posX = posX;
    }

	/**
	* Metodo <I>setPosY</I> que establece la posicion en Y del Sprite.<P>
	* @param posY, la nueva posicion para el sprite
	*/
    public void setPosY(float posY) {
        this.posY = posY;
    }

	/**
	* Metodo <I>getAncho</I> que devuelve el ancho en pixeles del Sprite.<P>
	*/
    public int getAncho() {
        return icono.getIconWidth();
    }

	/**
	* Metodo <I>getAlto</I> que devuelve el alto en pixeles del Sprite.<P>
	*/
    public int getAlto() {
        return icono.getIconHeight();
    }

	/**
	* Metodo <I>getVelocidadX</I> que devuelve la velocidad en X del Sprite.<P>
	*/
    public float getVelocidadX() {
        return dx;
    }

	/**
	* Metodo <I>getVelocidadY</I> que devuelve la velocidad en Y del Sprite.<P>
	*/
    public float getVelocidadY() {
        return dy;
    }

	/**
	* Metodo <I>setVelocidadX</I> que cambia la velocidad en X del Sprite.<P>
	* @param dx, float que cambia la velocidad de avance en el eje X
	*/
    public void setVelocidadX(float dx) {
        this.dx = dx;
    }

	/**
	* Metodo <I>setVelocidadY</I> que cambia la velocidad en Y del Sprite.<P>
	* @param dy, float que cambia la velocidad de avance en el eje Y
	*/
    public void setVelocidadY(float dy) {
        this.dy = dy;
    }

	/**
	 * Metodo setImageIcon modificador usado para cambiar el icono del objeto 
	 * @param icono es el <code>icono</code> del objeto.
	 */
	public void setImageIcon(Image icono) {
		this.icono = new ImageIcon(icono);
	}
	
	/**
	 * Metodo ImageIcon de acceso que regresa el icono del objeto 
	 * @return ImageIcon es el <code>ImageIcon</code> del objeto.
	 */
	public ImageIcon getImageIcon() {
		return icono;
	} 
    
	/**
	 * Metodo Image da acceso que regresa la imagen del icono 
	 * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
	 */
	public Image getImagenI() {
		return icono.getImage();
	}  


	/**
	 * Metodo Rectangle de acceso que regresa un nuevo rectangulo
	 * @return un objeto de la clase <code>Rectangle</code> que es el perimetro 
	 * del rectangulo
	 */
	public Rectangle getPerimetro(){
		return new Rectangle((int)getPosX(),(int)getPosY(),getAncho(),getAlto());
	}
	
	/**
	 * Checa si el objeto <code>Objetos</code> intersecta a otro <code>Objetos</code>
	 * @param Sprite, el objeto a comparar si hubo una colision.
	 * @return un valor booleano <code>true</code> si lo intersecta <code>false</code>
	 * en caso contrario
	 */
	public boolean intersecta(Sprite obj){
		return getPerimetro().intersects(obj.getPerimetro());
	}
	
	/**
	 * Checa si el objeto <code>Objetos</code> intersecta a otro <code>Objetos</code> por debajo
	 * @param Sprite, el objeto a comparar si hubo una colision.
	 * @return un valor booleano <code>true</code> si lo intersecta <code>false</code>
	 * en caso contrario
	 */
	public boolean intersectaAbajo(Sprite obj){
		 //Obtiene el area de la parte baja de un objeto a comparar
		Rectangle parteBajaObjeto1 = new Rectangle((int)getPosX(),(int)getPosY()+getAlto()-20,getAncho(),20);
		 //Obtiene el area de la parte alta de un objeto a comparar
		Rectangle parteAltaObjeto2 = new Rectangle((int)obj.getPosX(),(int)obj.getPosY(),obj.getAncho(),20);
		//llama al metodo intersecta para saber si han colisionado y regresa el valor
		return parteBajaObjeto1.intersects(parteAltaObjeto2); 
	}		


    /**
	 * Metodo getImageFromAnimation consigue la imagen actual de la animacion
	 * @return Image regresa la imagen
	 */
    public Image getImageFromAnimation(){
        return anim.getImage();
	}

    /**
	* Metodo <I>getWidth</I> que regresa el ancho de la imagen.<P>
	* @return int, que sera el valor del ancho de la imagen
	*/
    public int getWidth() {

       return anim.getImage().getWidth(null);
    }

	/**
	* Metodo <I>getHeight</I> que regresa el alto de la imagen.<P>
	*@return int, que sera el valor del ancho de la imagen
	*/
    public int getHeight() {
       return anim.getImage().getHeight(null);
    }

    /**
	 * Metodo clone, funciona para duplicar Sprites
	 * Sirve para crear multiples Sprites con la misma imagen
	 * @return un nuevo Sprite con una imagen
     */
    public Object clone() {
        return new Sprite(icono);
    }
    
	/**
	* Metodo <I>setAnimation</I> que cambia la animacion del Sprite.<P>
	* @param la animacion nueva
	*/
	protected void setAnimation(Animation anim){
		this.anim = anim;
		anim.getImage();
	}
	
	/**
	* Metodo <I>updateAnim</I> que actualiza la animacion del Sprite.<P>
	* @param elapsedTime, servira para saber el tiempo de desplegado de la animacion
	*/
	public void updateAnim(long elapsedTime){
		anim.update(elapsedTime);
	}
	
	/**
	* Metodo <I>revisarCambioDeEstado</I> 
	*/	
	public void revisarCambioDeEstado(String identificador, float newX){
	}
	
	/**
	* Metodo <I>getCurrState</I> que regresa el Estado del Sprite.<P>
	* @return el estado actual del objeto
	*/
	public String getCurrState(){
		return estadoActual;
	}
	
	/**
	* Metodo <I>setState</I> que modifica el Estado del Sprite.<P>
	* @param newState, cambia el estado del Sprite
	*/
	public void setState(String newState){
		estadoActual = newState;
	}
	
	/**
	* Metodo cambiaVida que modifica las vidas del Sprite
	* @param cant, cambia el estado del Sprite
	*/
    public void cambiaVida(int cant){
    	if(vida>=0 && vida<=vidaMax) //Si las nuevas vidas son menores a su maxima vida le suma las vidas actuales a las nuevas
    	{
    		vida+=cant;
    		if(vida>vidaMax) //si las vidas son mas que la vida maxima, las vidas se ponen al maximo
    			vida=vidaMax;
    		if(vida<0)		//Si las vidas son cero se queda igual
    			vida=0;
    	}	
    }
    
    /**
	* Metodo getVida regresa las vidas del Sprite
	* @return int, el numero de vidas del objeto
	*/
    public int getVida(){
    	return vida;
    }
    
    /**
	* Metodo setVidaMax define las vidas maximas del Sprite
	* @param vidaMax, el numero de vidas maximas.
	*/
    public void setVidaMax(int vidaMax){
    	this.vidaMax = vidaMax;
    }
    
    /**
	* Metodo iniciaVida inicializa las vidas del Sprite con la maxima posible
	*/
    public void iniciaVida(){
    	vida = vidaMax;
    }
    
     /**
	* Metodo getMaxVida regresa las vidas del Sprite
	* @return int, el numero de vidas del objeto
	*/
    public int getMaxVida(){
    	return vidaMax;
    }
}

