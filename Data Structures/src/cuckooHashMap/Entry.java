package cuckooHashMap;

public class Entry<K,V> {
	//keys and values
	private K k;
	private V v;
	
	//constructor
	public Entry(K key, V value) {
		k = key;
		v = value;
	}
	
	//return value
	public V getValue() {
		return v;
	}
	
	//return key
	public K getKey() {
		return k;
	}
	
	//return the current old and replace it
	public V setValue(V value) {
		V old = v;
		v = value;
		return old;
	}
	
	//set new key
	public void setKey(K key) {
		k = key;
	}
}
