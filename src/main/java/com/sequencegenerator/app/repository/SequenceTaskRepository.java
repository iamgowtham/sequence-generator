package com.sequencegenerator.app.repository;

import com.sequencegenerator.app.model.domain.SequenceTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SequenceTaskRepository extends JpaRepository<SequenceTask, UUID> {

}
