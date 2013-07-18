package com.lei.isenecaPhoto.Modelos;

import java.io.Serializable;

/**
 * clase modelo que representa un alumno del archivo CSV
 * @author Ivan
 *
 */
public class Alumno implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Cuando se refiere a Tutor se refiere al responsable del alumno(Padre/Madre/Tutor Legal)
	 * 
	 * El sexo puede ser "H" o "M"
	 * 
	 * la edad del alumno es la que tenga a 31/12 del año de la matricula
	 */
	
	//Campos
	private String alumno; //1
	private String dni; //1
	private String fechaNacimiento; //1
	private String curso;//3
	private String numExpedienteCentro;//3
	private String unidad;//3
	private String segundoApellidoAlumno;
	private String nombreAlumno;
	private String dniTutor; //2
	private String primerApellidoTutor; //2
	private String segundoApellidoTutor; //2
	private String nombreTutor; //2
	private String sexoTutor;//2
	private String dniTutor2;//2
	private String primerApellidoTutor2;//2
	private String segundoApellidoTutor2;//2
	private String nombreTutor2;//2
	private String sexoTutor2;//2
	private String localidadNacimiento; //1
	private String anoMatricula;//3
	private String numeroMatriculoEsteCurso;//3
	private String observacionesMatricula;//3
	private String provinciaNacimiento; //1
	private String paisNacimiento; //1
	private String edad; //1
	private String nacionalidad;//1
	private String sexo; //1
	private String primerApellidoAlumno;
	private String estadoMatricula;
	private String direccion; //1
	private String codigoPostal; //1
	private String localidadResidencia; //1
	private String provinciaResidencia; //1
	private String telefono; //1
	private String telefonoUrgencia; //1
	private String correoElectronico; //1
	private String numIdEscolar;//3

	//Constructor
	public Alumno() {
		super();
		this.alumno="";
		this.dni="";
		this.fechaNacimiento="";
		this.curso="";
		this.numExpedienteCentro="";
		this.unidad="";
		this.segundoApellidoAlumno="";
		this.nombreAlumno="";
		this.dniTutor="";
		this.primerApellidoTutor="";
		this.segundoApellidoTutor="";
		this.nombreTutor="";
		this.sexoTutor="";
		this.dniTutor2="";
		this.primerApellidoTutor2="";
		this.segundoApellidoTutor2="";
		this.nombreTutor2="";
		this.sexoTutor2="";
		this.localidadNacimiento="";
		this.anoMatricula="";
		this.numeroMatriculoEsteCurso="";
		this.observacionesMatricula="";
		this.provinciaNacimiento="";
		this.paisNacimiento="";
		this.edad="";
		this.nacionalidad="";
		this.sexo="";
		this.primerApellidoAlumno="";
		this.estadoMatricula="";
		this.direccion="";
		this.codigoPostal="";
		this.localidadResidencia="";
		this.provinciaResidencia="";
		this.telefono="";
		this.telefonoUrgencia="";
		this.correoElectronico="";
		this.numIdEscolar="";
	}
	
	//Propiedades
	public String getAlumno() {
		return alumno;
	}
	public void setAlumno(String alumno) {
		this.alumno = alumno;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getNumExpedienteCentro() {
		return numExpedienteCentro;
	}
	public void setNumExpedienteCentro(String numExpedienteCentro) {
		this.numExpedienteCentro = numExpedienteCentro;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public String getSegundoApellidoAlumno() {
		return segundoApellidoAlumno;
	}
	public void setSegundoApellidoAlumno(String segundoApellidoAlumno) {
		this.segundoApellidoAlumno = segundoApellidoAlumno;
	}
	public String getNombreAlumno() {
		return nombreAlumno;
	}
	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}
	public String getDniTutor() {
		return dniTutor;
	}
	public void setDniTutor(String dniTutor) {
		this.dniTutor = dniTutor;
	}
	public String getPrimerApellidoTutor() {
		return primerApellidoTutor;
	}
	public void setPrimerApellidoTutor(String primerApellidoTutor) {
		this.primerApellidoTutor = primerApellidoTutor;
	}
	public String getSegundoApellidoTutor() {
		return segundoApellidoTutor;
	}
	public void setSegundoApellidoTutor(String segundoApellidoTutor) {
		this.segundoApellidoTutor = segundoApellidoTutor;
	}
	public String getNombreTutor() {
		return nombreTutor;
	}
	public void setNombreTutor(String nombreTutor) {
		this.nombreTutor = nombreTutor;
	}
	public String getSexoTutor() {
		return sexoTutor;
	}
	public void setSexoTutor(String sexoTutor) {
		this.sexoTutor = sexoTutor;
	}
	public String getDniTutor2() {
		return dniTutor2;
	}
	public void setDniTutor2(String dniTutor2) {
		this.dniTutor2 = dniTutor2;
	}
	public String getPrimerApellidoTutor2() {
		return primerApellidoTutor2;
	}
	public void setPrimerApellidoTutor2(String primerApellidoTutor2) {
		this.primerApellidoTutor2 = primerApellidoTutor2;
	}
	public String getSegundoApellidoTutor2() {
		return segundoApellidoTutor2;
	}
	public void setSegundoApellidoTutor2(String segundoApellidoTutor2) {
		this.segundoApellidoTutor2 = segundoApellidoTutor2;
	}
	public String getNombreTutor2() {
		return nombreTutor2;
	}
	public void setNombreTutor2(String nombreTutor2) {
		this.nombreTutor2 = nombreTutor2;
	}
	public String getSexoTutor2() {
		return sexoTutor2;
	}
	public void setSexoTutor2(String sexoTutor2) {
		this.sexoTutor2 = sexoTutor2;
	}
	public String getLocalidadNacimiento() {
		return localidadNacimiento;
	}
	public void setLocalidadNacimiento(String localidadNacimiento) {
		this.localidadNacimiento = localidadNacimiento;
	}
	public String getAnoMatricula() {
		return anoMatricula;
	}
	public void setAnoMatricula(String anoMatricula) {
		this.anoMatricula = anoMatricula;
	}
	public String getNumeroMatriculoEsteCurso() {
		return numeroMatriculoEsteCurso;
	}
	public void setNumeroMatriculoEsteCurso(String numeroMatriculoEsteCurso) {
		this.numeroMatriculoEsteCurso = numeroMatriculoEsteCurso;
	}
	public String getObservacionesMatricula() {
		return observacionesMatricula;
	}
	public void setObservacionesMatricula(String observacionesMatricula) {
		this.observacionesMatricula = observacionesMatricula;
	}
	public String getProvinciaNacimiento() {
		return provinciaNacimiento;
	}
	public void setProvinciaNacimiento(String provinciaNacimiento) {
		this.provinciaNacimiento = provinciaNacimiento;
	}
	public String getPaisNacimiento() {
		return paisNacimiento;
	}
	public void setPaisNacimiento(String paisNacimiento) {
		this.paisNacimiento = paisNacimiento;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getPrimerApellidoAlumno() {
		return primerApellidoAlumno;
	}
	public void setPrimerApellidoAlumno(String primerApellidoAlumno) {
		this.primerApellidoAlumno = primerApellidoAlumno;
	}
	public String getEstadoMatricula() {
		return estadoMatricula;
	}
	public void setEstadoMatricula(String estadoMatricula) {
		this.estadoMatricula = estadoMatricula;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getLocalidadResidencia() {
		return localidadResidencia;
	}
	public void setLocalidadResidencia(String localidadResidencia) {
		this.localidadResidencia = localidadResidencia;
	}
	public String getProvinciaResidencia() {
		return provinciaResidencia;
	}
	public void setProvinciaResidencia(String provinciaResidencia) {
		this.provinciaResidencia = provinciaResidencia;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTelefonoUrgencia() {
		return telefonoUrgencia;
	}
	public void setTelefonoUrgencia(String telefonoUrgencia) {
		this.telefonoUrgencia = telefonoUrgencia;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getNumIdEscolar() {
		return numIdEscolar;
	}
	public void setNumIdEscolar(String numIdEscolar) {
		this.numIdEscolar = numIdEscolar;
	}

	@Override
	public String toString() {
		return "Registro [alumno=" + alumno + ", dni=" + dni
				+ ", fechaNacimiento=" + fechaNacimiento + ", curso=" + curso
				+ ", numExpedienteCentro=" + numExpedienteCentro + ", unidad="
				+ unidad + ", segundoApellidoAlumno=" + segundoApellidoAlumno
				+ ", nombreAlumno=" + nombreAlumno + ", dniTutor=" + dniTutor
				+ ", primerApellidoTutor=" + primerApellidoTutor
				+ ", segundoApellidoTutor=" + segundoApellidoTutor
				+ ", nombreTutor=" + nombreTutor + ", sexoTutor=" + sexoTutor
				+ ", dniTutor2=" + dniTutor2 + ", primerApellidoTutor2="
				+ primerApellidoTutor2 + ", segundoApellidoTutor2="
				+ segundoApellidoTutor2 + ", nombreTutor2=" + nombreTutor2
				+ ", sexoTutor2=" + sexoTutor2 + ", localidadNacimiento="
				+ localidadNacimiento + ", anoMatricula=" + anoMatricula
				+ ", numeroMatriculoEsteCurso=" + numeroMatriculoEsteCurso
				+ ", observacionesMatricula=" + observacionesMatricula
				+ ", provinciaNacimiento=" + provinciaNacimiento
				+ ", paisNacimiento=" + paisNacimiento + ", edad=" + edad
				+ ", nacionalidad=" + nacionalidad + ", sexo=" + sexo
				+ ", primerApellidoAlumno=" + primerApellidoAlumno
				+ ", estadoMatricula=" + estadoMatricula + ", direccion="
				+ direccion + ", codigoPostal=" + codigoPostal
				+ ", localidadResidencia=" + localidadResidencia
				+ ", provinciaResidencia=" + provinciaResidencia
				+ ", telefono=" + telefono + ", telefonoUrgencia="
				+ telefonoUrgencia + ", correoElectronico=" + correoElectronico
				+ ", numIdEscolar=" + numIdEscolar + "]";
	}
	
	
}
