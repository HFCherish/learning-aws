package learning.aws.infrastructure;

import learning.aws.domain.Order;
import learning.aws.domain.OrdersRepo;

import java.util.*;

/**
 * @author hf_cherish
 * @date 2018/6/26
 */
public class OrdersRepoImpl implements OrdersRepo {
    static Map<String, Order> orders = new HashMap<>();

    static {
        addOrder(new Order("petrina-default", 78));
    }

    static void addOrder(Order order) {
        orders.put(order.getId(), order);
    }

    @Override
    public Order save(Map<String, Object> order) {
        Order saved = new Order(order.getOrDefault("user", "").toString(), (int) order.getOrDefault("amount", 0));
        addOrder(saved);
        return saved;
    }

    @Override
    public List<Order> getAll(Integer limit) {
        return new ArrayList<>(orders.values()).subList(0, limit > orders.size() ? orders.size() : limit);
    }

    @Override
    public Optional<Order> getOne(String id) {
        return Optional.ofNullable(orders.get(id));
    }
}
