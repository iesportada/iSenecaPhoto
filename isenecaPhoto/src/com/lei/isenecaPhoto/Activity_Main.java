package com.lei.isenecaPhoto;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.lei.isenecaPhoto.Modelos.Alumno;

/**
 * clase que gestiona la vista "main"
 * @author Ivan
 *
 */
public class Activity_Main extends Activity {

	//Campos y Componentes
	private String nombreArchivo;
	private String ruta;
	private String split;
	private String caracterProvisional;
	private String nombreExtraAlumnos;
	private String nombreExtraRuta;
    private String nombreExtraNombre;
	private final int RC_FILE_EXPLORE = 1;
//	private final String TAG=this.getClass().getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}
	
	/**
	 * metodo que inicializa la vista
	 */
	private void init() {
//		this.nombreArchivo ="regalum.csv";
//		this.ruta=Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + nombreArchivo;
		this.split=";";
		this.caracterProvisional="\",\"";
		this.nombreExtraAlumnos="alumnos";
		this.nombreExtraRuta="ruta";
		this.nombreExtraNombre="nombre";
	}

	/**
	 * metodo que se lanza cuando pulso sobre el boton "leer csv"
	 * @param v
	 */
	public void leerCSV(View v) {
		Intent intent1 = new Intent(this, Activity_Listado_Archivos.class);
        startActivityForResult(intent1,RC_FILE_EXPLORE);

		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RC_FILE_EXPLORE:
			if(resultCode == RESULT_OK) {
				this.ruta = data.getStringExtra(nombreExtraRuta) + "/" +  data.getStringExtra(nombreExtraNombre);
//				//creo la tarea asincrona y la lanzo
				miTareaAsincrona asincrona = new miTareaAsincrona();
				asincrona.execute(ruta, split, caracterProvisional);
			}
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}
	
	/**
	 * clase que representa una tarea asincrona para la lectura del archivo CSV
	 * @author Ivan
	 *
	 */
	private class miTareaAsincrona extends AsyncTask<String, Void, Void> {

		ProgressDialog dialogo;
		@Override
		protected Void doInBackground(String... params) {
			//leo el fichero csv y una vez leido paso todos los nombres de los alumnos
			CSV csv = new CSV();
			ArrayList<Alumno> alumnos = csv.leerCSV(params[0], params[1], params[2]);
			Intent i = new Intent(Activity_Main.this, Activity_Listado_Alumnos.class);
			i.putExtra(nombreExtraAlumnos, alumnos);
			startActivity(i);
			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			//cierro el dialogo con el progress bar
			dialogo.dismiss();
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			//cierro el dialogo con el progress bar
			dialogo.dismiss();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//creo un dialogo con un progress bar indeterminado
			dialogo = new ProgressDialog(Activity_Main.this);
			dialogo.setIndeterminate(true);
			dialogo.setTitle(getResources().getString(R.string.informacion));
			dialogo.setMessage(getResources().getString(R.string.leyendo_csv));
			dialogo.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
		
	}
}
