package connectFour.domain;

/**
 * @author xuesheng (chenji@thayer.org)
 * Date created: Feb 12, 2021
 * Date due: Feb 24, 2021
 * This program simulates the popular board game -- Connect Four
 * This class contains many functionalities of ArrayList, and can be used as an ArrayList
 */

public class JCList<Type> {
	
	private Type[] list;
	private int firstFreeIndex; //where the next object will be added
	
	public JCList(){
		list = (Type[]) new Object[10];
		firstFreeIndex = 0;
	}
	
	//adds the object in the parameter to the list
	public void add(Type obj) {
		
		//if the total number of elements exceed the size of the list, then expand the list
		if(firstFreeIndex == list.length) {
			growListSize();
		}
			
		list[firstFreeIndex] = obj;
		firstFreeIndex++;
			
	}
	
	//replace the element at index of the list with obj
	public void replace(int index, Type obj) {
		
		if(index >= 0 && index < firstFreeIndex) {
			list[index] = obj;
		}
		
	}
	
	//returns the number of elements
	public int size() {
		return firstFreeIndex;
	}
	
	//retursn the element at the index
	public Type get(int index) {
		
		if(index < 0 || index >= firstFreeIndex) {
			throw new ArrayIndexOutOfBoundsException("Invalid Index");
		}
		
		return list[index];
		
	}
	
	//expand the list by 1.5
	private void growListSize() {
		
		int length = list.length + list.length/2;
		
		Type[] newList = (Type[]) new Object[length];
		
		for(int i = 0; i < firstFreeIndex; i++) {
			newList[i] = list[i];
		}
		
		list = newList;
		
	}
	
	//checks if the list contains obj
	//returns true if it does, false otherwise
	public boolean contains(Type obj) {
		
		return indexOf(obj) >= 0;
		
	}
	
	//removes obj from the list
	public void remove(Type obj) {
		
		int index = indexOf(obj);
		
		if(index >= 0) {
			firstFreeIndex--;
			shift(index);
		}
		
	}
	
	//shift everthing on the right of startIndex to the left by 1 slot
	private void shift(int startIndex) {
		
		for(int i = startIndex; i < firstFreeIndex; i++) {
			list[i] = list[i+1];
		}
		
	}
	
	//returns the first found index of obj in the list
	//returns -1 if obj isn't found
	public int indexOf(Type obj) {
		
		for(int i = 0; i < firstFreeIndex; i++) {
			
			if(list[i].equals(obj)) {	
				return i;
			}
			
		}
		
		return -1;
		
	}

}
