
import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.util.Vector;
import java.util.Enumeration;
import java.awt.*;

public class TileMapRenderer {

    public static final int TILE_SIZE = 64;
    private Image background;


	/**
	* Metodo int <I>pixelsToTiles()</I> <P>
	* Llama al metodo int pixelsToTiles con parametro Integer
	* @param pixels es el  <code>float</code> del pixel a convertir.
	*/
    public static int pixelsToTiles(float pixels) {
        return pixelsToTiles(Math.round(pixels));
    }


	/**
	* Metodo int <I>pixelsToTiles()</I> <P>
	* Devuelve el numero de Tile al que corresponde el pixel
	* @param pixels es el  <code>float</code> del pixel a convertir.
	*/
    public static int pixelsToTiles(int pixels) {
        return (int)Math.floor((float)pixels / TILE_SIZE);
    }


	/**
	* Metodo int <I>tilesToPixels()</I> <P>
	* Devuelve el pixel al que corresponde el numero de Tile
	* @param numTiles es el  <code>int</code> del Tile a convertir.
	*/
    public static int tilesToPixels(int numTiles) {
        return numTiles * TILE_SIZE;
    }


	/**
	* Metodo  <I>setBackground()</I> <P>
	* Cambia la imagen de fondo
	* @param background es el  <code>Image</code> de la imagen de fondo.
	*/
    public void setBackground(Image background) {
        this.background = background;
    }


