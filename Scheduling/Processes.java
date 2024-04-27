

import java.util.*;

public class Processes {


    public static void main(String[] args) {

        int number_processes = 0;

        Scanner scan = new Scanner(System.in); // Create a Scanner object
        System.out.println("Enter number of processes: ");
        number_processes = scan.nextInt();

        double[] burst_time = new double[number_processes];
        double[] arrival_time = new double[number_processes];
        int position[] = new int[number_processes];
        int priority[] = new int[number_processes];


        System.out.println("Enter arrival time: ");
        for (int i = 0; i < number_processes; i++) {
            arrival_time[i] = scan.nextDouble();
            System.out.println("Arrival time for process " + i + " is: " + arrival_time[i]);
            position[i] = i;

        }

        System.out.println("Enter burst time: ");
        for (int i = 0; i < number_processes; i++) {
            burst_time[i] = scan.nextDouble();
            System.out.println("Burst time for process " + i + " is: " + burst_time[i]);
        }

        System.out.println("Which algorithm to use? \nEnter 1 for FCFS, 2 for SJF, 3 for Priority, 4 for Round Robin: ");
        int option = 0;
        option = scan.nextInt();

        if (option == 1) {
            System.out.println("You chose: " + option + " which is First Come First Serve");
            sortProcessesBasedOnArrivalTime(number_processes, arrival_time, burst_time, position);
            firstComefirstServe(arrival_time, burst_time, number_processes, position);

        }
        else if (option == 2) {
            System.out.println("You chose: " + option + " which is SJF");
            sortProcessesBasedOnArrivalTime(number_processes, arrival_time, burst_time, position);
            SJF(arrival_time, burst_time, number_processes, position);
        }
        else if (option == 3) {

            System.out.println("You chose: " + option + " Priority Scheduling");
            System.out.println("\n Please enter priority: ");
            for (int i=0; i < number_processes; i++)
            {
                priority[i] = scan.nextInt();
            }

            sortProcesses(number_processes, arrival_time, burst_time, position, priority);
            priority(arrival_time, burst_time, number_processes, position, priority);
        }
        else if (option == 4) {
            System.out.println("You chose " + option + " which is Round Robin");
            System.out.println("\n Please enter time quantum: ");
            int timeQuantum = scan.nextInt();

            roundRobin(arrival_time, burst_time, number_processes, position, timeQuantum);

        }
        else
            System.out.println("\n The number you put is not correct, TRY AGAIN");


    }


    public static void sortProcessesBasedOnArrivalTime(int number_processes, double[] arrival_time, double[] burst_time, int[] position) {



        for (int i = 0; i < number_processes-1; i++) {
            for (int j = 0; j < number_processes-i-1; j++) {
                if (arrival_time[j] > arrival_time[j+1] || (arrival_time[j] == arrival_time[j+1] && burst_time[j] > burst_time[j+1])) {
                    // Swap processes
                    double temp = arrival_time[j];
                    arrival_time[j] = arrival_time[j+1];
                    arrival_time[j+1] = temp;

                    double temp1 = burst_time[j];
                    burst_time[j] = burst_time[j+1];
                    burst_time[j+1] = temp1;

                    int temp2 = position[j];
                    position[j] = position[j+1];
                    position[j+1] = temp2;
                }
            }
        }
    }


    public static void sortProcesses(int number_processes, double[] arrival_time, double[] burst_time, int[] position, int[] priority) {

        // Sort processes based on priority and arrival times
        for (int i = 0; i < number_processes-1; i++) {
            for (int j = 0; j < number_processes-i-1; j++) {
                if (priority[j] > priority[j+1] || (priority[j] == priority[j+1] && arrival_time[j] > arrival_time[j+1]) || (priority[j] == priority[j+1] && arrival_time[j] == arrival_time[j+1])) {
                    // Swap processes
                    int temp = priority[j];
                    priority[j] = priority[j+1];
                    priority[j+1] = temp;

                    double temp1 = arrival_time[j];
                    arrival_time[j] = arrival_time[j+1];
                    arrival_time[j+1] = temp1;

                    double temp2 = burst_time[j];
                    burst_time[j] = burst_time[j+1];
                    burst_time[j+1] = temp2;

                    int temp3 = position[j];
                    position[j] = position[j+1];
                    position[j+1] = temp3;
                }
            }
        }

    }



