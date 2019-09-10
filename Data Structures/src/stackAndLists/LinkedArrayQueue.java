package stackAndLists;

public class LinkedArrayQueue<E> {
	// don't forget the member data
	private static final int CAPACITY = 8;
	private SinglyLinkedList<E> list;
	private E[] arrayQueue;
	private int size;
	private int front;
	private int back;

	// the default constructor
	public LinkedArrayQueue() {
		list = new SinglyLinkedList<>();
		size = 0;		
		front = 0;
		back = 0;
	}

	// return the number of elements in the queue
	public int size() {
		return size;
	}

	// return the number of arrays currently storing elements
	public int numArrays() {
		return list.getSize();
	}

	// test if the queue is empty
	public boolean isEmpty() {
		return list.isEmpty();
	}

	// return the element at the front of the queue. return null if queue is empty
	public E first() {
		if(isEmpty()) {
			System.out.println("List is empty!");
			return null;
		}
		return list.first()[front];
	}

	// return the element at the back of the queue. return null if queue is empty
	public E last() {
		if(isEmpty()) {
			System.out.println("List is empty!");
			return null;
		}
		//System.out.println("-------------back is: " + back + "-----------");
		return list.last()[back-1];
	}

	// push e to the back of the queue.
	@SuppressWarnings("unchecked")
	public void enqueue(E e) {
		//if empty, make a new node and make it first
		if(isEmpty()) {
			arrayQueue = (E[])new Object[CAPACITY];
			list.addFirst(arrayQueue);
		}
		//if array is full make a new node and add it to the back
		if(back == arrayQueue.length) {
			arrayQueue = (E[])new Object[CAPACITY];
			list.addLast(arrayQueue);
			back = 0;
		}
		//update all info after making sure nodes are handled
		arrayQueue[back] = e;
		back++;
		size++;
	}

	// pop and return the element at the front of the queue. return null if queue is
	// empty
	public E dequeue() {
		//check if empty
		if(isEmpty()) {
			System.out.println("List is empty!");
			return null;
		}
		//store the item we want to return
		E answer = list.first()[front];
		front++;
		size--;
		//check if we need to remove the first node
		if(front == arrayQueue.length) {
			list.removeFirst();
			front = 0;
		}
		//check if no elements and then remove first node
		if(size==0) {
			list.removeFirst();
			front = 0;
			back = 0;
		}
		return answer;
	}

}