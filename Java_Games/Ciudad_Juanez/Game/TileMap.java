import java.awt.Image;
import java.util.Vector;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class TileMap {

    private Image[][] tiles;

    private Player player;						// Jugador
	
	private Vector<Sprite> sprites;				// Enemigos
	private ArrayList<Image> lista;				// Lista de imagenes a cargar
	private Vector <Bala> balas;				// Vector de Balas del Jugador
	private Vector <Bala> balasEnemigos;		// Vector Balas de Enemigos
   	private Vector <Explosiones> explosiones; 	// Vector de explosiones
   	private int puntos;
   /**
	* Constructor <I>TileMap</I> para crear un objeto de tipo TileMap.<P>
	*/
	public TileMap(int width, int height) {
        tiles = new Image[width][height]; 		// Lista en la que guarda las imagenes de los Tiles que se generen
    	new Vector <Sprite>();
		balas = new Vector<Bala>();				// Vector de Balas
		balasEnemigos = new Vector <Bala>(); 	// Vector donde iran las balas que disparen los enemigos
    	sprites = new Vector<Sprite>(); 		// Lista encadenada de Sprites (enemigos u objetos)
		explosiones = new Vector<Explosiones>();// Vector de explosiones
		puntos = 0;
       	cargaTiles(); //Carga al ArrayList lista las imagenes A,B,C,etc de la carpeta Imagenes.    	
    }

	/**
	* Metodo <I>getWidth()</I> <P>
	* Devuelve el numero de Tiles a lo ancho.
	*/
    public int getWidth() {
        return tiles.length;
    }
    
    public int getPuntos(){
    	return puntos;
    }
    
    public void setPuntos(int P){
    	this.puntos = P;
    }

	/**
	* Metodo <I>getHeight()</I> <P>
	* Devuelve el numero de Tiles a lo largo.
	*/
    public int getHeight() {
        return tiles[0].length;
    }
    
	/**
	* Metodo <I>addExplosion()</I> <P>
	* Agrega un objeto Explosion al vector PowerUps.
	* @param Explosion exp es el <code>objeto Explosion</code> a agregar.	
	*/
	public void addExplosion(Explosiones exp){
		explosiones.add(exp);				// Agrega un PowerUp al vector
	}

	
	/**
	* Metodo <I>removeExplosion()</I> <P>
	* Quita un objeto Explosion al vector explosions.
	* @param Explosiones exp es el <code>objeto Explosion</code> a quitar.	
	*/
	public void removeExplosion(Explosiones exp){
		explosiones.removeElement(exp);		// Quita un explosion del vector
	}

 	/**
	* Metodo <I>getExplosiones()</I> <P>
	* regresa el vector Explosions.
	*/
	public Vector <Explosiones> getExplosiones(){
    	return explosiones;					// Regresa el vector de Explosiones
    }


	/**
	* Metodo <I>getTile()</I> <P>
	* Devuelve la Imagen que corresponde al Tile en cierta posicion
	* @param int x es el <code>entero</code> de la posicion en X del Tile.
	* @param int y es el <code>entero</code> de la posicion en Y del Tile.
	*/
    public Image getTile(int x, int y) {
        if (x < 0 || x >= getWidth() ||
            y < 0 || y >= getHeight())
        {
            return null;
        }
        else {
            return tiles[x][y];
        }
    }


 	/**
	* Metodo <I>setTile()</I> <P>
	* Coloca un Tile en la posicion especificada.
	* @param int x es el <code>entero</code> de la posicion en X del Tile.
	* @param int y es el <code>entero</code> de la posicion en Y del Tile.
	* @param Image tile es el <code>Image</code> del tile.	
	*/
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;					// Se coloca un tile en la posicion dada
    }
    
 	/**
	* Metodo <I>addBala()</I> <P>
	* Agrega un objeto Bala al vector balas.
	* @param Bala b es el <code>objeto Bala</code> a agregar.	
	*/
	public void addBala(Bala b){
		balas.add(b);						// Agrega una bala al vector
	}

 	/**
	* Metodo <I>removeBala()</I> <P>
	* Quita un objeto Bala al vector balas.
	* @param Bala b es el <code>objeto Bala</code> a agregar.	
	*/
	public void removeBala(Bala b){
		balas.removeElement(b);				// Quita una bala del vector
	}

 	/**
	* Metodo <I>getBalas()</I> <P>
	* regresa el vector balas.
	*/
	public Vector <Bala> getBalas(){
    	return balas;						// Regresa el vector de balas
    }
	
	/**
	* Metodo <I>getPlayer()</I> <P>
	* Regresa el objeto Player correspondiente al jugador
	*/
    public Player getPlayer() {
        return player;						// Regresa el objeto Jugador
    }

	/**
	* Metodo <I>setPlayer()</I> <P>
	* Asigna al mapa el objeto Player recibido como parametro
	* @param Player player es el <code>Player</code> a asignar.
	*/
    public void setPlayer(Player player) {
        this.player = player;
    }

//////////////////////////SECCION BALAS DE ENEMIGOS//////////////////

 	/**
	* Metodo <I>addBalaenemigos()</I> <P>
	* Agrega un objeto Bala al vector balas de Enemigos.
	* @param Bala b es el <code>objeto Bala</code> a agregar.	
	*/ 
	public void addBalaEnemigos(Bala b){
		balasEnemigos.add(b);
	}

  	/**
	* Metodo <I>removeBalaEnemigos()</I> <P>
	* Quita un objeto Bala al vector balas de enemigos.
	* @param Bala b es el <code>objeto Bala</code> a agregar.	
	*/
	public void removeBalaEnemigos(Bala b){
		balasEnemigos.removeElement(b);
	}

 	/**
	* Metodo <I>getBalasJefs()</I> <P>
	* regresa el vector balas de Enemigos.
	*/
	public Vector<Bala> getBalasEnemigos(){
    	return balasEnemigos;
    }

