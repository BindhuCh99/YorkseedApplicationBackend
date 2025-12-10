package com.yorkseed.backend.repository;

import com.yorkseed.backend.model.Founder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FounderInfoRepository extends JpaRepository<Founder, Long> {
    // Custom query methods can be added here if needed
}
