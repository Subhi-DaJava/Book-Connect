package com.uyghurjava.book.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {}
