package connectFour.domain;

public class JCList<Type> {
	
	private Type[] list;
	private int firstFreeIndex;
	
	public JCList(){
		list = (Type[]) new Object[10];
		firstFreeIndex = 0;
	}
	
	public void add(Type obj) {
		
		if(firstFreeIndex == list.length) {
			
			growListSize();
			
		}
			
		list[firstFreeIndex] = obj;
		firstFreeIndex++;
			
	}
	
	public void replace(int index, Type obj) {
		
		if(index >= 0 && index < firstFreeIndex) {
			
			list[index] = obj;
			
		}
		
	}
	
	public int size() {
		return firstFreeIndex;
	}
	
	public Type get(int index) {
		
		if(index < 0 || index >= firstFreeIndex) {
			throw new ArrayIndexOutOfBoundsException("Invalid Index");
		}
		
		return list[index];
		
	}
	
	private void growListSize() {
		
		int length = list.length + list.length/2;
		
		Type[] newList = (Type[]) new Object[length];
		
		for(int i = 0; i < firstFreeIndex; i++) {
			newList[i] = list[i];
		}
		
		list = newList;
		
	}
	
	public boolean contains(Type obj) {
		
		return indexOf(obj) >= 0;
		
	}
	
	public void remove(Type obj) {
		
		int index = indexOf(obj);
		
		if(index >= 0) {
			firstFreeIndex--;
			shift(index);
		}
		
	}
	
	private void shift(int startIndex) {
		
		for(int i = startIndex; i < firstFreeIndex; i++) {
			
			list[i] = list[i+1];
			
		}
		
	}
	
	public int indexOf(Type obj) {
		
		for(int i = 0; i < firstFreeIndex; i++) {
			
			if(list[i].equals(obj)) {	
				return i;
			}
			
		}
		
		return -1;
		
	}

}
