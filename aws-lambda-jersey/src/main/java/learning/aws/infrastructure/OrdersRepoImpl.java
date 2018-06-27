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

    @Override
    public Order save(Map<String, Object> order) {
        Order toSave = new Order(order.getOrDefault(DBConstants.ORDER_USER, "").toString(), (int) order.getOrDefault(DBConstants.ORDER_AMOUNT, 0));
        orders.putItem(new Item()
                .withPrimaryKey(DBConstants.ORDER_ID, toSave.getId())
                .withString(DBConstants.ORDER_USER, toSave.getUser())
                .withInt(DBConstants.ORDER_AMOUNT, toSave.getAmount())
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
    }

    private Order buildOrderFromItem(Item next) {
        return new Order(next.getString(DBConstants.ORDER_USER), next.getInt(DBConstants.ORDER_AMOUNT), next.getString(DBConstants.ORDER_ID));
    }

    @Override
    public Optional<Order> getOne(String id) {
        Item item = orders.getItem(DBConstants.ORDER_ID, id);
        return item == null ? Optional.empty() : Optional.of(buildOrderFromItem(item));
    }
}
