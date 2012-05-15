
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Toolkit;

/**
* Clase Enemigo, es la clase encargada de darle los aspectos
* de inteligencia a los enemigos y sus comportamientos
*/  
public class Enemigo extends Creature{
	
		//Animaciones de los enemigos: Movimientos
		private Animation caminaDerecha = new Animation();
		
		//Animaciones de los enemigos: Ataque
		private Animation atacaDerecha = new Animation();
		
		//Animacion del enemigo: StandBy
		private Animation paradoDerecha = new Animation();

		//El estado actual en que se encuentra el enemigo
		private String estadoCaminaDerecha = "caminaDerecha";
		private String estadoCaminaIzquierda = "caminaIzquierda";
		
		Sprite contrincante; //El jugador sera considerado el contrincante, util para saber cuando atacara el enemigo
		
		private int count;
		private float oldX; //Velocidad antigua, siempre se esta cambiando cuando hay algun cambio de estado
		private long tiempoDeAtaque; //la duracion del ataque
		private long deltaEntreAtaques; //Tiempo entre ataques
		private long momentoDeInicioDeAtaque; //cuando empieza a atacar despues de haber detectado al jugador
	
	/**
	* Constructor <I>Enemigo</I> para crear un objeto de tipo Enemigo.
	* @param x, posicion int del objeto en el eje X
	* @param y, posicion int del objeto en el eje Y
	* @param imagen, Imagen con la que se inicializa el Enemigo
	*/
	public Enemigo(int x, int y, Image imagen){
		super(x, y, imagen); //metodo heredad del padre
		setVelocidadX(0.05f); //hace que la velocidad sea constante
		deltaEntreAtaques = 500; //tiempo entre ataques
		createAnimation(); //creacion de la animacion de ataque
		count = 0; //el contador se inicializa en 0
		setLado(1);// + positivo voltea a derecha, - negativo voltea a izquierda
		setState(estadoCaminaDerecha); //camina hacia el lado del jugador, por default a la derecha

	}


	/**
	* Metod void <I>revisarCambioDeEstado</I> que revisa el estado y/o cambia el estado del Enemigo.
	* @param identificador, String que compara cualquier estado con el estado actual del Enemigo
	* @param newX, float que asigna una nueva posicion para el Enemigo
	*/	
	public void revisarCambioDeEstado(String identificador, float newX)
		{
		if(identificador == Sprite.PROXIMA_CAIDA_LIBRE || identificador == Sprite.CHOQUE_CON_PARED)
			{
				float vel = dx*(-1);
				setVelocidadX(vel);
				setLado((int)(100*vel));
				if(getCurrState() == estadoCaminaDerecha && newX - TileMapRenderer.TILE_SIZE > 0)
					setState(estadoCaminaIzquierda);
				else{
					setState(estadoCaminaDerecha);	//Cambia el estado a caminar a la derecha
					setAnimation(caminaDerecha);	//Cambia la orientacion de las animaciones
					}
			}
		else if(identificador == Sprite.ESTA_CERCA)	//Dependiendo si el jugador esta cerca:
			{
				momentoDeInicioDeAtaque = System.currentTimeMillis();
				setAnimation(paradoDerecha);
				setLado((int)(100*dx));
				oldX = dx; //oldX en este caso corresponde a la velocidad
				setState(Sprite.ESTA_CERCA);
				dx=0;
			}
			//Sigue caminando si estaba en estado de atacar pero ya no esta cerca el jugador
			else if(identificador == Sprite.NO_ESTA_CERCA && getCurrState() != estadoCaminaDerecha && getCurrState() != estadoCaminaIzquierda)
			{
				setAnimation(caminaDerecha);
				if(volteaADerecha())
					dx=Math.abs(oldX);
				else
					dx=Math.abs(oldX) * -1;
				if(dx>0)
				{
					setState(estadoCaminaDerecha);
				}
				else if(dx<0){
					setState(estadoCaminaIzquierda);
				}
			}
		}
	
	/**
	* Metodo <I>atacar</I> hace que el Enemigo ataque dependiendo si esta cerca el jugador
	*/
	public boolean  atacar(){
		if(getCurrState() == ESTA_CERCA){
			if(System.currentTimeMillis() - momentoDeInicioDeAtaque > (deltaEntreAtaques + tiempoDeAtaque)){
				setAnimation(paradoDerecha);
				count = 0;
				momentoDeInicioDeAtaque = System.currentTimeMillis();
			}
			else if(System.currentTimeMillis() - momentoDeInicioDeAtaque > deltaEntreAtaques){
				count++;
				setAnimation(atacaDerecha);
				if(count < 2)
					return true;
			}
		}
		return false;
	}
	
