package com.webstore.customers.web.ship.cpu;

import java.util.TimerTask;

public class Load extends TimerTask{
	 public static long duration = 30;
	public Load(long time) {
		this.duration=time;
	}
	
	public static void completeTask() {
        int numCore = 2;
        int numThreadsPerCore = 4;
        double load = 0.8;
        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new BusyThread("Thread" + thread, load, duration).start();
        }
    }

	@Override
	public void run() {
		completeTask();
	}
}
