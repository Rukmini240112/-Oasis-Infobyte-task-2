import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int numberToGuess = random.nextInt(100) + 1; // Generates a number between 1 and 100
        int numberOfAttempts = 0;
        int maxAttempts = 10;
        int userGuess = 0;
        boolean hasGuessedCorrectly = false;

        System.out.println("Welcome to Guess the Number!");
        System.out.println("I have randomly selected a number between 1 and 100.");
        System.out.println("Try to guess it in less than " + maxAttempts + " attempts.");

        while (numberOfAttempts < maxAttempts && !hasGuessedCorrectly) {
            System.out.print("Enter your guess: ");
            if (scanner.hasNextInt()) {
                userGuess = scanner.nextInt();
                numberOfAttempts++;

                if (userGuess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } else if (userGuess > numberToGuess) {
                    System.out.println("Too high! Try again.");
                } else {
                    hasGuessedCorrectly = true;
                    System.out.println("Congratulations! You've guessed the number in " + numberOfAttempts + " attempts.");
                }
            } else {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }

        if (!hasGuessedCorrectly) {
            System.out.println("Sorry! You've used all " + maxAttempts + " attempts. The number was: " + numberToGuess);
        }

        scanner.close();
    }
}
