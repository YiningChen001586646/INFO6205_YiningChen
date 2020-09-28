/*
 * Copyright (c) 2018. Phasmid Software
 */

package edu.neu.coe.info6205.util;

import java.util.function.Consumer;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import edu.neu.coe.info6205.sort.simple.InsertionSort;

import static edu.neu.coe.info6205.util.Utilities.formatWhole;

/**
 * This class implements a simple Benchmark utility for measuring the running time of algorithms.
 * It is part of the repository for the INFO6205 class, taught by Prof. Robin Hillyard
 * <p>
 * It requires Java 8 as it uses function types, in particular, UnaryOperator&lt;T&gt; (a function of T => T),
 * Consumer&lt;T&gt; (essentially a function of T => Void) and Supplier&lt;T&gt; (essentially a function of Void => T).
 * <p>
 * In general, the benchmark class handles three phases of a "run:"
 * <ol>
 *     <li>The pre-function which prepares the input to the study function (field fPre) (may be null);</li>
 *     <li>The study function itself (field fRun) -- assumed to be a mutating function since it does not return a result;</li>
 *     <li>The post-function which cleans up and/or checks the results of the study function (field fPost) (may be null).</li>
 * </ol>
 * <p>
 * Note that the clock does not run during invocations of the pre-function and the post-function (if any).
 *
 * @param <T> The generic type T is that of the input to the function f which you will pass in to the constructor.
 */
public class Benchmark_Timer<T> implements Benchmark<T> {

    /**
     * Calculate the appropriate number of warmup runs.
     *
     * @param m the number of runs.
     * @return at least 2 and at most m/10.
     */
    static int getWarmupRuns(int m) {
        return Integer.max(2, Integer.min(10, m / 10));
    }

