package com.proiectsd.vcs.repository;

import com.proiectsd.vcs.model.DeltaSimulate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface DeltaSimulateRepository extends JpaRepository<DeltaSimulate, Long> {
    @Modifying
    @Transactional
    void deleteById(Long id);

}
