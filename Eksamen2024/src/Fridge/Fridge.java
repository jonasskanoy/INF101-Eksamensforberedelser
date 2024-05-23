package Fridge;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Fridge implements IFridge {

  //TODO: Implementer instansvariabler og konstruktør
  private ArrayList<FridgeItem> itemsInFridge;
  private final int MAX_SIZE;
  public int counterOfItemsInFridge;

  public Fridge(int maxSize) {
    this.MAX_SIZE = maxSize;
    this.itemsInFridge = new ArrayList<>();
    this.counterOfItemsInFridge = 0;
  }

  @Override
  public int nItemsInFridge() {
    return this.counterOfItemsInFridge;
  }

  @Override
  public int totalSize() {
    return this.MAX_SIZE;
  }

  @Override
  public boolean placeIn(FridgeItem item) {
    if (nItemsInFridge() < totalSize()) {
      this.itemsInFridge.add(item);
      this.counterOfItemsInFridge++;
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void takeOut(FridgeItem item) {
    if (!(this.itemsInFridge.contains(item))) {
      throw new NoSuchElementException("No such item in the fridge.");
    } else {
      this.itemsInFridge.remove(item);
      this.counterOfItemsInFridge--;
    }
  }

  @Override
  public void emptyFridge() {
    if (this.itemsInFridge.isEmpty()) {
      return;
    } else {
      this.counterOfItemsInFridge = 0;
      this.itemsInFridge.clear();
    }
  }

  @Override
  public List<FridgeItem> removeExpiredFood() {
    List<FridgeItem> fridgeRemovedExpiredFood = new ArrayList<>(
      this.itemsInFridge
    );
    for (FridgeItem item : this.itemsInFridge) {
      if (item.hasExpired()) {
        fridgeRemovedExpiredFood.remove(item);
        this.counterOfItemsInFridge--;
      }
    }
    return fridgeRemovedExpiredFood;
  }
}
