/**
 * Clase ObjetosHerencia
 *
 * @author Jorge Saldivar
 * @version 1.00 2008/6/13
 */
import java.awt.Image;

public class Cometas extends Objeto {

	/**
	 * Metodo constructor que hereda los atributos de la clase <code>Objeto</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto cometa.
	 * @param posY es el <code>posiscion en y</code> del objeto cometa.
	 * @param image es la <code>imagen</code> del objeto cometa.
	 */
	public Cometas(int posX,int posY,Image image){
		super(posX,posY,image);
	}
}
