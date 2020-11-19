package edu.neu.coe.info6205.sort.par;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much. TODO tidy it
 * up a bit.
 */
class ParSort {

	public static int cutoff = 1000;

	public static void sort(int[] array, int from, int to) {
		if (to - from < cutoff)
			Arrays.sort(array, from, to);// If there are fewer elements to sort than the cutoff, then use the system
											// sort instead.
		else {
			CompletableFuture<int[]> parsort1 = parsort(array, from, from + (to - from) / 2);
			CompletableFuture<int[]> parsort2 = parsort(array, from + (to - from) / 2, to);
			CompletableFuture<int[]> parsort = parsort1.thenCombine(parsort2, (xs1, xs2) -> {
				int[] result = new int[xs1.length + xs2.length];
				// merge two parsorts array to one
				mergePartition(array, from, to);
				return result;
			});
			parsort.whenComplete((result, throwable) -> System.arraycopy(result, 0, array, from, result.length));
			//System.out.println("# threads: " + ForkJoinPool.commonPool().getRunningThreadCount());
			parsort.join();
		}
	}

	private static CompletableFuture<int[]> parsort(int[] array, int from, int to) {
		//int threadCount = 3;
		//ForkJoinPool myPool = new ForkJoinPool(threadCount);
		return CompletableFuture.supplyAsync(() -> {
			int[] result = new int[to - from];
			// do sorting
			System.arraycopy(array, from, result, 0, result.length);
			sort(result, 0, to - from);
			return result;
		});
	}
	


	public static void mergePartition(int a[], int from, int to) {
		if (to > 1) {
			int k = to / 2;
			for (int i = from; i < from + k; i++) {
				Swap(a, i, i + k);
			}
			mergePartition(a, from, k);
			mergePartition(a, from + k, k);
		}
	}

	public static void Swap(int a[], int i, int j) {
		if (a[i] > a[j]) {
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}



}