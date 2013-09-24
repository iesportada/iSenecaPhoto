package com.lei.isenecaPhoto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.lei.isenecaPhoto.Adaptadores.AdaptadorAlumnos;
import com.lei.isenecaPhoto.Modelos.Alumno;
import com.lei.isenecaPhoto.Modelos.Grupo;

/**
 * clase que gestiona la vista "listado alumnos"
 * @author Ivan
 *
 */
public class Activity_Listado_Alumnos extends Activity {

	//Campos y Componentes
	private ListView lv;
	private Spinner spGrupos;
	private ArrayAdapter<String> adaptadorGrupos;
	private HashMap<String, Grupo> mapa;
	private ArrayList<String> grupos;
	private AdaptadorAlumnos adaptador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listado__alumnos);
		init();
	}

	/**
	 * metodo que inicializa la vista
	 */
	private void init() {
		this.spGrupos = (Spinner) findViewById(R.id.spGrupos);
		//obtengo el mapa de los grupos y saco las keys para mostrarlas en el spinner
		this.mapa = ((Aplicacion)this.getApplicationContext()).getGrupos();
		Iterator it = mapa.entrySet().iterator();
		this.grupos = new ArrayList<String>();
		while (it.hasNext())
			this.grupos.add(((Map.Entry)it.next()).getKey().toString());
		
		this.adaptadorGrupos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.grupos);
		this.adaptadorGrupos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spGrupos.setAdapter(adaptadorGrupos);
		this.spGrupos.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				//referencio el listview y cargo los nombres de los alumnos
				lv = (ListView) findViewById(R.id.lvAlumnos);
				String algo = adaptadorGrupos.getItem(arg2);
				ArrayList<Alumno> al = mapa.get(algo).getAlumnos();
				adaptador = new AdaptadorAlumnos(Activity_Listado_Alumnos.this,mapa.get(algo).getAlumnos());
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
						i.putExtra(CONSTANTES.EXTRA_ALUMNO, alumno);
						//lanzo el intent
						startActivity(i);
					}
				});
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
}
