
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
import java.util.PriorityQueue;
import java.util.Queue;

public class FCFS {

	// The list of processes to be scheduled
	public ArrayList<Process> processes = new ArrayList<Process>();
	// Queue
	private ArrayList<Process> queue;

	private int m_completedTime;
	private int processX = 0;
	private int processTimer = 0;
	private int brustTimer = 0;
	private int idle = 0;
	private boolean finish = false;
	private boolean isWorking = false;
	Process currentProcess;

	// Class constructor
	public FCFS(ArrayList<Process> processes) {

		queue = sortByAT(processes);
	}

	public void run() {

		while (!queue.isEmpty() && !finish) {
			nextProcess();
		}
		printTable();
		System.out.println("done time" + processTimer);
	}

	private void nextProcess() {
		if (!isWorking) {
			currentProcess = queue.get(0);
			System.out.println("new pid in queue" + currentProcess.processId);
			isWorking = true;

		}
		else {
			execute();
		}
	}

	private void execute() {
		brustTimer++;
		processTimer++;
		//when process X is done
		if (brustTimer == currentProcess.burstTime) {
			//set CT
			currentProcess.setCompletedTime(processTimer);
			System.out.println("CT : "+currentProcess.completedTime);
			//set TAT
			currentProcess.setTurnaroundTime(currentProcess.completedTime-currentProcess.arrivalTime);
			System.out.println("TAT : "+currentProcess.turnaroundTime);
			processes.add(currentProcess);
			System.out.println("add in processes" + processes.get(processes.size() - 1).processId);
			queue.remove(0);
			isWorking = false;

			brustTimer = 0; // reset
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
		System.out.println("\n\n%%%%%%%%%%%%%%%%%%%%%% GANNT CHART %%%%%%%%%%%%%%%%%%%%%%\n");

		String a_border = "";
		String a_PIDLine = "";
		String a_CTLine = "0";

		for (int i = 0; i < processes.size(); i++) {

			String empty = "";
			String idle = "";

			for (int j = 0; j < processes.get(i).getBurstTime(); j++) {
				empty += " ";
			}

			if (i != 0 && processes.get(i).getCompletedTime() != processes.get(i - 1).getCompletedTime()
					+ processes.get(i).getBurstTime()) {
				for (int k = 0; k < processes.get(i).getArrivalTime() - processes.get(i - 1).getCompletedTime(); k++) {
					idle += "*";
					a_CTLine += " ";
				}
				idle += "||";

				if (String.valueOf(processes.get(i - 1).getCompletedTime() + processes.get(i).getBurstTime())
						.length() == 1) {
					a_CTLine += " ";
				}

				if (String.valueOf(processes.get(i - 1).getCompletedTime() + processes.get(i).getBurstTime())
						.length() == 2) {
					a_CTLine += " ";
				}

				a_CTLine += (processes.get(i).getArrivalTime());
			}

			a_PIDLine += idle + empty + "P" + processes.get(i).getProcessId() + empty;

			if (i != processes.size() - 1) {
				a_PIDLine += "||";
			}

			a_CTLine += empty + "  " + empty + processes.get(i).getCompletedTime();

			if (String.valueOf(processes.get(i).getCompletedTime()).length() != 2) {
				a_CTLine += " ";
			}
		}

		for (int l = 0; l <= a_PIDLine.length() + 1; l++) {
			a_border += "=";
		}

		System.out.println(a_border + "\n|" + a_PIDLine + "|" + "\n" + a_border + "\n" + a_CTLine
				+ "\n* = indicates the CPU idle time");
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

//	private int calculateCT(Process a_process) {
//		if (a_process.getArrivalTime() <= m_completedTime) {
//			m_completedTime = m_completedTime + a_process.getBurstTime();
//		} else {
//			m_completedTime = a_process.getArrivalTime() + a_process.getBurstTime();
//		}
//		return m_completedTime;
//	}
//
//	private int calculateTAT(Process a_process) {
//		int tat = a_process.getCompletedTime() - a_process.getArrivalTime();
//		return tat;
//	}
//
//	private int calculateWT(Process a_process) {
//		int wt = a_process.getTurnaroundTime() - a_process.getBurstTime();
//		return wt;
//	}
}
