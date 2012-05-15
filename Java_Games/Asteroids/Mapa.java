/**
 * Clase ObjetosHerencia
 *
 * @author Jorge Saldivar
 * @version 1.00 2008/6/13
 */
import java.awt.Image;

public class Mapa extends Objeto {

	/**
	 * Metodo constructor que hereda los atributos de la clase <code>Objeto</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto mapa.
	 * @param posY es el <code>posiscion en y</code> del objeto mapa.
	 * @param image es la <code>imagen</code> del objeto mapa.
	 */
	public Mapa(int posX,int posY,Image image){
		super(posX,posY,image);
	}
}
