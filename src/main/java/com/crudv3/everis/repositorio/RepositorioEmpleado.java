package com.crudv3.everis.repositorio;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.crudv3.everis.modelo.Empleado;

@Repository
public interface RepositorioEmpleado extends MongoRepository<Empleado, String> {
  Optional<Empleado> findByDniContaining(String dni);

  boolean existsByDni(String dni);
}
