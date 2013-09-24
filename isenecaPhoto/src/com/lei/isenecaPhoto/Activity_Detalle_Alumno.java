package com.lei.isenecaPhoto;

import java.io.File;
import java.io.IOException;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lei.isenecaPhoto.Modelos.Alumno;

/**
 * clase que gestiona la vista "detalle alumno"
 * @author Ivan
 *
 */
public class Activity_Detalle_Alumno extends Activity {

	//Campos y Componentes
	//private final String TAG=this.getClass().getName();
	private LinearLayout lyRootPadre,lyRoot;
	private ImageView imgAlumno;
	private final String TAG_FOTO="no foto";
	private final int RC_VER_IMAGEN_ALUMNO = 1; // request code ver imagen alumno
	private String ruta_Camera; 
	private String ruta_Archivo_Camera;
	private File f;
	
	private Alumno alumno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalle_alumno);
		init();
		recogerExtras();
	}
	
	/**
	 * metodo que inicializa la vista
	 */
	private void init() {
		//asigno las rutas
		this.ruta_Camera = "/isenecaPhoto/photos/";
		this.ruta_Archivo_Camera ="";
		//Hago referencia a los componentes de la vista
		this.lyRootPadre = (LinearLayout) findViewById(R.id.lyRootPadre);
		this.lyRoot = (LinearLayout) findViewById(R.id.lyRoot);
		this.imgAlumno = (ImageView) findViewById(R.id.imgAlumno);
		this.imgAlumno.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//el alumno ya tenia una imagen en la memoria del telefono, por tanto debo abrir al imagen
				abrirImagen(f.getAbsolutePath());
			}
		});
	}

	/**
	 * metodo que recoge los posibles extras que le puedan llegar, si recoge extras se encarga de eliminar los posibles
	 * view en los que no haya ningun valor para a�adir
	 */
	private void recogerExtras() {
		
		if(getIntent().hasExtra(CONSTANTES.EXTRA_ALUMNO)) {
			this.alumno = getIntent().getParcelableExtra(CONSTANTES.EXTRA_ALUMNO);
			//Creo las vistas en tiempo de ejecucion
			for(int i = 0;i<((Aplicacion)this.getApplicationContext()).getColumnas().size();i++) {
				LinearLayout ly = new LinearLayout(this);
				ly.setOrientation(LinearLayout.VERTICAL);
				TextView tv = new TextView(this);
				tv.setTextColor(Color.WHITE);
				tv.setTypeface(null,Typeface.BOLD);
				tv.setText(((Aplicacion)this.getApplicationContext()).getColumnas().get(i));
				ly.addView(tv);
				tv = new TextView(this);
				tv.setTextColor(Color.WHITE);
				tv.setText(this.alumno.getMapa().get(((Aplicacion)this.getApplicationContext()).getColumnas().get(i)));
				ly.addView(tv);
				if(i==0) {
					//pongo el nombre del alumno arriba a la derecha
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					ly.setLayoutParams(params);
					this.lyRoot.addView(ly);
				}
				else if(!tv.getText().toString().equals(""))
					this.lyRootPadre.addView(ly);
			}	
			
			//asigno el nombre del archivo imagen
			this.ruta_Archivo_Camera = this.alumno.getMapa().get(CONSTANTES.NUM_IDEN_ALUMNO) + ".jpg";
			//asigno la ruta donde se va a guardar la imagen
			this.ruta_Camera +="/" + this.alumno.getMapa().get(CONSTANTES.UNIDAD_ALUMNO) + "/";
			
			
			
			
			// si la imagen no existe, asigno la imagen por defecto y le pongo el tag de "no foto"
			this.f = new File(Environment.getExternalStorageDirectory() + this.ruta_Camera, this.ruta_Archivo_Camera);
			
			if(!this.f.exists()) {
				this.imgAlumno.setImageDrawable(getResources().getDrawable(R.drawable.silueta));
				this.imgAlumno.setTag(TAG_FOTO);
			}
			// el archivo si existe, por tanto, cargo la imagen correspondiente
			else {
				Bitmap bm = BitmapFactory.decodeFile(this.f.getAbsolutePath());
				this.imgAlumno.setImageBitmap(bm);
			}
		}
	}
	
	/**
	 * metodo que se lanza cuando se pulsa sobre la imagen del alumno y esta imageno no es la "imagen por defecto"
	 * @param ruta
	 */
	private void abrirImagen(String ruta) {
		Intent i = new Intent(this, Activity_Ver_Imagen_Alumno.class);
		i.putExtra(CONSTANTES.EXTRA_RUTA_FOTO, ruta);
		i.putExtra(CONSTANTES.EXTRA_ALUMNO, this.alumno);
		startActivityForResult(i, RC_VER_IMAGEN_ALUMNO);
	}
	
	/**
	 * metodo que comprueba el estado de la SD
	 * @return
	 */
	private Boolean comprobarSD() { 
		//Comprobamos el estado de la memoria externa (tarjeta SD)
		String estado = Environment.getExternalStorageState();
		 
		if (estado.equals(Environment.MEDIA_MOUNTED))
		    return true;
		else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
			return false;
		else
			return false;
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
		dialogo.setPositiveButton(positiveButton, null);
		dialogo.create();
		dialogo.show();
	}
	
	/**
	 * metodo que rota un bitmap. Siempre para que quede en 90º
	 * @param bm
	 * @param ruta
	 * @return
	 */
	private Bitmap rotarImagen(Bitmap bm,String ruta) {
		Matrix matrix = null;
		ExifInterface ei;
		try {
			ei = new ExifInterface(ruta);
			int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

			matrix = new  Matrix();
			switch(orientation) {
			case ExifInterface.ORIENTATION_NORMAL:
				matrix.postRotate(0);
		        break;
		    case ExifInterface.ORIENTATION_ROTATE_90:
		    	matrix.postRotate(90);
		        break;
		    case ExifInterface.ORIENTATION_ROTATE_180:
		    	matrix.postRotate(180);
		        break;
		        
		    case ExifInterface.ORIENTATION_ROTATE_270:
		    	matrix.postRotate(270);
		        break;
			}
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Bitmap.createBitmap(bm, 0, 0,bm.getWidth(), bm.getHeight(), matrix, true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RC_VER_IMAGEN_ALUMNO:
			if(resultCode == RESULT_OK) {
				//si el archivo no existe asigno la imagen por defecto
				if(!this.f.exists()) {
					this.imgAlumno.setImageDrawable(getResources().getDrawable(R.drawable.silueta));
					this.imgAlumno.setTag(TAG_FOTO);
				}
				// el archivo si existe, por tanto, cargo la imagen correspondiente
				else {
					Bitmap bm = BitmapFactory.decodeFile(this.f.getAbsolutePath());
				    this.imgAlumno.setImageBitmap(bm);
			    }
			}
			break;
		}
	}
}
