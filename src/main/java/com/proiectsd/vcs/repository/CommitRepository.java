package com.proiectsd.vcs.repository;

import com.proiectsd.vcs.model.Branch;
import com.proiectsd.vcs.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommitRepository extends JpaRepository<Commit, Long> {
    @Modifying
    @Transactional
    void deleteById(Long id);
}
