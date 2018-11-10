
/*
 * File:	Process.java
 * Course: 	Operating Systems
 * Code: 	1DV512
 * Author: 	Suejb Memeti
 * Refactoring by: Amata Anantaprayoon (aa224iu)
 * Date: 	November, 2018
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS {

	// The list of processes to be scheduled
	public ArrayList<Process> processes;
	private int m_completedTime;

	// Class constructor
	public FCFS(ArrayList<Process> processes) {
		this.processes = sortByAT(processes);
		this.m_completedTime = 0;
	}

	public void run() {
		for (Process a_process : processes) {
			a_process.setCompletedTime(calculateCT(a_process));
			a_process.setTurnaroundTime(calculateTAT(a_process));
			a_process.setWaitingTime(calculateWT(a_process));
		}

	}

	public void printTable() {
		System.out.println("Scheduling a list of processes using the FCFS algorithm"
				+ "\n__________________________________________________________________"
				+ "\n|    PID   |    AT    |    BT    |    CT    |    TAT   |    WT    |"
				+ "\n|__________|__________|__________|__________|__________|__________|");

		for (Process a_process : processes) {
			System.out.printf("|%5s     |%5s     |%5s     |%5s     |%5s     |%5s     |\n", a_process.getProcessId(),
					a_process.getArrivalTime(), a_process.getBurstTime(), a_process.getCompletedTime(),
					a_process.getTurnaroundTime(), a_process.getWaitingTime());
			System.out.println("|__________|__________|__________|__________|__________|__________|");

		}
	}

	public void printGanttChart() {
		// TODO Print the demonstration of the scheduling algorithm using Gantt Chart

	}

	private ArrayList<Process> sortByAT(ArrayList<Process> m_processes) {
		Collections.sort(m_processes, new Comparator<Process>() {
			@Override
			public int compare(Process p1, Process p2) {
				return p1.getArrivalTime() - p2.getArrivalTime();
			}
		});
		return m_processes;
	}

	private int calculateCT(Process a_process) {
		if (a_process.getArrivalTime() <= m_completedTime) {
			m_completedTime = m_completedTime + a_process.getBurstTime();
		} else {
			m_completedTime = a_process.getArrivalTime() + a_process.getBurstTime();
		}
		return m_completedTime;
	}

	private int calculateTAT(Process a_process) {
		int tat = a_process.getCompletedTime() - a_process.getArrivalTime();
		return tat;
	}

	private int calculateWT(Process a_process) {
		int wt = a_process.getTurnaroundTime() - a_process.getBurstTime();
		return wt;
	}
}
