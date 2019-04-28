package com.javastudy.user.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Demo1 {
	
	public static void main(String[] args) throws InterruptedException {
		
		FutureTask<String> futureTask = new FutureTask<>(new task());
		futureTask.cancel(false);
		ExecutorService es = Executors.newFixedThreadPool(10);
//		es.execute(futureTask);
		Future<String> future = es.submit(new task());
		try {
//			System.out.println(futureTask.get());
			System.out.println(future.get());
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Executors.newScheduledThreadPool(10);
		
	}
	
}

class task implements Callable<String> {
	@Override
	public String call() throws Exception {
		
		return "2";
	}
}










