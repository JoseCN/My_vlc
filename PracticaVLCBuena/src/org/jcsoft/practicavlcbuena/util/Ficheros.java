package org.jcsoft.practicavlcbuena.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Ficheros implements Serializable{
	
	public static void escribirObjeto(Object objeto,String ruta) throws IOException{
		FileOutputStream fichero = null;
		ObjectOutputStream serializador = null;
		
		fichero = new FileOutputStream(ruta);
		serializador = new ObjectOutputStream(fichero);
		serializador.writeObject(objeto);
		
		if(serializador!=null)
			serializador.close();
		
	}
	
	public static Object leerObjeto(String ruta) throws IOException, ClassNotFoundException{
		
		FileInputStream fichero = null;
		ObjectInputStream serializador = null;
		Object objeto = null;
		
		fichero = new FileInputStream(ruta);
		serializador = new ObjectInputStream(fichero);
		objeto =serializador.readObject();
		
		if (serializador != null)
			serializador.close();
		
		return objeto;
	}
}
