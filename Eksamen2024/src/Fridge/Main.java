package Fridge;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Main {

  private static IFridge fridge = new Fridge(20);

  public static void main(String[] args) {
    int numberOfTestsPassed = 0;

    //Tests if single item can be placed in empty fridge.
    if (placeInEmptyFridgeTest()) numberOfTestsPassed++;

    //Tests that  you can't place an item in a full fridge.
    if (placeInFullFridgeTest()) numberOfTestsPassed++;

    //Tests that the number of items in the fridge is correct when adding items
    if (nItemsInFridgeTest()) numberOfTestsPassed++;

    //Tests if you can take out an item from fridge.
    if (takeoutTest()) numberOfTestsPassed++;

    //Tests that exception is thrown when attempting to remove item from empty fridge.
    if (takeOutFromEmptyFridgeTest()) numberOfTestsPassed++;

    //Tests if Fridge::emptyFridge removes every item from fridge.
    if (emptyFridgeTest()) numberOfTestsPassed++;

    //Checks it Fridge::removeExpiredFoods returns only expired food.
    if (removeExpiredItemsTest()) numberOfTestsPassed++;

    System.out.println("---FINAL RESULT---");
    System.out.println("---PASSED " + numberOfTestsPassed + "/7 TESTS---");
  }

  private static FridgeItem getItem() {
    LocalDate expirationDate = LocalDate.of(2021, 12, 24);
    FridgeItem item = new FridgeItem("Christmas Cake", expirationDate);
    return item;
  }

  private static void fillFridge() {
    FridgeItem item = getItem();
    int maxCapacity = fridge.totalSize();
    for (int i = 0; i < maxCapacity; i++) {
      fridge.placeIn(item);
    }
  }

  private static boolean placeInEmptyFridgeTest() {
    System.out.println("---PLACE IN EMPTY FRIDGE TEST---");
    FridgeItem item = getItem();

    boolean expected = true;
    boolean actual = fridge.placeIn(item);
    boolean passed = expected == actual;

    System.out.println("Expected result: " + expected);
    System.out.println("Actual result: " + actual);
    System.out.println("PASSED: " + passed);
    System.out.println();
    return passed;
  }

  private static boolean placeInFullFridgeTest() {
    System.out.println("---PLACE IN FULL FRIDGE TEST---");
    fillFridge();
    FridgeItem item = getItem();

    boolean expected = false;
    boolean actual = fridge.placeIn(item);
    boolean passed = expected == actual;

    System.out.println("Expected result: " + expected);
    System.out.println("Actual result: " + actual);
    System.out.println("PASSED: " + passed);
    System.out.println();
    return passed;
  }

  private static boolean nItemsInFridgeTest() {
    System.out.println("---N ITEMS IN FRIDGE TEST---");
    FridgeItem item = getItem();
    int maxCapacity = fridge.totalSize();
    boolean passed = false;
    for (int i = 0; i < maxCapacity; i++) {
      fridge.placeIn(item);
      int expected = i + 1;
      int actual = fridge.nItemsInFridge();
      System.out.println("Expected number of items in fridge: " + expected);
      System.out.println("Actual number of items in fridge:" + actual);
      passed = expected == actual;
    }
    boolean maxedOut = maxCapacity == fridge.nItemsInFridge();
    passed = passed && maxedOut;
    System.out.println("Fridge is filled to max capacity: " + maxedOut);
    System.out.println("PASSED: " + passed);
    System.out.println();
    return passed;
  }

  private static boolean takeoutTest() {
    System.out.println("---TAKE OUT TEST---");
    FridgeItem expected = getItem();
    fridge.placeIn(expected);

    int counter = fridge.nItemsInFridge();
    fridge.takeOut(expected);
    int newCounter = counter - 1;
    boolean passed = fridge.nItemsInFridge() == newCounter;
    System.out.println("PASSED: " + passed);
    System.out.println();
    return passed;
  }

  private static boolean takeOutFromEmptyFridgeTest() {
    System.out.println("---TAKE OUT FROM EMPTY FRIDGE TEST---");
    boolean passed = false;
    try {
      FridgeItem item = getItem();
      fridge.takeOut(item);
    } catch (NoSuchElementException e) {
      passed = true;
    }
    System.out.println("PASSED: " + passed);
    System.out.println();
    return passed;
  }

  private static boolean emptyFridgeTest() {
    System.out.println("---EMPTY FRIDGE TEST---");
    fillFridge();
    fridge.emptyFridge();

    int expected = 0;
    int actual = fridge.nItemsInFridge();
    boolean passed = expected == actual;

    System.out.println("Expected number of items in fridge: " + expected);
    System.out.println("Actual number of items in fridge: " + actual);
    System.out.println("PASSED: " + passed);
    System.out.println();
    return passed;
  }

  private static boolean removeExpiredItemsTest() {
    // Add expired items
    LocalDate expiredDate = LocalDate.of(2008, 4, 19);
    List<FridgeItem> expiredItems = new ArrayList<>();
    List<String> itemNames1 = Arrays.asList("Chicken", "Tofu", "Pizza", "Milk");
    for (int i = 0; i < itemNames1.size(); i++) {
      FridgeItem expiredItem = new FridgeItem(itemNames1.get(i), expiredDate);
      expiredItems.add(expiredItem);
      fridge.placeIn(expiredItem);
    }

    // Add good items
    LocalDate goodDate = LocalDate.of(2025, 1, 1);
    List<FridgeItem> goodItems = new ArrayList<>();
    List<String> itemNames2 = Arrays.asList("Pasta", "Onion", "Taco", "Soda");
    for (int i = 0; i < itemNames2.size(); i++) {
      FridgeItem goodItem = new FridgeItem(itemNames2.get(i), goodDate);
      goodItems.add(goodItem);
      fridge.placeIn(goodItem);
    }

    // Check the number of items in fridge after removing expired items
    boolean notNull = false;
    List<FridgeItem> expiredItemsFromFridge = fridge.removeExpiredFood();
    notNull = expiredItemsFromFridge != null;
    System.out.println(
      "Expired items were removed from fridge not null: " + notNull
    );

    int expected = 4;
    int actual = fridge.nItemsInFridge();
    boolean passed = (expected == actual) && notNull;

    System.out.println("Expected number of items in fridge: " + expected);
    System.out.println("Actual number of items in fridge: " + actual);
    System.out.println("PASSED: " + passed);
    System.out.println();
    return passed;
  }
}