        public static void firstComefirstServe(double[] arrival_time, double[] burst_time, int number_processes, int[] position) {

        double completion_time[] = new double[number_processes];
        double waiting_time[] = new double[number_processes];
        double total_waiting_time = 0;


        completion_time[0] = arrival_time[0] + burst_time[0];
        for (int i = 1; i < number_processes; i++) {
            if (completion_time[i - 1] <= arrival_time[i]) {
                completion_time[i] = arrival_time[i] + burst_time[i];
            } else {
                completion_time[i] = completion_time[i - 1] + burst_time[i];
            }
        }
        double[] turnaround_time = new double[number_processes];

        for (int i = 0; i < number_processes; i++) {
            waiting_time[i] = completion_time[i] - arrival_time[i] - burst_time[i];
            total_waiting_time += waiting_time[i];
            turnaround_time[i] = completion_time[i] - arrival_time[i];
        }


        double average_waiting_time = total_waiting_time / number_processes;


        //gantt chart implementation
        System.out.println("\n\nProcess execution sequence:");
        for (int i = 0; i < number_processes; i++) {
            if (i==0)
            {
                System.out.print("P" + (position[i]) + " started at " + arrival_time[i] + " completed at " + completion_time[i] + "\n");
            }
            else if (i == number_processes - 1) {
                System.out.print("P" + (position[i]) + " started at " + completion_time[number_processes - 2] + " completed at " + completion_time[i] + "\n");
            }
            else {
                System.out.print("P" + (position[i]) + " started at " + completion_time[i-1] + " completed at " + completion_time[i] + "\n");
            }
        }
        System.out.println();


        System.out.println("Process\t\tArrival Time\t\tBurst Time\t\tCompletion Time\t\tWaiting Time\t\tTurnaround Time");
        for (int i = 0; i < number_processes; i++) {
            System.out.println(position[i] + "\t\t\t" + arrival_time[i] + "\t\t\t\t\t\t" + burst_time[i] + "\t\t\t\t\t"
                    + completion_time[i] + "\t\t\t\t" + waiting_time[i] + "\t\t\t\t" + turnaround_time[i]);
        }

        System.out.println("\nAverage waiting time: " + average_waiting_time);

    }


    public static void SJF(double[] arrival_time, double[] burst_time, int number_processes, int[] position) {


        int completed_processes = 0; //number of processes completed
        double current_time = 0.0; //keeps track of current time
        double completion_time[] = new double[number_processes];
        boolean process_completed[] = new boolean[number_processes];
        double remaining_burst_time[] = new double[number_processes]; //keeps track of remaining burst for preempted processes
        int running_process = -1;
        double turnaround_time[] = new double[number_processes];
        double waiting_time[] = new double[number_processes];

        for (int i = 0; i < number_processes; i++) {
            remaining_burst_time[i] = burst_time[i];
        }

        while (completed_processes < number_processes) { //executes until all processes completed
            int process = -1;
            double min_burst_time = Double.MAX_VALUE;
            for (int i = 0; i < number_processes; i++) {
                if (!process_completed[i] && remaining_burst_time[i] < min_burst_time && arrival_time[i] <= current_time) {
                    process = i;
                    min_burst_time = remaining_burst_time[i];
                }



            }

            if (process == -1) {
                // No process is ready to run yet, move time forward to the earliest arrival time of un-completed processes
                double earliest_arrival_time = Double.MAX_VALUE;
                for (int i = 0; i < number_processes; i++) {
                    if (!process_completed[i] && arrival_time[i] < earliest_arrival_time) {
                        earliest_arrival_time = arrival_time[i];
                    }
                }
                current_time = earliest_arrival_time;
                continue;
            }

            if (running_process == -1 || remaining_burst_time[process] < remaining_burst_time[running_process]) {
                // A new process with a shorter burst time has arrived
                if (running_process != -1) {
                    // Save the remaining burst time of the previous process
                    remaining_burst_time[running_process] -= (current_time - arrival_time[running_process]);
                }

                running_process = process;
                current_time = arrival_time[process];
            }

            current_time += remaining_burst_time[process];
            completion_time[process] = current_time;
            process_completed[process] = true; //process is done
            completed_processes++; //controls while loop
            remaining_burst_time[process] = 0;

            waiting_time[process] = completion_time[process] - arrival_time[process] - burst_time[process];
            turnaround_time[process] = completion_time[process] - arrival_time[process];
        }



        //gantt chart implementation
        System.out.println("\n\nProcess execution sequence:");
        for (int i = 0; i < number_processes; i++) {
            if (i==0)
            {
                System.out.print("P" + (position[i]) + " started at " + arrival_time[i] + " completed at " + completion_time[i] + "\n");
            }
            else if (i == number_processes - 1) {
                System.out.print("P" + (position[i]) + " started at " + completion_time[number_processes - 2] + " completed at " + completion_time[i] + "\n");
            }
            else {
                System.out.print("P" + (position[i]) + " started at " + completion_time[i-1] + " completed at " + completion_time[i] + "\n");
            }
        }
        System.out.println();



        // Display Table
        System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\t\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < number_processes; i++) {
            System.out.println(position[i] + "\t\t\t" + arrival_time[i] + "\t\t\t\t" + burst_time[i] + "\t\t\t" + completion_time[i] + "\t\t\t\t" + waiting_time[i] + "\t\t\t\t" + turnaround_time[i]);
        }

