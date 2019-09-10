package project4;

import java.util.ArrayList;

public class CuckooHashMap<K, V> {
	private int capacity; // length of the table
	private int prime; // prime factor
	private int n = 0; // number of entries
	private Entry<K,V>[] table1; // first array
	private Entry<K,V>[] table2; // second array
	
	//default constructor
	public CuckooHashMap() {
		this(4, 10007);
	}
	
	//second constructor
	CuckooHashMap(int cap) {
		this(cap, 10007);
	}

	//third constructor
	@SuppressWarnings("unchecked")
	CuckooHashMap(int cap, int pr) {
		if (cap % 2 == 1) {
			cap += 1;
		}
		capacity = cap;
		prime = pr;
		table1 = new Entry[capacity/2];
		table2 = new Entry[capacity/2];
	}

	//calc load
	public float loadFactor() {
		return (float)n / (float)capacity;
	}

	public int size() {
		return n;
	}

	public int capacity() {
		return capacity;
	}

	public V get(K key) {
		//find hash
		int hash = h1(key);
		//check if the spot is not null and the key matches in the first table
		if(table1[hash] != null && table1[hash].getKey().equals(key)) {
			return table1[hash].getValue();
		}
		//same check for second array
		int hash2 = h2(key);
		if(table2[hash2] != null && table2[hash2].getKey().equals(key)) {
			return table2[hash2].getValue();
		}
		//if you dont find it return null
		return null;
	}

	public V remove(K key) {
		int hash = h1(key);
		V value = null;
		//check if the array is not empty and the keys are equal in array 1
		if(table1[hash] != null && table1[hash].getKey().equals(key)) {
			//set value and replace with null
			value = table1[hash].getValue();
			table1[hash] = null;
			n--;
		}
		int hash2 = h2(key);
		//same as above but for array 2
		if(table2[hash2] != null && table2[hash2].getKey().equals(key)) {
			value = table2[hash2].getValue();
			table2[hash2] = null;
			n--;
		}
		//if load factor has changed from a size decrement then resize
		if(loadFactor() < .25 && capacity > 4) {
			resize(capacity/2);
		}
		return value;
	}

	public V put(K key, V value) {
		//entry is what we are trying to put in
		Entry<K,V> entry = new Entry<>(key, value);
		Entry<K,V> temp;
		//check if the the keys are the same and if so replace it and return old value in array 1
		if(table1[h1(entry.getKey())] != null && table1[h1(entry.getKey())].getKey().equals(key)) {
			return table1[h1(entry.getKey())].setValue(value);
		}
		//same as above but for array 2
		if(table2[h2(entry.getKey())] != null && table2[h2(entry.getKey())].getKey().equals(key)) {
			return table2[h2(entry.getKey())].setValue(value);
		}
		//for loop runs through the size and if it reaches the end then it will rehash
		for(int i = 0; i < size()+1; i++) {
			//set temp to current key
			temp = table1[h1(entry.getKey())];
			//insert entry where temp was
			table1[h1(entry.getKey())] = entry;
			//if null then we found a free spot
			if(temp == null) {
				n++;
				//check if incrementing size requires resize
				if (loadFactor() > 0.5) {
					resize(capacity*2);
				}
				return null;
			}
			//didnt find a free spot so now we need to insert temp. Update entry to temp and temp to the key at array 2
			entry = temp;
			temp = table2[h2(entry.getKey())];
			//insert entry
			table2[h2(entry.getKey())] = entry;
			//if null we found a free spot
			if(temp == null) {
				n++;
				//check for resize
				if (loadFactor() > 0.5) {
					resize(capacity*2);
				}
				return null;
			}
			//if we did not find a free spot set entry as temp and repeat
			entry = temp;			
		}
		//if loop runs through then it will get here and rehash
		rehash();
		//recursively call function with the current entry key and value
		return put(entry.getKey(), entry.getValue());
	}

	public Iterable<Entry<K, V>> entrySet() {
		//make an arraylist for an entry and return it
		ArrayList<Entry<K,V>> set = new ArrayList<Entry<K,V>>();
		for(int i = 0; i < capacity/2; i++) {
			if(table1[i] != null) {
				set.add(table1[i]);
			}
			if(table2[i] != null) {
				set.add(table2[i]);
			}
		}
		return set;
	}

	public Iterable<K> keySet() {
		//arraylist for a K and return it
		ArrayList<K> set = new ArrayList<K>();
		for(int i = 0; i < capacity/2; i++) {
			if(table1[i] != null) {
				set.add(table1[i].getKey());
			}
			if(table2[i] != null) {
				set.add(table2[i].getKey());
			}
		}
		return set;
	}

	public Iterable<V> valueSet() {
		//make arraylist for values and return it
		ArrayList<V> set = new ArrayList<V>();
		for(int i = 0; i < capacity/2; i++) {
			if(table1[i] != null) {
				set.add(table1[i].getValue());
			}
			if(table2[i] != null) {
				set.add(table2[i].getValue());
			}
		}
		return set;
	}

	private void rehash() {
		//function for checking next prime. Increases prime and checks the modulo of the square root. formula was found online.
		int counter = 1;
		while(counter != 0) {
			prime++;
			counter = 0;
			for(int i = 2; i<Math.sqrt(prime); i++) {
				if(prime % i == 0) {
					counter ++;
				}
			}
		}
		//after next prime is found resize it with cpacity
		resize(capacity);
	}

	@SuppressWarnings("unchecked")
	private void resize(int newCap) {
		//store all entries into a buffer for safe keeping
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(capacity);
		for (Entry<K,V> e : entrySet()) {
			buffer.add(e);
		}
		//update capacity and resize tables
		capacity = newCap;
		n = 0;
		table1 = new Entry[capacity/2];
		table2 = new Entry[capacity/2];
		//add the entries back into the new arrays
		for (Entry<K,V> e : buffer) {
			put(e.getKey(), e.getValue());
		}
	}

	//~~~~~~HASH FUNCTIONS BELOW~~~~~~~~~~~
	
	private int h1(K key) {
		return (Math.abs(key.hashCode()) % prime) % (capacity / 2);
	}

	private int h2(K key) {
		return ((Math.abs(key.hashCode()) / prime) % prime) % (capacity / 2);
	}
}
