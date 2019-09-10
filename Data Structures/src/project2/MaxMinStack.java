package project2;

//Note that <E extends Comparable>. Therefore you should use Comparable
//instead of Object while creating arrays and casting them to generic type.
//Also use compareTO() instead of < or > while comparing generic elements
public class MaxMinStack<E extends Comparable<E>> {
	private int size;
	private static final int CAPACITY = 10;
	private E[] arry;
	private E[] minArry;
	private E[] maxArry;

//the default constructor
	public MaxMinStack() {
		//pass into other constructor
		this(CAPACITY);
	}

//another constructor which creates a stack of specified size
	@SuppressWarnings("unchecked")
	public MaxMinStack(int ary_size) {
		//initialize all 3 arrays and the size
		arry = (E[]) new Comparable[ary_size];
		minArry = (E[]) new Comparable[ary_size];
		maxArry = (E[]) new Comparable[ary_size];
		size = -1;
	}

//return the element on top of the stack without removing it. return null if stack is empty.
	public E top() {
		// check if stack is empty
		if (isEmpty()) {
			System.out.println("Stack is empty");
			return null;
		}
		return arry[size];
	}

//return the number of elements in the stack
	public int size() {
		// here size is the amount of elements and not the capacity of the stack
		return size + 1;
	}

//test if the stack is empty
	public boolean isEmpty() {
		return size == -1;
	}

//return the actual capacity of the stack(not the number of elements stored in it).
	public int capacity() {
		return arry.length;
	}

//return the maximum value stored in the stack. return null if stack is empty.
	public E maximum() {
		if (isEmpty()) {
			System.out.println("Stack is empty");
			return null;
		}
		return maxArry[size];
	}

//return the minimum value stored in the stack. return null if stack is empty.
	public E minimum() {
		if (isEmpty()) {
			System.out.println("Stack is empty");
			return null;
		}
		return minArry[size];
	}

//push a new element onto the stack
	public void push(E e) throws IllegalStateException {
		// handle full exception
		if (size() == arry.length) {
			throw new IllegalStateException("Stack is full.");
		}
		// increment size before storing the element
		size++;
		arry[size] = e;
		// if returns 0 or more push new number on stack maxStack, else push old element
		if (size != 0) {
			if (e.compareTo(maxArry[size - 1]) >= 0) {
				maxArry[size] = e;
			} else {
				maxArry[size] = maxArry[size - 1];
			}
			// if returns 0 or more push new number on stack minStack, else push old
			if (e.compareTo(minArry[size - 1]) <= 0) {
				minArry[size] = e;
			} else {
				minArry[size] = minArry[size - 1];
			}
		} else {
			//if array is empty add element to the min and max arrays
			minArry[size] = e;
			maxArry[size] = e;
		}

	}

//pop the element on top of the stack and return it. return null if stack is empty.
	public E pop() {
		if (isEmpty()) {
			System.out.println("Stack is empty.");
			return null;
		}
		//store element we want to pop
		E topElement = arry[size];
		//set that spot in the arrays to null
		arry[size] = null;
		maxArry[size] = null;
		minArry[size] = null;
		size--;
		return topElement;
	}

}