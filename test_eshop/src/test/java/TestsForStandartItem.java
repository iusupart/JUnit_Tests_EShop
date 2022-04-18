import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.StandardItem;

public class TestsForStandartItem {

    int id;
    String name;
    float price;
    String category;
    int loyalityPoints;

     @Test
     public void testConstructorStandartItem() {
         id = 1;
         name = "Grisha";
         price = 1.45f;
         category = "electronical";
         loyalityPoints = 1010;

         StandardItem standardItem = new StandardItem(id, name, price, category, loyalityPoints);

         Assertions.assertEquals(id, standardItem.getID());
         Assertions.assertEquals(name, standardItem.getName());
         Assertions.assertEquals(price, standardItem.getPrice());
         Assertions.assertEquals(category, standardItem.getCategory());
         Assertions.assertEquals(loyalityPoints, standardItem.getLoyaltyPoints());
     }


     @Test
    public void testCopyFunction() {
         id = 12;
         name = "Potato";
         price = 2f;
         category = "vegetable";
         loyalityPoints = 10;

         StandardItem standardItemForEquals = new StandardItem(id, name, price, category, loyalityPoints);

         StandardItem standardItemForTest = new StandardItem(id, name, price, category, loyalityPoints);

         Assertions.assertEquals(standardItemForTest, standardItemForEquals.copy());
     }

     @ParameterizedTest(name = "It will be standart item with {0} id, {1} name, {2} price, from {3} category and have {4} loyality points")
     @CsvSource({"1,alesha,alesha,1.4f,vegetables,10,10,true","2,vova,vova,1.5f,fruits,25,25,true","2,vova,vova,1.5f,fruits,25,24,false","2,vova,oleg,1.5f,fruits,25,24,false","100,grisha,grisha,2f,mech,40,40,true"})
     public void testParam(int id, String name, String nameForCompare, float price, String category, int loyalityPoints, int loyalityPointsforCompare, boolean output){
         StandardItem itemForCompare = new StandardItem(id, nameForCompare, price, category, loyalityPointsforCompare);
         StandardItem itemForTest = new StandardItem(id, name, price, category, loyalityPoints);
         boolean expectableOuput = itemForCompare.equals(itemForTest);
         Assertions.assertEquals(expectableOuput, output);
     }



}
