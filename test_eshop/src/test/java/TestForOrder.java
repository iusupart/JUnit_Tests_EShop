import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shop.Order;
import shop.ShoppingCart;

public class TestForOrder {

    @Test
    public void TestForOrder() throws Exception{
        String customerName = "Albert Richt";
        String customerAddress  = "Praha Chaloupeckeho 1";
        ShoppingCart shoppingCart = new ShoppingCart();
        int state = 10;
        Order order = new Order(shoppingCart, customerName, customerAddress, state);
        Assertions.assertEquals(state, order.getState());
        Assertions.assertEquals(shoppingCart.getCartItems(), order.getItems());
        Assertions.assertEquals(customerName, order.getCustomerName());
        Assertions.assertEquals(customerAddress, order.getCustomerAddress());
    }

    @Test
    public void testOrderWithNull() throws Exception {
        String customerAddress = null;
        String customerName = "Super Hacker";
        int state = 10;
        ShoppingCart shoppingCart = new ShoppingCart();
        Assertions.assertThrows(Exception.class,
                () -> new Order(shoppingCart, customerName, customerAddress, state));
    }

}
