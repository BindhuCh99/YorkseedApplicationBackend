package com.yorkseed.backend.dto;

import com.yorkseed.backend.model.UserRole;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {

    // ---------- Basic Info ----------
    @NotBlank @Size(max = 50)
    private String firstName;

    @NotBlank @Size(max = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String email;

    @NotBlank @Size(min = 6, max = 40)
    private String password;

    @NotNull
    private UserRole role;

    // ---------- Location (accept both city/* and location* keys) ----------
    @NotBlank @Size(max = 80)
    @JsonAlias({"locationCity"})
    private String city;

    @NotBlank @Size(max = 80)
    @JsonAlias({"locationState"})
    private String state;

    @NotBlank @Size(max = 80)
    @JsonAlias({"locationCountry"})
    private String country;

    // ---------- LinkedIn (optional) ----------
    @Size(max = 200)
    @Pattern(
            regexp = "^$|^(https?://)?(www\\.)?linkedin\\.com/(in|pub|company)/[A-Za-z0-9._%+-/]+/?$",
            message = "Please provide a valid LinkedIn URL (e.g., https://www.linkedin.com/in/yourprofile)"
    )
    private String linkedin;

    // ---------- Founder Specific Fields ----------
    private String problemStatement;      // textarea
    private String startupStage;          // dropdown
    private String hasCoFounderOrTeam;    // yes/no
    private String industrySector;        // text input
    private String companyName;           // optional
    private String websiteUrl;            // optional
    private String aboutCompany;          // textarea
    private String lookingFor;            // optional
    private String valueProposition;      // optional
    private String region;                // optional

    // ---------- Investor Specific Fields ----------
    // Frontend merges industryFocus + investmentType into this
    private String investmentFocus;
    private String investmentStage;       // dropdown
    private String investmentSize;        // check size
    private String previousInvestments;   // text input
    private String firmName;              // optional
    private String firmWebsite;           // optional
    private String investmentThesis;      // optional
    private String valueAdd;              // optional
    private String preferredRegions;      // optional
}
