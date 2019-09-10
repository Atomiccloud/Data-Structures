package project2;

public class SinglyLinkedList<E> {
	@SuppressWarnings("hiding")
	private class Node<E>{
		private E[] element;
		private Node<E> next;
		
		public Node(E[] e, Node<E> n) {
			element = e;
			next = n;
		}
		public E[] getElement() {
			return element;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> n) {
			next = n;
		}
	}
	
	//Class variables
	private Node<E> head;
	private Node<E> tail;
	private int size;
	
	public SinglyLinkedList() {
		size = 0;
	}
	
	public int getSize() {
		//returns number of nodes
		return size;
	}
	
	public boolean isEmpty() {
		//returns if empty or not
		return size==0;
	}
	
	public E[] first() {
		//check if empty
		if(isEmpty()) {
			return null;
		}
		return head.getElement();
	}
	
	public E[] last() {
		//check if empty
		if(isEmpty()) {
			return null;
		}
		return tail.getElement();
	}
	
	public void addFirst(E[] e) {
		//make new node and set it to head
		head = new Node<E>(e, head);
		if(size == 0) {
			tail = head;
		}
		size++;
	}
	
	public void addLast(E[] e) {
		//make new node and set it to tail if not empty
		Node<E> last = new Node<E>(e, null);
		if(isEmpty()) {
			head = last;
		} else {
			tail.setNext(last);
		}
		tail = last;
		size++;
	}
	
	public E[] removeFirst() {
		if(isEmpty()) {
			return null;
		}
		//store first element in variable and return the variable
		E[] removed = head.getElement();
		//change head to the next node
		head = head.getNext();
		size--;
		//check if empty after decreasing size
		if(size == 0) {
			tail = null;
		}
		return removed;
	}
}