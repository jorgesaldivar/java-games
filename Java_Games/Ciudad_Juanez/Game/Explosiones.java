
import java.awt.Image;
import java.awt.Toolkit;

public class Explosiones extends Sprite {

	//Animacion de la explosion, tiempo de duracion en la pantalla desde que aparece
	private Animation animExp;
	long tiempoCreacion;
	int tiempoAnim = 350;
	int tiempoTotal =0;
		
	/**
	 * Metodo constructor usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 */
	public Explosiones(int posX, int posY, Image image) {
		super(posX,posY,image);	
		cargaImagenes();
		tiempoCreacion = System.currentTimeMillis();
	}

	/**
	* Metodo <I>updateAnim()</I> <P>
	* cambia la animacion de la explosion.
	* Si ha pasado cierto tiempo desde que comenzo la animacion,
	* se cambia de animacion y se cambia el Estado.
	*/
	public void updateAnim(long elapsedTime){
		tiempoTotal += elapsedTime;
		if( tiempoTotal < tiempoAnim)
			animExp.update(elapsedTime);
		else
			animExp.start();
		setImageIcon(animExp.getImage());		
		
	}
	
	/**
	* Metodo Boleano <I>Termina()</I> <P>
	*@return Booleano si la explosion debe de quitarse o no de la pantalla
	*/
	public boolean termina(){
		if(tiempoTotal > tiempoAnim){
			return true;
		}
		return false;
	}
		
	/**
	* Metodo <I>cargaImagenes()</I> <P>
	* Carga todas las imagenes de Explosiones que se usaran
	*/    	
    public void cargaImagenes(){
    	// Animacion de Explosion
    	Image a1 =  Toolkit.getDefaultToolkit().getImage("images/explosion1.png");
        Image a2 =  Toolkit.getDefaultToolkit().getImage("images/explosion2.png");           
        Image a3 =  Toolkit.getDefaultToolkit().getImage("images/explosion3.png");
    	Image a4 =  Toolkit.getDefaultToolkit().getImage("images/explosion4.png");
        Image a5 =  Toolkit.getDefaultToolkit().getImage("images/explosion5.png");           
        Image a6 =  Toolkit.getDefaultToolkit().getImage("images/explosion6.png");
        Image a7 =  Toolkit.getDefaultToolkit().getImage("images/explosion7.png");

	    // Animacion de las explosiones    	    
		animExp = new Animation();
		animExp.addFrame(a1, 50);
		animExp.addFrame(a2, 50);
		animExp.addFrame(a3, 50);
		animExp.addFrame(a4, 50);
		animExp.addFrame(a5, 50);
		animExp.addFrame(a6, 50);
		animExp.addFrame(a7, 50);
		

    }	
}
    
    