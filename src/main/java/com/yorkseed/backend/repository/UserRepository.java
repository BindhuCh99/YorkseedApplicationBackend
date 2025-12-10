package com.yorkseed.backend.repository;

import com.yorkseed.backend.model.User;
import com.yorkseed.backend.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailVerificationToken(String token);

    @Query(
            value = """
            SELECT DISTINCT u
            FROM User u
            LEFT JOIN u.founderInfo f
            LEFT JOIN u.investorInfo i
            WHERE (:role IS NULL OR u.role = :role)
              AND u.emailVerified = true
              AND (
                   :q IS NULL
                OR LOWER(CONCAT(COALESCE(u.firstName,''),' ',COALESCE(u.lastName,''))) LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(u.city,''))                 LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(u.state,''))                LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(u.country,''))              LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(u.linkedin,''))             LIKE LOWER(CONCAT('%',:q,'%'))

       
                OR LOWER(COALESCE(f.problemStatement,''))     LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.startupStage,''))         LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.hasCoFounderOrTeam,''))   LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.industrySector,''))       LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.websiteUrl,''))           LIKE LOWER(CONCAT('%',:q,'%'))
           
                OR LOWER(COALESCE(f.companyName,''))          LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.aboutCompany,''))         LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.lookingFor,''))           LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.valueProposition,''))     LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.region,''))               LIKE LOWER(CONCAT('%',:q,'%'))

            
                OR LOWER(COALESCE(i.investmentFocus,''))      LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.investmentStage,''))      LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.investmentSize,''))       LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.previousInvestments,''))  LIKE LOWER(CONCAT('%',:q,'%'))
            
                OR LOWER(COALESCE(i.firmName,''))             LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.firmWebsite,''))          LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.investmentThesis,''))     LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.valueAdd,''))             LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.preferredRegions,''))     LIKE LOWER(CONCAT('%',:q,'%'))
              )
            """,
            countQuery = """
            SELECT COUNT(DISTINCT u)
            FROM User u
            LEFT JOIN u.founderInfo f
            LEFT JOIN u.investorInfo i
            WHERE (:role IS NULL OR u.role = :role)
              AND u.emailVerified = true
              AND (
                   :q IS NULL
                OR LOWER(CONCAT(COALESCE(u.firstName,''),' ',COALESCE(u.lastName,''))) LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(u.city,''))                 LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(u.state,''))                LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(u.country,''))              LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(u.linkedin,''))             LIKE LOWER(CONCAT('%',:q,'%'))

                OR LOWER(COALESCE(f.problemStatement,''))     LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.startupStage,''))         LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.hasCoFounderOrTeam,''))   LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.industrySector,''))       LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.websiteUrl,''))           LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.companyName,''))          LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.aboutCompany,''))         LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.lookingFor,''))           LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.valueProposition,''))     LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(f.region,''))               LIKE LOWER(CONCAT('%',:q,'%'))

                OR LOWER(COALESCE(i.investmentFocus,''))      LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.investmentStage,''))      LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.investmentSize,''))       LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.previousInvestments,''))  LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.firmName,''))             LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.firmWebsite,''))          LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.investmentThesis,''))     LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.valueAdd,''))             LIKE LOWER(CONCAT('%',:q,'%'))
                OR LOWER(COALESCE(i.preferredRegions,''))     LIKE LOWER(CONCAT('%',:q,'%'))
              )
            """
    )
    Page<User> searchMembers(@Param("role") UserRole role,
                             @Param("q") String q,
                             Pageable pageable);
}
