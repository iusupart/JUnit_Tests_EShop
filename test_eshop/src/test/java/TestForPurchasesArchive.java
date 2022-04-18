import archive.ItemPurchaseArchiveEntry;
import archive.PurchasesArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shop.Item;
import shop.Order;
import shop.StandardItem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.mockito.Mockito.*;

public class TestForPurchasesArchive {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Test
    public void TestForPrintItemPurchaseStatistics() {
        Iterator i = mock(Iterator.class);
        Collection<ItemPurchaseArchiveEntry> itemEntries = new HashMap().values();
        String textForCompare = "";
        for (ItemPurchaseArchiveEntry e : itemEntries){
            textForCompare += e.toString();
        }
        when(i.next()).thenReturn("ITEM PURCHASE STATISTICS:").thenReturn(textForCompare);
        String result = i.next()+""+i.next();
        Assertions.assertEquals("ITEM PURCHASE STATISTICS:" + textForCompare, result);
    }

    @Test
    public void TestForGetHowManyTimesHasBeenItemSold() {
        int expCount = 0;
        Item item = new StandardItem(10, "Volodya", 1010, "PC", 300);
        PurchasesArchive purchasesArchive = Mockito.mock(PurchasesArchive.class);
        int i = purchasesArchive.getHowManyTimesHasBeenItemSold(item);
        Assertions.assertEquals(expCount,i);
    }

    @Test
    public void TestForOutOrderToPurchasesArchive(){
        Order order = Mockito.mock(Order.class);
        PurchasesArchive purchasesArchive1 = Mockito.mock(PurchasesArchive.class);
        purchasesArchive1.putOrderToPurchasesArchive(order);
        Mockito.verify(purchasesArchive1).putOrderToPurchasesArchive(order);
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    public void TestForCallingConstructor(){
        int expCountOfItems = 0;
       Order order = Mockito.mock(Order.class);
       PurchasesArchive purchasesArchive = new PurchasesArchive();
       ArrayList<Item> orderItems = order.getItems();
       String exp = "ITEM PURCHASE STATISTICS:\n";
       purchasesArchive.printItemPurchaseStatistics();

       Assertions.assertEquals(exp, output.toString());

       Assertions.assertEquals(expCountOfItems, orderItems.size());

    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(null);
    }

}
