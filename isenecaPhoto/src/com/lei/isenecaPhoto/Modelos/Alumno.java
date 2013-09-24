package com.lei.isenecaPhoto.Modelos;

import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * clase modelo que representa un alumno del archivo CSV
 * @author Ivan
 *
 */
public class Alumno implements Parcelable {
	
	//Campos
	private HashMap<String, String> mapa;
	public static final Parcelable.Creator<Alumno> CREATOR = new Parcelable.Creator<Alumno>() {

        public Alumno createFromParcel(Parcel in) {
            return new Alumno(in);
        }

        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };
	
	public Alumno() {
		super();
		this.mapa = new HashMap<String, String>();
	}
	
	public Alumno(Parcel parcelable) {
		super();
		readFromParcelable(parcelable);
	}

	public HashMap<String, String> getMapa() {
		return mapa;
	}

	public void setMapa(HashMap<String, String> mapa) {
		this.mapa = mapa;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeMap(this.mapa);
	}
	
	private void readFromParcelable(Parcel parcelable) {
		this.mapa = parcelable.readHashMap(Alumno.class.getClassLoader());
	}
}
