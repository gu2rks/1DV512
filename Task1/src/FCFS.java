
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
