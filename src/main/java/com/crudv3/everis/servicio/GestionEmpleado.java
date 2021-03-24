package com.crudv3.everis.servicio;

import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import com.crudv2.everis.modelo.Empleado;

@RestController
@RequestMapping("gestion")
public class GestionEmpleado {

  String auxDNI, campo, valor;

  @Autowired
  private MongoTemplate mongoTemplate;

  // Paso previo a buscar empleado por DNI
  @GetMapping("/pasarelaBus")
  public RedirectView pasarelaBus() {
    System.setProperty("java.awt.headless", "false");
    auxDNI = JOptionPane.showInputDialog("Introduce DNI del empleado a buscar:");
    return new RedirectView("/gestion/empleado");
  }

  // Buscar empleado por DNI
  @GetMapping("/empleado")
  public List<Empleado> buscarEmpleado() {
    Query query = new Query();
    query.addCriteria(Criteria.where("dni").is(auxDNI));
    return mongoTemplate.find(query, Empleado.class);
  }
  // Fin Buscar empleado por DNI

  // Mostrar todos los empleados
  @GetMapping("/empleados")
  public List<Empleado> EmpleadosTodos() {
    return mongoTemplate.findAll(Empleado.class);
  }

}
