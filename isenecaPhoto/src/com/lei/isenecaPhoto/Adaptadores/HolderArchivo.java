package com.lei.isenecaPhoto.Adaptadores;

import android.widget.ImageView;
import android.widget.TextView;

public class HolderArchivo {

	private TextView nombreArchivo,dato,fecha;
	private ImageView imagen;

	public TextView getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(TextView nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public TextView getDato() {
		return dato;
	}

	public void setDato(TextView dato) {
		this.dato = dato;
	}

	public TextView getFecha() {
		return fecha;
	}

	public void setFecha(TextView fecha) {
		this.fecha = fecha;
	}

	public ImageView getImagen() {
		return imagen;
	}

	public void setImagen(ImageView imagen) {
		this.imagen = imagen;
	}
	
	
}
