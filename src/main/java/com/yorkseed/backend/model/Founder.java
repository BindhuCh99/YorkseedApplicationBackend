package com.yorkseed.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "founder_info")
public class Founder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ----- Required (from signup page) -----
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String problemStatement;      // "problem" textarea

    @NotBlank
    private String startupStage;          // stage select (idea/pre-seed/seed/...)

    @NotBlank
    private String hasCoFounderOrTeam;    // hasTeam radio (yes/no)

    @NotBlank
    private String industrySector;        // sector input (e.g., FinTech/AI/Healthtech)

    // ----- Optional (from signup page extras) -----
    @Column(length = 120)
    private String companyName;           // companyName

    @Column(length = 512)
    private String websiteUrl;            // website

    @Column(columnDefinition = "TEXT")
    private String aboutCompany;          // aboutCompany textarea

    @Column(length = 255)
    private String lookingFor;            // lookingFor (Investors/Advisors/Co-founders)

    @Column(length = 255)
    private String valueProposition;      // valueProposition (e.g., "AI-enabled, 10x faster")

    @Column(length = 120)
    private String region;                // region (e.g., North America, Global)

    // ----- Relation -----
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
