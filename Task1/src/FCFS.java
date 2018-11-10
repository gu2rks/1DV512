
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

	ArrayList<GanttInfo> ganttList = new ArrayList<>();

	private int m_completedTime;

	// Class constructor
	public FCFS(ArrayList<Process> processes) {
		this.processes = sortByAT(processes);
		this.m_completedTime = 0;
	}

	public void run() {
		for (Process a_process : processes) {
			a_process.setCompletedTime(calculateCT(a_process));
			GanttInfo ganttInfo = new GanttInfo(a_process.getProcessId(), a_process.getCompletedTime());
			ganttList.add(ganttInfo);
			a_process.setTurnaroundTime(calculateTAT(a_process));
			a_process.setWaitingTime(calculateWT(a_process));
		}
		printTable();
		printGanttChart();

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
		System.out.println("\n\n%%%% GANTT CHART %%%"
				+ "\n");
		StringBuilder line1, line2, line3 = new StringBuilder();
		for (GanttInfo g : ganttList) {
			

			if (!g.equals(ganttList.get(ganttList.size() - 1)))
				System.out.printf("  P%-2d |", g.getPID());
			else {
				System.out.printf("  P%-2d | \n", g.getPID());
			}
		}

		System.out.print("0   ");
		for (GanttInfo g : ganttList) {
			if (!g.equals(ganttList.get(ganttList.size() - 1)))
				System.out.printf("  %-2d   ", g.getCT());
			else
				System.out.printf("  %-2d \n", g.getCT());
		}

		System.out.println("\n");
	}

	/*
	 * public void printGanttChart() { StringBuilder time = new StringBuilder();
	 * StringBuilder a_border = new StringBuilder(); StringBuilder an_Id = new
	 * StringBuilder();
	 * 
	 * for (int i = 0; i < processes.size(); i++) { Process a_process =
	 * processes.get(i); if (i == 0) { time.append(a_process.getArrivalTime()); //
	 * take the initial time }
	 * 
	 * for (int j = 0; j < a_process.getCompletedTime(); j++) {
	 * a_border.append("=="); an_Id.append(" "); time.append("  ");
	 * 
	 * if (j == a_process.getCompletedTime() - 1) { time.setLength(a_border.length()
	 * + 1); // manage the white spaces time.append(+a_process.getCompletedTime());
	 * // temp_ct = a_process.getCompletedTime(); an_Id.append("P" +
	 * a_process.getProcessId()); a_border.append("|");
	 * 
	 * while (an_Id.length() < a_border.length() - 1) { // for making the chart
	 * nicely an_Id.append(" "); } an_Id.append("|"); } } } System.out.println("|" +
	 * a_border.toString() + "\n|" + an_Id.toString() + "\n|" + a_border.toString()
	 * + "\n" + time.toString() + "\n\n"); }
	 */
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

class GanttInfo {
	private int processID;
	private int ct;

	GanttInfo(int processID, int ct) {
		this.processID = processID;
		this.ct = ct;
	}

	public int getPID() {
		return processID;
	}

	public int getCT() {
		return ct;
	}
}
