package com.uyghurjava.book.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate-account");

    private final String name;

    EmailTemplateName(String templateName) {
        this.name = templateName;
    }
}
