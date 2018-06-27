package learning.aws.domain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author hf_cherish
 * @date 2018/6/26
 */
public interface OrdersRepo {
    Order save(Map<String, Object> order);

    List<Order> getAll(Integer limit);

    Optional<Order> getOne(String id);
}
