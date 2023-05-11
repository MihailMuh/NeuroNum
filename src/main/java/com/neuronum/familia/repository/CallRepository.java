package com.neuronum.familia.repository;

import com.neuronum.familia.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRepository extends JpaRepository<Call, String> {
}
