package com.yorkseed.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "investor_info")
public class Investor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------- Core required fields (from signup page) ----------
    @NotBlank
    private String investmentFocus;        // From frontend: merged "industryFocus" + "investmentType"

    @NotBlank
    private String investmentStage;        // Investing stage dropdown (Idea/Seed/Series A...)

    @NotBlank
    private String investmentSize;         // Typical check size

    @Column(columnDefinition = "TEXT")
    private String previousInvestments;    // From 'portfolio' field — examples of companies invested in

    // ---------- Optional extended fields (from signup page) ----------
    @Column(length = 150)
    private String firmName;               // e.g., "Ark Kapital"

    @Column(length = 512)
    private String firmWebsite;            // e.g., https://arkkapital.com

    @Column(columnDefinition = "TEXT")
    private String investmentThesis;       // Short thesis text from signup

    @Column(columnDefinition = "TEXT")
    private String valueAdd;               // Value add beyond capital (e.g., GTM help, hiring)

    @Column(length = 255)
    private String preferredRegions;       // e.g., US, EU, Global

    // ---------- Relations ----------
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
