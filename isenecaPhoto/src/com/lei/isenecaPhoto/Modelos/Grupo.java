package com.lei.isenecaPhoto.Modelos;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * clase modelo que representa un grupo/curso
 * @author Ivan
 *
 */
public class Grupo implements Comparable<Grupo>, Parcelable {

	private String nombre;
	private ArrayList<Alumno> alumnos;
	public static final Parcelable.Creator<Grupo> CREATOR = new Parcelable.Creator<Grupo>() {

        public Grupo createFromParcel(Parcel in) {
            return new Grupo(in);
        }

        public Grupo[] newArray(int size) {
            return new Grupo[size];
        }
    };
	
	//Constructor
	public Grupo() {
		super();
		this.nombre="";
		this.alumnos = new ArrayList<Alumno>();
	}
	
	//Constructor parametrizado
	public Grupo(String nombre) {
		super();
		this.nombre=nombre;
		this.alumnos = new ArrayList<Alumno>();
	}
	
	//Constructor parametrizado
	public Grupo(Parcel parcelable) {
		super();
		readFromParcelable(parcelable);
	}

	//Propiedades
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Alumno> getAlumnos() {
		return alumnos;
	}
	public void setAlumnos(ArrayList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	/**
	 * metodo que compara un grupo con otro
	 */
	@Override
	public int compareTo(Grupo g) {
		return this.nombre.toLowerCase().compareTo(g.getNombre());
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(this.nombre);
		dest.writeList(this.alumnos);
	}
	
	private void readFromParcelable(Parcel parcelable) {
		this.nombre = parcelable.readString();
		this.alumnos = parcelable.readArrayList(Grupo.class.getClassLoader());
	}
}
