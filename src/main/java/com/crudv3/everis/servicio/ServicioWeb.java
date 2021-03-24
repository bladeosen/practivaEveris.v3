package com.crudv3.everis.servicio;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crudv3.everis.modelo.Empleado;
import com.crudv3.everis.repositorio.RepositorioEmpleado;

@RestController
@RequestMapping("/control")
public class ServicioWeb {

  @Autowired
  RepositorioEmpleado repositorioEmpleado;

  // Insertar Empleado
  @PostMapping("/empleados")
  public ResponseEntity<Empleado> insertarEmpleado(@RequestBody Empleado empleado) { // Crea un objeto de la clase Empleado con sus
                                                                                     // atributos
    try {
      Empleado _empleado = repositorioEmpleado.save(
          new Empleado(
              empleado.getDni(),
              empleado.getNombre(),
              empleado.getApellidos(),
              empleado.getPosicion()));
      return new ResponseEntity<>(_empleado, HttpStatus.CREATED); // manda el body, Retorna 201
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Si falla la ejecucíon mostramos el código de error 500
    }
  }/* Fin Insertar Empleado */

  // Modifica empleado
  @PutMapping("/empleados/{id}")
  public ResponseEntity<Empleado> modificarEmpleado(@PathVariable("id") String id, @RequestBody Empleado empleado) {
    Optional<Empleado> datosEmpleados = repositorioEmpleado.findById(id); // Con el Optional evito que devuelva un null

    try {
      if (datosEmpleados.isPresent()) { // alternativa a la condición repositorioEmpleado.existsById(id)
        Empleado _empleado = datosEmpleados.get();
        _empleado.setDni(empleado.getDni());
        _empleado.setNombre(empleado.getNombre());
        _empleado.setApellidos(empleado.getApellidos());
        _empleado.setPosicion(empleado.getPosicion());
        return new ResponseEntity<>(repositorioEmpleado.save(_empleado), HttpStatus.OK); // Retorna el 200 ya que devuelve la modificación
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si falla la ejecucíon mostramos el código de error 404
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Si falla la ejecucíon mostramos el código de error 500
    }

  }/* Fin Modifica empleado */

  // Consulta empleado
  @GetMapping("/empleados/{id}") // Solicita y recupera el recurso, en este caso sera el empleado con ID xx
  public ResponseEntity<Empleado> mostrarEmpleado(@PathVariable("id") String id) {
    Optional<Empleado> datosEmpleados = repositorioEmpleado.findById(id); // Evitamos que nos de un null con el Optional

    try {
      if (datosEmpleados.isPresent()) { // Comprueba si existe, o si está presente, en este caso
        return new ResponseEntity<>(datosEmpleados.get(), HttpStatus.OK); // manda el body, retorna 200
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si falla la ejecucíon mostramos el código de error 404
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Manda el código de error 500 si falla la ejecución
    }
  } /* Fin consulta Empleado */

  // Elimina Empleado por ID
  @DeleteMapping("/empleados/{id}") // Borra recurso, en este caso, el empleado con cierta ID
  public ResponseEntity<HttpStatus> deleteEmpleado(@PathVariable("id") String id) {

    try {
      repositorioEmpleado.deleteById(id); // borramos empleado que coincida con el id
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);// Retorna 204, ya que devuelve vacío
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Codigo de error 500
    }
  }/* Fin elimina Empleado por ID */

  // Elimina Todos los empleados
  @DeleteMapping("/empleados")
  public ResponseEntity<HttpStatus> deleteAllEmpleados() {

    try {
      repositorioEmpleado.deleteAll();// borramos todos los empleados
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);// Retorna 204
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Codigo de error 500
    }
  }/* Fin elimina todos los empleados */

  @GetMapping("/empleados")
  public ResponseEntity<List<Empleado>> getAllEmpleados(@RequestParam(required = false) String dni) {
    try {
      List<Empleado> empleados = new ArrayList<Empleado>();
      String mensaje = "No hay ningún empleado en la BBDD.";
      repositorioEmpleado.findAll().forEach(empleados::add); // Busca todos los empleados, los recorre y los agrega al List (con el ::add)

      if (empleados.isEmpty()) { // Comprueba si está vacío
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(empleados, HttpStatus.OK); // Devuelve los empleados (es el body) y retorna el codigo 200
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Codigo de error 500
    }
  }
}
