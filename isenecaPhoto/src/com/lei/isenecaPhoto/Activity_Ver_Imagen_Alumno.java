package com.lei.isenecaPhoto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lei.isenecaPhoto.Modelos.Alumno;

/**
 * clase que gestiona la vista "ver imagen alumno"
 * @author Ivan
 *
 */
public class Activity_Ver_Imagen_Alumno extends Activity {

	//Campos y Componenes
	private ImageView img;
	private String nombreExtraRutaFoto ="rutaFoto";
	private String nombreExtraAlumno ="alumno";
	private final int RC_CAMERA = 1; // request code camera
	private final int RC_GALERIA = 2; // request code galeria
	private String ruta_Camera="/isenecaPhoto/photos/";
	private String ruta_Archivo_Camera="";
	private File f;
	private final String TAG=this.getClass().getName();
	private Button btBorrarFoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ver__imagen__alumno);
		init();
		recogerExtras();
	}
	
	/**
	 * metodo que recoge los extras de la activity
	 */
	private void recogerExtras() {
		if(getIntent().hasExtra(nombreExtraRutaFoto)) {
			this.f = new File(getIntent().getStringExtra(nombreExtraRutaFoto));
			if(f.exists()) {
				Bitmap bm = BitmapFactory.decodeFile(getIntent().getStringExtra(nombreExtraRutaFoto));
				this.img.setImageBitmap(bm);
			}
			else {
				this.img.setImageDrawable(getResources().getDrawable(R.drawable.silueta));
				this.btBorrarFoto.setVisibility(View.INVISIBLE);
			}
		}
	}

	/**
	 * metodo que inicializa la vista
	 */
	private void init() {
		this.img = (ImageView) findViewById(R.id.imgAlumno);
		//asigno el nombre del archivo imagen
		this.ruta_Archivo_Camera = ((Alumno)getIntent().getSerializableExtra(nombreExtraAlumno)).getNumIdEscolar() + ".jpg";
		//asigno la ruta donde se va a guardar la imagen
		this.ruta_Camera +="/" + ((Alumno)getIntent().getSerializableExtra(nombreExtraAlumno)).getUnidad() + "/";
		this.btBorrarFoto = (Button) findViewById(R.id.btBorrarImagen);
	}

	/**
	 * metodo que se lanza cuando pulso sobre el boton "tomar foto"
	 * @param v
	 */
	public void tomarFoto(View v) {
		lanzarCamara();
	}
	
	/**
	 * metodo que se lanza cuando se pulsa sobre el boton "seleccionar foto"
	 * @param v
	 */
	public void seleccionarFoto(View v) {
		seleccionarFoto();
	}
	

	/**
	 * metodo que se lanza cuando pulso sobre el boton "borrar foto"
	 * @param v
	 */
	public void borrarFoto(View v) {
		mostrarDialogo(getResources().getString(R.string.informacion), getResources().getString(R.string.borrar_imagen_alumno), getResources().getString(R.string.si), getResources().getString(R.string.no));
	}
	
	/**
	 * metodo que permite al usuario seleccionar una imagen de la carpeta  /mnt/sdcard/DCIM
	 */
	private void seleccionarFoto() {
		Intent intent = new Intent();
	    intent.setType("image/*");
	    intent.setAction(Intent.ACTION_GET_CONTENT);
	    startActivityForResult(Intent.createChooser(intent,getResources().getString(R.string.seleccione_foto)), RC_GALERIA);
	}
	
	/**
	 * metodo que lanza la aplicacion de la camara
	 */
	private void lanzarCamara() {
		//cargo la ruta de las imagenes tomadas por la camara con esta aplicacion
		this.f  = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + this.ruta_Camera);
		// si la ruta no existe creo el directorio
		if(!this.f.exists()) {
			if(this.f.mkdirs())
				System.out.print("creado");
			else
				System.out.print("no creado");
		}
		//preparo el intent de la camara
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		//añado a la ruta de las imagenes el nombre del archivo con el que se va a guardar
	    this.f = new File(Environment.getExternalStorageDirectory() + this.ruta_Camera, this.ruta_Archivo_Camera);
	    //añado el extra de salida al fichero
	    i.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(this.f));
	    //lanzo el intent
	    startActivityForResult(i, RC_CAMERA);
	}
	
	/**
	 * metodo que borra un archivo
	 * @param ruta
	 */
	private boolean borrarFoto(String ruta) {
		if(this.f.delete()) {
			Log.i(TAG, "archivo borrado");
			Toast.makeText(this, getResources().getString(R.string.imagen_borrada), Toast.LENGTH_SHORT).show();
			return true;
		}
		else {
			Log.i(TAG, "archivo no borrado");
			Toast.makeText(this, getResources().getString(R.string.imagen_no_borrada), Toast.LENGTH_SHORT).show();
			return false;
		}
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
				borrarFoto(f.getAbsolutePath());
				Intent i = new Intent();
				setResult(RESULT_OK, i);
				finish();
			}
		});
		dialogo.setNegativeButton(negativeButton, null);
		dialogo.create();
		dialogo.show();
	}
	
	/**
	 * metodo que copia un fichero de una ruta origen a una ruta destino
	 * @param origen
	 * @param destino
	 */
	private void copiarFichero(File origen,File destino) {
		InputStream inStream = null;
		OutputStream outStream = null;
	 
	    	try{
	    	    inStream = new FileInputStream(origen);
	    	    outStream = new FileOutputStream(destino);
	 
	    	    byte[] buffer = new byte[1024];
	 
	    	    int length;
	    	    while ((length = inStream.read(buffer)) > 0)
	    	    	outStream.write(buffer, 0, length);
	    	    
	    	}catch(IOException e){
	    		e.printStackTrace();
	    	}
	    	finally {
				try {
					if(inStream!=null)
						inStream.close();
					if(outStream!=null)
		    			 outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent i;
		switch (requestCode) {
		case RC_CAMERA:
			i = new Intent();
			setResult(RESULT_OK, i);
			finish();
			break;
		case RC_GALERIA:
			if(resultCode == RESULT_OK) {
				String rutaImagenSeleccionada = obtenerRutaFoto(data.getData());
				copiarFichero(new File(rutaImagenSeleccionada),new File(Environment.getExternalStorageDirectory() + this.ruta_Camera, this.ruta_Archivo_Camera));
				i = new Intent();
				setResult(RESULT_OK, i);
				finish();
			}
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}

	/**
	 * metodo que obtiene la ruta de la foto seleccionada
	 * @param imagenSeleccionada
	 * @return
	 */
	private String obtenerRutaFoto(Uri imagenSeleccionada) {
		String[] columnas = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(imagenSeleccionada,columnas, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(columnas[0]);
        String ruta = cursor.getString(columnIndex);
        cursor.close();
        return ruta;
	}
}
