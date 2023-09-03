package by.leshkevich.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author S.Leshkevich
 * @version 1.0
 * This is the common base class for the Java enum banking type.
 */
@Getter
@AllArgsConstructor
public enum TypeOperation {
    TRANSLATION("TRANSLATION"),
    WITHDRAWAL("WITHDRAWAL"),
    REFILL("REFILL"),
    ACCRUAL_OF_INTEREST("ACCRUAL_OF_INTEREST");

    private final String message;
}