package com.neuronum.userservice.repository;

import com.neuronum.userservice.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository extends JpaRepository<Key, String> {
}
