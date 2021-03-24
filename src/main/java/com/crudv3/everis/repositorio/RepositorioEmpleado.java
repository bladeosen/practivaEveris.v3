package com.crudv3.everis.repositorio;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.crudv3.everis.modelo.Empleado;

@Repository
public interface RepositorioEmpleado extends MongoRepository<Empleado, String> {
  List<Empleado> findByNombreContaining(String nombre);
}
