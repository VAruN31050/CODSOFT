import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int minRange = 1;
        int maxRange = 100;
        int numberOfAttempts = 7; 
        int score = 0;

        System.out.println("Welcome to the Number Guessing Game");
        boolean playAgain = true;

        while (playAgain) {
            int randomNumber = random.nextInt(maxRange - minRange + 1) + minRange;
            int attemptsLeft = numberOfAttempts;

            System.out.println("\nSelected a number between " + minRange + " and " + maxRange + ".");
            while (attemptsLeft > 0) {
                System.out.print("Attempts left: " + attemptsLeft + ". Enter your guess: ");
                int userGuess = scanner.nextInt();

                if (userGuess == randomNumber) {
                    System.out.println("!!Congratulations!! You guessed the correct number!");
                    score++;
                    break;
                } else if (userGuess < randomNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }

                attemptsLeft--;
            }

            if (attemptsLeft == 0) {
                System.out.println("Sorry, you've run out of attempts. The correct number was: " + randomNumber);
            }

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainResponse = scanner.next().toLowerCase();
            if (!playAgainResponse.equals("yes")) {
                playAgain = false;
            }
        }

        System.out.println("Game over! Your final score is: " + score);
        scanner.close();
    }
}