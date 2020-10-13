package edu.neu.coe.info6205.sort.simple;

import java.util.Arrays;

/**
 * This is a basic implementation of insertion sort.
 * It does not extend Sort, nor does it employ any optimizations.
 */
public class InsertionSortBasic {

    public void sort(Object[] a) {
        for (int i = 1; i < a.length; i++) swap(i, a);
    }

    private void swap(int i, Object[] a) {
    	int j=i-1;
    	int key=(int) a[i];
    	while(j>=0 && (int)a[j]>key) {
        swap(a,j,key);
        j--;
        key--;
        }
    	
        // TO BE IMPLEMENTED
    	
    }

    private void swap(Object[] a, int j, int i) {
        Object temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }

    public static void main(String[] args) {
        InsertionSortBasic sorter = new InsertionSortBasic();
        System.out.println(Arrays.toString(args));
        sorter.sort(args);
        System.out.println(Arrays.toString(args));
    }
}
