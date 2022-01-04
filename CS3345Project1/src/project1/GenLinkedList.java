package project1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class GenLinkedList<T> {
	//Head and tail nodes, and current size
	private Node<T> head;
	private Node<T> tail;
	private int size = 0;
	
	private class Node<T> {
		//Data and next node
		private T data;
		private Node<T> next;
		
		//Constructor to call parameters
		Node(T d, Node<T> n) {
			data = d;
			next = n;
		}
	}
	
	public void addFront(T d) {
		//If head is null, create new head and make it tail also
		if (head == null) {
			head = new Node<T>(d, null);
			tail = head;
		}
		//Otherwise, update the new node to be new head
		else {
			Node<T> newNode = new Node<T>(d, head);
			
			newNode.next = head;
			head = newNode;
		}
		++size;
	}
	
	public void addEnd(T d) {
		//If head is null, create new head and make it tail also
		if (head == null) {
			head = new Node<T>(d, null);
			tail = head;
		}
		//Else, put it at end and update tail
		else {
			Node<T> newNode = new Node<T>(d, null);
			
			tail.next = newNode;
			tail = newNode;
		}
		++size;
	}
	
	public T removeFront() {
		T oldData = null;
		
		//If no head, throw exception
		if (head == null)
			throw new NoSuchElementException();
		
		//If tail equals head, nullify both
		else if (head == tail) {
			oldData = (T) head.data;
			
			head = null;
			tail = null;
		}
		//Otherwise, update head to next node and nullify previous head
		else {
			oldData = (T) head.data;
			
			Node<T> newHead = head.next;
			head = newHead;
		}
		--size;
		return oldData;
	}
	
	public T removeEnd() {
		T oldData = null;
		
		//If no head or tail, throw exception
		if (head == null || tail == null)
			throw new NoSuchElementException();
		
		//If head is tail, nullify both
		else if (head == tail) {
			oldData = (T) tail.data;
			
			head = null;
			tail = null;
		}
		//Else, go to the second to last node and make it new tail and nullify next node
		else {
			Node<T> temp = head;
			
			while (temp.next != tail) {
				if (temp.next != null)
					temp = temp.next;
			}
			oldData = (T) tail.data;
			tail = temp;
			tail.next = null;
		}
		--size;
		return oldData;
	}
	
	public void set(T d, int position) {
		//If index out of bounds, throw exception
		if (position < 0 || position >= size)
			throw new ArrayIndexOutOfBoundsException("Index " + position + " is invalid for size " + size);
		
		//If set at index 0 or the last position, set at front and back
		else if (position == 0)
			head.data = d;
		else if (position == size - 1)
			tail.data = d;
		
		else {
			//Temporary node and index
			Node<T> temp = head;
			int tempInd = 0;
			
			//Iterate until just before position, then update data
			while (tempInd < position) {
				if (temp.next != null)
					temp = temp.next;
				
				++tempInd;
			}
			temp.data = d;
		}
	}
	
	public T get(int position) {	
		//If index out of bounds, throw exception
		if (position < 0 || position >= size)
			throw new ArrayIndexOutOfBoundsException("Index " + position + " is invalid for size " + size);
		
		//Otherwise, iterate to the value at the position and return the data
		else {
			Node<T> temp = head;
			int tempCnt = 0;
			
			while (tempCnt < position) {
				++tempCnt;
				temp = temp.next;
			}
			return (T) temp.data;
		}
	}
	
	public void add(T d, int position) {
		//If index out of bounds, throw exception
		if (position < 0 || position > size)
			throw new ArrayIndexOutOfBoundsException("Index " + position + " is invalid for size " + size);
		
		//If set at index 0 or the size, add at front or back respectively
		else if (position == 0)
			addFront(d);
		else if (position == size)
			addEnd(d);
		
		//Else, find the index just before the position and put node after that, put in between last iteration and its next
		else {
			Node<T> temp = head;
			int tempInd = 0;
			
			while (tempInd < position - 1) {
				if (temp.next != null)
					temp = temp.next;
				
				++tempInd;
			}
			Node<T> newNode = new Node<T>(d, temp.next);
			temp.next = newNode;
			++size;
		}
	}
	
	private void remove(int position) {
		//If index out of bounds, throw exception
		if (position < 0 || position >= size)
			throw new ArrayIndexOutOfBoundsException("Index " + position + " is invalid for size " + size);
		
		//If index at 0 or last index, remove first or last value respectively
		else if (position == 0) 
			removeFront();
		else if (position == size - 1)
			removeEnd();
		
		//Else, go to the node just at position before the one we delete and point it to two nodes after
		//Use prev and point it to temp next pointer to skip temp (node to delete)
		else {
			Node<T> temp = head;
			Node<T> prev = null;
			int tempCnt = 0;
			
			while (tempCnt < position) {
				prev = temp;
				
				if (temp.next != null)
					temp = temp.next;
				++tempCnt;
			}
			prev.next = temp.next;
			
			temp.next = null;
			temp = null;
			--size;
		}
	}
	
	private Node<T> getNode(int position) {
		//If index out of bounds, throw exception
		if (position < 0 || position >= size)
			throw new ArrayIndexOutOfBoundsException("Index " + position + " is invalid for size " + size);
		
		//Else, go to the node at desired position and return it
		else {
			Node<T> temp = head;
			int tempCnt = 0;
			
			while (tempCnt < position) {
				++tempCnt;
				
				if (temp.next != null)
					temp = temp.next;
			}
			return temp;
		}
	}
	
	public void swap(int position1, int position2) {
		//If index out of bounds, throw exception
		if (position1 < 0 || position1 >= size)
			throw new ArrayIndexOutOfBoundsException("Index " + position1 + " is invalid for size " + size);
		
		//If index out of bounds, throw exception
		else if (position2 < 0 || position2 >= size)
			throw new ArrayIndexOutOfBoundsException("Index " + position2 + " is invalid for size " + size);
		
		//Else, get the nodes at positions, replace node at position 1 with node at position 2 and vice versa
		else {
			Node<T> node1 = getNode(position1);
			Node<T> node2 = getNode(position2);
			
			remove(position1);
			add((T) node2.data, position1);
			
			remove(position2);
			add((T) node1.data, position2);
		}
	}
	
	private void rotatePart(int start, int end) {
		//While start less than end, swap at indices start and end, then shift start right and shift end left
		while (start < end) {
			swap(start, end);
			++start;
			--end;
		}
	}
	
	public void shift(int places) {
		//If shift by greater than size or less than 0, throw exception
		if (Math.abs(places) > size)
			throw new ArrayIndexOutOfBoundsException("Shifting by " + places + " is too much for size " + size);
		
		//Shift by 0 places, then end
		else if (places == 0)
			return;
		
		else {
			//If places is positive, subtract its absolute value from size to get opposite
			if (places >= 0)
				places = size - Math.abs(places);
			//If negative, convert to absolute value
			else 
				places = Math.abs(places);

			//Rotate from index 0 to last index, 0 to one before places, and places to last index (to get all latter indices rotated)
			rotatePart(0, size - 1);
			rotatePart(0, places - 1);
			rotatePart(places, size - 1);
		}
	}	
	
	public void removeMatching(T d) {
		Node<T> temp = head;
		
		//Iterate through the list (after head)
		while (temp.next != null) {
			Node<T> nextNode = temp.next;
			
			//If next node has item we want to delete
			if (nextNode.data == d) {
				//If next node is null, point to null
				if (nextNode.next == null) {
					temp.next = null;
					tail = temp;
				}
				//Else, point current to next node's next and nullify next node
				else {
					temp.next = nextNode.next;
					nextNode.next = null;
					nextNode = null;
				}
				--size;
			}
			//Else, traverse
			else
				temp = temp.next;
		}
		//Remove the head if we need to
		if (head.data == d)
			removeFront();
	}
	
	public void erase(int position, int eraseCount) {
		//If position or number of nodes to erase from and including there is negative, error
		if (position < 0 || eraseCount < 0)
			throw new ArrayIndexOutOfBoundsException("Please enter a valid starting index and erase count value");
		
		//If position and number of nodes to erase (from and including) is out of bounds, error
		else if ((position + eraseCount) > size) 
			throw new ArrayIndexOutOfBoundsException("Please stay in bounds");
		
		//If erase nothing
		else if (eraseCount == 0)
			return;
		
		else {
			//Else, traverse and count number of nodes
			Node<T> temp = head;
			int tempCnt = 0;
			
			//While point to valid node, traverse
			while (temp.next != null) {
				Node<T> nextNode = temp.next;
				
				//If count is one before position to less than the sum of position and number of nodes to delete, traverse
				if (tempCnt >= position - 1 && tempCnt < (position + eraseCount) - 1) {
					//If next node is null, point to null
					if (nextNode.next == null) {
						temp.next = null;
						tail = temp;
					}
					//Else, point current to next node's next and nullify next node
					else {
						temp.next = nextNode.next;
						nextNode.next = null;
						nextNode = null;
					}
					--size;
				}
				//Else, traverse 
				else
					temp = temp.next;
				//One more node viewed
				++tempCnt;
			}
			//If start from position 0, remove the head
			if (position == 0)
				removeFront();
		}
	}
	
	private List<Node<T>> arrayToLinkedList(List<T> list) {
		//Nodes will be added to linked list (starting with head node of list)
		List<Node<T>> nodes = new LinkedList<Node<T>>();
		Node<T> tempNode = new Node<T>(list.get(0), null);
		nodes.add(tempNode);
		
		//Iterate from 2nd to last index, then make new node for value and add into linked list
		for (int i=1; i<list.size(); i++) {
			Node<T> anotherNode = new Node<T>(list.get(i), null);
			nodes.add(anotherNode);
			
			//Point to that value and traverse temporary pointer
			tempNode.next = anotherNode;
			tempNode = tempNode.next;
		}
		return nodes;
	}
	
	public void insertList(List<T> list, int position) {
		//If position out of bounds, throw exception
		if (position < 0 || position > size)
			throw new ArrayIndexOutOfBoundsException("Index " + position + " is invalid for size " + size);
		
		//If array empty, skip
		else if (list.size() == 0)
			return;
		
		else {
			//Get linked list converted list, then get first and last values of it
			List<Node<T>> nodes = arrayToLinkedList(list);
			Node<T> firstListNode = nodes.get(0);
			Node<T> lastListNode = nodes.get(list.size() - 1);
			
			//If list is empty, head, tail, and size are completely based off of input array 
			if (size == 0) {
				head = firstListNode;
				tail = lastListNode;
				size = list.size();
			}
			//If enter at position 0, link last node of list to head, and first node of list is new head
			else if (position == 0) {
				firstListNode.next = head;
				head = lastListNode;
				
				//Increment size by size of input array 
				size += list.size();
			}
			//If enter at size after last index, point tail to first node of list, and make last node of list new tail
			else if (position == size) {
				tail.next = firstListNode;
				tail = lastListNode;
				
				//Increment size by size of input array 
				size += list.size();
			}
			else {
				//Start from head and count from 0
				Node<T> temp = head;
				int tempCnt = 0;
					
				//While pointing to not null value, traverse
				while (temp.next != null) {
					Node<T> nextNode = temp.next;
					
					//One position before position to enter list, last node of list to next node, and temp to first list node
					if (tempCnt == (position - 1)) {
						lastListNode.next = nextNode;
						temp.next = firstListNode;
						break;
					}
					//Increment count and traverse
					++tempCnt;
					temp = temp.next;
				}
				//Increment size by size of input array 
				size += list.size();
			}
		}
	}
	
	public String toString() {
		String list = "Contents of linked list: ";	
		Node<T> temp = head;
		
		//Iterate through list to get contents of the list in a string
		while (temp != null) {
			list += temp.data;
			temp = temp.next;
			list += " ";
		}
		return list;
	}
	
	public void info() {
		if (head != null && tail != null) {
			//Show head node, tail node, and size
			System.out.println("Head node: " + head.data);
			System.out.println("Tail node: " + tail.data);
			System.out.println("Size: " + size + "\n");
		}
	}
}

