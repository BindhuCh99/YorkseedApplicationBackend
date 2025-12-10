package com.yorkseed.backend.service;

//import com.yorkseed.backend.Interface.EmailService;
import com.yorkseed.backend.dto.LoginRequest;
import com.yorkseed.backend.dto.LoginResponse;
import com.yorkseed.backend.dto.SignupRequest;
import com.yorkseed.backend.exception.ApplicationException;
import com.yorkseed.backend.model.*;
import com.yorkseed.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final EmailService emailService;

    @Value("${app.email.verification-token-expiry-hours:24}")
    private int emailVerificationTokenExpiryHours;

    public User registerUser(SignupRequest signupRequest) {
        // Uniqueness check
        final String email = safe(signupRequest.getEmail()).toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new ApplicationException.EmailAlreadyExists("Email is already in use!");
        }

        // ---- Base User ----
        User user = new User();
        user.setRole(signupRequest.getRole());
        user.setFirstName(safe(signupRequest.getFirstName()));
        user.setLastName(safe(signupRequest.getLastName()));
        user.setEmail(email);
        user.setPassword(safe(signupRequest.getPassword())); // TODO: hash later
        user.setEmailVerified(true);

        // Location (from signup page: locationCity/locationState/locationCountry)
        user.setCity(safe(signupRequest.getCity()));
        user.setState(safe(signupRequest.getState()));
        user.setCountry(safe(signupRequest.getCountry()));

        // LinkedIn (optional)
        user.setLinkedin(normalizeUrl(signupRequest.getLinkedin()));

        // ---- Role-specific ----
        if (signupRequest.getRole() == UserRole.FOUNDER) {
            Founder founder = new Founder();
            founder.setProblemStatement(safe(signupRequest.getProblemStatement()));
            founder.setStartupStage(safe(signupRequest.getStartupStage()));
            founder.setHasCoFounderOrTeam(safe(signupRequest.getHasCoFounderOrTeam()));
            founder.setIndustrySector(safe(signupRequest.getIndustrySector()));
            founder.setWebsiteUrl(normalizeUrl(signupRequest.getWebsiteUrl()));

            // Extra founder fields (from updated signup page)
            founder.setCompanyName(safe(signupRequest.getCompanyName()));
            founder.setAboutCompany(safe(signupRequest.getAboutCompany()));
            founder.setLookingFor(safe(signupRequest.getLookingFor()));
            founder.setValueProposition(safe(signupRequest.getValueProposition()));
            founder.setRegion(safe(signupRequest.getRegion()));

            founder.setUser(user);
            user.setFounderInfo(founder);

        } else if (signupRequest.getRole() == UserRole.INVESTOR) {
            Investor investor = new Investor();
            investor.setInvestmentFocus(safe(signupRequest.getInvestmentFocus()));   // merged focus
            investor.setInvestmentStage(safe(signupRequest.getInvestmentStage()));
            investor.setInvestmentSize(safe(signupRequest.getInvestmentSize()));
            investor.setPreviousInvestments(safe(signupRequest.getPreviousInvestments()));

            // Extra investor fields (from updated signup page)
            investor.setFirmName(safe(signupRequest.getFirmName()));
            investor.setFirmWebsite(normalizeUrl(signupRequest.getFirmWebsite()));
            investor.setInvestmentThesis(safe(signupRequest.getInvestmentThesis()));
            investor.setValueAdd(safe(signupRequest.getValueAdd()));
            investor.setPreferredRegions(safe(signupRequest.getPreferredRegions()));

            investor.setUser(user);
            user.setInvestorInfo(investor);
        }

        // Email verification token
//        String token = UUID.randomUUID().toString();
//        user.setEmailVerificationToken(token);
//        user.setEmailVerificationTokenExpiry(LocalDateTime.now().plusHours(emailVerificationTokenExpiryHours));

        // Persist & notify
        User saved = userRepository.save(user);
        //emailService.sendVerificationEmail(saved.getEmail(), token);
        return saved;
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(safe(loginRequest.getEmail()).toLowerCase());
        if (userOptional.isEmpty()) {
            return LoginResponse.error("Invalid email or password");
        }

        User user = userOptional.get();

        if (!user.isEmailVerified()) {
            return LoginResponse.error("Email not verified. Please verify your email before logging in.");
        }
        if (!safe(loginRequest.getPassword()).equals(user.getPassword())) {
            return LoginResponse.error("Invalid email or password");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return LoginResponse.success("Login successful", user);
    }

    public boolean verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new ApplicationException.ResourceNotFound("Invalid verification token"));

        if (user.isEmailVerified()) return true;

        if (LocalDateTime.now().isAfter(user.getEmailVerificationTokenExpiry())) {
            throw new ApplicationException.BadRequest("Verification token has expired");
        }

        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationTokenExpiry(null);
        userRepository.save(user);
        return true;
    }

    /* ----------------- helpers ----------------- */

    private static String safe(String v) {
        return v == null ? "" : v.trim();
    }

    private static String normalizeUrl(String v) {
        String s = safe(v);
        if (s.isEmpty()) return s;
        try {
            URI uri = new URI(s);
            if (uri.getScheme() == null) return "https://" + s;
            return uri.toString();
        } catch (Exception ignored) {
            return s;
        }
    }
}
