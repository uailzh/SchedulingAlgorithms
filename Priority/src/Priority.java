import java.util.*;
public class Priority {

    public static void main(String[] args) {

        int number_processes = 0;

        Scanner scan = new Scanner(System.in); // Create a Scanner object
        System.out.println("Enter number of processes: ");
        number_processes = scan.nextInt();

        int[] priority = new int[number_processes];
        double[] burst_time = new double[number_processes];
        double[] arrival_time = new double[number_processes];
        double[] waiting_time = new double[number_processes];
        double[] turnaround_time = new double[number_processes];
        double completion_time[] = new double[number_processes];
        int[] position = new int[number_processes];

        for (int i=0; i<number_processes; i++)
        {
            position[i] = i; //keeps track of positions in array
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

        System.out.println("Enter priority: ");
        for (int i = 0; i < number_processes; i++) {
            priority[i] = scan.nextInt();
            System.out.println("Priority for process " + i + " is: " + priority[i]);
        }


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



        int completed_processes = 0;
        double current_time = 0.0; //keeps track of current time
        boolean process_completed[] = new boolean[number_processes]; //contains boolean if process is done or not
        double remaining_burst_time[] = new double[number_processes]; //keeps track of remaining burst time
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
}

