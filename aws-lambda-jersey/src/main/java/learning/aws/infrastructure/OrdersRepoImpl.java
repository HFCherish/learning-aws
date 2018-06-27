package learning.aws.infrastructure;

import com.amazonaws.services.dynamodbv2.document.*;
import learning.aws.domain.Order;
import learning.aws.domain.OrdersRepo;

import javax.inject.Inject;
import java.util.*;

/**
 * @author hf_cherish
 * @date 2018/6/26
 */
public class OrdersRepoImpl implements OrdersRepo {

    private final Table orders;

    @Inject
    public OrdersRepoImpl(DynamoDB dynamoDB) {
        this.orders = dynamoDB.getTable(DBConstants.ORDERS_TABLE);
    }

//    static Map<String, Order> orders = new HashMap<>();
//
//    static {
//        addOrder(new Order("petrina-default", 78));
//    }

//    static void addOrder(Order order) {
//        orders.put(order.getId(), order);
//    }

    @Override
    public Order save(Map<String, Object> order) {
        Order toSave = new Order(order.getOrDefault("user", "").toString(), (int) order.getOrDefault("amount", 0));
        orders.putItem(new Item()
                .withPrimaryKey("id", toSave.getId())
                .withString("user", toSave.getUser())
                .withInt("amount", toSave.getAmount())
        );
        return toSave;
    }

    @Override
    public List<Order> getAll(Integer limit) {
        ItemCollection<ScanOutcome> scan = orders.scan();
        ArrayList<Order> fetched = new ArrayList<>();
        for( Iterator<Item> iterator = scan.iterator(); iterator.hasNext(); ) {
            fetched.add(buildOrderFromItem(iterator.next()));
        }
        return fetched;
//        return new ArrayList<>(orders.values()).subList(0, limit > orders.size() ? orders.size() : limit);
    }

    private Order buildOrderFromItem(Item next) {
        return new Order(next.getString("user"), next.getInt("amount"), next.getString("id"));
    }

    @Override
    public Optional<Order> getOne(String id) {
        Item item = orders.getItem("id", id);
        return item == null ? Optional.empty() : Optional.of(buildOrderFromItem(item));
    }
}
