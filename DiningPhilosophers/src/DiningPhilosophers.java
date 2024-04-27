/* Name: Uail Zhukenov
* ID: T00645104
* Date: March 11, 2023
* Description: Dining Philosophers Implementation
* */
import java.util.concurrent.Semaphore;
import java.util.Scanner;

public class DiningPhilosophers {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of philosophers:");
        int n = scan.nextInt();


        Semaphore[] forks = new Semaphore[n];

        // initialize the forks
        for (int i = 0; i < n; i++) {
            forks[i] = new Semaphore(1);
        }

        // create the philosophers
        Philosopher[] philosophers = new Philosopher[n];
        for (int i = 0; i < n; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % n]);
        }


        char option;
        System.out.println("Would you like to start?");
        System.out.println("Type Y for yes and N for no");
        option = scan.next().charAt(0);

        question(philosophers, option);


    }

    private static class Philosopher extends Thread {
        private int id;
        private Semaphore leftFork;
        private Semaphore rightFork;
        private boolean isEating;

        public Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
            this.isEating = false;
        }

        public void run(Philosopher philosophers[]) {
            boolean result = true;
            while (result) {
                if (leftFork.availablePermits() == 0 || rightFork.availablePermits() == 0) {
                    if (isEating == true)
                    {
                        System.out.println("Philosopher is already eating");
                    }
                    else {
                        System.out.println("At least one of the forks is busy right now.");
                    }
                } else {
                    // philosopher tries to pick up left fork
                    try {
                        leftFork.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // philosopher tries to pick up right fork
                    try {
                        rightFork.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // philosopher is eating
                    System.out.println("Philosopher " + id + " is eating.");
                    isEating = true;

                    // wait for some time to simulate eating
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                result = false;
            }
        }

        public void quit(Philosopher[] philosophers) {
            Scanner scan = new Scanner(System.in);
            if (isEating) {
                // philosopher drops left fork
                leftFork.release();

                // philosopher drops right fork
                rightFork.release();

                System.out.println("Philosopher " + id + " is done eating.");
                isEating = false;

            } else {
                System.out.println("Philosopher " + id + " was not eating.");
                char option2;
                System.out.println("Would you like someone to drop the fork?");
                option2 = scan.next().charAt(0);

                if (option2 == 'Y') {
                    System.out.println("Please enter which philosopher is done eating:");
                    int position1 = scan.nextInt();
                    philosophers[position1].interrupt();
                    philosophers[position1].quit(philosophers);
                } else {
                    System.out.println("\nOkay, philosophers are still eating\n");
                }
            }
        }
    }






    public static void question(Philosopher philosophers[], char option) {

        Scanner scan = new Scanner(System.in);

        while (option == 'Y') {
            System.out.println("Please enter which philosopher gets hungry:");
            int position = scan.nextInt();

            // start the philosopher
            philosophers[position].run(philosophers);

            char option2;
            System.out.println("Would you like someone to drop the fork?");
            option2 = scan.next().charAt(0);

            if (option2 == 'Y') {
                System.out.println("Please enter which philosopher is done eating:");
                int position1 = scan.nextInt();
                philosophers[position1].interrupt();
                philosophers[position1].quit(philosophers);
            } else {
                System.out.println("\nOkay, philosophers are still eating\n");
            }

            // wait for all philosophers to finish eating
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



            System.out.println("Do you want to continue?");
            option = scan.next().charAt(0);

            if (option == 'N') {
                System.out.println("Okay, Bye then!");
                break;
            }
        }
    }

}


