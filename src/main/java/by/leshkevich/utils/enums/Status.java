package by.leshkevich.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Status {
    IN_PROCESSING("IN PROCESSING"),
    COMPLETED("COMPLETED"),
    REJECTED("REJECTED");

    @Getter
    private String message;
}