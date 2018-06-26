//package learning.aws.resource;
//
//import learning.aws.support.ApiSupport;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import javax.ws.rs.core.Response;
//import java.util.List;
//
//import static learning.aws.support.TestHelper.orderJsonForTest;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.core.StringContains.containsString;
//import static org.junit.Assert.assertThat;
//
///**
// * @author hf_cherish
// * @date 2018/6/26
// */
//@Ignore
//public class OrdersResourceTest extends ApiSupport {
//
//    @Test
//    public void should_create_right() {
//        Response response = post("/orders", orderJsonForTest("productName"));
//
//        assertThat(response.getStatus(), is(201));
//        assertThat(response.getLocation().toString(), containsString("/orders"));
//    }
//
//    @Test
//    public void should_get_all() {
//        Response response = target("/orders").queryParam("limit", 2).request().buildGet().invoke();
//
//        assertThat(response.getStatus(), is(200));
//        List orders = response.readEntity(List.class);
//        assertThat(orders.size() > 0, is(true));
//        System.out.println(">>>>>>>>>>>>>>>>>> orders: " + orders);
//
//    }
//}