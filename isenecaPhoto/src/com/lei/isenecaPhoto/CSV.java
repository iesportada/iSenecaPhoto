package com.lei.isenecaPhoto;

import java.nio.charset.Charset;
import java.util.HashMap;

import android.content.Context;

import com.csvreader.CsvReader;
import com.lei.isenecaPhoto.Modelos.Alumno;
import com.lei.isenecaPhoto.Modelos.Grupo;

/**
 * clase encargada de gestionar archivos CSV
 * @author Ivan
 *
 */
public class CSV {
	
	//private final String TAG=this.getClass().getName();
	private Context contexto;
	
	/**
	 * constructor
	 */
	public CSV() {
		super();
	}
	
	public CSV(Context contexto){
		this.contexto = contexto;
	}

	/**
	 * metodo que lee el fichero CSV y obtiene sus registros
	 * @param ruta: es la ubicacion donde esta el archivo que vamos a leer
	 * @param split: es el caracter por el cual vamos a separar los distintos valores de un registro
	 * @param caracterProvisional: es el caracter que vamos a sustituir provisionalmente para la mejor separacion de los valores del registro
	 * @return ArrayList con los alumnos leidos del csv
	 */
	public HashMap<String, Grupo> leerCSV(String ruta, String split) {
		int contador = 0;
		HashMap<String, Grupo> grupos = new HashMap<String, Grupo>();
		Alumno registro = null;
		CsvReader reader = null;
		
		try {
			// instancio el objeto readerCSV
			reader = new CsvReader(ruta, split.charAt(0), Charset.forName("ISO-8859-1"));
			// recorremos las filas del fichero
			while (reader.readRecord()) {
				registro = new Alumno();
				if(contador == 0) {
					((Aplicacion)this.contexto.getApplicationContext()).getColumnas().clear();
					for(int i=0;i<reader.getColumnCount();i++) {
						((Aplicacion)this.contexto.getApplicationContext()).getColumnas().add(reader.get(i));
					}
					contador++;
				}
				else {
					for(int i =0;i<reader.getColumnCount();i++)
					registro.getMapa().put(((Aplicacion)this.contexto.getApplicationContext()).getColumnas().get(i), reader.get(i));
					
					if(grupos.containsKey(registro.getMapa().get(CONSTANTES.UNIDAD_ALUMNO)))
						grupos.get(registro.getMapa().get(CONSTANTES.UNIDAD_ALUMNO)).getAlumnos().add(registro);
					
					else {
						grupos.put(registro.getMapa().get(CONSTANTES.UNIDAD_ALUMNO), new Grupo(registro.getMapa().get(CONSTANTES.UNIDAD_ALUMNO)));
						grupos.get(registro.getMapa().get(CONSTANTES.UNIDAD_ALUMNO)).getAlumnos().add(registro);
					}
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			reader.close();
		} 
		
		//devuelvo los alumnos
		return grupos;
	}
}
