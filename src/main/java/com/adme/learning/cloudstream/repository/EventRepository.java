package com.adme.learning.cloudstream.repository;

import com.adme.learning.cloudstream.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
}
