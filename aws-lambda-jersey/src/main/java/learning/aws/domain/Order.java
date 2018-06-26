package learning.aws.domain;

import java.util.UUID;

/**
 * @author hf_cherish
 * @date 2018/6/26
 */
public class Order {
    private final String user;
    private final int amount;
    private String id;

    public Order(String user, int amount) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public int getAmount() {
        return amount;
    }
}
