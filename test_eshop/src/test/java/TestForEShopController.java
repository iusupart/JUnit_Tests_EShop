import archive.PurchasesArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
import shop.*;
import storage.Storage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static shop.EShopController.purchaseShoppingCart;
import static shop.EShopController.startEShop;

public class TestForEShopController {

    private static Storage storage;
    private static PurchasesArchive archive;

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private ByteArrayOutputStream output1 = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void resetStream() {
        System.setOut(null);
    }

    @Test
    public void TestForInsertItems() {
        String[] expectableOutput = {"STORAGE IS CURRENTLY CONTAINING:\n" +
                "STOCK OF ITEM:  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5    PIECES IN STORAGE: 10\n",
                "STORAGE IS CURRENTLY CONTAINING:\n" +
                        "STOCK OF ITEM:  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5    PIECES IN STORAGE: 10\n" +
                "STORAGE IS CURRENTLY CONTAINING:\n" +
                        "STOCK OF ITEM:  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5    PIECES IN STORAGE: 10\n" +
                        "STOCK OF ITEM:  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10    PIECES IN STORAGE: 100\n"
        };
        Storage storage = new Storage();
        int[] itemCount = {10,100};
        Item[] storageItems = {
                new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
                new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
        };

        for (int i = 0; i < storageItems.length; i++) {
            storage.insertItems(storageItems[i], itemCount[i]);
            storage.printListOfStoredItems();
            Assertions.assertEquals(expectableOutput[i], output.toString());
        }
    }

    @Test
    public void TestAddAndRemoveItemsInCart() {
        Storage storage = new Storage();
        int[] itemCount = {10,100};
        Item[] storageItems = {
                new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
                new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
        };

        for (int i = 0; i < storageItems.length; i++) {
            storage.insertItems(storageItems[i], itemCount[i]);
        }
        ShoppingCart newCart = new ShoppingCart();
        int expCountAfterAdd = 2;
        newCart.addItem(storageItems[0]);
        newCart.addItem(storageItems[1]);

        int count = newCart.getItemsCount();
        Assertions.assertEquals(expCountAfterAdd, count);

        int expCountAfterDel = 1;
        newCart.removeItem(2);

        int countA = newCart.getItemsCount();
        Assertions.assertEquals(expCountAfterDel, countA);
    }

    @Test
    public void TestPurchase() throws Exception {
        EShopController.startEShop();
        EShopController eShopController = new EShopController();
        storage = new Storage();
        archive = new PurchasesArchive();
        int[] itemCount = {10,10,4,5,10,2};

        Item[] storageItems = {
                new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5),
                new StandardItem(2, "Dancing Panda v.3 with USB port", 6000, "GADGETS", 10),
                new StandardItem(3, "Screwdriver", 200, "TOOLS", 5),
                new DiscountedItem(4, "Star Wars Jedi buzzer", 500, "GADGETS", 30, "1.8.2013", "1.12.2013"),
                new DiscountedItem(5, "Angry bird cup", 300, "GADGETS", 20, "1.9.2013", "1.12.2013"),
                new DiscountedItem(6, "Soft toy Angry bird (size 40cm)", 800, "GADGETS", 10, "1.8.2013", "1.12.2013")
        };

        for (int i = 0; i < storageItems.length; i++) {
            storage.insertItems(storageItems[i], itemCount[i]);
        }

