package learning.aws.resource;

import learning.aws.domain.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hf_cherish
 * @date 2018/6/26
 */
@Path("/orders")
public class OrdersResource {

//    @Context
//    OrdersRepo ordersRepo;

    static Map<String, Order> orders = new HashMap<>();

    static {
        addOrder(new Order("petrina-default", 78));
    }

    static void addOrder(Order order) {
        orders.put(order.getId(), order);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Map<String, Object> order) {
//        Order saved = ordersRepo.save(order);
        Order saved = new Order(order.getOrDefault("user", "").toString(), (int) order.getOrDefault("amount", 0));
        addOrder(saved);
        return Response.created(URI.create("/orders/" + saved.getId())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Order> getAll(@QueryParam("limit") Integer limit) {
//        return ordersRepo.getAll(limit);

        return new ArrayList<>(orders.values()).subList(0, limit > orders.size() ? orders.size() : limit);
    }

    @Path("{id}")
    public OrderApi getOne(@PathParam("id") String id) {
        return new OrderApi(orders.get(id));
    }
}
