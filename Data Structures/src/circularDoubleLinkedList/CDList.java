package circularDoubleLinkedList;

public class CDList<E> implements Cloneable {
	// --------------nested node class------------
	private static class Node<E> {
		private E element;
		private Node<E> prev;
		private Node<E> next;

		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}

		public E getElement() {
			return element;
		}

		public Node<E> getPrev() {
			return prev;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setPrev(Node<E> p) {
			prev = p;
		}

		public void setNext(Node<E> n) {
			next = n;
		}

		public String toString() {
			return "(" + prev.getElement() + "," + element + "," + next.getElement() + ")";
		}
	}
	// ---------------end of nested node class------------

	// Member data
	private Node<E> tail = null;
	private int size = 0;

	// Member Methods
	public CDList() {
		// default contructor
	}

	public CDList(CDList<E> cdl) {
		// Go through the size of the list and set a new node for each node in the old
		// list.
		for (int i = 0; i < cdl.size; i++) {
			// add first is used to add the nodes and then reverse the array in the opposite
			// direction to fill it
			addFirst(cdl.tail.getElement());
			cdl.rotateBackward();
		}
	}

	public int size() {
		// gets size of array
		return size;
	}

	public boolean isEmpty() {
		// checks if array is empty
		return size == 0;
	}

	public E first() {
		// return the first element of the list
		return tail.getNext().getElement();
	}

	public E last() {
		// return the last element of the list
		return tail.getElement();
	}

	public void rotate() {
		// rotate the first element to the back of the list
		if (tail != null) {
			tail = tail.getNext();
		}
	}

	public void rotateBackward() {
		// rotate the last element to the front of the list
		if (tail != null) {
			tail = tail.getPrev();
		}
	}

	public void addFirst(E e) {
		// add element e to the front of the list
		//check if size = 0 or not and update tail accordingly. (from your slides, thanks!)
		if (size == 0) {
			tail = new Node<>(e, null, null);
			tail.setNext(tail);
			tail.setPrev(tail);
		} else {
			Node<E> firstNode = new Node<>(e, tail, tail.getNext());
			tail.getNext().setPrev(firstNode);
			tail.setNext(firstNode);
		}
		size++;
	}

	public void addLast(E e) {
		// add element e to the back of the list
		//call addfirst and then move tail
		addFirst(e);
		tail = tail.getNext();
	}

	public E removeFirst() {
		// remove and return the element at the front of the list
		//checks if list is empty
		if (isEmpty()) {
			return null;
		}
		//updates tail.getNext() and removes link to old "head"
		tail.setNext(tail.getNext().getNext());
		tail.getNext().setPrev(tail);
		size--;
		return tail.getNext().getElement();
	}

	public E removeLast() {
		// remove and return the element at the back of the list
		//check if list is empty
		if (isEmpty()) {
			return null;
		}
		//updates tail and removes link to the old tail
		tail = tail.getPrev();
		tail.setNext(tail.getNext().getNext());
		tail.getNext().setPrev(tail);
		size--;
		return tail.getElement();
	}

	@Override
	public CDList<E> clone() {
		// clone and return the new list(deep clone)
		//simply calls the clone constructor and passes in the object that called it
		CDList<E> clonedList = new CDList<E>(this);
		return clonedList;
	}

	public boolean equals(Object o) {
		// check if the current instance and o are from the same class, have the same
		// type
		// and have the same sequence of elements despite
		// having possibly different starting points.
		boolean isEqual = false;
		//checking for 
		if (!(o instanceof CDList)) {
			return false;
		}
		if (!(this.size == ((CDList<E>) o).size)) {
			return false;
		}
		//check if both lists are empty
		if(this.size == 0 && ((CDList<E>) o).size == 0) {
			return true;
		}
		// Rotates list until it finds equal starting point
		for (int i = 0; i < this.size; i++) {
			if (this.tail.getElement().equals(((CDList<E>) o).tail.getElement())) {
				//if elements match, rotate both lists and check all elements in them
				for (int j = 0; j < this.size(); j++) {
					if (this.tail.getElement().equals(((CDList<E>) o).tail.getElement())) {
						isEqual = true;
						this.rotate();
						((CDList<E>) o).rotate();
					} else {
						//If we dont find a match make sure isEqual is false and I included a break here so we don't
						//continue going through the loop when we dont need to. 
						isEqual = false;
						break;
					}
				}
			} else {
				//if the elements dont match, rotate first list and check again
				this.rotate();
			}
		}
		//isEqual will only remain true if all elements match in the above checks.
		return isEqual;
	}

	public void attach(CDList<E> cdl) {
		//clone cdl first
		CDList<E> cdlClone = new CDList<E>(cdl);
		
		// insert cdl after the tail of the current list
		Node<E> tempNode = this.tail.getNext();
		this.tail.setNext(cdlClone.tail.getNext());
		cdlClone.tail.getNext().setPrev(this.tail);
		cdlClone.tail.setNext(tempNode);
		cdlClone.tail.getNext().setPrev(cdlClone.tail);
		this.tail = cdlClone.tail;
		this.size += cdlClone.size;
	}

	public void removeDuplicates() {
		// remove all elements that happen more than once in the list. If the tail gets
		// //deleted, the element immediately before the tail will become the new tail.
		for (int i = 0; i < this.size; i++) {
			//store tail element into a variable to check against the rest of the list
			E temp = this.tail.getElement();
			for (int j = 1; j < this.size; j++) {
				this.rotate();
				//if the temp finds a match then it will call remove last and update tail. 
				if (temp == tail.getElement()) {
					removeLast();
				}
			}
			//if temp does not find a match, it will rotate array and update temp to the new tail to check again.
			this.rotate();
		}
	}

	public void printList() {
		// prints all elements in the list
		if (size == 0)
			System.out.println("List is empty.");
		else {
			Node<E> walk = tail.getNext();

			while (!(walk.getNext().equals(tail.getNext()))) {
				System.out.print(walk.toString() + ", ");
				walk = walk.getNext();
			}
			System.out.println(walk.toString());
		}
	}
}