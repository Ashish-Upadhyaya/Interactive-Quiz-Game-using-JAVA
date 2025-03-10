# Interactive-Quiz-Game-using-JAVA

## Overview

The **Quiz Game Application** is a Java-based desktop application designed to provide an interactive and engaging quiz experience. The application allows users to select a difficulty level (Easy, Medium, or Hard) and the number of questions they want to answer. The game features a timer for each question, a scoring system, and the ability to skip questions or quit the game. At the end of the quiz, the user is presented with their results, including the number of correct and incorrect answers, as well as their total score.

## Features

- **Difficulty Levels**: Users can choose from three difficulty levels: Easy, Medium, and Hard.
- **Customizable Quiz Length**: Users can specify the number of questions they want to answer.
- **Timer**: Each question has a 30-second timer, adding a sense of urgency to the game.
- **Interactive UI**: The application features a user-friendly interface with colorful buttons and clear labels.
- **Score Tracking**: The application keeps track of the user's score, displaying it in real-time.
- **Question Skipping**: Users can skip questions if they are unsure of the answer.
- **End-of-Quiz Summary**: At the end of the quiz, users are presented with a summary of their performance, including the number of correct and incorrect answers, and their total score.
- **Replay Option**: Users can replay the quiz with the same or different settings.

## How to Run the Application

1. **Prerequisites**:
   - Ensure you have Java Development Kit (JDK) installed on your system.
   - A Java IDE (e.g., IntelliJ IDEA, Eclipse) is recommended for running the application.

2. **Download the Code**:
   - Clone the repository or download the source code files (`QuizGame.java` and `Question.java`).

3. **Compile and Run**:
   - Open the project in your Java IDE.
   - Compile and run the `QuizGame.java` file.

4. **Play the Game**:
   - Upon running the application, a window will open prompting you to select a difficulty level and the number of questions.
   - After making your selections, the quiz will begin.
   - Answer the questions by clicking on the provided options. You can skip questions or quit the game at any time.
   - At the end of the quiz, you will see your results and have the option to replay or quit.

## Code Structure

- **QuizGame.java**: This is the main class that contains the logic for the quiz game. It handles the UI, question management, scoring, and timer functionality.
- **Question.java**: This class represents a single quiz question, including the question text, answer choices, and the index of the correct answer.

### Key Components

- **UI Components**:
  - `JFrame`: The main window of the application.
  - `JPanel`: Used to organize and layout the UI components.
  - `JLabel`: Displays the question, score, and timer.
  - `JButton`: Used for answer options, next, skip, and quit buttons.
  - `Timer`: Manages the 30-second countdown for each question.

- **Game Logic**:
  - **Question Management**: Questions are stored in a list and shuffled at the start of the game. The application displays one question at a time and handles user input.
  - **Scoring**: The application keeps track of the user's score based on correct and incorrect answers.
  - **Timer**: A 30-second timer is started for each question. If the timer runs out, the question is automatically skipped.
  - **End-of-Quiz Summary**: After all questions are answered, the application displays a summary of the user's performance.

## Customization

- **Adding More Questions**: You can easily add more questions to the quiz by modifying the `initializeQuestions()` method in the `QuizGame` class. Simply add new `Question` objects to the `questions` list for each difficulty level.
- **Changing the Timer**: The timer duration can be adjusted by modifying the `timeRemaining` variable in the `startQuestionTimer()` method.
- **UI Customization**: The appearance of the UI can be customized by modifying the colors, fonts, and layout in the `initializeUI()` method.

## Screenshots

![Quiz Game Screenshot](screenshot.png)  
*Example of the Quiz Game in action.*

## License

This project is open-source and available under the MIT License. Feel free to use, modify, and distribute the code as you see fit.

## Contributing

Contributions are welcome! If you have any suggestions, bug reports, or feature requests, please open an issue or submit a pull request.

## Acknowledgments

- This project was inspired by the need for a simple, interactive quiz game that can be used for educational purposes or just for fun.
- Special thanks to the Java Swing library for providing the tools to create a rich desktop application.

## Contact

For any questions or feedback, feel free to reach out to the project maintainer at [your-email@example.com].

---

Enjoy playing the Quiz Game! 🎮
