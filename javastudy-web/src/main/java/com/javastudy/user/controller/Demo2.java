package com.javastudy.user.controller;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Demo2 {
	
	public static void main(String[] args) {
		
		Timer timer = new Timer();
		MyTask myTask = new MyTask();
		timer.scheduleAtFixedRate(myTask, 0L, 3000L);
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myTask.cancel();
		AtomicInteger a = new AtomicInteger(1);
		AtomicReference<Object> b = new AtomicReference<>(new Object());
	}
}

class MyTask extends TimerTask {
	@Override
	public void run() {
		System.out.println("111");
	}
}










