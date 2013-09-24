package com.lei.isenecaPhoto;

import java.util.ArrayList;
import java.util.HashMap;

import com.lei.isenecaPhoto.Modelos.Grupo;

import android.app.Application;

public class Aplicacion extends Application {

	private ArrayList<String> columnas;
	private HashMap<String, Grupo> grupos;

	@Override
	public void onCreate() {
		super.onCreate();
		this.columnas = new ArrayList<String>();
		this.grupos = new HashMap<String, Grupo>();
	}

	public ArrayList<String> getColumnas() {
		return columnas;
	}

	public HashMap<String, Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(HashMap<String, Grupo> grupos) {
		this.grupos = grupos;
	}
	
	
}
