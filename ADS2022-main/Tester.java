

// Tester for our various classes this semester


public class Tester {
    public static void main(String[] args){

        int[] x = new int[] {6, 4, 2, 5, 7, 8, 5};
        for(int y : x){
             //System.out.println("Value: " + y);
        }

        DSArrayList<Integer> z = new DSArrayList<>(7);
        for(int i = 0; i < 7; i++) z.add(i*i);
        for(int y : z){
             //System.out.println("Value in z: " + y);
        }


        // Demo of our own DSHashMap class
        DSHashMap<Integer> dsh = new DSHashMap<Integer>();

        //System.out.println(dsh);


        long startTime = System.currentTimeMillis();

        int numItems = 30;
        for(int i = 0; i < numItems; i++){
            //System.out.printf("About to add the %dth item\n", i);
            dsh.put(randStr(10), 17);
        }

        if (dsh.a.get(0) == null)
            System.out.println("First chain is null");

        long duration = System.currentTimeMillis() - startTime;
        //System.out.println("That took " + duration + " milliseconds");

        // Print all the keys in our DSHashMap
        int count = 0;
        for(String k : dsh){
             System.out.println(k);
             count++;
        }
        if (count == 30) {
            System.out.println("We succeded");
        }
        System.out.println(count);
        /*
         * >>> dsh["Amry"] = 100
>>> dsh["Kate"] = 9
>>> dsh["Sanskriti"] = 27
>>> for x in dsh: print(x)
... 
Amry
Kate
Sanskriti
>>> for x in dsh: print(dsh[x])
... 
100
9
27
>>> for x in dsh: print(x, dsh[x])
... 
Amry 100
Kate 9
Sanskriti 27

         */

        /*
        DSArrayList<String> dsal = new DSArrayList<>(2);
        // Should be able to add arbitrarily many elements
        // dsal should resize itself.
        int numItems = 100;
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < numItems; i++){
            dsal.add("string");
        }
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("DSArrayList has " + dsal.length() + " elements");
        System.out.println("That took " + duration + " milliseconds");
        */

        
    }

	/**
	 * Generate a random string of length l, of lower-case letters
	 * 
	 * @param l Length of string to generate
	 * @return String of random letters
	 */
	static String randStr(int l) {
		String rv = "";
		for (int i = 0; i < l; i++) {
			rv += (char) ('a' + (char) (26 * Math.random()));
		}
        //System.out.println(rv);
		return rv;
	}
}