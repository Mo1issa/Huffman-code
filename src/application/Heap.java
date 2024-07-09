package application;

	/**
 * heap class create heap using array 
 * min capacity 
 */
import java.util.*;

@SuppressWarnings("unchecked")
public class Heap<AnyType extends Comparable<AnyType>> implements Comparable<Heap<AnyType>>{
   private static final int CAPACITY = 2;
   protected int size;            // Number of elements in heap
   protected AnyType[] heap;     // The heap array

   public Heap(){
      size = 0;
      heap = (AnyType[]) new Comparable[CAPACITY];
   }

 /*
  * Construct the binary heap given an array of items.
  */
   public Heap(AnyType[] array){
      size = array.length;
      heap = (AnyType[]) new Comparable[array.length+1];

      System.arraycopy(array, 0, heap, 1, array.length);//we do not use 0 index

      buildHeap();
   }

   private void buildHeap(){
      for (int k = size/2; k > 0; k--)
         sink(k);
   }
   
   private void sink(int k){
      AnyType tmp = heap[k];
      int child;

      for(; 2*k <= size; k = child)
      {
         child = 2*k;

         if(child != size &&
            heap[child].compareTo(heap[child + 1]) > 0)
        	 child++;

         if(tmp.compareTo(heap[child]) > 0)  
        	 heap[k] = heap[child];
         else
             break;
      }
      heap[k] = tmp;
   }

 /**
  * Deletes the top item
  */
   public AnyType deleteMin() throws RuntimeException{
      if (size == 0)
    	  throw new RuntimeException();
      AnyType min = heap[1];
      heap[1] = heap[size--];
      sink(1);
      return min;
	}
  
   
   public AnyType getHead(){
	   return this.heap[0];
   }
   
 /**
  * Inserts a new item
  */
   public void insert(AnyType x){
	    
      if(size == heap.length - 1)
    	  doubleSize();

      //Insert a new item to the end of the array
      int pos = ++size;

      //Percolate up
      for(; pos > 1 && x.compareTo(heap[pos/2]) < 0; pos = pos/2 )
         heap[pos] = heap[pos/2];

      heap[pos] = x;
   }
   
   public void insert(Heap<AnyType> x){
	      if(size == heap.length - 1)
	    	  doubleSize();

	      //Insert a new item to the end of the array
	      int pos = ++size;

	      //Percolate up
	      for(; pos > 1 && x.getHead().compareTo(heap[pos/2]) < 0; pos = pos/2 )
	         heap[pos] = heap[pos/2];

	      heap[pos] = x.getHead();
	   }
   
   private void doubleSize(){
      AnyType [] old = heap;
      heap = (AnyType []) new Comparable[heap.length * 2];
      System.arraycopy(old, 1, heap, 1, size);
   }

//   public static void main(String[] args){
//	  Integer temp[] = {5,9,12,13,16,45};
//	  Heap<Integer> arrayofHeap[] = new Heap [temp.length];
//	  
//	  for (int i=0 ; i<arrayofHeap.length ; i++)
//		  arrayofHeap[i] = new Heap<Integer>(new Integer[] {temp[i]});
//      
//	 
//    while (arrayofHeap.length > 1){
//    	int j =0 ; 
//    	
//    	Integer tempA[] = new Integer[arrayofHeap[j].size];
//    	for (int i=0 ; i<tempA.length ; i++)
//    		tempA[i] = arrayofHeap[j].deleteMin();
//    	Arrays.sort(tempA);
//    	Integer t1 = tempA[tempA.length-1];
//    	j ++; 
//    	Integer tempB[] = new Integer[arrayofHeap[j].size];
//    	for (int i=0 ; i<tempB.length ; i++)
//    		tempB[i] = arrayofHeap[j].deleteMin();
//    	Arrays.sort(tempB);
//    	Integer t2 = tempB[tempB.length-1];
//    	 for (int i=0 ; i<tempA.length ; i++)
//    		 arrayofHeap[j].insert(tempA[i]);
//    	 for (int i=0 ; i<tempB.length ; i++)
//    		 arrayofHeap[j].insert(tempB[i]);
//    	 
//    	arrayofHeap[j].insert(t2 + t1);
//	    arrayofHeap = Arrays.copyOfRange(arrayofHeap, 1,arrayofHeap.length);
//	    arrayofHeap = sort(arrayofHeap);
//    }
//
//    Integer tempResult[] = new Integer[arrayofHeap[0].size];
//    for (int i=0 ; i<tempResult.length ; i++)
//    	tempResult[i] = arrayofHeap[0].deleteMin();
//    //reverse array 
//    for (int i=0 , j = tempResult.length-1 ; i <tempResult.length/2; i++, j--){
//    	int tempValue = tempResult[i];
//    	tempResult[i] = tempResult[j];
//    	tempResult[j] = tempValue;
//    }
//    
////    Tree<Integer> tree = new Tree<Integer>();
////    tree.root = new TreeNode<Integer>(tempResult[0],"");
////    for (int i=1 ; i<tempResult.length ; i+=2)
////    	tree.insert(tempResult[i],tempResult[i+1]);
////    System.out.println(tree.inOrderTraversal());
////    
//   }

@Override
	public int compareTo(Heap<AnyType> o) {
		return 0;
	}

//	public static Heap<Integer>[] sort (Heap<Integer>[] array){
//		Integer[] original = new Integer[array.length];
//		Integer[] sorted = new Integer[array.length];
//  		for (int i=0 ; i<array.length ; i++){
//			Integer tempA[] = new Integer[array[i].size];
//			for (int j=0 ; j<tempA.length ; j++)
//	    		tempA[j] = (Integer)array[i].deleteMin();
//	    	Arrays.sort(tempA);
//	    	original[i] = tempA[tempA.length-1];
//	    	sorted[i] = tempA[tempA.length-1];
//	    	array[i] = new Heap<Integer>(tempA);
//	    	
//		}
//  		Arrays.sort(sorted);
//  		int index[] = new int [array.length];
//  		for (int i=0 ; i<index.length;i++)
//  			index[i] = Arrays.binarySearch(sorted, original[i]);
//  		Heap<Integer> arrayofHeap[] = new Heap [array.length];
//  		for (int i=0 ; i<array.length ; i++){
//  			Integer tempA[] = new Integer[array[i].size];
//			for (int j=0 ; j<tempA.length ; j++)
//	    		tempA[j] = (Integer)array[i].deleteMin();
//  			arrayofHeap[index[i]] = new Heap<Integer>( tempA );
//  		}
//  		return arrayofHeap;
//	}
}










