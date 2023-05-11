package com.neuronum.userservice.repository;

import com.neuronum.userservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
    Client findByAccessKey(String key);
}
