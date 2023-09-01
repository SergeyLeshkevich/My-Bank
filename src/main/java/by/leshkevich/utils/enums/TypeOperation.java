package by.leshkevich.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeOperation {
    TRANSLATION("TRANSLATION"),
    WITHDRAWAL("WITHDRAWAL"),
    REFILL("REFILL"),
    ACCRUAL_OF_INTEREST("ACCRUAL_OF_INTEREST");

    private final String message;
}