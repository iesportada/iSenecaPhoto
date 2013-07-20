package com.lei.isenecaPhoto.Modelos;

/**
 * clase modelo que representa un archivo del sistema android
 * @author Ivan
 *
 */
public class Archivo implements Comparable<Archivo> {

	//Campos
	private String nombre;
	private String dato;
	private String fecha;
	private String ruta;
	private String imagen;
	
	//Constructor
	public Archivo(String nombre,String dato, String fecha, String ruta, String imagen)
	{
		this.nombre = nombre;
		this.dato = dato;
		this.fecha = fecha;
		this.ruta = ruta; 
		this.imagen = imagen;
	}
	
	//Propiedades
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDato() {
		return dato;
	}


	public void setDato(String dato) {
		this.dato = dato;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getRuta() {
		return ruta;
	}


	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	/**
	 * metodo que compara un objeto "Archivo" con otro objeto "Archivo"
	 * @param o
	 * @return
	 */
	public int compareTo(Archivo o) {
		if(this.nombre != null)
			return this.nombre.toLowerCase().compareTo(o.getNombre().toLowerCase()); 
		else 
			throw new IllegalArgumentException();
	}
}
