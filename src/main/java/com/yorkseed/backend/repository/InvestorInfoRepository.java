package com.yorkseed.backend.repository;

import com.yorkseed.backend.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorInfoRepository extends JpaRepository<Investor, Long> {
    // Custom query methods can be added here if needed
}
