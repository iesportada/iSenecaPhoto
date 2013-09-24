package com.lei.isenecaPhoto.Adaptadores;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lei.isenecaPhoto.R;
import com.lei.isenecaPhoto.Modelos.Archivo;

public class AdaptadorArchivos extends ArrayAdapter<Archivo>{

	private Context contexto;
	private ArrayList<Archivo>archivos;
	
	public AdaptadorArchivos(Context context,ArrayList<Archivo> datos) {
		super(context, android.R.layout.simple_list_item_1, datos);
		this.contexto = context;
		this.archivos = datos;
	}
	public Archivo getItem(int i)
	 {
		 return archivos.get(i);
	 }
	
	 @Override
       public View getView(int position, View convertView, ViewGroup parent) {
               View item = convertView;
               HolderArchivo holder;
               if (item == null) {
            	   item = ((Activity)this.contexto).getLayoutInflater().inflate(R.layout.list_item_archivos, null);
            	   holder = new HolderArchivo();
            	   holder.setNombreArchivo((TextView) item.findViewById(R.id.TextView01));
            	   holder.setDato((TextView) item.findViewById(R.id.TextView02));
            	   holder.setFecha((TextView) item.findViewById(R.id.TextViewDate));
            	   holder.setImagen((ImageView) item.findViewById(R.id.fd_Icon1));
            	   item.setTag(holder);
               }
               else
            	   holder = (HolderArchivo) item.getTag();
               
               holder.getNombreArchivo().setText(archivos.get(position).getNombre());
               holder.getDato().setText(this.archivos.get(position).getDato());
               holder.getFecha().setText(this.archivos.get(position).getFecha());
               String uri = "drawable/" + this.archivos.get(position).getImagen();
          	   int imageResource = this.contexto.getResources().getIdentifier(uri, null, this.contexto.getPackageName());
          	   holder.getImagen().setImageDrawable(this.contexto.getResources().getDrawable(imageResource));
          	   
               return item;
       }

}
