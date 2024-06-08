package com.uyghurjava.book.auth;

import com.uyghurjava.book.email.EmailService;
import com.uyghurjava.book.email.EmailTemplateName;
import com.uyghurjava.book.exception.ActivationTokenExpiredException;
import com.uyghurjava.book.exception.UserNotFoundWithGivenUserId;
import com.uyghurjava.book.exception.ValidTokenNotFoundException;
import com.uyghurjava.book.role.RoleRepository;
import com.uyghurjava.book.security.JwtService;
import com.uyghurjava.book.user.User;
import com.uyghurjava.book.user.UserRepository;
import com.uyghurjava.book.user.token.Token;
import com.uyghurjava.book.user.token.TokenRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    // authentication manager is used to authenticate the user,it is provided by spring security by default,
    // it should create a bean in BeansConfig class
    private final AuthenticationManager authenticationManager;

    @Value("${application.mail.frontend.activation-url}")
    private String activationUrl;

    public void register(RegisterRequest registerRequest) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(
                        () -> new IllegalStateException("Role not found")
                );

        var user = User.builder()
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);

        sendValidationEmail(user);

    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        // sending email
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Activate your account"
        );
        log.info("Validation Email has been sent to {}, from sendValidateEmail", user.getEmail());
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(8);

        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(20))
                .user(user)
                .build();

        tokenRepository.save(token);
        log.info("Generated activation token and saved to database, from generateAndSaveActivationToken method");
        return  generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";

        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length()); // from 0-9
            codeBuilder.append(characters.charAt(randomIndex));
        }
        log.info("Generated activation code");
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.email(),
                        authenticationRequest.password()
                )
        );

        // if authentication is successful, then we can generate a token and return it
        var claims = new HashMap<String, Object>();
        var user = (User) auth.getPrincipal();
        claims.put("fullName", user.getFullName());

        var jwtToken = jwtService.generateToken(claims, user);
        log.info("User {} is authenticated", user.getEmail());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ValidTokenNotFoundException("Token not found or Token is invalid"));

        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            log.info("Activation Token is expired, new token sent to your email");
            throw new ActivationTokenExpiredException("Activation Token is expired, new token sent to your email");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UserNotFoundWithGivenUserId("User not found"));

        user.setEnabled(true);

        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
