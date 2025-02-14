package org.tennis.computer.domain.errors;

import lombok.Getter;

@Getter
public class GameNotValid extends DomainError{
    public GameNotValid(String message) {
        super("Game not valid ! "+ message);
    }
}