        String expectableOutput = "Item with ID 1 added to the shopping cart.\n" +
                "Item with ID 2 added to the shopping cart.\n" +
                "Item with ID 3 added to the shopping cart.\n" +
                "Item with ID 5 added to the shopping cart.\n" +
                "Item with ID 6 added to the shopping cart.\n" +
                "STORAGE IS CURRENTLY CONTAINING:\n" +
                "STOCK OF ITEM:  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5    PIECES IN STORAGE: 9\n" +
                "STOCK OF ITEM:  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10    PIECES IN STORAGE: 9\n" +
                "STOCK OF ITEM:  Item   ID 3   NAME Screwdriver   CATEGORY TOOLS   PRICE 200.0   LOYALTY POINTS 5    PIECES IN STORAGE: 3\n" +
                "STOCK OF ITEM:  Item   ID 4   NAME Star Wars Jedi buzzer   CATEGORY GADGETS   ORIGINAL PRICE 500.0    DISCOUNTED PRICE 35000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 5\n" +
                "STOCK OF ITEM:  Item   ID 5   NAME Angry bird cup   CATEGORY GADGETS   ORIGINAL PRICE 300.0    DISCOUNTED PRICE 24000.0  DISCOUNT FROM Sun Sep 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 9\n" +
                "STOCK OF ITEM:  Item   ID 6   NAME Soft toy Angry bird (size 40cm)   CATEGORY GADGETS   ORIGINAL PRICE 800.0    DISCOUNTED PRICE 72000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013    PIECES IN STORAGE: 1\n";

        String expectableOutput2 = "ITEM PURCHASE STATISTICS:\n" +
                "ITEM  Item   ID 1   NAME Dancing Panda v.2   CATEGORY GADGETS   PRICE 5000.0   LOYALTY POINTS 5   HAS BEEN SOLD 1 TIMES\n" +
                "ITEM  Item   ID 2   NAME Dancing Panda v.3 with USB port   CATEGORY GADGETS   PRICE 6000.0   LOYALTY POINTS 10   HAS BEEN SOLD 1 TIMES\n" +
                "ITEM  Item   ID 3   NAME Screwdriver   CATEGORY TOOLS   PRICE 200.0   LOYALTY POINTS 5   HAS BEEN SOLD 1 TIMES\n" +
                "ITEM  Item   ID 5   NAME Angry bird cup   CATEGORY GADGETS   ORIGINAL PRICE 300.0    DISCOUNTED PRICE 24000.0  DISCOUNT FROM Sun Sep 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013   HAS BEEN SOLD 1 TIMES\n" +
                "ITEM  Item   ID 6   NAME Soft toy Angry bird (size 40cm)   CATEGORY GADGETS   ORIGINAL PRICE 800.0    DISCOUNTED PRICE 72000.0  DISCOUNT FROM Thu Aug 01 00:00:00 CEST 2013    DISCOUNT TO Sun Dec 01 00:00:00 CET 2013   HAS BEEN SOLD 1 TIMES\n";

        ShoppingCart newCart = new ShoppingCart();

        newCart.addItem(storageItems[0]);
        newCart.addItem(storageItems[1]);
        newCart.addItem(storageItems[2]);
        newCart.addItem(storageItems[4]);
        newCart.addItem(storageItems[5]);

        int expCountAfterAdd = 5;
        int count = newCart.getItemsCount();

        Assertions.assertEquals(expCountAfterAdd, count);

        eShopController.purchaseShoppingCart(newCart, "Libuse Novakova","Kosmonautu 25, Praha 8", storage, archive);

        storage.printListOfStoredItems();

        Assertions.assertEquals(expectableOutput, output.toString());

        System.setOut(new PrintStream(output1));

        archive.printItemPurchaseStatistics();

        Assertions.assertEquals(expectableOutput2, output1.toString());
    }

    @Test
    public void TestPurchaseCartIsEmpty() {
        int itemCount = 10;
        int expConut = 1;

        PurchasesArchive archive = new PurchasesArchive();
        ShoppingCart newCart1 = new ShoppingCart();
        EShopController eShopController = new EShopController();
        Assertions.assertThrows(Exception.class,
                () -> purchaseShoppingCart(newCart1, "Libuse Novakova", "Kosmonautu 25, Praha 8", storage, archive));

        StandardItem item = new StandardItem(1, "Dancing Panda v.2", 5000, "GADGETS", 5);
        Storage storage = new Storage();

        storage.insertItems(item, itemCount);
        newCart1.addItem(item);

        int outputCount = newCart1.getItemsCount();

        Assertions.assertEquals(expConut, outputCount);

        newCart1.removeItem(1);

        Assertions.assertThrows(Exception.class,
                () -> purchaseShoppingCart(newCart1, "Libus Novakov", "Namnesti miry 225, Praha 1", storage, archive));
    }
}
