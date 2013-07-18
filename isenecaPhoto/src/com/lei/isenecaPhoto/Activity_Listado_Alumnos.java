package com.lei.isenecaPhoto;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lei.isenecaPhoto.Adaptadores.AdaptadorAlumnos;
import com.lei.isenecaPhoto.Modelos.Alumno;

/**
 * clase que gestiona la vista "listado alumnos"
 * @author Ivan
 *
 */
public class Activity_Listado_Alumnos extends Activity {

	//Campos y Componentes
	private ListView lv;
	private AdaptadorAlumnos adaptador;
	private String nombreExtraAlumnos ="alumnos";
	private String nombreExtraAlumno ="alumno";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listado__alumnos);
		init();
	}

	/**
	 * metodo que inicializa la vista
	 */
	@SuppressWarnings("unchecked")
	private void init() {
		// pregunto si la actividad tiene extras para recoger
		if(getIntent().hasExtra(nombreExtraAlumnos)) { // es el extra del alumno
			//referencio el listview y cargo los nombres de los alumnos
			this.lv = (ListView) findViewById(R.id.lvAlumnos);
			this.adaptador = new AdaptadorAlumnos(this, (ArrayList<Alumno>) getIntent().getSerializableExtra(nombreExtraAlumnos));
			lv.setAdapter(adaptador);
			//asigno el evento para cuando pulse en un alumno cargar sus datos
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
					//recogo el alumno
					Alumno alumno = adaptador.getItem(arg2);
					//creo el intent
					Intent i = new Intent(Activity_Listado_Alumnos.this, Activity_Detalle_Alumno.class);
					//adjunto el alumno
					i.putExtra(nombreExtraAlumno, alumno);
					//lanzo el intent
					startActivity(i);
				}
			});
		}
	}
}
