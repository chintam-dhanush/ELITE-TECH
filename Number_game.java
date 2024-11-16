import java.util.Random;
import java.util.Scanner;

public class Number_game {
    public static void main(String[] args) {

        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);
        int round = 1, totalScore = 0, roundsPlayed = 0;

        System.out.println("Welcome to the Number Guessing Game!");

        while (round == 1) {
            int targetNum = rand.nextInt(100) + 1;
            int userGuess;
            roundsPlayed++;

            System.out.println("Select a difficulty level:");
            System.out.println("easy: 15 tries\t\tmid: 10 tries\t\thard: 5 tries");
            System.out.print("Please enter your choice (easy, mid, hard): ");

            int attemptsLeft = 0;
            String difficulty = scanner.nextLine();

            if (difficulty.equalsIgnoreCase("easy")) 
                attemptsLeft = 15;
            else if (difficulty.equalsIgnoreCase("mid")) 
                attemptsLeft = 10;
            else if (difficulty.equalsIgnoreCase("hard")) 
                attemptsLeft = 5;
            else {
                System.out.println("Invalid choice! Please restart and select a valid difficulty level.");
                continue;
            }

            System.out.println("Try to guess the number between 1 and 100!");

            do {
                System.out.print("Enter your guess: ");
                userGuess = scanner.nextInt();

                if (targetNum == userGuess) {
                    System.out.println("Congratulations! You guessed it right: " + targetNum);
                    totalScore += 10 + (attemptsLeft * 2);
                    break;
                } else if (userGuess > targetNum) {
                    System.out.println("The number is smaller. Tries left: " + --attemptsLeft);
                } else {
                    System.out.println("The number is bigger. Tries left: " + --attemptsLeft);
                }
            } while (attemptsLeft > 0);

            if (attemptsLeft == 0) {
                System.out.println("Oops! You've used all your attempts. The correct number was: " + targetNum);
            }

            System.out.println("Your total score so far: " + totalScore);
            System.out.println("Would you like to play another round? (yes/no): ");
            scanner.nextLine(); 
            String playAgain = scanner.nextLine();

            if (playAgain.equalsIgnoreCase("yes")) 
                round = 1;
            else 
                round = 0;
        }

        double avgScore =totalScore / roundsPlayed;
        System.out.println("Game Over! Your final score is: " + totalScore);
        System.out.printf("Average score per round: %.2f%n", avgScore);
        System.out.println("Thanks for playing!");
    }
}
