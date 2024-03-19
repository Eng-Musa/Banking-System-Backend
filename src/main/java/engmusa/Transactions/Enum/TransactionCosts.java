package engmusa.Transactions.Enum;

import lombok.Getter;

@Getter
public enum TransactionCosts {
    CHECKING_BALANCE(0),
    SENDING_COST(25);
    private final float cost;
    TransactionCosts(float cost) {
        this.cost = cost;
    }

}
