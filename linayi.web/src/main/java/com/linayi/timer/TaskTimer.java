package com.linayi.timer;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class TaskTimer extends HttpServlet{
	
	@Override
	public void init() throws ServletException {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("...........");
			}
		}, 1000, 2000);
	}
	
}
