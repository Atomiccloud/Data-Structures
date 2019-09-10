package project3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BET {
	/****************************** BEGIN NODE CLASS **************************/
	private class BinaryNode<E> {
		private BinaryNode<E> parent;
		private BinaryNode<E> left;
		private BinaryNode<E> right;
		private E element;
		
		//node constructor
		public BinaryNode(BinaryNode<E> p, BinaryNode<E> r, BinaryNode<E> l, E e) {
			parent = p;
			left = l;
			right = r;
			element = e;
		}

		public E getElement() {
			return element;
		}

		public BinaryNode<E> getParent() {
			return parent;
		}

		public BinaryNode<E> getLeftChild() {
			return left;
		}

		public BinaryNode<E> getRightChild() {
			return right;
		}

		public boolean isLeaf() {
			if (this.getLeftChild() == null && this.getRightChild() == null) {
				return true;
			}
			return false;
		}
	}

	/****************************** END NODE CLASS **************************/
	//Class Objects and Variables
	private Stack<BinaryNode<String>> stacker = new Stack<>();
	private Stack<BinaryNode<String>> operatorStack = new Stack<>();
	private Queue<String> que = new LinkedList<>();
	private BinaryNode<String> root = null;
	private int size = 0;
	private int numOfLeaves = 0;
	boolean atLeastOneOp;

	public BET() {

	}

	public BET(String expr, char mode) {
		//boolean for throwing errors if no operators
		atLeastOneOp = false;
		//Pass to correct build method based on the mode
		if (mode == 'i') {
			if(!buildFromInfix(expr)){
				System.out.println("Invalid Notation: " + expr);
				System.exit(0);
			}
		} else if (mode == 'p') {
			if(!buildFromPostfix(expr)) {
				System.out.println("Invalid Notation: " + expr);
				System.exit(0);
			}
		//if not p or i then unknown mode
		} else {
			System.out.println("Unknown Mode Entered");
		}

	}

	public boolean buildFromPostfix(String postfix) {
		//if not empty, make empty
		if (!isEmpty()) {
			makeEmpty(root);
		}
		try {
			//split string into array
			String[] expr = postfix.split(" ");
			for (int i = 0; i < expr.length; i++) {
				//checks if its an operator and pops from stack and sets the children
				if (operatorCheck(expr[i])) {
					BinaryNode<String> node = new BinaryNode<String>(null, stacker.pop(), stacker.pop(), expr[i]);
					node.getLeftChild().parent = node;
					node.getRightChild().parent = node;
					stacker.push(node);
					root = node;
					atLeastOneOp = true;
				} else {
					//if not an operator push on to stack
					BinaryNode<String> node = new BinaryNode<String>(null, null, null, expr[i]);
					stacker.push(node);
				}
			}
			//if no operators throw exception
			if (!atLeastOneOp) {
				throw new Exception();
			}

		} catch (Exception e) {
			//if any errors are thrown and caught return false;
			return false;
		}
		return true;
	}

	//This is the shunting yard algorithm which takes an infix expression and converts it to a postfix expression
	//This allows me to then pass the expression into buildFromPostfix() and use it to build the tree.
	//I have opted to not use this method but left it in the code
	//NOT COMMENTED BECAUSE THIS IS NOT USED
	public boolean buildFromInfixWithShuntingYardAlgorithm(String infix) {
		try {
			//check if empty
			if (!isEmpty()) {
				makeEmpty(root);
			}
			String[] expr = infix.split(" ");
			for (int i = 0; i < expr.length; i++) {
				if (operatorCheck(expr[i])) {
					atLeastOneOp = true;
					if(!operatorStack.isEmpty()) {
						while (precedenceCheck(expr[i]) && !operatorStack.peek().getElement().equals("(")) {
							que.add(operatorStack.pop().getElement());
							if(operatorStack.isEmpty()) {
								break;
							}
						}						
					}
					BinaryNode<String> node = new BinaryNode<>(null, null, null, expr[i]);
					operatorStack.push(node);
				} else if (expr[i].equals("(")) {
					BinaryNode<String> node = new BinaryNode<>(null, null, null, expr[i]);
					operatorStack.push(node);
				} else if (expr[i].equals(")")) {
					while (!operatorStack.peek().getElement().equals("(")) {
						que.add(operatorStack.pop().getElement());
					}
					if (operatorStack.peek().getElement().equals("(")) {
						operatorStack.pop();
					}
				} else {
					que.add(expr[i]);
				}
			}
			if(!atLeastOneOp) {
				throw new Exception();
			}
			while (!operatorStack.isEmpty()) {
				if(operatorStack.peek().getElement().equals("(")) {
					throw new Exception("Invalid Notation");
				}
				que.add(operatorStack.pop().getElement());
			}
			String postfix = "";
			String q = que.toString();
			for(int i = 0; i<q.length(); i++) {
				if(que.toString().charAt(i) == '[' || que.toString().charAt(i) == ']' || que.toString().charAt(i) == ',') {
					//do nothing
				} else {
					postfix = postfix + que.toString().charAt(i);
				}
			}
			buildFromPostfix(postfix);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//Build from infix without using shunting yard algorithm
	public boolean buildFromInfix(String infix) {
		//check if empty
		if (!isEmpty()) {
			makeEmpty(root);
		}
		try {
			//split string into an array
			String[] expr = infix.split(" ");
			//this is for a case with parenthesis to pop when nested
			boolean pop = true;
			for (int i = 0; i < expr.length; i++) {
				//series of if checks for (, ), operators or operand
				if (expr[i].equals("(")) {
					BinaryNode<String> node = new BinaryNode<String>(null, null, null, expr[i]);
					stacker.push(node);
					pop = false;
				}
				if (expr[i].equals(")")) {
					//flags for popping and entering loop
					boolean exitLoop = false;
					pop = true;
					while (!exitLoop) {
						//builds the subtree until ( is on top of stack
						BinaryNode<String> node = operatorStack.pop();
						root = node;
						node.right = stacker.pop();
						node.getRightChild().parent = node;
						node.left = stacker.pop();
						node.getLeftChild().parent = node;
						if (stacker.peek().getElement().equals("(")) {
							//pop and discard (
							stacker.pop();
							exitLoop = true;
						}
						if (i < expr.length - 1 || !stacker.isEmpty()) {
							stacker.push(node);
						}
					}
				}
				if (operatorCheck(expr[i])) {
					// check to see greater magnitude
					BinaryNode<String> node = new BinaryNode<String>(null, null, null, expr[i]);
					if (!operatorStack.isEmpty()) {
						//if greater then push the node
						if (expr[i].equals("/") && operatorStack.peek().element.equals("+")) {
							operatorStack.push(node);
						} else if (expr[i].equals("*") && operatorStack.peek().element.equals("+")) {
							operatorStack.push(node);
						} else if (expr[i].equals("/") && operatorStack.peek().element.equals("-")) {
							operatorStack.push(node);
						} else if (expr[i].equals("*") && operatorStack.peek().element.equals("-")) {
							operatorStack.push(node);
						} else if (pop == false) {
							operatorStack.push(node);
							pop = true;
						} else {
							//if not then make subtree
							BinaryNode<String> stackNode = operatorStack.pop();
							root = stackNode;
							stackNode.right = stacker.pop();
							stackNode.getRightChild().parent = stackNode;
							stackNode.left = stacker.pop();
							stackNode.getLeftChild().parent = stackNode;
							stacker.push(stackNode);
							operatorStack.push(node);
						}
					} else {
						//if none of the above then push node
						operatorStack.push(node);
					}
					//if not operator or paren then make node and push on stacker
				} else if (!operatorCheck(expr[i]) && !expr[i].equals("(") && !expr[i].equals(")")) {
					BinaryNode<String> node = new BinaryNode<String>(null, null, null, expr[i]);
					stacker.push(node);
				}
			}
			//if stacks are not empty then build tree until empty
			while (!operatorStack.isEmpty() || !stacker.isEmpty()) {
				BinaryNode<String> stackNode = operatorStack.pop();
				root = stackNode;
				stackNode.right = stacker.pop();
				stackNode.getRightChild().parent = stackNode;
				stackNode.left = stacker.pop();
				stackNode.getLeftChild().parent = stackNode;
				if (!operatorStack.isEmpty()) {
					stacker.push(stackNode);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void printInfixExpression() {
		printInfixExpression(root);
		System.out.println();
	}

	public void printPostfixExpression() {
		printPostfixExpression(root);
		System.out.println();

	}

	public int size() {
		//call size
		size = 0;
		return size(root);
	}

	public boolean isEmpty() {
		//check if size is 0
		return size() == 0;
	}

	public int leafNodes() {
		//call leaf nodes
		numOfLeaves = 0;
		return leafNodes(root);
	}

	private void printInfixExpression(BinaryNode<String> n) {
		//print using recursive
		if (n.getLeftChild() != null) {
			System.out.print("( ");
			printInfixExpression(n.getLeftChild());

		}

		System.out.print(n.getElement() + " ");

		if (n.getRightChild() != null) {
			printInfixExpression(n.getRightChild());
			System.out.print(") ");
		}
	}

	private void makeEmpty(BinaryNode<String> t) {
		//recursively set all nodes to null
		atLeastOneOp = false;
		if (t.getLeftChild() != null) {
			makeEmpty(t.getLeftChild());
		}
		if (t.getRightChild() != null) {
			makeEmpty(t.getRightChild());
		}
		t.left = null;
		t.right = null;
		t.parent = null;
		t.element = null;
		root = null;
		size--;
	}

	private void printPostfixExpression(BinaryNode<String> n) {
		//print using recursion
		if (n.getLeftChild() != null) {
			printPostfixExpression(n.getLeftChild());
		}
		if (n.getRightChild() != null) {
			printPostfixExpression(n.getRightChild());
		}
		System.out.print(n.getElement() + " ");
	}

	private int size(BinaryNode<String> t) {
		//counts the amount of nodes and returns it
		if (t == null) {
			return 0;
		}
		if (t.getLeftChild() != null) {
			size(t.getLeftChild());
		}
		if (t.getRightChild() != null) {
			size(t.getRightChild());
		}
		size++;
		return size;
	}

	private int leafNodes(BinaryNode<String> t) {
		//checks and counts leaf nodes
		if (t == null) {
			return 0;
		}
		if (t.getLeftChild() != null) {
			leafNodes(t.getLeftChild());
		}
		if (t.getRightChild() != null) {
			leafNodes(t.getRightChild());
		}
		if (t.isLeaf()) {
			numOfLeaves++;
		}
		return numOfLeaves;
	}

	//checks if operator
	private boolean operatorCheck(String s) {
		switch (s) {
		case "+":
			return true;
		case "-":
			return true;
		case "*":
			return true;
		case "/":
			return true;
		default:
			return false;
		}
	}

	//only used in shunting yard algorithm
	private boolean precedenceCheck(String s) {
			if (s.equals("/") && operatorStack.peek().element.equals("+")) {
				return false;
			} else if (s.equals("*") && operatorStack.peek().element.equals("+")) {
				return false;
			} else if (s.equals("/") && operatorStack.peek().element.equals("-")) {
				return false;
			} else if (s.equals("*") && operatorStack.peek().element.equals("-")) {
				return false;
			}
		return true;
	}
}