    /**
     * Run function f m times and return the average time in milliseconds.
     *
     * @param supplier a Supplier of a T
     * @param m        the number of times the function f will be called.
     * @return the average number of milliseconds taken for each run of function f.
     */
    @Override
    public double runFromSupplier(Supplier<T> supplier, int m) {
        logger.info("Begin run: " + description + " with " + formatWhole(m) + " runs");
        // Warmup phase
        final Function<T, T> function = t -> {
            fRun.accept(t);
            return t;
        };
        new Timer().repeat(getWarmupRuns(m), supplier, function, fPre, null);

        // Timed phase
        return new Timer().repeat(m, supplier, function, fPre, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun, Consumer<T> fPost) {
        this.description = description;
        this.fPre = fPre;
        this.fRun = fRun;
        this.fPost = fPost;
    }

    /**
     * Constructor for a Benchmark_Timer with option of specifying all three functions.
     *
     * @param description the description of the benchmark.
     * @param fPre        a function of T => T.
     *                    Function fPre is run before each invocation of fRun (but with the clock stopped).
     *                    The result of fPre (if any) is passed to fRun.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, UnaryOperator<T> fPre, Consumer<T> fRun) {
        this(description, fPre, fRun, null);
    }

    /**
     * Constructor for a Benchmark_Timer with only fRun and fPost Consumer parameters.
     *
     * @param description the description of the benchmark.
     * @param fRun        a Consumer function (i.e. a function of T => Void).
     *                    Function fRun is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     *                    When you create a lambda defining fRun, you must return "null."
     * @param fPost       a Consumer function (i.e. a function of T => Void).
     */
    public Benchmark_Timer(String description, Consumer<T> fRun, Consumer<T> fPost) {
        this(description, null, fRun, fPost);
    }

    /**
     * Constructor for a Benchmark_Timer where only the (timed) run function is specified.
     *
     * @param description the description of the benchmark.
     * @param f           a Consumer function (i.e. a function of T => Void).
     *                    Function f is the function whose timing you want to measure. For example, you might create a function which sorts an array.
     */
    public Benchmark_Timer(String description, Consumer<T> f) {
        this(description, null, f, null);
    }

    private final String description;
    private final UnaryOperator<T> fPre;
    private final Consumer<T> fRun;
    private final Consumer<T> fPost;

    final static LazyLogger logger = new LazyLogger(Benchmark_Timer.class);
    
    public static void main(String[] args) {
    	InsertionSort insertionsort= new InsertionSort();
    	Random rd = new Random();
    	int length=1000;
    	int reverse=length;
    	Integer[] ordered= new Integer[length];
    	Integer[] reversed=new Integer[length];
    	Integer[] random= new Integer[length];
    	Integer[] partially= new Integer[length];

    	//random & sorted &	reverse
    	 for (int i = 0; i < length; i++) {
            random[i] = rd.nextInt();
            ordered[i]=i;
            reversed[reverse - 1] = i; 
            reverse = reverse - 1; 
    	 }
    //partially sorted  
    	 for (int i = 0; i < length; i++) {
    		 if (i<500) partially [i]=i;
    		 else partially[i]=rd.nextInt();
    	 }

    	
   //Benchmark_Timer benchmark_timer=new Benchmark_Timer<>;
    	int runTime1=1000;
    	int runTime2=2000;
    	int runTime3=4000;
    	int runTime4=8000;
    	int runTime5=16000;
    	int runTime6=32000;
    			
    	
    			
    	//random	
    	System.out.println("Random");
    	Benchmark_Timer benchmark_timer1=new Benchmark_Timer("Test Insertion Sort_random", t->insertionsort.sort(random));
    	System.out.println("time: " +benchmark_timer1.run(random,runTime1));
    	Benchmark_Timer benchmark_timer11=new Benchmark_Timer("Test Insertion Sort_random", t->insertionsort.sort(random));
    	System.out.println("time: " +benchmark_timer11.run(random,runTime2));
    	Benchmark_Timer benchmark_timer12=new Benchmark_Timer("Test Insertion Sort_random", t->insertionsort.sort(random));
    	System.out.println("time: " +benchmark_timer12.run(random,runTime3));
    	Benchmark_Timer benchmark_timer13=new Benchmark_Timer("Test Insertion Sort_random", t->insertionsort.sort(random));
    	System.out.println("time: " +benchmark_timer13.run(random,runTime4));
    	Benchmark_Timer benchmark_timer14=new Benchmark_Timer("Test Insertion Sort_random", t->insertionsort.sort(random));
    	System.out.println("time: " +benchmark_timer14.run(random,runTime5));
    	Benchmark_Timer benchmark_timer15=new Benchmark_Timer("Test Insertion Sort_random", t->insertionsort.sort(random));
    	System.out.println("time: " +benchmark_timer15.run(random,runTime6));
    	
    	
    	
    	System.out.println("Ordered");
    	Benchmark_Timer benchmark_timer2=new Benchmark_Timer("Test Insertion Sort_ordered ", t->insertionsort.sort(ordered));
    	System.out.println("time:" +benchmark_timer2.run(ordered,runTime1));
    	Benchmark_Timer benchmark_timer21=new Benchmark_Timer("Test Insertion Sort_ordered ", t->insertionsort.sort(ordered));
    	System.out.println("time:" +benchmark_timer21.run(ordered,runTime2));
    	Benchmark_Timer benchmark_timer22=new Benchmark_Timer("Test Insertion Sort_ordered ", t->insertionsort.sort(ordered));
    	System.out.println("time:" +benchmark_timer22.run(ordered,runTime3));
    	Benchmark_Timer benchmark_timer23=new Benchmark_Timer("Test Insertion Sort_ordered ", t->insertionsort.sort(ordered));
    	System.out.println("time:" +benchmark_timer23.run(ordered,runTime4));
    	Benchmark_Timer benchmark_timer24=new Benchmark_Timer("Test Insertion Sort_ordered ", t->insertionsort.sort(ordered));
    	System.out.println("time:" +benchmark_timer24.run(ordered,runTime5));
    	Benchmark_Timer benchmark_timer25=new Benchmark_Timer("Test Insertion Sort_ordered ", t->insertionsort.sort(ordered));
    	System.out.println("time:" +benchmark_timer25.run(ordered,runTime6));
    	
    	
    	
    	
    	System.out.println("reverse");
    	Benchmark_Timer benchmark_timer3=new Benchmark_Timer("Test Insertion Sort_resersed ", t->insertionsort.sort(reversed));
    	System.out.println("time:" +benchmark_timer3.run(reversed,runTime1));
    	Benchmark_Timer benchmark_timer31=new Benchmark_Timer("Test Insertion Sort_resersed ", t->insertionsort.sort(reversed));
    	System.out.println("time:" +benchmark_timer31.run(reversed,runTime2));
    	Benchmark_Timer benchmark_timer32=new Benchmark_Timer("Test Insertion Sort_resersed ", t->insertionsort.sort(reversed));
    	System.out.println("time:" +benchmark_timer32.run(reversed,runTime3));
    	Benchmark_Timer benchmark_timer33=new Benchmark_Timer("Test Insertion Sort_resersed ", t->insertionsort.sort(reversed));
    	System.out.println("time:" +benchmark_timer33.run(reversed,runTime4));
    	Benchmark_Timer benchmark_timer34=new Benchmark_Timer("Test Insertion Sort_resersed ", t->insertionsort.sort(reversed));
    	System.out.println("time:" +benchmark_timer34.run(reversed,runTime5));
    	Benchmark_Timer benchmark_timer35=new Benchmark_Timer("Test Insertion Sort_resersed ", t->insertionsort.sort(reversed));
    	System.out.println("time:" +benchmark_timer35.run(reversed,runTime6));
    	
    	
    	
    	
    	System.out.println("partially");
    	Benchmark_Timer benchmark_timer4=new Benchmark_Timer("Test Insertion Sort_partially", t->insertionsort.sort(partially));
    	System.out.println("time:" +benchmark_timer4.run(partially,runTime1));
    	Benchmark_Timer benchmark_timer41=new Benchmark_Timer("Test Insertion Sort_partially", t->insertionsort.sort(partially));
    	System.out.println("time:" +benchmark_timer41.run(partially,runTime2));
    	Benchmark_Timer benchmark_timer42=new Benchmark_Timer("Test Insertion Sort_partially", t->insertionsort.sort(partially));
    	System.out.println("time:" +benchmark_timer42.run(partially,runTime3));
    	Benchmark_Timer benchmark_timer43=new Benchmark_Timer("Test Insertion Sort_partially", t->insertionsort.sort(partially));
    	System.out.println("time:" +benchmark_timer43.run(partially,runTime4));
    	Benchmark_Timer benchmark_timer44=new Benchmark_Timer("Test Insertion Sort_partially", t->insertionsort.sort(partially));
    	System.out.println("time:" +benchmark_timer44.run(partially,runTime5));
    	Benchmark_Timer benchmark_timer45=new Benchmark_Timer("Test Insertion Sort_partially", t->insertionsort.sort(partially));
    	System.out.println("time:" +benchmark_timer45.run(partially,runTime6));
    	
    	
    	
    	

    }
}