        double avg_wait = 0.0;
        for (int i=0; i<number_processes; i++)
        {
            avg_wait += waiting_time[i];
        }

        avg_wait = avg_wait/number_processes;

        System.out.println("\n Average Waiting Time: " + avg_wait);


    }

    public static void priority(double[] arrival_time, double[] burst_time, int number_processes, int[] position, int[] priority) {

        int completed_processes = 0;
        double current_time = 0.0; //keeps track of current time
        boolean process_completed[] = new boolean[number_processes]; //contains boolean if process is done or not
        double remaining_burst_time[] = new double[number_processes]; //keeps track of remaining burst time
        double turnaround_time[] = new double[number_processes];
        double waiting_time[] = new double[number_processes];
        double completion_time[] = new double[number_processes];


        for (int i = 0; i < number_processes; i++) {
            remaining_burst_time[i] = burst_time[i];
        }

        List<String> chart = new ArrayList<>(); // array list for gantt chart
        //keeps going until all processes are done
        while (completed_processes < number_processes) {
            int selected_process = -1;
            for (int i = 0; i < number_processes; i++) {
                if (!process_completed[i] && arrival_time[i] <= current_time) {
                    if (selected_process == -1) {
                        selected_process = i;
                    } else if (priority[i] < priority[selected_process]) {
                        selected_process = i;
                    }
                }
            }

            // If there are no processes with an arrival time less than or equal to the current time,
            // then we set the current time to the earliest arrival time among the processes.
            if (selected_process == -1) {
                double min_arrival_time = Double.MAX_VALUE;
                for (int i = 0; i < number_processes; i++) {
                    if (!process_completed[i] && arrival_time[i] < min_arrival_time) {
                        min_arrival_time = arrival_time[i];
                    }
                }
                current_time = min_arrival_time;
            } else {
                process_completed[selected_process] = true; //process is completed
                chart.add("P" + position[selected_process] + " [" + current_time + ", " + (current_time + remaining_burst_time[selected_process]) + "]");
                current_time += remaining_burst_time[selected_process];
                completion_time[selected_process] = current_time;
                waiting_time[selected_process] = completion_time[selected_process] - arrival_time[selected_process] - burst_time[selected_process];
                turnaround_time[selected_process] = completion_time[selected_process] - arrival_time[selected_process];
                completed_processes++; //increments number of completed processes to control while loop
            }
        }



        System.out.println("Gantt Chart: ");
        for (String process : chart) {
            System.out.println(process);
        }



        // Display results
        System.out.println("Process\tArrival Time\tBurst Time\t\tPriority\tCompletion Time\t\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < number_processes; i++) {
            System.out.println(position[i] + "\t\t\t" + arrival_time[i] + "\t\t\t\t" + burst_time[i] + "\t\t\t\t" + priority[i] + "\t\t\t\t" + completion_time[i] + "\t\t\t\t" + waiting_time[i] + "\t\t\t\t" + turnaround_time[i]);
        }

        double avg_wait = 0.0;

        for(int i=0; i < number_processes ;i++)
        {
            avg_wait += waiting_time[i];
        }

        avg_wait = avg_wait/number_processes;

        System.out.println("\nAverage Waiting Time: " + avg_wait );


    }

    public static void roundRobin(double[] arrival_time, double[] burst_time, int number_processes, int[] position, int timeQuantum) {

        double current_time = 0;
        double sum_waiting_time = 0;

        int completed_processes = 0;
       double remaining_time[] = new double[number_processes];
        double waiting_time[] = new double[number_processes];
        double turnaround_time[] = new double[number_processes];

        for (int i=0; i<number_processes; i++) {
            remaining_time[i] = burst_time[i];
        }

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




















