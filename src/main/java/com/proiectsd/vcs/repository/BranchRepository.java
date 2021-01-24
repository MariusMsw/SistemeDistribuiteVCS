package com.proiectsd.vcs.repository;

import com.proiectsd.vcs.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {

}
