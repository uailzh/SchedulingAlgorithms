

import java.util.*;

public class RoundRobin {

    public static void main(String[] args) {

        int number_processes = 0;

        Scanner scan = new Scanner(System.in); // Create a Scanner object
        System.out.println("Enter number of processes: ");
        number_processes = scan.nextInt();

        double[] burst_time = new double[number_processes];
        double[] arrival_time = new double[number_processes];
        double[] waiting_time = new double[number_processes];
        double[] turnaround_time = new double[number_processes];
        double[] remaining_time = new double[number_processes];
        int[] position = new int[number_processes];

        System.out.println("Enter arrival, burst time: ");
        for (int i = 0; i < number_processes; i++) {
            arrival_time[i] = scan.nextDouble();
            remaining_time[i] = burst_time[i] = scan.nextDouble();
            position[i] = i;
            System.out.println("Arrival time and burst time for process " + i + " are: " + arrival_time[i] + " " + burst_time[i]);
        }

        System.out.println("Enter time quantum for Round Robin algorithm: ");
        int timeQuantum = scan.nextInt();



        double current_time = 0;
        double sum_waiting_time = 0;

        int completed_processes = 0;

        // Create an array to store the completion time for each process
        double[] completion_time = new double[number_processes];
        // Create an ArrayList to store the execution sequence of processes
        List<Integer> execution_sequence = new ArrayList<Integer>();

        // Run the Round Robin algorithm
        while (completed_processes < number_processes) {
            boolean process_completed = true;
            for (int i = 0; i < number_processes; i++) {
                if (remaining_time[i] > 0 && arrival_time[i] <= current_time) {
                    process_completed = false;
                    execution_sequence.add(i);
                    if (remaining_time[i] > timeQuantum) {
                        remaining_time[i] -= timeQuantum;
                        current_time += timeQuantum;
                    } else {
                        current_time += remaining_time[i];
                        waiting_time[i] = current_time - arrival_time[i] - burst_time[i];
                        remaining_time[i] = 0;
                        completed_processes++;
                        turnaround_time[i] = current_time - arrival_time[i];
                        sum_waiting_time += waiting_time[i];

                        completion_time[i] = current_time;
                    }
                }
            }
            if (process_completed) {
                current_time++;
            }
        }

        // Calculate and print the average waiting time
        double avg_waiting_time = sum_waiting_time / number_processes;
        System.out.println("Average Waiting Time: " + avg_waiting_time);



        // Display Chart
        System.out.print("Gain Chart: ");
        int prev_process_id = -1;
        double current_execution_time = 0;
        for (int i = 0; i < execution_sequence.size(); i++) {
            int process_id = execution_sequence.get(i);
            if (process_id != prev_process_id) {
                if (prev_process_id != -1) {
                    System.out.print(String.format("%.1f", current_execution_time) + " ");
                }
                System.out.print("P" + process_id + " executed for ");
                prev_process_id = process_id;
                current_execution_time = 0;
            }
            current_execution_time += timeQuantum;
        }
        System.out.print(String.format("%.1f", current_execution_time));



        System.out.println("\n");


        // Display results
        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\t\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < number_processes; i++) {
            System.out.println(position[i] + "\t\t\t" + arrival_time[i] + "\t\t\t\t" + burst_time[i] + "\t\t\t" + completion_time[i] + "\t\t\t\t" + waiting_time[i] + "\t\t\t\t" + turnaround_time[i]);
        }

    }

}



