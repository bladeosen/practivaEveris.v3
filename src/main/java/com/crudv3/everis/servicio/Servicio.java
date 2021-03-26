package com.crudv3.everis.servicio;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crudv3.everis.modelo.Empleado;
import com.crudv3.everis.repositorio.RepositorioEmpleado;

@Service
public class Servicio {

  @Autowired
  RepositorioEmpleado repositorioEmpleado;

  // Mostrar Todos los Empleados
  public List<Empleado> mostrarAllEmpleados() {
    // Busca todos los empleados
    return repositorioEmpleado.findAll();
  }/* Fin mostrar Todos los Empleados */

  // Mostrar Empleado por ID
  public Optional<Empleado> mostrarEmpleado(String id) {
    return repositorioEmpleado.findById(id);
  }/* Fin mostrar Empleado por ID */

  // Insertar Empleado
  public Empleado insertarEmpleado(Empleado empleado) {
    Empleado _empleado = repositorioEmpleado.save(
        new Empleado(
            empleado.getDni(),
            empleado.getNombre(),
            empleado.getApellidos(),
            empleado.getPosicion()));
    return _empleado;
  }/* Fin insertar Empleado */

  // Modificar Empleado por ID
  public Empleado modificarEmpleado(String id, Empleado empleado) {
    Optional<Empleado> datosEmpleados = repositorioEmpleado.findById(id); // Con el Optional evito que devuelva un null

    if (datosEmpleados.isPresent()) { // alternativa a la condición repositorioEmpleado.existsById(id)
      Empleado _empleado = datosEmpleados.get();
      _empleado.setDni(empleado.getDni());
      _empleado.setNombre(empleado.getNombre());
      _empleado.setApellidos(empleado.getApellidos());
      _empleado.setPosicion(empleado.getPosicion());
      return repositorioEmpleado.save(_empleado); // Si el usuario existe retornamos los datos
    }
    return null; // Si el empleado no existe retorna null
  }/* Fin modificar Empleado por ID */

  // Eliminar Empleado por ID
  public boolean deleteEmpleado(String id) {
    try {
      repositorioEmpleado.deleteById(id); // borramos empleado que coincida con el id
      if (!repositorioEmpleado.findById(id).isPresent()) { // Comprobamos que se ha borrado
        return true; // Devolvemos true si el empleado ha sido eliminado
      } else {
        return false;
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    return false;
  }/* Fin eliminar Empleado por ID */

  // Elimina Todos los empleados
  public boolean deleteAllEmpleados() {
    repositorioEmpleado.deleteAll();// borramos todos los empleados
    return true;
  }/* Fin elimina todos los empleados */
}
