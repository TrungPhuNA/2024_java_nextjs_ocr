package be.ocrapi.repository;

import be.ocrapi.model.EmployerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerTypeRepository extends JpaRepository<EmployerType, Integer> {



}