	/**
	* Metodo <I>persigueA</I> 
	* Determina si hay un jugador cerca y le asigna al enemigo atacar al jugador
	* @param contrincante, Sprite al cual debe seguir el Enemigo
	*/
	public void persigueA(Sprite contrincante){
		this.contrincante = contrincante;
	}
	
	
	/**
	* Metodo Tipo Bala <I>dibujaBala</I>
	* Encargado de obtener la imagen correspondiente y crear la bala en su posicion inicial
	* @param derecha, booleano que indica true si esta orientado a la derecha el Enemigo
	* @param mapa, TileMap al cual se agregan las balas
	*/
	public Bala dibujaBala(boolean derecha, TileMap mapa){
		int lado = (derecha)? 1:-1;
		Bala bala = new Bala((int)getPosX() + 50*lado, (int)(getPosY() + getAlto()/4), 
		Toolkit.getDefaultToolkit().getImage("images/IMG_HECTOR/Enemigo/Ataca/Efectoataca/efecto08.png"), 
		lado*0.5f);
		
		bala.setTipoDisparo(4);
		return bala;	
	}
	
	/**
	* Metodo <I>createAnimation</I> para cargar las imagenes de animacion del enemigo.<P>
	*/		
	public void createAnimation(){
		
		//Imagenes del enemigo moviendose
		Image caminaD1 = new ImageIcon("images/IMG_HECTOR/Enemigo/Camina/alien1camina1.png").getImage();
		Image caminaD2 = new ImageIcon("images/IMG_HECTOR/Enemigo/Camina/alien1camina2.png").getImage();
		Image caminaD3 = new ImageIcon("images/IMG_HECTOR/Enemigo/Camina/alien1camina3.png").getImage();
		Image caminaD4 = new ImageIcon("images/IMG_HECTOR/Enemigo/Camina/alien1camina4.png").getImage();
		Image caminaD5 = new ImageIcon("images/IMG_HECTOR/Enemigo/Camina/alien1camina5.png").getImage();
		Image caminaD6 = new ImageIcon("images/IMG_HECTOR/Enemigo/Camina/alien1camina6.png").getImage();
		Image caminaD7 = new ImageIcon("images/IMG_HECTOR/Enemigo/Camina/alien1camina7.png").getImage();
		Image caminaD8 = new ImageIcon("images/IMG_HECTOR/Enemigo/Camina/alien1camina8.png").getImage();

		//Animacion de los enemigos moviendose
		caminaDerecha.addFrame(caminaD1, 120);
		caminaDerecha.addFrame(caminaD2, 120);
		caminaDerecha.addFrame(caminaD3, 120);
		caminaDerecha.addFrame(caminaD4, 120);
		caminaDerecha.addFrame(caminaD5, 120);
		caminaDerecha.addFrame(caminaD6, 120);
		caminaDerecha.addFrame(caminaD7, 120);
		caminaDerecha.addFrame(caminaD8, 120);

		
		//Imagenes de los enemigos atacando
		Image alienA1 = new ImageIcon("images/IMG_HECTOR/Enemigo/Ataca/alien1ataca1.png").getImage();
		Image alienA2 = new ImageIcon("images/IMG_HECTOR/Enemigo/Ataca/alien1ataca2.png").getImage();
		Image alienA3 = new ImageIcon("images/IMG_HECTOR/Enemigo/Ataca/alien1ataca3.png").getImage();
		Image alienA4 = new ImageIcon("images/IMG_HECTOR/Enemigo/Ataca/alien1ataca4.png").getImage();
		Image alienA5 = new ImageIcon("images/IMG_HECTOR/Enemigo/Ataca/alien1ataca5.png").getImage();
		Image alienA6 = new ImageIcon("images/IMG_HECTOR/Enemigo/Ataca/alien1ataca6.png").getImage();

		//Animacion del enemigo atacando
		atacaDerecha.addFrame(alienA1, 120);
		atacaDerecha.addFrame(alienA2, 120);
		atacaDerecha.addFrame(alienA3, 120);
		atacaDerecha.addFrame(alienA4, 120);
		atacaDerecha.addFrame(alienA5, 120);
		atacaDerecha.addFrame(alienA6, 120);
		
		//Imagen del enemigo en StandBy
		Image paradoD = new ImageIcon("images/IMG_HECTOR/Enemigo/alien1parado.png").getImage();
		
		tiempoDeAtaque = 120*6; //tiempoDeAtaque = sumatoria -> tiempo de cada frame de ataque
		paradoDerecha.addFrame(paradoD, 1000);
		setAnimation(caminaDerecha);
	}
}
