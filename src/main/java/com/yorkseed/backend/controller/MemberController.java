package com.yorkseed.backend.controller;

import com.yorkseed.backend.dto.MemberCardDto;
import com.yorkseed.backend.dto.PageResponse;
import com.yorkseed.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public PageResponse<MemberCardDto> listMembers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return memberService.listMembers(role, q, page, size);
    }
}
