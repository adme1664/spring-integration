package com.adme.learning.cloudstream.repository;

import com.adme.learning.cloudstream.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
}
