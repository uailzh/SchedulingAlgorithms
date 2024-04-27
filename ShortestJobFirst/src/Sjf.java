
import java.util.*;


public class Sjf {



    public static void main(String[] args) {


        int number_processes = 0;

        Scanner scan = new Scanner(System.in); // Create a Scanner object
        System.out.println("Enter number of processes: ");
        number_processes = scan.nextInt();

        double[] burst_time = new double[number_processes];
        double[] arrival_time = new double[number_processes];
        double[] waiting_time = new double[number_processes];
        double[] turnaround_time = new double[number_processes];
        int[] position = new int[number_processes];

        for (int i=0; i<number_processes; i++)
        {
            position[i] = i;
        }

        System.out.println("Enter arrival time: ");
        for (int i = 0; i < number_processes; i++) {
            arrival_time[i] = scan.nextDouble();
            System.out.println("Arrival time for process " + i + " is: " + arrival_time[i]);
        }

        System.out.println("Enter burst time: ");
        for (int i = 0; i < number_processes; i++) {
            burst_time[i] = scan.nextDouble();
            System.out.println("Burst time for process " + i + " is: " + burst_time[i]);
        }


        // Sort processes based on arrival time
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



        int completed_processes = 0; //number of processes completed
        double current_time = 0.0; //keeps track of current time
        double completion_time[] = new double[number_processes];
        boolean process_completed[] = new boolean[number_processes];
        double remaining_burst_time[] = new double[number_processes]; //keeps track of remaining burst for preempted processes
        int running_process = -1;

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





}
