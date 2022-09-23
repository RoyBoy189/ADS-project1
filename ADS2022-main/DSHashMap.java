import java.text.BreakIterator;
import java.util.Iterator;
/*
 * HashMap class written by the 2022 ADS class
 *
 * V is the type parameter, the type of the value we will store.
 * For now, all of our keys will be Strings.
 */

import javax.swing.plaf.ComponentInputMapUIResource;

class DSHashMap<V> implements Iterable<String> {

  // The backing array - contains the values
  DSArrayList<DSArrayList<KVP>> a; // the backing DSArrayList
  int capacity; // Size of the backing array
  int numItems; // Number of KVPs added to the DSHashMap
  int maxChainLength; // for analysis
  int totalCount;

  // A Java constructor is identified by three properties
  // The same name as the class
  // public
  // no return type, not even void
  // class HashmapIlt implements Iterator<String>{

  //     String current; // or V current
  //     int index = 0;

  //     public HashmapIlt(DSHashMap<V> hashMap)  {
  //         current = hashMap.a.get(0).get(0).key; //current index
  //     }

  //     public boolean hasNext() {
  //         if(current == null) {
  //             return false; //if there's nothing, it's false.
  //         }
  //         // return index < hashMap.a.get(0).capacity; //numItems
  //         return true;
  //     }

  //     public String next() {
  //         String returnValue = hasMap.a.get(0).get(index).key; //returns the key
  //         index++;
  //         current = hasMap.a.get(0).get(index).key; //update current
  //         return returnValue;
  //     }
  // }

  @Override
  public Iterator<String> iterator() {
    Iterator<String> it = new Iterator<String>() {
      int index = 0;
      int chainIndex = 0;

      @Override
      public boolean hasNext() {
        while (a.get(chainIndex) == null) {
          chainIndex++;
          index = 0;
          if (chainIndex >= capacity) { //numitems or capactity could work
            return false;
          }
        }
        if (index >= a.get(chainIndex).length() && chainIndex >= capacity) {
          return false;
        }

        return true;
      }

      @Override
      public String next() {
        String returnValue = a.get(chainIndex).get(index).key;
        index++;
        if (a.get(chainIndex).get(index) == null) {
          index = 0;
          chainIndex++;
        }
        return returnValue;
      }
    };
    return it;
  }

  public DSHashMap() {
    capacity = 2;
    a = new DSArrayList<>(capacity);
    numItems = 0;
    maxChainLength = 0;
    totalCount = 0;
  }

  /**
   * Add a key,value pair to the DSHashMap.
   * If the key is already there, update the value
   *
   * @param key
   * @param value
   */
  void put(String key, V value) {
    // Check to see if our DSHashMap is getting crowded
    if (numItems > capacity / 2) {
      rehash();
    }

    // Give us the location of this key's value in the backing array
    int location = hash(key);
    if (a.get(location) == null) {
      a.put(location, new DSArrayList<KVP>(10));
      a.get(location).add(new KVP(key, value));
      numItems++;
    } else { // Already a chain here, with stuff in it
      // search that chain for our key.
      for (int i = 0; i < a.get(location).length(); i++) {
        KVP kvp = a.get(location).get(i);
        // If we find the key, replace the value
        if (kvp.key.equals(key)) {
          a.get(location).replace(i, new KVP(key, value));
          return;
        }
        if (i >= 3) {
          // System.out.println("OMG! 3! " + totalCount);
          totalCount++;
        }
      }
      // If we don't find the key, create a new KVP and add it to the chain
      a.get(location).add(new KVP(key, value));
      numItems++;
      int chainLength = a.get(location).length;
      if (chainLength > maxChainLength) {
        maxChainLength = chainLength;
        //System.out.println("New max chain length is " + maxChainLength);
      }
    }
  }

  /*
   * Double the size of the backing DSArrayList
   * Move all KVPs to their new locations and chains
   */
  private void rehash() {
    // Save a reference to the current backing array
    DSArrayList<DSArrayList<KVP>> olda = a;

    // Create a new, larger backing array
    int newCapacity = 2 * this.capacity;
    a = new DSArrayList<>(newCapacity);
    this.capacity = newCapacity;
    System.out.println("New capacity is " + this.capacity);

    // Loop over old backing array
    this.numItems = 0;
    this.maxChainLength = 0;
    for (DSArrayList<KVP> chain : olda) {
      if (chain == null) continue;

      for (KVP kvp : chain) {
        if (kvp == null) break;
        this.put(kvp.key, kvp.value);
      }
    }
  }

  /**
   * Return the value associated with this key
   * If the key is not in the DSHashMap, return an exception
   *
   * @param key
   * @return The value of type V stored with this key
   */
  V get(String key) {
    int location = hash(key);
    if (a.get(location) == null) {
      throw new IndexOutOfBoundsException("Key not found: " + key);
    } else { // Already a chain here, with stuff in it
      // search that chain for our key.
      for (int i = 0; i < a.get(location).length(); i++) {
        KVP kvp = a.get(location).get(i);
        System.out.printf(
          "loc = %d, i = %d: Looking for %s, found %s\n",
          location,
          i,
          key,
          kvp.key
        );
        // If we find the key, replace the value
        if (kvp.key.equals(key)) {
          return kvp.value;
        }
      }
      // If we don't find the key, create a new KVP and add it to the chain
      throw new IndexOutOfBoundsException("Key not found: " + key);
    }
  }

  // This is the hash function. Tells where a string's value
  // should be stored in the backing array
  int hash(String s) {
    int rv = 0;

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      rv = (rv * 7 + c) % capacity;
    }
    return rv;
  }

  void fun(String s) {
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      System.out.println("Character " + c + "'s ASCII value is " + (int) c);
    }
  }

  // public Iterator<V> iterator() {

  //     return new HashmapIlt(this);
  // }

  /**
   * Inner class for Key-Value pairs
   */
  class KVP {

    String key;
    V value;

    public KVP(String s, V v) {
      key = s;
      value = v;
    }
  }
}
