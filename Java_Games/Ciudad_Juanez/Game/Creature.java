
import java.awt.Image;


	
/**
  * Clase Creatura, Una creatura es un Sprite que es afectado por la gravedad
  * y puede ser destruido. Tiene 2 animaciones movimiento a la izquierda 
  * y movimiento a la derecha.
  */  

public abstract class Creature extends Sprite {

    //Variables usadas para saber el tiempo que pasara de el estado muriendo al estado muerto
    //Estos estados sirven para determinar cuando sera eliminado la Creatura
    private static final int DIE_TIME = 1000;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_DYING = 1;
    public static final int STATE_DEAD = 2;
   
    private int state; //estados posibles de las creatras
    private long stateTime; //El tiempo entre estados.

	/**
	 * Metodo constructor de Creature usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 */
    public Creature(int posX, int posY, Image imagen)
    {
        super(posX,posY,imagen);
        iniciaVida();
    }
    
    /**
	 * Metodo constructor de Creature usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 * @param int, seran las vidas maximas de la creatura.
	 */
    public Creature(int posX, int posY, Image imagen, int vidaMax)
    {
        super(posX,posY,imagen);
        setVidaMax(vidaMax);
        iniciaVida();
    }

	/**
	 * Metodo constructor de Creature usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param anim es la <code>Animation</code> del objeto.
	 */
	public Creature(int posX, int posY, Animation anim){
		super(posX, posY, anim);
	}
	

	/**
	* Metodo <I>getMaxSpeed</I> regresa la maxima velocidad posible.
	* @return 0, esto significa que el movimiento no tendra ningun tipo de restriccion
	* al momento de moverse
	*/
    public float getMaxSpeed() {
        return 0;
    }

	/**
	* Metodo <I>wakeUp</I> 
	* Despierta a la criatura (usado para que se despierten cuando 
	* el personaje este en pantalla y que antes de eso no se muevan)
	*/
   public void wakeUp() {
        if (getState() == STATE_NORMAL && getVelocidadX() == 0) 
            setVelocidadX(-getMaxSpeed());
    }


	/**
	* Metodo <I>getState</I> que devuelve el Estado de la  Creature.<P>
	* @return state, regresa el estado de la creatura.
	*/
    public int getState() {
        return state;
    }


	/**
	* Metodo <I>setState</I> que cambia el Estado de la  Creature.<P>
	*/
    public void setState(int state) {
        if (this.state != state) //Si el estado a cambiar es diferente al estado realiza el proceso
        {
            this.state = state; //asignacion del estado
            stateTime = 0; //el tiempo que ha estado, como se ha cambiado se inicializa en 0
	            if (state == STATE_DYING) 
	            {
	                setVelocidadX(0); //detiene el movimiento de la creatura
	                setVelocidadY(0); //detiene el movimiento de la creatura
	            }
        }
    }


	/**
	* Metodo <I>isAlive</I> que devuelve un boolean para indicar
	* @return boolean, si la creatura no ha sido destruido regresa true,
	* si la creatura creature ha sido destruida regresa false 	
	*/
    public boolean isAlive() {
        return (state == STATE_NORMAL);
    }


	/**
	* Metodo <I>isFlying</I> que devuelve un boolean para indicar
	* si Creature esta volando o no.
	* @return false si la creatura no esta volando, true si esta volando
	*/
    public boolean isFlying() {
        return false;
    }


	/**
	* Metodo <I>collideHorizontal</I> si la creatura choca con alguna pared
	* este metodo se encarga de voltearlo.
	*/
    public void collideHorizontal() {
        setVelocidadX(-getVelocidadX());
    }


	/**
	* Metodo <I>collideVertical</I> que frena verticalmente a Creature.<P>
	* Si choca con un techo la velocidad en Y se vuelve cero y empieza su
	* caida libre.
	*/
    public void collideVertical() {
        setVelocidadY(0);
    }


	/**
	* Metodo <I>update</I> que actualiza el estado de Creature.
	* @return long, el tiempo en que durar entre estados de muriendo
	* y muerto
	*/
    public void update(long elapsedTime) {
        stateTime += elapsedTime; //El tiempo que ha transcurrido en este estado mas un diferencial de tiempo
        if (state == STATE_DYING && stateTime >= DIE_TIME) 
        {
            setState(STATE_DEAD);
        }
    }
	
}
