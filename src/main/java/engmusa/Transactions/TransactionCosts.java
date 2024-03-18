package engmusa.Transactions;

public enum TransactionCosts {
    CHECKING_BALANCE(2),
    SENDING_COST(25);
    private final float cost;
    TransactionCosts(float cost) {
        this.cost = cost;
    }
    public float getCost() {
        return cost;
    }

}
