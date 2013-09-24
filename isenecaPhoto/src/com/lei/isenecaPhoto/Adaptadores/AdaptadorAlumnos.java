package com.lei.isenecaPhoto.Adaptadores;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lei.isenecaPhoto.R;
import com.lei.isenecaPhoto.Modelos.Alumno;

/**
 * clase que gestiona los alumnos en el listview de los alumnos
 * @author Ivan
 *
 */
public class AdaptadorAlumnos extends ArrayAdapter<Alumno> {

	//Campos
	private Context contexto;
	private ArrayList<Alumno> alumnos;
	
	//Constructor
	public AdaptadorAlumnos(Context context,ArrayList<Alumno> alumnos) {
		super(context, android.R.layout.simple_list_item_1,alumnos);
		this.contexto = context;
		this.alumnos = alumnos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		HolderAlumno holder;
		
		//cuando la vista no esta creada todavia
		if( item == null) {
			item = ((Activity)this.contexto).getLayoutInflater().inflate(R.layout.list_item_alumno, null);
			holder = new HolderAlumno();
			holder.setNombreAlumno((TextView) item.findViewById(R.id.lblNombreAlumno));
			item.setTag(holder);
		}
		// la vista si estaba creada
		else {
			holder = (HolderAlumno) item.getTag();
		}
		//asigno el nombre
		holder.getNombreAlumno().setText(this.alumnos.get(position).getMapa().get("Alumno/a"));
		//devuelvo la vista
		return item;
	}

}
