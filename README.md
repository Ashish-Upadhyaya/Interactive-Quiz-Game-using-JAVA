# Interactive Quiz Game Using Java

## Overview
This is a Java-based interactive quiz game application built using the Swing library for the graphical user interface. The game allows players to select their difficulty level (Easy, Medium, or Hard) and answer multiple-choice questions within a 30-second time limit per question. Players can track their score in real-time and view their final results at the end of the quiz. This project is ideal for beginners looking to understand how to build interactive desktop applications in Java while exploring concepts like event handling, timers, and UI design.

## Features

### Core Functionality
- **Difficulty Selection**: Players can choose between three difficulty levels: Easy, Medium, and Hard. Each level contains a unique set of questions tailored to its complexity.
- **Customizable Game Length**: Players can specify how many questions they want to answer during the game.
- **Timer for Each Question**: A 30-second countdown timer ensures players answer questions quickly. If the timer runs out, the game automatically moves to the next question.
- **Score Tracking**: Correct answers increase the player's score. Incorrect answers are highlighted in red, and the correct answer is shown in green.
- **Skip and Next Buttons**: Players can skip a question or move to the next one after answering.
- **Game Over Screen**: Displays total questions, correct answers, wrong answers, and the final score. Includes options to replay the game or quit.
- **Replay Functionality**: Players can restart the game with shuffled questions without restarting the application.
- **Quit Option**: Allows players to exit the game at any time and view their current results.

### Additional Features
- **Hint Button**: Provides hints by disabling incorrect options, helping players make informed choices.
- **Pause/Resume Button**: Allows players to pause the game and resume it later.
- **Sound Effects**: Plays sound effects for correct and wrong answers, enhancing the gaming experience.

## Installation and Setup

### Prerequisites
- **Java Development Kit (JDK)**: Ensure you have JDK 8 or higher installed on your system.
- **IDE (Optional)**: Use an IDE like IntelliJ IDEA, Eclipse, or NetBeans for easier development and testing.

### Steps to Run the Application
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Ashish-Upadhyaya/Interactive-Quiz-Game-using-JAVA.git
   ```
2. **Navigate to the Project Directory**:
   ```bash
   cd quiz-game
   ```
3. **Compile the Code**:
   Open a terminal and compile the Java files:
   ```bash
   javac QuizGame.java
   ```
4. **Run the Application**:
   Execute the compiled class file:
   ```bash
   java QuizGame
   ```
5. **Play the Game**:
   - Follow the on-screen instructions to select your difficulty level and start the quiz.

## Gameplay Instructions

1. **Start the Game**:
   - Launch the application and select your preferred difficulty level (Easy, Medium, or Hard).
   - Specify the number of questions you'd like to answer.
2. **Answer Questions**:
   - Read the question displayed on the screen.
   - Select one of the four provided options by clicking the corresponding button.
   - Correct answers will turn the button green, while incorrect answers will turn it red and highlight the correct option.
3. **Use Timer Wisely**:
   - Each question has a 30-second timer. Answer before the timer runs out to avoid penalties.
4. **Skip or Move to the Next Question**:
   - Use the "Skip" button to bypass a question.
   - After answering, click "Next" to proceed to the next question.
5. **End of the Game**:
   - Once all questions are answered or skipped, the game ends.
   - View your final score, total correct answers, and total wrong answers on the game-over screen.
6. **Replay or Quit**:
   - Click "Replay 🔄" to restart the game with shuffled questions.
   - Click "Quit" to exit the application.

## Code Structure

### Classes
- **`QuizGame`**:
  - The main class that initializes the game window and handles the game logic.
  - Manages UI components, question display, scoring, and timers.
- **`Question`**:
  - Represents a single quiz question with its text, answer choices, and the index of the correct answer.

### Key Methods
- `initializeUI()`: Sets up the graphical user interface.
- `showLevelSelectionDialog()`: Prompts the user to select difficulty and number of questions.
- `initializeQuestions()`: Loads questions based on the selected difficulty.
- `shuffleQuestions()`: Randomizes the order of questions.
- `showQuestion()`: Displays the current question and starts the timer.
- `startQuestionTimer()`: Starts the 30-second countdown for each question.
- `handleTimeUp()`: Handles scenarios when the timer runs out.
- `endQuiz()`: Displays the game-over screen with final results.
- `nextQuestion()`: Moves to the next question or ends the game if all questions are answered.

## Customization

### Adding New Questions
To add more questions:
1. Open the `initializeQuestions()` method in the `QuizGame` class.
2. Add new `Question` objects to the respective difficulty level (`Easy`, `Medium`, or `Hard`).
   Example:
   ```java
   questions.add(new Question("What is the capital of Australia?", 
       new String[]{"Sydney", "Melbourne", "Canberra", "Brisbane"}, 2));
   ```

### Modifying the Timer
To change the timer duration:
1. Locate the `startQuestionTimer()` method.
2. Modify the `timeRemaining` variable to your desired value (e.g., 60 for a 1-minute timer).

## Screenshots

### Main Game Screen
![Main Game Screen](https://github.com/Ashish-Upadhyaya/Interactive-Quiz-Game-using-JAVA/blob/main/Screenshot%202025-03-11%20031002.png)

### Game Over Screen
![Game Over Screen](https://github.com/Ashish-Upadhyaya/Interactive-Quiz-Game-using-JAVA/blob/main/Screenshot%202025-03-11%20031036.png)

## Contributing

Contributions are welcome! If you'd like to improve this project, please follow these steps:
1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Submit a pull request with a detailed description of your changes.

### Guidelines for Contributions
- Ensure your code follows the existing style and conventions.
- Write clear commit messages and include relevant documentation.
- Test your changes thoroughly before submitting a pull request.

## License

This project is licensed under the Apache 2.0 License. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Inspired by classic quiz games and educational apps.
- Built using Java Swing for a lightweight and platform-independent UI.

## Contact

For any questions, suggestions, or feedback, feel free to reach out via email at [ashishofficial231@gmail.com] or open an issue on the GitHub repository.

Enjoy the game! 🎮
