package com.yorkseed.backend.dto;

import com.yorkseed.backend.model.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberCardDto {
    private Long id;
    private String name;
    private UserRole role;

    // -------------------- Location & Contact --------------------
    private String city;
    private String state;
    private String country;
    private String linkedin;

    // -------------------- Founder-specific fields --------------------
    private String problemStatement;
    private String startupStage;
    private String hasCoFounderOrTeam;
    private String industrySector;
    private String websiteUrl;
    private String companyName;
    private String aboutCompany;
    private String lookingFor;
    private String valueProposition;
    private String region;

    // -------------------- Investor-specific fields --------------------
    private String investmentFocus;
    private String investmentStage;
    private String investmentSize;
    private String previousInvestments;
    private String firmName;
    private String firmWebsite;
    private String investmentThesis;
    private String valueAdd;
    private String preferredRegions;
}
