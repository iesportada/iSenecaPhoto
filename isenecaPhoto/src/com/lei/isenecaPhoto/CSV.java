package com.lei.isenecaPhoto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.lei.isenecaPhoto.Modelos.Alumno;

import android.util.Log;

/**
 * clase encargada de gestionar archivos CSV
 * @author Ivan
 *
 */
public class CSV {
	
	private final String TAG=this.getClass().getName();
	
	/**
	 * constructor
	 */
	public CSV() {
		super();
	}

	/**
	 * metodo que lee el fichero CSV y obtiene sus registros
	 * @param ruta: es la ubicacion donde esta el archivo que vamos a leer
	 * @param split: es el caracter por el cual vamos a separar los distintos valores de un registro
	 * @param caracterProvisional: es el caracter que vamos a sustituir provisionalmente para la mejor separacion de los valores del registro
	 * @return ArrayList con los alumnos leidos del csv
	 */
	public ArrayList<Alumno> leerCSV(String ruta, String split, String caracterProvisional) {
		String[] registroLeido;
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
//		InputStream flujo = getResources().openRawResource(R.raw.regalum);
		FileInputStream flujo = null;
		BufferedReader lector = null;
		try {
			flujo = new FileInputStream(ruta);
			lector = new BufferedReader(new InputStreamReader(flujo, "UTF-8"));
			String texto = lector.readLine();
			if (texto != null) {
//				registroLeido = texto.replace(",\"", "\",\"").replace("\",\"", ";").replace("\"", "").split(";");
				registroLeido = texto.replace(",\"", caracterProvisional).replace(caracterProvisional, split).replace("\"", "").split(split);
				while (texto != null) {

					/*
					 * De la linea leida separo los valores, como el primer
					 * campo es nombre completo y tiene una coma, la sentencia
					 * lo dividiria en 2 siendo realmente uno( ej: Diaz
					 * Molina,Ivan) para ello remplazamos provisionalmente la
					 * primera "," por un ";"
					 */

					// Creo un objeto de tipo registro y asigno los valores a
					// sus respectivos campos
					Alumno registro = new Alumno();
					registro.setAlumno(registroLeido[0]);
					registro.setDni(registroLeido[1]);
					registro.setFechaNacimiento(registroLeido[2]);
					registro.setCurso(registroLeido[3]);
					registro.setNumExpedienteCentro(registroLeido[4]);
					registro.setUnidad(registroLeido[5]);
					registro.setSegundoApellidoAlumno(registroLeido[6]);
					registro.setNombreAlumno(registroLeido[7]);
					registro.setDniTutor(registroLeido[8]);
					registro.setPrimerApellidoTutor(registroLeido[9]);
					registro.setSegundoApellidoTutor(registroLeido[10]);
					registro.setNombreTutor(registroLeido[11]);
					registro.setSexoTutor(registroLeido[12]);
					registro.setDniTutor2(registroLeido[13]);
					registro.setPrimerApellidoTutor2(registroLeido[14]);
					registro.setSegundoApellidoTutor2(registroLeido[15]);
					registro.setNombreTutor2(registroLeido[16]);
					registro.setSexoTutor2(registroLeido[17]);
					registro.setLocalidadNacimiento(registroLeido[18]);
					registro.setAnoMatricula(registroLeido[19]);
					registro.setNumeroMatriculoEsteCurso(registroLeido[20]);
					registro.setObservacionesMatricula(registroLeido[21]);
					registro.setProvinciaNacimiento(registroLeido[22]);
					registro.setPaisNacimiento(registroLeido[23]);
					registro.setEdad(registroLeido[24]);
					registro.setNacionalidad(registroLeido[25]);
					registro.setSexo(registroLeido[26]);
					registro.setPrimerApellidoAlumno(registroLeido[27]);
					registro.setEstadoMatricula(registroLeido[28]);
					registro.setDireccion(registroLeido[29]);
					registro.setCodigoPostal(registroLeido[30]);
					registro.setLocalidadResidencia(registroLeido[31]);
					registro.setProvinciaResidencia(registroLeido[32]);
					registro.setTelefono(registroLeido[33]);
					registro.setTelefonoUrgencia(registroLeido[34]);
					registro.setCorreoElectronico(registroLeido[35]);
					registro.setNumIdEscolar(registroLeido[36]);

					// a�ado el nuevo registro al ArrayList
					alumnos.add(registro);
					texto = lector.readLine();
					if (texto != null)
//						registroLeido = texto.replace(",\"", "\",\"").replace("\",\"", ";").replace("\"", "").split(";");
						registroLeido = texto.replace(",\"", caracterProvisional).replace(caracterProvisional, split).replace("\"", "").split(split);
				}

				//Muestro los registros leidos en el LOGCAT
				for (Alumno tmp : alumnos) {
					Log.i(TAG, tmp.toString());
					Log.i(TAG,"----------------------------------------------------------------");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//Cerramos el lector y el flujo
				if (lector != null)
					lector.close();
				if (flujo != null)
					flujo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//devuelvo los alumnos
		return alumnos;
	}
}
