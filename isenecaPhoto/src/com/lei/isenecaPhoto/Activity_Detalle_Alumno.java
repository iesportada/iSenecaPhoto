package com.lei.isenecaPhoto;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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
	private final String TAG=this.getClass().getName();
	private TextView nombreAlumno, dni, fechaNacimiento, localidadNacimiento,
	provinciaNacimiento, paisNacimiento, edad, nacionalidad, sexo,
	direccion, codigoPostal, localidadResidencia, provinciaResidencia,
	telefono, telefonoUrgencia, correoElectronico,tutor1,dniTutor1,tutor2,dniTutor2;
	private LinearLayout lyRoot;
	private ImageView imgAlumno;
	private final String TAG_FOTO="no foto";
	private final int RC_VER_IMAGEN_ALUMNO = 1; // request code ver imagen alumno
	private String ruta_Camera="/isenecaPhoto/photos/";
	private String ruta_Archivo_Camera="";
	private File f;
	
	private String nombreExtraAlumno ="alumno";
	private String nombreExtraRutaFoto ="rutaFoto";
	private Alumno alumno;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalle__alumno);
		init();
		recogerExtras();
	}

	/**
	 * metodo que recoge los posibles extras que le puedan llegar, si recoge extras se encarga de eliminar los posibles
	 * view en los que no haya ningun valor para añadir
	 */
	private void recogerExtras() {
		if(getIntent().hasExtra(nombreExtraAlumno)) {
			this.alumno = (Alumno) getIntent().getSerializableExtra(nombreExtraAlumno);
			this.nombreAlumno.setText(this.alumno.getNombreAlumno() + " " + this.alumno.getPrimerApellidoAlumno() + " " + this.alumno.getSegundoApellidoAlumno());
			if(this.alumno.getEdad().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyEdadAlumno));
			else
				this.edad.setText(this.alumno.getEdad());
			if(this.alumno.getSexo().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lySexoAlumno));
			else
				this.sexo.setText(this.alumno.getSexo());
			if(this.alumno.getDireccion().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyDireccion));
			else
				this.direccion.setText(this.alumno.getDireccion());
			if(this.alumno.getCodigoPostal().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyCodigoPostal));
			else
				this.codigoPostal.setText(this.alumno.getCodigoPostal());
			if(this.alumno.getCorreoElectronico().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyCorreoElectronico));
			else
				this.correoElectronico.setText(this.alumno.getCorreoElectronico());
			if(this.alumno.getNacionalidad().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyNacionalidad));
			else
				this.nacionalidad.setText(this.alumno.getNacionalidad());
			if(this.alumno.getDni().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyDniAlumno));
			else
				this.dni.setText(this.alumno.getDni());
			if(this.alumno.getFechaNacimiento().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyFechaNacimientoAlumno));
			else
				this.fechaNacimiento.setText(this.alumno.getFechaNacimiento());
			if(this.alumno.getTelefono().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyTelefono));
			else
				this.telefono.setText(this.alumno.getTelefono());
			if(this.alumno.getTelefonoUrgencia().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyTelefonoUrgencia));
			else
				this.telefonoUrgencia.setText(this.alumno.getTelefonoUrgencia());
			if(this.alumno.getLocalidadNacimiento().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyLocalidadNacimiento));
			else
				this.localidadNacimiento.setText(this.alumno.getLocalidadNacimiento());
			if(this.alumno.getLocalidadResidencia().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyLocalidadResidencia));
			else
				this.localidadResidencia.setText(this.alumno.getLocalidadResidencia());
			if(this.alumno.getProvinciaNacimiento().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyLocalidadResidencia));
			else
				this.provinciaNacimiento.setText(this.alumno.getProvinciaNacimiento());
			if(this.alumno.getProvinciaResidencia().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyProvinciaResidencia));
			else
				this.provinciaResidencia.setText(this.alumno.getProvinciaResidencia());
			if(this.alumno.getPaisNacimiento().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyPaisNacimiento));
			else
				this.paisNacimiento.setText(this.alumno.getPaisNacimiento());
			if(this.alumno.getNombreTutor().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyTutor1));
			else
				this.tutor1.setText(this.alumno.getNombreTutor() + " " + this.alumno.getPrimerApellidoTutor() + " " +  this.alumno.getSegundoApellidoTutor());
			if(this.alumno.getDniTutor().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyDniTutor1));
			else
				this.dniTutor1.setText(this.alumno.getDniTutor());
			if(this.alumno.getNombreTutor2().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyTutor2));
			else
				this.tutor2.setText(this.alumno.getNombreTutor2() + " " + this.alumno.getPrimerApellidoTutor2() + " " +  this.alumno.getSegundoApellidoTutor2());
			if(this.alumno.getDniTutor2().equals(""))
				this.lyRoot.removeView(findViewById(R.id.lyDniTutor2));
			else
				this.dniTutor2.setText(this.alumno.getDniTutor2());
			
			//asigno el nombre del archivo imagen
			this.ruta_Archivo_Camera = this.alumno.getNumIdEscolar() + ".jpg";
			//asigno la ruta donde se va a guardar la imagen
			this.ruta_Camera +="/" + this.alumno.getUnidad() + "/";
			// si la imagen no existe, asigno la imagen por defecto y le pongo el tag de "no foto"
			this.f = new File(Environment.getExternalStorageDirectory() + this.ruta_Camera, this.ruta_Archivo_Camera);
			if(!this.f.exists()) {
				this.imgAlumno.setBackground(getResources().getDrawable(R.drawable.silueta));
				this.imgAlumno.setTag(TAG_FOTO);
			}
			// el archivo si existe, por tanto, cargo la imagen correspondiente
			else {
				Bitmap bm = BitmapFactory.decodeFile(this.f.getAbsolutePath());
				this.imgAlumno.setImageBitmap(Bitmap.createScaledBitmap(bm, 64, 64, false));
			}

		}
	}

	/**
	 * metodo que inicializa la vista
	 */
	private void init() {
		//Hago referencia a los componentes de la vista
		this.nombreAlumno = (TextView) findViewById(R.id.lblNombreAlumno2);
		this.edad = (TextView) findViewById(R.id.lblEdad);
		this.sexo = (TextView) findViewById(R.id.lblSexo);
		this.fechaNacimiento = (TextView) findViewById(R.id.lblFechaNacimiento);
		this.dni = (TextView) findViewById(R.id.lblDNI);
		this.correoElectronico = (TextView) findViewById(R.id.lblCorreoElectronico);
		this.codigoPostal = (TextView) findViewById(R.id.lblCodigoPostal);
		this.nacionalidad = (TextView) findViewById(R.id.lblNacionalidad);
		this.direccion = (TextView) findViewById(R.id.lblDireccion);
		this.telefono = (TextView) findViewById(R.id.lblTelefono);
		this.telefonoUrgencia = (TextView) findViewById(R.id.lblTelefonoUrgencia);
		this.localidadNacimiento = (TextView) findViewById(R.id.lblLocalidadNacimiento);
		this.localidadResidencia = (TextView) findViewById(R.id.lblLocalidadResidencia);
		this.provinciaNacimiento = (TextView) findViewById(R.id.lblProvinciaNacimiento);
		this.provinciaResidencia = (TextView) findViewById(R.id.lblProvinciaResidencia);
		this.paisNacimiento = (TextView) findViewById(R.id.lblPaisNacimiento);
		this.tutor1 = (TextView) findViewById(R.id.lblTutor1);
		this.dniTutor1 = (TextView) findViewById(R.id.lblDniTutor1);
		this.tutor2 = (TextView) findViewById(R.id.lblTutor2);
		this.dniTutor2 = (TextView) findViewById(R.id.lblDniTutor2);
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
	 * metodo que se lanza cuando se pulsa sobre la imagen del alumno y esta imageno no es la "imagen por defecto"
	 * @param ruta
	 */
	private void abrirImagen(String ruta) {
		Intent i = new Intent(this, Activity_Ver_Imagen_Alumno.class);
		i.putExtra(this.nombreExtraRutaFoto, ruta);
		i.putExtra(nombreExtraAlumno, this.alumno);
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
					this.imgAlumno.setImageBitmap(Bitmap.createScaledBitmap(bm, 64, 64, false));
				}
			}
			break;

		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}
}
