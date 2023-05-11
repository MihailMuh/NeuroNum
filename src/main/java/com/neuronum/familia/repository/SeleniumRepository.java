package com.neuronum.familia.repository;

import com.neuronum.selentity.SeleniumData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeleniumRepository extends JpaRepository<SeleniumData, String> {
}
