package com.lei.isenecaPhoto;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lei.isenecaPhoto.Adaptadores.AdaptadorArchivos;
import com.lei.isenecaPhoto.Modelos.Archivo;

/**
 * clase que gestiona la vista "listado archivos"
 * @author Ivan
 *
 */
public class Activity_Listado_Archivos extends ListActivity {
	
	//Campos y Componentes
	private File directorioActual;
    private AdaptadorArchivos adaptador;
    private String formatoAdmitido;
    private String nombreExtraRuta;
    private String nombreExtraNombre;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        init();
        rellenarListView(directorioActual); 
    }
    
    /**
     * metodo que inicializa la vista
     */
    private void init() {
    	this.directorioActual = new File("/");
    	this.nombreExtraRuta="ruta";
    	this.nombreExtraNombre="nombre";
    	this.formatoAdmitido="csv";
	}
    /**
     * metodo que rellena el listview
     * @param f
     */
	private void rellenarListView(File f) {
		File[] directorios = f.listFiles();
		this.setTitle(this.getResources().getString(R.string.directorio_actual) + f.getName());
		ArrayList<Archivo> arrayDirectorios = new ArrayList<Archivo>();
		ArrayList<Archivo> arrayFicheros = new ArrayList<Archivo>();
		try {
			for (File tmp : directorios) {
				Date ultimaFechaModificacion = new Date(tmp.lastModified());
				DateFormat formato = DateFormat.getDateTimeInstance();
				String fecha_modificada = formato.format(ultimaFechaModificacion);
				if (tmp.isDirectory()) {

					File[] fbuf = tmp.listFiles();
					int buf = 0;
					if (fbuf != null) {
						buf = fbuf.length;
					} else
						buf = 0;
					String num_item = String.valueOf(buf);
					if (buf == 0)
						num_item = num_item + " item";
					else
						num_item = num_item + " items";

					arrayDirectorios.add(new Archivo(tmp.getName(), num_item,fecha_modificada, tmp.getAbsolutePath(), this.getResources().getString(R.string.icono_directorio)));
				} else if(getExtension(tmp.getAbsolutePath()).equalsIgnoreCase(this.formatoAdmitido))
					arrayFicheros.add(new Archivo(tmp.getName(), tmp.length() + " " + this.getResources().getString(R.string.byte_nombre),fecha_modificada, tmp.getAbsolutePath(), this.getResources().getString(R.string.icono_archivo)));
			}
		} catch (Exception e) {

		}
		Collections.sort(arrayDirectorios);
		Collections.sort(arrayFicheros);
		arrayDirectorios.addAll(arrayFicheros);
		if (!f.getName().equalsIgnoreCase(""))
			arrayDirectorios.add(0, new Archivo("..", this.getResources().getString(R.string.directorio_padre), "", f.getParent(),this.getResources().getString(R.string.icono_subir)));
		
		this.adaptador = new AdaptadorArchivos(this, arrayDirectorios);
		this.setListAdapter(this.adaptador);
    }
    
	/**
	 * metodo que se lanza al pulsar sobre un objeto del listview
	 */
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Archivo archivo = this.adaptador.getItem(position);
		if(archivo.getImagen().equalsIgnoreCase(this.getResources().getString(R.string.icono_directorio))|| archivo.getImagen().equalsIgnoreCase(this.getResources().getString(R.string.icono_subir))){
				this.directorioActual = new File(archivo.getRuta());
				rellenarListView(this.directorioActual);
		}
		else
			abrirArchivo(archivo);
	}
    
    /**
     * metodo que se lanza cuando se pulsa sobre un archivo
     * @param o
     */
    private void abrirArchivo(Archivo o) {
    	Intent intent = new Intent();
        intent.putExtra(nombreExtraRuta,directorioActual.toString());
        intent.putExtra(nombreExtraNombre,o.getNombre());
        setResult(RESULT_OK, intent);
        finish();
    }
    
    /**
     * metodo que devuelve la extension de un archivo
     * @param archivo
     * @return
     */
    private String getExtension(String archivo) {
    	String extension = "";
    	int i = archivo.lastIndexOf('.');
    	if (i > 0)
    	    extension = archivo.substring(i+1);
    	return extension;
    }
}
