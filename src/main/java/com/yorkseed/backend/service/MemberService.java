package com.yorkseed.backend.service;

import com.yorkseed.backend.dto.MemberCardDto;
import com.yorkseed.backend.dto.PageResponse;
import com.yorkseed.backend.model.User;
import com.yorkseed.backend.model.UserRole;
import com.yorkseed.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;

    public PageResponse<MemberCardDto> listMembers(String roleParam, String q, int page, int size) {
        UserRole role = null;
        if (roleParam != null && !roleParam.isBlank()) {
            role = UserRole.valueOf(roleParam.toUpperCase());
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        var results = userRepository.searchMembers(
                role,
                (q == null || q.isBlank()) ? null : q.trim(),
                pageable
        );

        List<MemberCardDto> items = results.getContent().stream()
                .map(this::toDto)
                .toList();

        return new PageResponse<>(items, results.getTotalElements(), results.getNumber(), results.getSize());
    }

    private String fmt(String s) { return s == null ? "" : s.trim(); }

    // (Not currently used by the DTO since frontend composes city/state/country)
    private String formatLocation(User u) {
        return String.join(", ",
                fmt(u.getCity()),
                fmt(u.getState()),
                fmt(u.getCountry())
        ).replaceAll("(^,\\s*|,\\s*,)", "").replaceAll(",\\s*$", "");
    }

    private MemberCardDto toDto(User u) {
        var builder = MemberCardDto.builder()
                .id(u.getId())
                .name(((u.getFirstName() == null ? "" : u.getFirstName()) + " " +
                        (u.getLastName()  == null ? "" : u.getLastName())).trim())
                .role(u.getRole())
                .city(u.getCity())
                .state(u.getState())
                .country(u.getCountry())
                .linkedin(u.getLinkedin());

        if (u.getFounderInfo() != null) {
            var f = u.getFounderInfo();
            builder
                    .problemStatement(f.getProblemStatement())
                    .startupStage(f.getStartupStage())
                    .hasCoFounderOrTeam(f.getHasCoFounderOrTeam())
                    .industrySector(f.getIndustrySector())
                    .websiteUrl(f.getWebsiteUrl())
                    // newly added founder fields
                    .companyName(f.getCompanyName())
                    .aboutCompany(f.getAboutCompany())
                    .lookingFor(f.getLookingFor())
                    .valueProposition(f.getValueProposition())
                    .region(f.getRegion());
        }

        if (u.getInvestorInfo() != null) {
            var i = u.getInvestorInfo();
            builder
                    .investmentFocus(i.getInvestmentFocus())
                    .investmentStage(i.getInvestmentStage())
                    .investmentSize(i.getInvestmentSize())
                    .previousInvestments(i.getPreviousInvestments())
                    // newly added investor fields
                    .firmName(i.getFirmName())
                    .firmWebsite(i.getFirmWebsite())
                    .investmentThesis(i.getInvestmentThesis())
                    .valueAdd(i.getValueAdd())
                    .preferredRegions(i.getPreferredRegions());
        }

        return builder.build();
    }
}