	/** 
	* Metodo  <I>draw()</I> <P>
	* Pinta el mapa, los Sprites, el Player, las Balas y los Items
	* @param g es el  <code>Graphics2D</code> objeto grafico.
	* @param map es el  <code>TileMap</code>.
	* @param screenWidth es el  <code>int</code> del ancho del mapa.
	* @param screenHeight es el  <code>int</code> del alto del mapa.
	*/
    @SuppressWarnings("unchecked")
	public void draw(Graphics2D g, TileMap map, int screenWidth, int screenHeight)
    {
        Sprite player = map.getPlayer();										//Consigue el objeto Jugador.
		Vector<Sprite> otrasImagenes = map.getSprites();						//Consigue el vector de enemigos.

        int mapWidth = tilesToPixels(map.getWidth());							//Pasa el ancho del mapa de Tiles a pixeles.
		int mapHeight = tilesToPixels(map.getHeight());							//Pasa la altura del mapa de Tiles a pixeles.
		
        int offsetX = screenWidth / 2 - Math.round(player.getPosX()) - TILE_SIZE; //Obtiene el desplazamiento del mapa basado en la posicion del jugador
        offsetX = Math.min(offsetX, 0);											  //Decide si desplazar o no el mapa, al estar en la orilla izquierda.
        offsetX = Math.max(offsetX, screenWidth - mapWidth);					  //Decide si desplazar o no el mapa, al estar en la orilla derecha.

		int offsetY= screenHeight/2 - Math.round(player.getPosY());				//Igual que lo anterior, pero para un desplazamiento vertical
		offsetY = Math.min(offsetY, 0);
		offsetY = Math.max(offsetY, screenHeight-mapHeight); 
			
        if (background == null || screenHeight > background.getHeight(null))	//En caso de no tener un background, se pinta de negro el fondo
        {	
            g.setColor(Color.black);
            g.fillRect(0, 0, screenWidth, screenHeight);
        }

        // Se pinta el background, usando el desplazamiento obtenido previamente. (Brackeen)
        if (background != null) {
            int x = offsetX * (screenWidth - background.getWidth(null)) / (screenWidth - mapWidth);
            int y = screenHeight - background.getHeight(null);
				y = offsetY * (screenHeight - background.getHeight(null)) / (screenHeight - mapHeight);
            g.drawImage(background, x, y, null);
        }

        // Se dibujan los Tiles visibles
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(screenWidth) + 1;
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = firstTileX; x <= lastTileX; x++) {
                Image image = map.getTile(x, y);
                if (image != null) {
                    g.drawImage(image,
                        tilesToPixels(x) + offsetX,
                        tilesToPixels(y) + offsetY,
                        null);
                }
            }
        }
        
        //(Brackeen)
		for (int x = 0; x < otrasImagenes.size(); x++){ 								// Se recorre el vector de enemigos
			Sprite sprite = (Sprite)otrasImagenes.get(x);							
			if (!sprite.volteaADerecha()) {												// Se ve si el enemigo voltea a la derecha
	     		g.drawImage(getFlippedImage(sprite.getImageFromAnimation(),g), 			// En caso afirmativo, consigue una imagen girada
	     			Math.round(sprite.getPosX()) + offsetX, 
	     				Math.round(sprite.getPosY()) + offsetY,null);
			} else {																	// En caso contrario, deja la imagen como esta y la pinta 
				g.drawImage(sprite.getImageFromAnimation(), Math.round(sprite.getPosX()) + offsetX, 
					Math.round(sprite.getPosY()) + offsetY,null);
			}
			g.setColor(Color.WHITE);
			
		}	
		//Se pinta al jugador
		if (((Player)player).getLado()) {
        	g.drawImage(getFlippedImage(player.getImagenI(), g), Math.round(player.getPosX()) + offsetX, // Si esta volteando, gira la imagen
        		Math.round(player.getPosY()) + offsetY, null);
		} else {
      		g.drawImage(player.getImagenI(), Math.round(player.getPosX()) + offsetX, 					// Se pinta la imagen normal, sino esta girando
      			Math.round(player.getPosY()) + offsetY, null);
		}
		
		Vector<Explosiones> explosiones = map.getExplosiones();			// Consigue las explosiones y las mete a un vector
		//Se crea una enumeracion del vector explosiones
		Enumeration e = explosiones.elements(); 
		Explosiones exp;	 
		while (e.hasMoreElements()) {						// Se recorre el vector de PowerUps
			exp = (Explosiones) e.nextElement();
			g.drawImage(exp.getImagenI(), Math.round(exp.getPosX()) + offsetX - 20, 
    		 		Math.round(exp.getPosY()) + offsetY - 40, null);
		}
		
		
		// Seccion en donde se pintan las balas
		Vector <Bala> balas = map.getBalas();				// Se consigue el vector de balas
		e = balas.elements();
		Bala b;
		while (e.hasMoreElements()) {						// Se recorre el vector de balas
			b = (Bala) e.nextElement();
			if (b.getVelocidadX() < 0) {						// Si la bala va a la izquierda, se pinta la imagen invertida de la bala
    		 	g.drawImage(getFlippedImage(b.getImagenI(), g), Math.round(b.getPosX()) + offsetX, 
    		 		Math.round(b.getPosY()) + offsetY, null);
			} else {										// Si no, se pinta la imagen normal de la bala
				g.drawImage(b.getImagenI(), Math.round(b.getPosX()) + offsetX, 
					Math.round(b.getPosY()) + offsetY, null);	
			}	
		}
		
		// Se pintan las balas de los enemigos
		balas = map.getBalasEnemigos();						//Se consigue el vector de balasde enemigos
		e = balas.elements();
		while (e.hasMoreElements()) {						// Se recorre el vector de balas
			b = (Bala) e.nextElement();
			if (b.getVelocidadX()<0) {						// Si la bala va a la izquierda, se consigue una imagen invertida
    		 	g.drawImage(getFlippedImage(b.getImagenI(), g), Math.round(b.getPosX()) + offsetX,
    		 		Math.round(b.getPosY()) + offsetY, null);
			} else {
				g.drawImage(b.getImagenI(), Math.round(b.getPosX()) + offsetX, // Se pinta la imagen normal
					Math.round(b.getPosY()) + offsetY, null);			
			}
		}		
		
		
		///PARA DESPLEGAR BARRA DE VIDA DEL PLAYER
		int vidas = ((Player)player).getVida();					// Se Consigue la vida del personaje.
		//Estetica (Colores, estilo de letra, posicion, etc.)
		g.setColor(Color.RED);
		g.fillRect(160, 545, vidas*10, 25);
		g.setFont(new Font("Arial", Font.BOLD, 30)); 
		g.setColor(Color.WHITE);
		g.drawString("VIDA", 50, 570);
		g.drawString("Score: "+map.getPuntos(),50,50);
				
    }

	/**
	* Metodo  <I>getFlippedImage()</I> <P>
	* Gira la imagen horizontalmente
	* @param image es el  <code>Image</code> de la imagen a voltear.
	* @param g es <code>Graphics</code> a pintar
	*/    
    public Image getFlippedImage(Image image,Graphics g) {
		// Crea un objeto de Transform
	    AffineTransform transform = new AffineTransform();
		transform.scale(-1, 1); //Hace que la transformacion sea horizontal.
	    transform.translate((-2) * image.getWidth(null) / 2,(0) * image.getHeight(null) / 2); //Gira la imagen, tomando como eje la mitad de la imagen
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); //Crea un objeto de Ambiente Grafico
  		GraphicsDevice gs = ge.getDefaultScreenDevice();	     //Consigue el Dispositivo grafico
		  	GraphicsConfiguration gc = gs.getDefaultConfiguration(); //Consigue la configuracion actual
	        Image newImage = gc.createCompatibleImage( //Crea una nueva imagen, manteniendo la misma altura, ancho y transparencias del original.
	            image.getWidth(null),
	            image.getHeight(null),
	            Transparency.BITMASK);
	
	    // Se dibuja la imagen transformada
		Graphics2D g2 = (Graphics2D)newImage.getGraphics();
		g2.drawImage(image, transform, null);
		g2.dispose();
		
	    return newImage; //Se regresa la imagen invertida
  }
}
