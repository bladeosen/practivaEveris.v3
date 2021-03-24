package com.crudv3.everis.servicio;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crudv3.everis.modelo.Empleado;
import com.crudv3.everis.repositorio.RepositorioEmpleado;

@RestController
@RequestMapping("/control")
public class ControladorWeb {

  @Autowired
  RepositorioEmpleado repositorioEmpleado;

  // Insertar Empleado
  @PostMapping("/empleados")
  public ResponseEntity<Empleado> insertarEmpleado(@RequestBody Empleado empleado) { // Crea un objeto de la clase Empleado con sus
                                                                                     // atributos
    try {
      Empleado _empleado = repositorioEmpleado.save(new Empleado(empleado.getDni(), empleado.getNombre(),
          empleado.getApellidos(), empleado.getPosicion()));
      return new ResponseEntity<>(_empleado, HttpStatus.ACCEPTED); // Retorna 201
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }/* Fin Insertar Empleado */

  // Modifica empleado
  @PutMapping("/empleados/{id}")
  public ResponseEntity<Empleado> updateEmpleado(@PathVariable("id") String id, @RequestBody Empleado empleado) {
    Optional<Empleado> datosEmpleados = repositorioEmpleado.findById(id); // Con el Optional evito que devuelva un null

    if (datosEmpleados.isPresent()) { // alternativa a la condición repositorioEmpleado.existsById(id)
      Empleado _empleado = datosEmpleados.get();
      _empleado.setDni(empleado.getDni());
      _empleado.setNombre(empleado.getNombre());
      _empleado.setApellidos(empleado.getApellidos());
      _empleado.setPosicion(empleado.getPosicion());
      return new ResponseEntity<>(repositorioEmpleado.save(_empleado), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }/* Fin Modifica empleado */

}
