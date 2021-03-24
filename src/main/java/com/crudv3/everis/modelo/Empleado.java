package com.crudv3.everis.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "empleados")
public class Empleado {

  @Id
  private String id;

  private String dni;

  private String nombre;

  private String apellidos;

  private String posicion;

  public Empleado() {

  }

  public Empleado(String dni, String nombre, String apellidos, String posicion) {
    this.dni = dni;
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.posicion = posicion;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getPosicion() {
    return posicion;
  }

  public void setPosicion(String posicion) {
    this.posicion = posicion;
  }

  @Override
  public String toString() {
    return "Empleado [id=" + id + ", DNI=" + dni + ", Nombre=" + nombre + ", Apellidos=" + apellidos + ", Posicion=" + posicion + "]";
  }
}