//////////////////////////////FIN DE SECCION BALAS DE ENEMIGOS//////////////////////
 	/**
	* Metodo <I>addSprite()</I> <P>
	* Coloca un Sprite en la posicion especificada.
	* @param int x es el <code>entero</code> de la posicion en X del Sprite.
	* @param int y es el <code>entero</code> de la posicion en Y del Sprite.
	* @param Image tile es el <code>Image</code> del Sprite.	
	* @param TileMap map es el <code>TileMap</code> actual.	
	*/
    private void addSprite(TileMap map, Sprite sprite, int tileX, int tileY){
		if (sprite != null) {
			Image imagen = sprite.getImageFromAnimation();
			// Centra al sprite
			sprite.setPosX(TileMapRenderer.tilesToPixels(tileX) + (TileMapRenderer.tilesToPixels(1) - imagen.getWidth(null)) / 2);
	
			// Pone al sprite en el piso.
			sprite.setPosY(TileMapRenderer.tilesToPixels(tileY + 1) - imagen.getHeight(null));
	
			//Asigna una velocidad al sprite
			sprite.setVelocidadX(0.05f);
			//ia se termino el bloque a quitar
			map.addSprite(sprite); //Se agrega el sprite al mapa
		}		
	}
	
 	/**
	* Metodo <I>addSprite()</I> <P>
	* Agrega un objeto Sprite al vector sprites.
	* @param Sprite sprite es el <code> Sprite</code> a agregar.	
	*/
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}


 	/**
	* Metodo <I>removeSprite()</I> <P>
	* Remueve un objeto Sprite del vector sprites.
	* @param Sprite sprite es el <code> Sprite</code> a quitar.	
	*/
    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }


	/**
	* Metodo <I>getSprites()</I> <P>
	* Regresa un vector de los Sprites del mapa
	*/
    public Vector<Sprite> getSprites() {
        return sprites;
    }
	

 	/**
	* Metodo <I>loadMap()</I> <P>
	* Con base en un archivo de texto asigna tiles a una matriz
	* Tambien se encarga de asignar la posicion de inicio de los enemigos
	* y items en el mapa
	* @param nivel, entero que indica el numero de mapa correspondiente al nivel
	*/      
	public TileMap loadMap(int nivel) throws IOException {
	    ArrayList<String> lines = new ArrayList<String>();
	    int width = 0;
	    int height = 0;
		
		// Se carga el archivo de texto
	    BufferedReader reader = new BufferedReader(
	        new FileReader("Mundos/Mundo"+nivel+".txt"));
	        
	    while (true) {
	    	// Se lee el archivo de texto
	        String line = reader.readLine();
	        // no quedan mas lineas
	        if (line == null) {
	            reader.close();
	            break;
	        }
	
	        // Cuenta cada linea que no empieze con # (comentarios)
	        if (!line.startsWith("#")) {
	            lines.add(line); //guarda las lineas en un ArrayList
	            width = Math.max(width, line.length()); //Saca cuanto midio la linea mas larga
	        }
	    }
	    height = lines.size(); //Saca cuanto fueron en total las lineas
	    TileMap newMap = new TileMap(width, height); //Genera un nuevo Mapa con el ancho y alto obtenidos

	    for (int y = 0; y < height; y++) {
	        String line = (String)lines.get(y); //Va leyendo linea por linea del ArrayList lineas
	        for (int x=0; x<line.length(); x++) { 
				char ch = line.charAt(x); //Lee caracter por caracter
				
                int tile = ch - 'A'; //Asigna a tile el valor en entero de la Letra. Ejemplo A = 0, B=1, C=2,etc, etc.
                
                
                if (tile >= 0 && tile < lista.size()) {
                    newMap.setTile(x, y, (Image)lista.get(tile)); //Agrega un Tile al mapa con el numero de columna y linea y la imagen obtenida de la lista formada por cargaTiles
                } 
                
                if(ch == '+'){	// Agrega un enemigo al mapa
					addSprite(newMap, new Enemigo(0, 0,
						Toolkit.getDefaultToolkit().getImage("images/IMG_HECTOR/Enemigo/Camina/alien1camina1.png")),x,y); 
				}
				
  
	        }
	    }
	
		//Agrega al jugador al mapa
 		player = new Player(0, 0, Toolkit.getDefaultToolkit().getImage("images/corre1.png"));
        player.setPosX(TileMapRenderer.tilesToPixels(3));
        player.setPosY(80);
        newMap.setPlayer(player);		
        
        // regresa el objeto de Mapa	
	    return newMap;
	}

 	/**
	* Metodo <I>cargaTiles()</I> <P>
	* Lee las imagenes a usarse por los tiles.
	*/	
    public void cargaTiles() {
        // Busca las imagenes de tiles, basandose en orden alfabetico
        lista = new ArrayList<Image>();
        char ch = 'A';
        while (true) {
            String name = ch + ".png";
            File file = new File("images/" + name);

            if (!file.exists()) {
                break;
            }
            //Si encuentra un tile, lo agrega al listado de imagenes
            lista.add(loadImage(name));
            ch++;
        }
    }

    /**
        Carga las imagenes
    */
    public Image loadImage(String name) {
        String filename = "images/" + name;
        return new ImageIcon(filename).getImage();
    }
}

