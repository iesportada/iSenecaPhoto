package com.lei.isenecaPhoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
	private ImageView imgAlumno;
	private final int RC_CAMERA = 1; // request code camera
	private final int RC_GALERIA = 2; // request code galeria
	private String ruta_Camera="/" + CONSTANTES.ISENECAPHOTO + "/photos/";
	private String ruta_Archivo_Camera="";
	private File f;
	private int tamanoW; //ancho
	private int tamanoH; //alto
	private SharedPreferences prefs;
	private String separador; //separador para dividir el ancho por un lado del alto -->  150x150
	private final String TAG=this.getClass().getName();
	private Button btBorrarFoto;
	private CharSequence[] tamanosFotos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ver_imagen_alumno);
		init();
		recogerExtras();
	}

	/**
	 * metodo que inicializa la vista
	 */
	private void init() {
		this.imgAlumno = (ImageView) findViewById(R.id.imgAlumno);
		//asigno los tamaños de las fotos
		this.tamanosFotos = new CharSequence[] {"112x150", "150x200", "225x300","480x640"}; // valores buenos  si el movil esta en vertical, si esta en horizontal se invierten
		//asigno el nombre del archivo imagen
		this.ruta_Archivo_Camera = ((Alumno)getIntent().getParcelableExtra(CONSTANTES.EXTRA_ALUMNO)).getMapa().get(CONSTANTES.NUM_IDEN_ALUMNO) + ".jpg";
		//obtengo las preferencias
		this.prefs = getSharedPreferences(CONSTANTES.NOMBRE_PREFERENCIAS,Context.MODE_PRIVATE);
		//asigno el separador de tamaños de foto
		this.separador = "x";
		//obtengo la posicion del array de las preferencias, por defecto devuelvo un 0
		int[] tamanos = obtenerTamanos(prefs.getInt(CONSTANTES.TAMANO_FOTO, 0),separador);		
		//asigno tamaño para el thumb
		this.tamanoW = tamanos[0];
		this.tamanoH = tamanos[1];
		//asigno la ruta donde se va a guardar la imagen
		this.ruta_Camera +="/" + ((Alumno)getIntent().getParcelableExtra(CONSTANTES.EXTRA_ALUMNO)).getMapa().get("Unidad") + "/";
		this.btBorrarFoto = (Button) findViewById(R.id.btBorrarImagen);
	}
	
	/**
	 * metodo que recoge los extras de la activity
	 */
	private void recogerExtras() {
		if(getIntent().hasExtra(CONSTANTES.EXTRA_RUTA_FOTO)) {
			this.f = new File(getIntent().getStringExtra(CONSTANTES.EXTRA_RUTA_FOTO));	
			if(f.exists()) {
				Bitmap bm = BitmapFactory.decodeFile(this.f.getAbsolutePath());
				this.imgAlumno.setImageBitmap(bm);

			}
			else {
				this.imgAlumno.setImageDrawable(getResources().getDrawable(R.drawable.silueta));
				this.btBorrarFoto.setVisibility(View.INVISIBLE);
			}
		}
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
		mostrarDialogo(getResources().getString(R.string.informacion), getResources().getString(R.string.borrar_imagen_alumno), getResources().getString(R.string.no), getResources().getString(R.string.si));
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
		//a�ado a la ruta de las imagenes el nombre del archivo con el que se va a guardar
	    this.f = new File(Environment.getExternalStorageDirectory() + this.ruta_Camera, this.ruta_Archivo_Camera);
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
			Toast.makeText(this, getResources().getString(R.string.imagen_borrada), Toast.LENGTH_SHORT).show();
			return true;
		}
		else {
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
	private void mostrarDialogo(String titulo,String mensaje,String texto1,String texto2) {
		Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setTitle(titulo);
		dialogo.setMessage(mensaje);
		dialogo.setPositiveButton(texto1,null);
		dialogo.setNegativeButton(texto2, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				borrarFoto(f.getAbsolutePath());
				Intent i = new Intent();
				setResult(RESULT_OK, i);
				finish();
			}
		});
		dialogo.create();
		dialogo.show();
	}
	
	/**
	 * metodo que guarda una imagen en la ruta indicada
	 * @param origen
	 * @param destino
	 */
	private Boolean guardarImagen(Bitmap bm,String ruta) {	
		try {
			FileOutputStream out = new FileOutputStream(ruta);
			if(bm.compress(CompressFormat.JPEG, 100, out))
				return true;
			else
				return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_CANCELED)
			return;
		String rutaImagenSeleccionada;
		Bitmap img;

		//pregunto por el codigo de respuesta para saber que operacion hacer
		Intent i;
		switch (requestCode) {
		case RC_CAMERA:
			if(resultCode == RESULT_OK) {
				//comprimo el fichero al tamaño indicado en la variable
				img = rotarImagen(BitmapFactory.decodeFile(this.f.getAbsolutePath()), this.f.getAbsolutePath());
				img = ThumbnailUtils.extractThumbnail(img, this.tamanoW, this.tamanoH);
				//guardo la imagen comprimida
				if(guardarImagen(img,new File(Environment.getExternalStorageDirectory() + this.ruta_Camera, this.ruta_Archivo_Camera).getAbsolutePath()))
					Toast.makeText(this, getResources().getString(R.string.imagen_guardada), Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(this, getResources().getString(R.string.imagen_no_guardada), Toast.LENGTH_SHORT).show();
				i = new Intent();
				setResult(RESULT_OK, i);
				finish();
			}
			break;
		case RC_GALERIA:
			if(resultCode == RESULT_OK) {
				//obtengo la ruta del fichero
				rutaImagenSeleccionada = obtenerRutaFoto(data.getData());
				//comprimo el fichero al tamaño indicado en la variable
				img = rotarImagen(BitmapFactory.decodeFile(rutaImagenSeleccionada), rutaImagenSeleccionada);
				img = ThumbnailUtils.extractThumbnail(img, this.tamanoW, this.tamanoH);
				//guardo la imagen comprimida
				if(guardarImagen(img,this.f.getAbsolutePath()))
					Toast.makeText(this, getResources().getString(R.string.imagen_guardada), Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(this, getResources().getString(R.string.imagen_no_guardada), Toast.LENGTH_SHORT).show();
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity__ver__imagen__alumno, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.opcionTamanoFoto:
			crearDialogoTamanos();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * metodo que muestra un alertdialog con un listview para visualizar el tamaño de las fotos
	 */
	private void crearDialogoTamanos() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getResources().getString(R.string.elige_tamano));
		dialog.setSingleChoiceItems(tamanosFotos, prefs.getInt(CONSTANTES.TAMANO_FOTO, 0), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int pos) {
				//obtengo las preferencias
				Editor editor = getSharedPreferences(CONSTANTES.NOMBRE_PREFERENCIAS,Context.MODE_PRIVATE).edit();
				//edito las preferencias
				if(editor!=null) {
					editor.putInt(CONSTANTES.TAMANO_FOTO, pos);
					editor.commit();
					//obtengo el tamaño
					int[] tamanos = obtenerTamanos(prefs.getInt(CONSTANTES.TAMANO_FOTO, 0),separador);		
					//asigno tamaño para el thumb
					tamanoW = tamanos[0];
					tamanoH = tamanos[1];
				}
				dialog.dismiss();
			}
		});
		dialog.create();
		dialog.show();
	}
	
	/**
	 * metodo que obtiene las dimensiones que estan formateadas en 150x150 y las devuelve
	 * como 150 y 150 en un int[]   ancho/alto
	 * @param pos
	 * @return
	 */
	private int[] obtenerTamanos(int pos,String separador) {
		try {
		CharSequence formateado = this.tamanosFotos[pos]; // formato 150x150
    	String[] anchoAlto = formateado.toString().split(separador); //formato 150 150
    	int[] tamanos = new int[2];
    	tamanos[0] = Integer.valueOf(anchoAlto[0]); // formato 150
    	tamanos[1] = Integer.valueOf(anchoAlto[1]); // formato 150
    	return tamanos;
		}
		catch(Exception e) {
			return new int[2];
		}
	}
}
