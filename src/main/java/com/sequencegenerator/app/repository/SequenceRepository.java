package com.sequencegenerator.app.repository;

import com.sequencegenerator.app.model.domain.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SequenceRepository extends JpaRepository<Sequence, UUID> {

}
