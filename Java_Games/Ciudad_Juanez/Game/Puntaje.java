/**
  * Clase Puntaje, es la clase encargada de contabilizar los puntos
  * logrados cada vez que se destruye un enemigo, ademas que es la
  * que crea el archivo de texto y lo maneja,
  */  

public class Puntaje implements Comparable <Puntaje>{

	private String nombre; //Nombre del jugador
	private int puntaje;  //Puntaje que a obtenido

	/**
	 * Constructor vacio con darle valores iniciales al momento de
	 * crear el objeto Puntaje
	 */
	public Puntaje() {
		nombre = "";
		puntaje = 0;
	}

	/**
	 * Metodo constructor usado para crear el objeto
	 * @param nombre es el <code>nombre</code> del objeto.
	 * @param puntaje es el <code>puntaje</code> del objeto.
	 */
	public Puntaje(String nombre, int puntaje) {
		this.nombre = nombre;
		this.puntaje = puntaje;
	}

	/**
	 * Metodo modificador usado para cambiar el nombre del objeto.
	 * @param nombre es el <code>nombre</code> del objeto.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Metodo de acceso que regresa el nombre del objeto 
	 * @return nombre es el <code>nombre</code> del objeto.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Metodo modificador usado para cambiar el puntaje del objeto
	 * @param puntaje es el <code>puntaje</code> del objeto.
	 */
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	/**
	 * Metodo de acceso que regresa el puntaje del objeto 
	 * @return puntaje es el <code>puntaje</code> del objeto.
	 */
	public int getPuntaje() {
		return puntaje;
	}

	/**
	 * Metodo que regresa el objeto en formato String 
	 * @return un objeto de la clase <code>String</code>.
	 */
	public String toString(){
		return "" + getPuntaje() + "," + getNombre();
	}

	/**
	 * Metodo que comapra dos puntajes, para ordenar el vector de Puntajes 
	 * @return un entero.
	 */
    public int compareTo(Puntaje o) {
        return o.getPuntaje() - this.getPuntaje() ;
    }	
}