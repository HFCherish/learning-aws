package learning.aws.resource;

import learning.aws.domain.Order;
import learning.aws.domain.OrdersRepo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author hf_cherish
 * @date 2018/6/26
 */
@Path("/orders")
public class OrdersResource {

    @Inject
    OrdersRepo ordersRepo;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Map<String, Object> order, @Context UriInfo uriInfo) {
        Order saved = ordersRepo.save(order);
        return Response.created(URI.create(uriInfo.getRequestUri().toString() + "/" + saved.getId())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Order> getAll(@QueryParam("limit") Integer limit) {
        return ordersRepo.getAll(limit);
    }

    @Path("{id}")
    public OrderResource getOne(@PathParam("id") String id) {
        return ordersRepo.getOne(id).map(OrderResource::new).orElseThrow(() -> new NotFoundException("order " + id + "not exists!"));
    }
}
