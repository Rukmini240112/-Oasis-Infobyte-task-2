import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuizSystem quizSystem = new QuizSystem(scanner);
        quizSystem.start();
    }

    private List<User> users;
    private List<Question> questions;
    private User currentUser;
    private Scanner scanner;

    public QuizSystem(Scanner scanner) {
        users = new ArrayList<>();
        questions = new ArrayList<>();
        this.scanner = scanner;
        initializeUsers();
        initializeQuestions();
    }

    private void initializeUsers() {
        users.add(new User("user1", "password1", "User One"));
        users.add(new User("user2", "password2", "User Two"));
    }

    private void initializeQuestions() {
        questions.add(new Question("What is 2 + 2?", new String[]{"1", "2", "3", "4"}, 3));
        questions.add(new Question("What is 2 * 8?", new String[]{"16", "24", "32", "14"}, 0));
        questions.add(new Question("What is 2 - 2?", new String[]{"10", "21", "0", "4"}, 2));
        questions.add(new Question("What is 2 / 2?", new String[]{"0.5", "1", "2", "0"}, 1));
    }

    public void start() {
        System.out.println("Welcome to the Quiz System");
        if (authenticateUser()) {
            showMenu();
        } else {
            System.out.println("Invalid login credentials.");
        }
    }

    private boolean authenticateUser() {
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    private void showMenu() {
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Update Profile");
            System.out.println("2. Change Password");
            System.out.println("3. Take Quiz");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    takeQuiz();
                    break;
                case 4:
                    System.out.println("Logging out. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private void updateProfile() {
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        currentUser.setName(newName);
        System.out.println("Profile updated successfully.");
    }

    private void changePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        currentUser.setPassword(newPassword);
        System.out.println("Password changed successfully.");
    }

    private void takeQuiz() {
        int score = 0;
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 60 * 1000; // 60 seconds timer

        for (Question question : questions) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("\nTime's up! Quiz auto-submitted.");
                break;
            }

            System.out.println(question.getQuestion());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.print("Enter your answer: ");
            int answer = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (question.isCorrect(answer - 1)) {
                score++;
            }
        }

        System.out.println("Quiz completed. Your score: " + score + "/" + questions.size());
    }
}

class User {
    private String userId;
    private String password;
    private String name;

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Question {
    private String question;
    private String[] options;
    private int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(int answer) {
        return answer == correctAnswer;
    }
}
