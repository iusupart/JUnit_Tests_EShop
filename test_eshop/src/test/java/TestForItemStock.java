import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.Item;
import shop.Order;
import shop.StandardItem;
import storage.ItemStock;

public class TestForItemStock {

    @Test
    public void TestForItemStock(){
        Item item = new StandardItem(10, "Volodya", 1010, "PC", 300);
        int expCount = 0;
        ItemStock itemStock = new ItemStock(item);

        Assertions.assertEquals(item, itemStock.getItem());
        Assertions.assertEquals(expCount, itemStock.getCount());
    }

    @ParameterizedTest(name = "empty count of items plus {0} equals {1}")
    @CsvSource({"2,2","1,1","30,30","4,4","10000,10000"})
    public void TestForIncreaseItemCount(int countInc, int expValue) {
        Item item = new StandardItem(10, "Volodya", 1010, "PC", 300);
        ItemStock itemStock = new ItemStock(item);

        itemStock.IncreaseItemCount(countInc);

        Assertions.assertEquals(expValue, itemStock.getCount());
    }

    @ParameterizedTest(name = "count {0} minus {1} equals {2}")
    @CsvSource({"2,2,0","2,1,1","30,27,3","4,2,2","10000,0,10000"})
    public void TestForDecreaseItemCount(int countIncForTest, int countDec, int expValue) {
        Item item = new StandardItem(10, "Volodya", 1010, "PC", 300);
        ItemStock itemStock = new ItemStock(item);
        itemStock.IncreaseItemCount(countIncForTest);

        itemStock.decreaseItemCount(countDec);

        Assertions.assertEquals(expValue, itemStock.getCount());
    }


}
