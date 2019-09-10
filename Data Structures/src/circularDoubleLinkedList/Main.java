package circularDoubleLinkedList;

class Main {
	public static void main(String[] args) {

		// testing the default constructor
		CDList<Integer> test = new CDList<Integer>();

		// add two elements to the list
		test.addFirst(1);
		test.addLast(2);
		test.addLast(3);

		// remove the first element
		test.removeFirst();

		// add three more elements
		test.addFirst(3);
		test.addLast(2);
		test.addFirst(4);

		// remove the last element
		test.removeLast();
		System.out.println("test list is empty: " + test.isEmpty());
		System.out.println("test list size: " + test.size());
		System.out.println("test list first element: " + test.first());
		System.out.println("test list last element: " + test.last());
		System.out.print("test:");
		test.printList();

		// rotate the list
		test.rotate();
		test.rotate();
		System.out.println("rotate list twice to the left");
		System.out.print("test:");
		test.printList();

		// rotate the list
		test.rotateBackward();
		System.out.println("rotate list once to the right");
		System.out.print("test:");
		test.printList();

		// create a list of strings
		CDList<String> test1 = new CDList<String>();
		test1.addFirst("aa");
		test1.addLast("bb");
		test1.addLast("cc");
		test1.addLast("dd");

		// clone it using the copy constructor
		CDList<String> test2 = new CDList<String>(test1);

		// clone it by using clone method
		CDList<String> test3 = test1.clone();
		
		
		
		test3.addFirst("ff");

		System.out.print("test1:");
		test1.printList();
		System.out.print("test2:");
		test2.printList();
		System.out.print("test3:");
		test3.printList();

		// equivalence testing
		if (test1.equals(test2))
			System.out.println("test1 and test2 are equal");
		if (!test1.equals(test3))
			System.out.println("test1 and test3 are not equal");

		// test attach method
		System.out.println("attaching test3 to test1");
		test1.attach(test3);
		System.out.print("test1:");
		test1.printList();

		// test duplicate removal
		System.out.println("removing duplicates from test1");
		test1.removeDuplicates();
		System.out.print("test1:");
		test1.printList();

	}
}