class Test {
	public static void main(String args[]) {
		List<Integer> listInts = new ArrayList<Integer>();
		for (int i=1; i<=6; i++)
			listInts.add(i);
		
		//Insert list into empty linked list
		GenLinkedList<Integer> ll = new GenLinkedList<Integer>();
		ll.insertList(listInts, 0);

		String list = ll.toString();
		System.out.println(list);
		ll.info();
		
		//Add to front and back of list
		ll.addFront(69);
		ll.addEnd(66);
		String list2 = ll.toString();
		System.out.println(list2);
		ll.info();
		
		//Remove front and back of list
		ll.removeFront();
		ll.removeEnd();
		String list3 = ll.toString();
		System.out.println(list3);
		ll.info();
		
		//Set a node at index 3
		ll.add(6, 3);
		String list4 = ll.toString();
		System.out.println(list4);
		ll.info();
		
		//Get node at index 3
		int num = ll.get(3);
		System.out.println("Value at position 3: " + num + "\n");
		
		//Swap indices 1 and 6
		ll.swap(0, 5);
		String list5 = ll.toString();
		System.out.println(list5);
		ll.info();
		
		//Remove nodes with value 6
		ll.removeMatching(6);
		String list6 = ll.toString();
		System.out.println(list6);
		ll.info();
		
		//Erase 3 values starting from index 0
		ll.erase(0, 3);
		String list7 = ll.toString();
		System.out.println(list7);
		ll.info();
		
		List<Integer> listN = new LinkedList<>();
		for (int i=1; i<=4; i++)
			listN.add(69);
		
		//Add a list to index 1
		ll.insertList(listN, 1);
		String list8 = ll.toString();
		System.out.println(list8);
		ll.info();
		
		//Rotate by 2
		ll.shift(2);
		String list9 = ll.toString();
		System.out.println(list9);
		ll.info();

		//Set to 69
		ll.set(69, 4);
		String list10 = ll.toString();
		System.out.println(list10);
		ll.info();
	}
}