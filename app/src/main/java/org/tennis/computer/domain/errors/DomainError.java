package org.tennis.computer.domain.errors;

import lombok.Getter;

@Getter
public abstract class DomainError {
    private final String message;
    protected DomainError(String message){
        this.message = message;
    }
}
