Scheduling Algorithms Implementation

This repository contains Java code implementing four scheduling algorithms, both pre-emptive and non-pre-emptive. The four scheduling algorithms implemented are:

First Come First Serve (FCFS)
Shortest Job First (SJF)
Priority Scheduling
Round-Robin
Additionally, the repository includes a simulation for the Dining Philosophers problem.

Folder Structure

FCFS: Contains the implementation of the First Come First Serve scheduling algorithm.
SJF: Contains the implementation of the Shortest Job First scheduling algorithm.
PriorityScheduling: Contains the implementation of the Priority Scheduling algorithm.
RoundRobin: Contains the implementation of the Round-Robin scheduling algorithm.
DiningPhilosophers: Contains the implementation of the Dining Philosophers problem.
Combined: Contains a combined implementation of all scheduling algorithms in a single Java code.
Input Requirements

The program prompts the user for the following general inputs:

Number of processes
Arrival time of each process
Burst time of each process
Additionally, depending on the chosen algorithm, specific inputs may be required:

For Priority Scheduling: Priority of each process
For Round-Robin: Time quantum
Output

Upon providing the necessary inputs, the program generates the following outputs:

A table containing calculations of completion time, turnaround time, and waiting time for each process.
A Gantt chart representation of the scheduling sequence.
The average waiting time for all processes.
Notes

Shortest Job First does not support pre-emption.
Priority Scheduling does not support pre-emption.
Usage

Clone or download the repository to your local machine.
Navigate to the folder of the scheduling algorithm you want to run.
Compile and run the Java code.
Follow the prompts to input the required data.
View the output displayed in the console.
