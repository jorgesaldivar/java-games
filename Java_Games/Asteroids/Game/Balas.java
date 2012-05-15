/**
 * Clase ObjetosHerencia
 *
 * @author Jorge Saldivar
 * @version 1.00 2008/6/13
 */
import java.awt.Image;

public class Balas extends ObjetoMasBoleana {

	/**
	 * Metodo constructor que hereda los atributos de la clase <code>ObjetoMasBoleana</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto Bala.
	 * @param posY es el <code>posiscion en y</code> del objeto Bala.
	 * @param image es la <code>imagen</code> del objeto Bala.
	 * @param boleana y boleana2 es la <code>represetacionBoleana</code> del objeto Bala.
	 */
	

	public Balas(int posX,int posY,Image image,boolean boleana,boolean boleana2){
		super(posX,posY,image,boleana,boleana2);
	}
	

}
