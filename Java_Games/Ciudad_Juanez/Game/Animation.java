import java.awt.Image;
import java.util.ArrayList;

/**
* Clase Animation, es la clase encargada de manejar las series
* de imagenes y el tiempo en que esas imagemenes seran mostradas
* para crear una animacion
*/

public class Animation {

    private ArrayList<AnimFrame> frames; //Estructura de datos en la que se guardan los frames
    private int currFrameIndex; //Apuntador para saber la posicion en la que se encuentra actualmente el frame
    private long animTime; //El tiempo de desplegado de las imagenes
    private long totalDuration; //La duracion total de la animacion


    
	/*
	* Constructor Animacion para crear un objeto animacion vacio.
	*/
    public Animation() {

        frames = new ArrayList<AnimFrame>();
        totalDuration = 0;
        start();
    }

 	/*
	* Metodo addFrame se encarga de agregar un nuevo frame a la estructura
	* de datos y dandole la imagen y el tiempo en que sera desplegada
	* @param image, imagen a usar como frame
	* @param duration, duracion total del frame
	*/
    public synchronized void addFrame(Image image,
        long duration)
    {
        totalDuration += duration; //Aumenta la duracion total de la animacion
        frames.add(new AnimFrame(image, totalDuration)); //agrega un nuevo frame con la imagen y el tiempo correspondiente
    }


    /*
	* Metodo start, inicia la animacion desde el primer frame
	*/
    public synchronized void start() {
        animTime = 0;
        currFrameIndex = 0;
    }

    /*
	* Metodo upadate Animacion para crear un objeto animacion vacio.
	*/
    public synchronized void update(long elapsedTime) {

        if (frames.size() > 1) //Verifica que la estructura de datos tenga mas de un frame que desplegar
        { 
            animTime += elapsedTime; //aumenta el tiempo de la animacion, esta controla que frames han sido desplegados

            if (animTime >= totalDuration) //checha si el la animacion ha llegado a su fin
            {
            	//Instrucciones encargadas de generar un rebobinado a la animacion
                animTime = animTime % totalDuration; 
                currFrameIndex = 0;
            }

            while (animTime > getFrame(currFrameIndex).endTime) {
                currFrameIndex++; //Aumenta el apuntador para cambiar de frame
            }
        }
    }


	/**
	* Metodo getImage,regresa la imagen actual de la animacion
	* @return la imagen actual de la animacion
    */
    public synchronized Image getImage() {
        if (frames.size() == 0) { //si la animacion no tiene frames regresa null
            return null;
        }
        else { // Si tiene frames regrasa la imagen
            return getFrame(currFrameIndex).image;
        }
    }

    /**
	* Metodo AnimFrame,regresa un frame especifico
	* @param int, funciona como el apuntador, es el
	* que permite la seleccion del frame
	* @return la imagen actual de la animacion
    */
    private AnimFrame getFrame(int i) {
        return (AnimFrame)frames.get(i);
    }

	/**
	* Constructor AnimFrame, crea un nuevo y vacio objeto AnimFrame
    */
    
    private class AnimFrame {
		//Caracteristicas del objeto
        Image image; //una imagen
        long endTime; //el tiempo que tardara el frame en cambiar

	/**
	* Constructor AnimFrame, crea un nuevo objeto AnimFrame
	* @param image, la imagen a asignar a la AnimFrame
	* @param long, el tiempo de desplegado del AnimFrame
    */
        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
