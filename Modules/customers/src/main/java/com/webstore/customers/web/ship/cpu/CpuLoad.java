package com.webstore.customers.web.ship.cpu;

import java.util.Timer;
import java.util.TimerTask;

public class CpuLoad extends Thread{
	private long threadRunTime = 0;
	public CpuLoad(long time) {
		threadRunTime = time;
	}
	@Override
	public void run() {
		TimerTask timerTask = new Load(threadRunTime);
        //running timer task as daemon thread
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, threadRunTime+10000);
        System.out.println("cpu load  started");
        //cancel after sometime
        try {
            Thread.sleep(threadRunTime*5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask cancelled");
	}
	private static void spin(int milliseconds) {
	    long sleepTime = milliseconds*1000000L; // convert to nanoseconds
	    long startTime = System.nanoTime();
	    while ((System.nanoTime() - startTime) < sleepTime) {}
	}
	public static void main(String[] args) {
//		CpuLoad cpuLoad = new CpuLoad(3000);
//		cpuLoad.start();
		final int NUM_TESTS = 100;
	    long start = System.nanoTime();
	    for (int i = 0; i < NUM_TESTS; i++) {
	        spin(500);
	    }
	    System.out.println("Took " + (System.nanoTime()-start)/1000000 +"ms (expected " + (NUM_TESTS*500) + ")");
	}
}
