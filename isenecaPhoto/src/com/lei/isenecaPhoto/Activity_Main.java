package com.lei.isenecaPhoto;

import java.io.File;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lei.isenecaPhoto.Modelos.Grupo;

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
	private String nombreExtraRuta;
    private String nombreExtraNombre;
    private File f;
	private final int RC_FILE_EXPLORE = 1;
	private String rutaArchivo;
	private TextView lblTitulo;
//	private final String TAG=this.getClass().getName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		preferencias();
	}
	/**
	 * metodo que inicializa la vista
	 */
	private void init() {
		//pongo el titulo y la version de la aplicacion
		try {
			this.lblTitulo = (TextView) findViewById(R.id.lblTitulo);
			String titulo = getResources().getString(R.string.app_name);
			PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
			titulo += " v" + pi.versionName;
			this.lblTitulo.setText(titulo);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		this.rutaArchivo = "/" + CONSTANTES.ISENECAPHOTO + "/";
		this.nombreArchivo ="RegAlum.csv";
		this.ruta=Environment.getExternalStorageDirectory().getAbsolutePath() + this.rutaArchivo;
		this.f = new File(this.ruta);
		this.f.mkdirs();
		this.ruta += this.nombreArchivo;
		this.split=",";
		this.nombreExtraRuta="ruta";
		this.nombreExtraNombre="nombre";
		
	}
	
	/**
	 * metodo que crea/carga/guarda preferencias
	 */
	private void preferencias() {
		//obtengo las preferencias
		SharedPreferences prefs = getSharedPreferences(CONSTANTES.NOMBRE_PREFERENCIAS,Context.MODE_PRIVATE);
		//si el archivo de preferencia no contiene la POSICION DEL ARRAY del tamaño de las fotos
		//le asigno yo una posicion manualmente
		if(!prefs.contains(CONSTANTES.TAMANO_FOTO)) {
			Editor editor = prefs.edit();
			editor.putInt(CONSTANTES.TAMANO_FOTO, 2);
			editor.commit();
		}
	}

	/**
	 * metodo que se lanza cuando pulso sobre el boton "leer csv"
	 * @param v
	 */
	public void leerCSV(View v) {
		/*
		 * primero intento cargar el archivo en una ruta por defecto
		 * 
		 * Si lo encuentra lo cargo directamente, sino muestro un mensaje al usuario de que no se encuentra
		 * el archivo y que si desea buscarlo el
		 */
		this.f = new File(this.ruta);
		if(this.f.exists()) {
			//creo la tarea asincrona y la lanzo
			miTareaAsincrona asincrona = new miTareaAsincrona();
			asincrona.execute(this.ruta, split);
		}
		else
			mostrarDialogo(getResources().getString(R.string.informacion), getResources().getString(R.string.csv_no_encontrado), getResources().getString(R.string.si), getResources().getString(R.string.no));
	}
	
	/**
	 * metodo que muestra un mensaje de alerta al usuario
	 * @param titulo
	 * @param mensaje
	 * @param positiveButton
	 * @param negativeButton
	 */
	private void mostrarDialogo(String titulo,String mensaje,String positiveButton,String negativeButton) {
		Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setTitle(titulo);
		dialogo.setMessage(mensaje);
		dialogo.setPositiveButton(positiveButton, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent1 = new Intent(Activity_Main.this, Activity_Listado_Archivos.class);
		        startActivityForResult(intent1,RC_FILE_EXPLORE);
			}
		});
		dialogo.setNegativeButton(negativeButton, null);
		dialogo.create();
		dialogo.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RC_FILE_EXPLORE:
			if(resultCode == RESULT_OK) {
				this.ruta = data.getStringExtra(nombreExtraRuta) + "/" +  data.getStringExtra(nombreExtraNombre);
				//creo la tarea asincrona y la lanzo
				miTareaAsincrona asincrona = new miTareaAsincrona();
				asincrona.execute(ruta, split);
			}
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity__main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.opcionAcercaDe:
//			i = new Intent(this, Activity_AcercaDe.class);
//			startActivity(i);
			crearAcercaDe();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * metodo que crea el dialogo de "acerca de"
	 */
	private void crearAcercaDe() {
		try {
			AlertDialog.Builder dialogo = new Builder(this);
			dialogo.setIcon(android.R.drawable.ic_dialog_info);
			dialogo.setTitle(getResources().getString(R.string.title_activity_activity__acerca_de));
			PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
			
			String mensaje = getResources().getString(R.string.app_name) + " v"
					+ pi.versionName + "\n"
					+ getResources().getString(R.string.descripcion_app) + "\n"
					+ getResources().getString(R.string.ano) + " - "
					+ getResources().getString(R.string.autor);
			final LinearLayout ly = new LinearLayout(this);
			ly.setGravity(Gravity.CENTER);
			ly.setOrientation(LinearLayout.VERTICAL);
			TextView tv = new TextView(this);
			tv.setGravity(Gravity.CENTER);
			tv.setText(mensaje);
			ly.addView(tv);
			tv = new TextView(this);
			tv.setGravity(Gravity.CENTER);
			tv.setText(getResources().getString(R.string.linkedin2));
			tv.setClickable(true);
			tv.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(getResources().getString(R.string.linkedin)));
					startActivity(i);
				}
			});
			tv.setTextColor(Color.CYAN);
			ly.addView(tv);
			dialogo.setView(ly);
			dialogo.setPositiveButton(getResources().getString(R.string.aceptar), null);
			dialogo.create();
			dialogo.show();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
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
			CSV csv = new CSV(Activity_Main.this);
			//obtengo un mapa que contiene los grupos y cada grupo contiene sus alumnos
			HashMap<String, Grupo> grupos = csv.leerCSV(params[0], params[1]);
			//lo guardo en application
			((Aplicacion)getApplicationContext()).setGrupos(grupos);
			//lanzo la activity
			Intent i = new Intent(Activity_Main.this, Activity_Listado_Alumnos.class);
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
			dialogo.setCancelable(false);
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
