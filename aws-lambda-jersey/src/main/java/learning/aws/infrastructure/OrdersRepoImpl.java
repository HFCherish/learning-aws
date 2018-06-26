package learning.aws.infrastructure;

import learning.aws.domain.Order;
import learning.aws.domain.OrdersRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hf_cherish
 * @date 2018/6/26
 */
public class OrdersRepoImpl implements OrdersRepo {
    static List<Order> orders = new ArrayList<>();

    static {
        orders.add(new Order("petrina-default", 78));
    }

    @Override
    public Order save(Map<String, Object> order) {
        Order saved = new Order(order.getOrDefault("user", "").toString(), (int) order.getOrDefault("amount", 0));
        orders.add(saved);
        return saved;
    }

    @Override
    public List<Order> getAll(Integer limit) {
        return orders.subList(0, limit);
    }
}
