package com.crudv3.everis.controlador;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crudv3.everis.modelo.Empleado;
import com.crudv3.everis.servicio.Servicio;

@RestController
@RequestMapping("/control")
public class ControladorWeb {

  @Autowired
  private Servicio servicio;

  // Mostrar todos los empleados
  @GetMapping("/empleados")
  public ResponseEntity<List<Empleado>> mostrarAllEmpleados() {

    return new ResponseEntity<>(servicio.mostrarAllEmpleados(), HttpStatus.OK); // Devuelve los empleados (es el body) y retorna el codigo
                                                                                // 200

  } /* Fin mostrar todos los empleados */

  // Consulta empleado por ID
  @GetMapping("/empleados/{id}") // Solicita y recupera el recurso, en este caso sera el empleado con ID xx
  public ResponseEntity<Empleado> mostrarEmpleado(@PathVariable("id") String id) {

    try {
      Optional<Empleado> datosEmpleado = servicio.mostrarEmpleado(id);
      if (datosEmpleado.isPresent()) { // Comprueba si existe, o si está presente, en este caso
        return new ResponseEntity<>(datosEmpleado.get(), HttpStatus.OK); // manda el body, retorna 200
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si falla la ejecucíon mostramos el código de error 404
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Manda el código de error 500 si falla la ejecución
    }
  } /* Fin consulta Empleado */

  // Insertar Empleado
  @PostMapping("/empleados")
  public ResponseEntity<Empleado> insertarEmpleado(@RequestBody Empleado empleado) {

    try {
      Empleado _empleado = servicio.insertarEmpleado(empleado);
      return new ResponseEntity<>(_empleado, HttpStatus.CREATED); // manda el body, Retorna 201
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Si falla la ejecucíon mostramos el código de error 500
    }
  }/* Fin Insertar Empleado */

  // Modifica empleado
  @PutMapping("/empleados/{id}")
  public ResponseEntity<Empleado> modificarEmpleado(@PathVariable("id") String id, @RequestBody Empleado empleado) {

    try {
      Empleado empleadoMod = servicio.modificarEmpleado(id, empleado); // Con el Optional evito que devuelva un null
      if (empleadoMod != null) { // Si el usuario no existe retorna null
        return new ResponseEntity<>(empleadoMod, HttpStatus.OK); // Retorna el 200 ya que devuelve la modificación
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si el empleadoMod es null lanza el NOT_FOUND
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Si falla la ejecucíon mostramos el código de error 500
    }

  }/* Fin Modifica empleado */

  // Eliminar Empleado por ID
  @DeleteMapping("/empleados/{id}")
  public ResponseEntity<HttpStatus> deleteEmpleado(@PathVariable("id") String id) {

    boolean result = servicio.deleteEmpleado(id);
    if (result) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT); // manda el body, retorna 204
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // Elimina Todos los empleados
  @DeleteMapping("/empleados")
  public ResponseEntity<HttpStatus> deleteAllEmpleados() {

    boolean deleteAll = servicio.deleteAllEmpleados();
    try {
      if (deleteAll) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);// Retorna 204
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no hay empleados para borrar manda un NOT_FOUND
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Codigo de error 500
    }
  }/* Fin elimina todos los empleados */
}
