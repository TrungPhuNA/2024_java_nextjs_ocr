package be.ocrapi.repository;

import be.ocrapi.model.Certificate;
import be.ocrapi.model.WorkingTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingTaskRepository extends JpaRepository<WorkingTasks, Integer> {



}