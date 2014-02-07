package org.jcsoft.practicavlcbuena.filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class VideoFiltro  extends FileFilter{

	@Override
	public boolean accept(File fichero) {
		
		if (fichero.isDirectory())
			return true;
		
		if (fichero.getName().indexOf(".") == -1)
			return false;
		
		String extension = fichero.getName().substring(fichero.getName().lastIndexOf("."), fichero.getName().length());
		
		// Añadir todas las extensiones multimedia soportadas por el reproductor
		if (extension.equals(".flv") ||
				extension.equals(".avi") ||
				extension.equals(".mpeg") ||
				extension.equals(".wma") ||
				extension.equals(".mp4")||
				extension.equals(".mp3"))
			return true;
		
		return false;
	}

	@Override
	public String getDescription() {
		
		return "Archivos de video";
	}

}
