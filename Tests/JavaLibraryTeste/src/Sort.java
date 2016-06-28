

import java.util.Comparator;
import java.util.Random;

/**
 * A class that contains several sorting routines,
 * implemented as static methods.
 * Arrays are rearranged with smallest item first,
 * using compareTo.
 * @author Mark Allen Weiss
 */
public final class Sort
{
    /**
     * Simple insertion sort.
     * @param a an array of Comparable items.
     */
    //@ requires a != null;
    //@ ensures isSorted(a);
    public void insertionSort( int[ ] a )
    {
        int j;

        for( int p = 1; p < a.length; p++ )
        {
            int tmp = a[ p ];
            
            for( j = p; j > 0 && tmp<a[ j - 1 ]; j-- )
                a[ j ] = a[ j - 1 ];

            a[ j ] = tmp;
        }
    }

    /**
     * Shellsort, using Shell's (poor) increments.
     * @param a an array of Comparable items.
     */
    //@ requires a != null;
    //@ ensures isSorted(a);
    public void shellsort(int[ ] a )
    {
        int j;

        /* 1*/
        for( int gap = a.length / 2; gap > 0; gap /= 2 )
            /* 2*/          for( int i = gap; i < a.length; i++ )
            {
                /* 3*/              int tmp = a[ i ];
                /* 4*/
                for( j = i; j >= gap &&
                        tmp < a[ j - gap ] ; j -= gap )
                    /* 5*/                  a[ j ] = a[ j - gap ];
                /* 6*/
                a[ j ] = tmp;
            }
    }
    
    private  boolean isSorted(int[] a) {
        for(int i=0; i<a.length-1; i++) {
            if(a[i]>a[i+1]) {
                return false;
            }
        }
        return true;
    }


    /**
     * Standard heapsort.
     * @param a an array of Comparable items.
     */
    //@ requires a != null;
    //@ ensures isSorted(a);
    private static void heapsort( Comparable [ ] a )
    {
        /* 1*/      for( int i = a.length / 2; i >= 0; i-- )  /* buildHeap */
            /* 2*/          percDown( a, i, a.length );
        /* 3*/
        for( int i = a.length - 1; i > 0; i-- )
        {
            /* 4*/          swapReferences( a, 0, i );            /* deleteMax */
            /* 5*/
            percDown( a, 0, i );
        }
    }

    /**
     * Internal method for heapsort.
     * @param i the index of an item in the heap.
     * @return the index of the left child.
     */
    private static int leftChild( int i )
    {
        return 2 * i + 1;
    }

    /**
     * Internal method for heapsort that is used in
     * deleteMax and buildHeap.
     * @param a an array of Comparable items.
     * @index i the position from which to percolate down.
     * @int n the logical size of the binary heap.
     */
    private static void percDown( Comparable [ ] a, int i, int n )
    {
        int child;
        Comparable tmp;

        /* 1*/
        for( tmp = a[ i ]; leftChild( i ) < n; i = child )
        {
            /* 2*/          child = leftChild( i );
            /* 3*/
            if( child != n - 1 && a[ child ].compareTo( a[ child + 1 ] ) < 0 )
                /* 4*/              child++;
            /* 5*/
            if( tmp.compareTo( a[ child ] ) < 0 )
                /* 6*/              a[ i ] = a[ child ];
            else
                /* 7*/              break;
        }
        /* 8*/      a[ i ] = tmp;
    }

    /**
     * Mergesort algorithm.
     * @param a an array of Comparable items.
     */
    //@ requires a != null;
    //@ ensures isSorted(a);
    public void mergeSort( int[ ] a )
    {
        int [ ] tmpArray = new int[ a.length ];

        mergeSort( a, tmpArray, 0, a.length - 1 );
    }

    public static void mergeSort( int[ ] a, int[ ] tmpArray,
                                   int left, int right )
    {
        if( left < right )
        {
            int center = ( left + right ) / 2;
            mergeSort( a, tmpArray, left, center );
            mergeSort( a, tmpArray, center + 1, right );
            merge( a, tmpArray, left, center + 1, right );
        }
    }

    public static void merge(  int[ ] a, int[ ] tmpArray,
                                int leftPos, int rightPos, int rightEnd )
    {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;

        while( leftPos <= leftEnd && rightPos <= rightEnd )
            if( a[ leftPos ] <= a[ rightPos ] )
                tmpArray[ tmpPos++ ] = a[ leftPos++ ];
            else
                tmpArray[ tmpPos++ ] = a[ rightPos++ ];

        while( leftPos <= leftEnd ) 
            tmpArray[ tmpPos++ ] = a[ leftPos++ ];

        while( rightPos <= rightEnd )
            tmpArray[ tmpPos++ ] = a[ rightPos++ ];

        for( int i = 0; i < numElements; i++, rightEnd-- )
            a[ rightEnd ] = tmpArray[ rightEnd ];
    }

    /**
     * Quicksort algorithm.
     * @param a an array of Comparable items.
     */
    public static void quicksort( int[ ] a )
    {
        quicksort( a, 0, a.length - 1 );
    }

    private static final int CUTOFF = 10;

    /**
     * Method to swap to elements in an array.
     * @param a an array of objects.
     * @param index1 the index of the first object.
     * @param index2 the index of the second object.
     */
    public static final void swapReferences( Object [ ] a, int index1, int index2 )
    {
        Object tmp = a[ index1 ];
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = tmp;
    }
    
    public static final void swap(int[] a,int index1,int index2) {
        int tmp = a[ index1 ];
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = tmp;
    }

    /**
     * Return median of left, center, and right.
     * Order these and hide the pivot.
     */
    public static int median3( int[ ] a, int left, int right )
    {
        int center = ( left + right ) / 2;
        if( a[ center ]<a[ left ] )
            swap( a, left, center );
        if( a[ right ] < a[ left ] )
            swap( a, left, right );
        if( a[ right ] < a[ center ] )
            swap( a, center, right );

        // Place pivot at position right - 1
        swap( a, center, right - 1 );
        return a[ right - 1 ];
    }

    /**
     * Internal quicksort method that makes recursive calls.
     * Uses median-of-three partitioning and a cutoff of 10.
     * @param a an array of Comparable items.
     * @param left the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     */
    public static void quicksort( int[ ] a, int left, int right)
    {
        if( left + CUTOFF <= right )
        {
            int pivot = median3( a, left, right );

            int i = left, j = right - 1;

            for( ; ; )
            {
                while( a[ ++i ] < pivot )  { }
                while( a[ --j ] > pivot ) { }
                if( i < j )
                    swap( a, i, j );
                else
                    break;
            }

            swap( a, i, right - 1 );

            quicksort( a, left, i - 1 );
            quicksort( a, i + 1, right );
        }
        else
            insertionSort( a, left, right );
    }

    /**
     * Internal insertion sort routine for subarrays
     * that is used by quicksort.
     * @param a an array of Comparable items.
     * @param left the left-most index of the subarray.
     * @param right the right-most index of the subarray.
     */
    public static void insertionSort( int[ ] a, int left, int right )
    {
        for( int p = left + 1; p <= right; p++ )
        {
            int tmp = a[ p ];
            int j;

            for( j = p; j > left && tmp < a[ j - 1 ]; j-- )
                a[ j ] = a[ j - 1 ];
            a[ j ] = tmp;
        }
    }

    /**
     * Quick selection algorithm.
     * Places the kth smallest item in a[k-1].
     * @param a an array of Comparable items.
     * @param k the desired rank (1 is minimum) in the entire array.
     */


    private static final int NUM_ITEMS = 1000;
    private static int theSeed = 1;

}
