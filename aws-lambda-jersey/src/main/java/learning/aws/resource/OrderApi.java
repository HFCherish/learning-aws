package learning.aws.resource;

import learning.aws.domain.Order;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author hf_cherish
 * @date 2018/6/26
 */
public class OrderApi {
    private Order order;

    public OrderApi(Order order) {
        this.order = order;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder() {
        return order;
    }
}
