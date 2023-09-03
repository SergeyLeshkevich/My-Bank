package by.leshkevich.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author S.Leshkevich
 * @version 1.0
 * This is the common base class for the Java language enum status type.
 */
@AllArgsConstructor
public enum Status {
    IN_PROCESSING("IN PROCESSING"),
    COMPLETED("COMPLETED"),
    REJECTED("REJECTED");

    @Getter
    private String message;
}