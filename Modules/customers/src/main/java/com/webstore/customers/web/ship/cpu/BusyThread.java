package com.webstore.customers.web.ship.cpu;

import java.util.concurrent.TimeUnit;

public class BusyThread extends Thread{
	private double load;
    private long duration;

    /**
     * Constructor which creates the thread
     * @param name Name of this thread
     * @param load Load % that this thread should generate
     * @param duration Duration that this thread should generate the load for
     */
    public BusyThread(String name, double load, long duration) {
        super(name);
        this.load = load;
        this.duration = duration;
    }

    /**
     * Generates the load when run
     */
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            // Loop for the given duration
        	for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(duration);stop>System.nanoTime();) {
    			
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("BusyThread with Thread-ID : "+Thread.currentThread().getName()+":"+Thread.currentThread().getId()+" Runs for : "+(System.currentTimeMillis()-startTime));
    }
}
