import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizGame extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int totalQuestionsToPlay;
    private String selectedLevel;

    private JLabel questionLabel;
    private JButton[] optionButtons;
    private JButton nextButton;
    private JButton skipButton;
    private JButton quitButton;
    private JLabel scoreLabel;
    private JLabel timerLabel;
    private JPanel mainPanel;

    private Timer questionTimer; // Timer for the 30-second countdown
    private int timeRemaining; // Time remaining for the current question

    private int totalCorrect = 0;
    private int totalWrong = 0;

    public QuizGame() {
        // Set up the JFrame
        setTitle("Quiz Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize UI
        initializeUI();

        // Show level selection dialog
        showLevelSelectionDialog();

        // Make the JFrame visible
        setVisible(true);
    }

    private void initializeUI() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(25, 25, 112)); // Dark blue background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top Panel for Score, Question, and Timer
        JPanel topPanel = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns
        topPanel.setBackground(new Color(25, 25, 112));

        // Score Label (Left)
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        topPanel.add(scoreLabel);

        // Question Label (Center)
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Bold and attractive font
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(questionLabel);

        // Timer Label (Right)
        timerLabel = new JLabel("Time: 30");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        topPanel.add(timerLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Options Panel (2x2 Grid with gaps)
        JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 30, 30)); // 2 rows, 2 columns with 30px gaps
        optionsPanel.setBackground(new Color(25, 25, 112));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding around options
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            optionButtons[i].setBackground(new Color(0, 102, 204)); // Blue button color
            optionButtons[i].setForeground(Color.WHITE);
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            optionButtons[i].addActionListener(new OptionButtonListener());
            optionsPanel.add(optionButtons[i]);
        }
        mainPanel.add(optionsPanel, BorderLayout.CENTER);

        // Button Panel for Next, Skip, and Quit
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 row, 3 columns with 10px gaps
        buttonPanel.setBackground(new Color(25, 25, 112));

        // Next Button
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setBackground(new Color(0, 153, 0)); // Green button color
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setEnabled(false); // Disabled by default
        nextButton.addActionListener(new NextButtonListener());
        buttonPanel.add(nextButton);

        // Skip Button
        skipButton = new JButton("Skip");
        skipButton.setFont(new Font("Arial", Font.BOLD, 18));
        skipButton.setBackground(new Color(255, 153, 0)); // Orange button color
        skipButton.setForeground(Color.WHITE);
        skipButton.setFocusPainted(false);
        skipButton.addActionListener(new SkipButtonListener());
        buttonPanel.add(skipButton);

        // Quit Button
        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 18));
        quitButton.setBackground(new Color(255, 0, 0)); // Red button color
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(new QuitButtonListener());
        buttonPanel.add(quitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void showLevelSelectionDialog() {
        String[] levels = {"Easy", "Medium", "Hard"};
        selectedLevel = (String) JOptionPane.showInputDialog(
                this,
                "Select Difficulty Level:",
                "Level Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                levels,
                levels[0]
        );

        String totalQuestions = JOptionPane.showInputDialog(
                this,
                "How many questions do you want to play?",
                "Game Length",
                JOptionPane.QUESTION_MESSAGE
        );

        try {
            totalQuestionsToPlay = Integer.parseInt(totalQuestions);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Defaulting to 5 questions.");
            totalQuestionsToPlay = 5;
        }

        // Initialize questions based on the selected level
        initializeQuestions();
        shuffleQuestions(); // Shuffle questions at the start
        showQuestion();
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        if (selectedLevel.equals("Easy")) {
            questions.add(new Question("What is the capital of France?", 
                new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 2));

            questions.add(new Question("Which planet is known as the Red Planet?", 
                new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 1));

            questions.add(new Question("Who wrote 'To Kill a Mockingbird'?", 
                new String[]{"Harper Lee", "Mark Twain", "J.K. Rowling", "Ernest Hemingway"}, 0));

            questions.add(new Question("What is the largest ocean on Earth?", 
                new String[]{"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"}, 3));
            
            questions.add(new Question("Which gas do humans breathe out?", 
                new String[]{"Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"}, 1));
            
            questions.add(new Question("What is the chemical symbol for water?", 
                new String[]{"H2O", "CO2", "NaCl", "O2"}, 0));
            
            questions.add(new Question("Who painted the Mona Lisa?", 
                new String[]{"Vincent van Gogh", "Leonardo da Vinci", "Pablo Picasso", "Claude Monet"}, 1));
            
            questions.add(new Question("What is the square root of 64?", 
                new String[]{"6", "7", "8", "9"}, 2));
            
            questions.add(new Question("Which animal is known as the 'King of the Jungle'?", 
                new String[]{"Lion", "Tiger", "Elephant", "Giraffe"}, 0));
            
            questions.add(new Question("What is the tallest mountain in the world?", 
                new String[]{"Mount Everest", "K2", "Kangchenjunga", "Lhotse"}, 0));
            
            questions.add(new Question("Which country is home to the kangaroo?", 
                new String[]{"Brazil", "Australia", "India", "South Africa"}, 1));
            
            questions.add(new Question("What is the primary language spoken in Brazil?", 
                new String[]{"Spanish", "Portuguese", "French", "English"}, 1));
            
            questions.add(new Question("What is the hardest natural substance on Earth?", 
                new String[]{"Gold", "Iron", "Diamond", "Quartz"}, 2));
            
            questions.add(new Question("Which planet is closest to the Sun?", 
                new String[]{"Venus", "Earth", "Mercury", "Mars"}, 2));
            
            questions.add(new Question("What is the capital of Japan?", 
                new String[]{"Beijing", "Seoul", "Tokyo", "Bangkok"}, 2));
            
            questions.add(new Question("Who discovered gravity?", 
                new String[]{"Albert Einstein", "Isaac Newton", "Galileo Galilei", "Nikola Tesla"}, 1));
            
            questions.add(new Question("What is the smallest prime number?", 
                new String[]{"0", "1", "2", "3"}, 2));
            
            questions.add(new Question("Which instrument is used to measure temperature?", 
                new String[]{"Barometer", "Thermometer", "Hygrometer", "Anemometer"}, 1));
            
            questions.add(new Question("What is the main ingredient in guacamole?", 
                new String[]{"Tomato", "Avocado", "Onion", "Lime"}, 1));
            
            questions.add(new Question("Which continent is Egypt located on?", 
                new String[]{"Asia", "Europe", "Africa", "South America"}, 2));
            
            questions.add(new Question("What is the largest mammal in the world?", 
                new String[]{"Elephant", "Blue Whale", "Giraffe", "Hippopotamus"}, 1));
            
            questions.add(new Question("Which element has the atomic number 1?", 
                new String[]{"Helium", "Oxygen", "Hydrogen", "Carbon"}, 2));
            
            questions.add(new Question("What is the freezing point of water in Celsius?", 
                new String[]{"0°C", "32°C", "100°C", "-10°C"}, 0));
            
            questions.add(new Question("Which bird is known for its ability to mimic human speech?", 
                new String[]{"Parrot", "Eagle", "Owl", "Penguin"}, 0));
            
            questions.add(new Question("What is the currency of the United Kingdom?", 
                new String[]{"Euro", "Dollar", "Pound Sterling", "Yen"}, 2));
            
            questions.add(new Question("Which fruit is known as the 'King of Fruits'?", 
                new String[]{"Apple", "Mango", "Durian", "Banana"}, 2));
            
            questions.add(new Question("What is the largest organ in the human body?", 
                new String[]{"Heart", "Brain", "Skin", "Liver"}, 2));
            
            questions.add(new Question("Which famous scientist developed the theory of relativity?", 
                new String[]{"Isaac Newton", "Niels Bohr", "Albert Einstein", "Stephen Hawking"}, 2));
            
            questions.add(new Question("What is the national flower of Japan?", 
                new String[]{"Rose", "Lotus", "Cherry Blossom", "Sunflower"}, 2));
            
            questions.add(new Question("What is the most abundant gas in the Earth's atmosphere?", 
                new String[]{"Oxygen", "Nitrogen", "Carbon Dioxide", "Argon"}, 1));
            
            questions.add(new Question("Which vitamin is produced when a person is exposed to sunlight?", 
                new String[]{"Vitamin A", "Vitamin B", "Vitamin C", "Vitamin D"}, 3));
            
            questions.add(new Question("What is the capital of Canada?", 
                new String[]{"Toronto", "Montreal", "Vancouver", "Ottawa"}, 3));
            
            questions.add(new Question("Which planet has the most moons?", 
                new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 3));
            
            questions.add(new Question("What is the name of the longest river in the world?", 
                new String[]{"Amazon River", "Nile River", "Yangtze River", "Mississippi River"}, 1));
            
            questions.add(new Question("Which country gifted the Statue of Liberty to the USA?", 
                new String[]{"France", "Germany", "Italy", "Spain"}, 0));
            
            questions.add(new Question("What is the process by which plants make their own food?", 
                new String[]{"Respiration", "Photosynthesis", "Fermentation", "Digestion"}, 1));
            
            questions.add(new Question("What is the largest planet in our solar system?", 
                new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 2));
            
            questions.add(new Question("Which color is not part of the rainbow?", 
                new String[]{"Red", "Green", "Pink", "Blue"}, 2));
            
            questions.add(new Question("What is the capital of Australia?", 
                new String[]{"Sydney", "Melbourne", "Canberra", "Brisbane"}, 2));
            
            questions.add(new Question("What is the boiling point of water in Celsius?", 
                new String[]{"50°C", "100°C", "150°C", "200°C"}, 1));
            
            questions.add(new Question("Which sport is played at Wimbledon?", 
                new String[]{"Tennis", "Soccer", "Basketball", "Cricket"}, 0));
            
            questions.add(new Question("What is the main language spoken in China?", 
                new String[]{"Cantonese", "Mandarin", "Hindi", "Japanese"}, 1));
            
            questions.add(new Question("Which animal is known for its black and white stripes?", 
                new String[]{"Zebra", "Tiger", "Leopard", "Cheetah"}, 0));
            
            questions.add(new Question("What is the chemical symbol for gold?", 
                new String[]{"Au", "Ag", "Fe", "Cu"}, 0));
            
            questions.add(new Question("What is the largest desert in the world?", 
                new String[]{"Sahara Desert", "Arabian Desert", "Gobi Desert", "Antarctica"}, 3));
            
            questions.add(new Question("Which Disney character is known as the 'Little Mermaid'?", 
                new String[]{"Ariel", "Belle", "Cinderella", "Snow White"}, 0));
            
            questions.add(new Question("What is the capital of Italy?", 
                new String[]{"Milan", "Rome", "Venice", "Florence"}, 1));
            
            questions.add(new Question("Which gas makes up about 78% of the Earth's atmosphere?", 
                new String[]{"Oxygen", "Nitrogen", "Carbon Dioxide", "Argon"}, 1));
            
            questions.add(new Question("What is the smallest country in the world?", 
                new String[]{"Monaco", "Maldives", "Vatican City", "San Marino"}, 2));
            
            questions.add(new Question("Which planet is often called the 'Morning Star' or 'Evening Star'?", 
                new String[]{"Venus", "Mercury", "Mars", "Jupiter"}, 0));
            
            questions.add(new Question("What is the national animal of India?", 
                new String[]{"Tiger", "Lion", "Elephant", "Peacock"}, 0));
            
            questions.add(new Question("Which month has the fewest days in a common year?", 
                new String[]{"January", "February", "March", "April"}, 1));
            
            questions.add(new Question("What is the capital of Russia?", 
                new String[]{"Moscow", "Saint Petersburg", "Kazan", "Sochi"}, 0));
            
            questions.add(new Question("Which famous playwright wrote 'Romeo and Juliet'?", 
                new String[]{"William Shakespeare", "Charles Dickens", "Mark Twain", "Jane Austen"}, 0));
            
            questions.add(new Question("What is the largest continent in the world?", 
                new String[]{"Africa", "Asia", "Europe", "North America"}, 1));
            
            questions.add(new Question("What is the name of the fairy in 'Peter Pan'?", 
                new String[]{"Tinker Bell", "Cinderella", "Aurora", "Belle"}, 0));

        } else if (selectedLevel.equals("Medium")) {
            questions.add(new Question("What is the largest ocean on Earth?", 
                new String[]{"Atlantic", "Indian", "Arctic", "Pacific"}, 3));
            
            questions.add(new Question("Which element has the chemical symbol 'O'?", 
                new String[]{"Oxygen", "Gold", "Silver", "Iron"}, 0));
            
            questions.add(new Question("Who painted the Mona Lisa?", 
                new String[]{"Vincent Van Gogh", "Leonardo da Vinci", "Pablo Picasso", "Claude Monet"}, 1));

            questions.add(new Question("What is the capital of Brazil?", 
                new String[]{"Rio de Janeiro", "Brasília", "São Paulo", "Salvador"}, 1));
            
            questions.add(new Question("Which planet is known as the 'Blue Planet'?", 
                new String[]{"Earth", "Mars", "Neptune", "Uranus"}, 0));
            
            questions.add(new Question("What is the largest species of shark?", 
                new String[]{"Great White Shark", "Hammerhead Shark", "Whale Shark", "Tiger Shark"}, 2));
            
            questions.add(new Question("Who wrote the play 'Hamlet'?", 
                new String[]{"William Shakespeare", "George Bernard Shaw", "Oscar Wilde", "Arthur Miller"}, 0));
            
            questions.add(new Question("What is the SI unit of force?", 
                new String[]{"Joule", "Newton", "Watt", "Pascal"}, 1));
            
            questions.add(new Question("Which country is home to the ancient city of Petra?", 
                new String[]{"Egypt", "Jordan", "Israel", "Turkey"}, 1));
            
            questions.add(new Question("What is the main ingredient in hummus?", 
                new String[]{"Chickpeas", "Lentils", "Black Beans", "Kidney Beans"}, 0));
            
            questions.add(new Question("Which element has the atomic number 79?", 
                new String[]{"Silver", "Gold", "Copper", "Iron"}, 1));
            
            questions.add(new Question("What is the process by which water vapor becomes liquid?", 
                new String[]{"Evaporation", "Condensation", "Sublimation", "Freezing"}, 1));
            
            questions.add(new Question("Which war was fought between the North and South regions of the United States?", 
                new String[]{"World War I", "American Civil War", "Revolutionary War", "War of 1812"}, 1));
            
            questions.add(new Question("What is the largest moon of Saturn?", 
                new String[]{"Europa", "Titan", "Ganymede", "Callisto"}, 1));
            
            questions.add(new Question("Who invented the telephone?", 
                new String[]{"Thomas Edison", "Alexander Graham Bell", "Nikola Tesla", "Albert Einstein"}, 1));
            
            questions.add(new Question("What is the name of the longest bone in the human body?", 
                new String[]{"Femur", "Tibia", "Humerus", "Radius"}, 0));
            
            questions.add(new Question("Which country is the largest producer of coffee?", 
                new String[]{"Colombia", "Brazil", "Ethiopia", "Vietnam"}, 1));
            
            questions.add(new Question("What is the capital of Turkey?", 
                new String[]{"Istanbul", "Ankara", "Izmir", "Antalya"}, 1));
            
            questions.add(new Question("Which artist painted 'The Starry Night'?", 
                new String[]{"Vincent van Gogh", "Pablo Picasso", "Claude Monet", "Edgar Degas"}, 0));
            
            questions.add(new Question("What is the chemical formula for table salt?", 
                new String[]{"NaCl", "H2O", "CO2", "CaCO3"}, 0));
            
            questions.add(new Question("Which mountain range separates Europe and Asia?", 
                new String[]{"Himalayas", "Rocky Mountains", "Andes", "Ural Mountains"}, 3));
            
            questions.add(new Question("What is the currency of Sweden?", 
                new String[]{"Euro", "Krona", "Dollar", "Pound"}, 1));
            
            questions.add(new Question("Which composer wrote the 'Moonlight Sonata'?", 
                new String[]{"Ludwig van Beethoven", "Wolfgang Amadeus Mozart", "Johann Sebastian Bach", "Pyotr Ilyich Tchaikovsky"}, 0));
            
            questions.add(new Question("What is the primary language spoken in Argentina?", 
                new String[]{"Portuguese", "Spanish", "Italian", "French"}, 1));
            
            questions.add(new Question("Which river flows through Egypt?", 
                new String[]{"Nile River", "Amazon River", "Yangtze River", "Mississippi River"}, 0));
            
            questions.add(new Question("What is the smallest country in Africa by land area?", 
                new String[]{"Seychelles", "Mauritius", "Comoros", "Djibouti"}, 0));
            
            questions.add(new Question("Which gas is most abundant in the Earth's crust?", 
                new String[]{"Oxygen", "Silicon", "Aluminum", "Iron"}, 0));
            
            questions.add(new Question("Who was the first woman to fly solo across the Atlantic Ocean?", 
                new String[]{"Amelia Earhart", "Sally Ride", "Valentina Tereshkova", "Bessie Coleman"}, 0));
            
            questions.add(new Question("What is the largest island in the Mediterranean Sea?", 
                new String[]{"Sicily", "Sardinia", "Cyprus", "Crete"}, 0));
            
            questions.add(new Question("Which philosopher is known for his work 'The Republic'?", 
                new String[]{"Aristotle", "Plato", "Socrates", "Confucius"}, 1));
            
            questions.add(new Question("What is the capital of South Korea?", 
                new String[]{"Seoul", "Busan", "Incheon", "Daegu"}, 0));
            
            questions.add(new Question("Which scientist formulated the laws of motion?", 
                new String[]{"Albert Einstein", "Isaac Newton", "Galileo Galilei", "Niels Bohr"}, 1));
            
            questions.add(new Question("What is the largest lake in Africa?", 
                new String[]{"Lake Victoria", "Lake Tanganyika", "Lake Malawi", "Lake Chad"}, 0));
            
            questions.add(new Question("Which novel features the character Holden Caulfield?", 
                new String[]{"To Kill a Mockingbird", "The Catcher in the Rye", "1984", "Brave New World"}, 1));
            
            questions.add(new Question("What is the capital of Chile?", 
                new String[]{"Santiago", "Valparaíso", "Concepción", "Antofagasta"}, 0));
            
            questions.add(new Question("Which element is represented by the symbol 'Fe'?", 
                new String[]{"Iron", "Fluorine", "Francium", "Fermium"}, 0));
            
            questions.add(new Question("What is the largest type of penguin?", 
                new String[]{"Emperor Penguin", "Adélie Penguin", "Chinstrap Penguin", "Gentoo Penguin"}, 0));
            
            questions.add(new Question("Which country is known as the 'Land of the Rising Sun'?", 
                new String[]{"China", "Japan", "South Korea", "Thailand"}, 1));
            
            questions.add(new Question("What is the national sport of Canada?", 
                new String[]{"Ice Hockey", "Soccer", "Basketball", "Lacrosse"}, 3));
            
            questions.add(new Question("Which planet has the shortest day?", 
                new String[]{"Mercury", "Venus", "Earth", "Jupiter"}, 3));
            
            questions.add(new Question("What is the largest bird in the world?", 
                new String[]{"Eagle", "Ostrich", "Albatross", "Penguin"}, 1));
            
            questions.add(new Question("Which famous scientist developed the theory of general relativity?", 
                new String[]{"Isaac Newton", "Niels Bohr", "Albert Einstein", "Stephen Hawking"}, 2));
            
            questions.add(new Question("What is the capital of Norway?", 
                new String[]{"Oslo", "Stockholm", "Copenhagen", "Helsinki"}, 0));
            
            questions.add(new Question("Which animal is known for its ability to regenerate lost limbs?", 
                new String[]{"Octopus", "Starfish", "Crab", "Lizard"}, 1));
            
            questions.add(new Question("What is the name of the first artificial satellite launched into space?", 
                new String[]{"Apollo 11", "Sputnik 1", "Voyager 1", "Hubble Space Telescope"}, 1));
            
            questions.add(new Question("Which element is used in pencils?", 
                new String[]{"Carbon", "Graphite", "Lead", "Silicon"}, 1));
            
            questions.add(new Question("What is the capital of Greece?", 
                new String[]{"Athens", "Thessaloniki", "Patras", "Heraklion"}, 0));
            
            questions.add(new Question("Which country is home to the Great Barrier Reef?", 
                new String[]{"Australia", "Indonesia", "Malaysia", "Philippines"}, 0));
            
            questions.add(new Question("What is the name of the longest river in Asia?", 
                new String[]{"Yangtze River", "Yellow River", "Mekong River", "Ganges River"}, 0));
            
            questions.add(new Question("Which artist painted 'The Persistence of Memory'?", 
                new String[]{"Salvador Dalí", "Pablo Picasso", "Vincent van Gogh", "Henri Matisse"}, 0));
            
            questions.add(new Question("What is the largest organ inside the human body?", 
                new String[]{"Heart", "Brain", "Liver", "Lungs"}, 2));
            
            questions.add(new Question("Which war was fought between the British and the French from 1756 to 1763?", 
                new String[]{"American Civil War", "Seven Years' War", "Napoleonic Wars", "Crimean War"}, 1));
            
            questions.add(new Question("What is the name of the highest waterfall in the world?", 
                new String[]{"Niagara Falls", "Angel Falls", "Victoria Falls", "Yosemite Falls"}, 1));

        } else if (selectedLevel.equals("Hard")) {
            questions.add(new Question("What is the smallest prime number?", 
                new String[]{"1", "2", "3", "5"}, 1));
            
            questions.add(new Question("Which country is known as the Land of the Rising Sun?", 
                new String[]{"China", "Japan", "South Korea", "Thailand"}, 1));
            
            questions.add(new Question("Who discovered penicillin?", 
                new String[]{"Alexander Fleming", "Louis Pasteur", "Marie Curie", "Isaac Newton"}, 0));

            questions.add(new Question("What is the rarest naturally occurring element on Earth?", 
                new String[]{"Gold", "Astatine", "Platinum", "Uranium"}, 1));
            
            questions.add(new Question("Which treaty ended World War I?", 
                new String[]{"Treaty of Versailles", "Treaty of Tordesillas", "Treaty of Paris", "Treaty of Ghent"}, 0));
            
            questions.add(new Question("What is the largest known star in the universe?", 
                new String[]{"Betelgeuse", "UY Scuti", "VY Canis Majoris", "Rigel"}, 1));
            
            questions.add(new Question("Who wrote the novel 'One Hundred Years of Solitude'?", 
                new String[]{"Gabriel García Márquez", "Mario Vargas Llosa", "Isabel Allende", "Jorge Luis Borges"}, 0));
            
            questions.add(new Question("What is the name of the first woman to win a Nobel Prize?", 
                new String[]{"Marie Curie", "Rosalind Franklin", "Ada Lovelace", "Jane Goodall"}, 0));
            
            questions.add(new Question("Which ancient civilization built the city of Machu Picchu?", 
                new String[]{"Aztecs", "Mayans", "Incas", "Olmecs"}, 2));
            
            questions.add(new Question("What is the densest planet in our solar system?", 
                new String[]{"Earth", "Mercury", "Venus", "Mars"}, 0));
            
            questions.add(new Question("Which enzyme is responsible for breaking down carbohydrates in the mouth?", 
                new String[]{"Amylase", "Lipase", "Protease", "Pepsin"}, 0));
            
            questions.add(new Question("What is the capital of Bhutan?", 
                new String[]{"Thimphu", "Paro", "Punakha", "Phuentsholing"}, 0));
            
            questions.add(new Question("Which philosopher wrote 'Critique of Pure Reason'?", 
                new String[]{"Immanuel Kant", "Friedrich Nietzsche", "John Locke", "David Hume"}, 0));
            
            questions.add(new Question("What is the smallest country in the world by population?", 
                new String[]{"Monaco", "Nauru", "Tuvalu", "Vatican City"}, 3));
            
            questions.add(new Question("Which gas is responsible for the green color of the auroras?", 
                new String[]{"Oxygen", "Nitrogen", "Hydrogen", "Helium"}, 0));
            
            questions.add(new Question("What is the name of the longest mountain range in the world?", 
                new String[]{"Himalayas", "Rocky Mountains", "Andes", "Alps"}, 2));
            
            questions.add(new Question("Who was the first person to reach the South Pole?", 
                new String[]{"Robert Falcon Scott", "Roald Amundsen", "Ernest Shackleton", "Edmund Hillary"}, 1));
            
            questions.add(new Question("What is the chemical formula for ozone?", 
                new String[]{"O2", "O3", "CO2", "H2O"}, 1));
            
            questions.add(new Question("Which ancient wonder was located in the city of Halicarnassus?", 
                new String[]{"Colossus of Rhodes", "Mausoleum at Halicarnassus", "Statue of Zeus", "Temple of Artemis"}, 1));
            
            questions.add(new Question("What is the name of the largest desert in Asia?", 
                new String[]{"Gobi Desert", "Karakum Desert", "Thar Desert", "Syrian Desert"}, 0));
            
            questions.add(new Question("Which composer wrote the opera 'The Magic Flute'?", 
                new String[]{"Ludwig van Beethoven", "Wolfgang Amadeus Mozart", "Johann Sebastian Bach", "Richard Wagner"}, 1));
            
            questions.add(new Question("What is the name of the process by which plants lose water vapor through their leaves?", 
                new String[]{"Transpiration", "Respiration", "Photosynthesis", "Evaporation"}, 0));
            
            questions.add(new Question("Which country has the most active volcanoes?", 
                new String[]{"Japan", "Indonesia", "United States", "Iceland"}, 1));
            
            questions.add(new Question("What is the name of the longest river in Europe?", 
                new String[]{"Danube River", "Volga River", "Rhine River", "Seine River"}, 1));
            
            questions.add(new Question("Who discovered the electron?", 
                new String[]{"Niels Bohr", "J.J. Thomson", "Ernest Rutherford", "Albert Einstein"}, 1));
            
            questions.add(new Question("What is the name of the deepest trench in the world?", 
                new String[]{"Java Trench", "Kermadec Trench", "Mariana Trench", "Tonga Trench"}, 2));
            
            questions.add(new Question("Which language is spoken by the most people as a native language?", 
                new String[]{"English", "Spanish", "Mandarin Chinese", "Hindi"}, 2));
            
            questions.add(new Question("What is the name of the first successful vaccine developed by Edward Jenner?", 
                new String[]{"Polio Vaccine", "Smallpox Vaccine", "Measles Vaccine", "Tuberculosis Vaccine"}, 1));
            
            questions.add(new Question("Which element has the highest melting point?", 
                new String[]{"Tungsten", "Carbon", "Titanium", "Iron"}, 0));
            
            questions.add(new Question("What is the name of the first artificial satellite launched into space?", 
                new String[]{"Apollo 11", "Sputnik 1", "Voyager 1", "Hubble Space Telescope"}, 1));
            
            questions.add(new Question("Which country is home to the ancient city of Persepolis?", 
                new String[]{"Egypt", "Iran", "Iraq", "Turkey"}, 1));
            
            questions.add(new Question("What is the name of the phenomenon where light bends around massive objects in space?", 
                new String[]{"Redshift", "Gravitational Lensing", "Doppler Effect", "Quantum Tunneling"}, 1));
            
            questions.add(new Question("Who wrote the philosophical work 'Thus Spoke Zarathustra'?", 
                new String[]{"Friedrich Nietzsche", "Jean-Paul Sartre", "Simone de Beauvoir", "Martin Heidegger"}, 0));
            
            questions.add(new Question("What is the name of the largest coral reef system in the world?", 
                new String[]{"Great Barrier Reef", "Belize Barrier Reef", "Red Sea Coral Reef", "Maldives Coral Reef"}, 0));
            
            questions.add(new Question("Which physicist proposed the concept of wormholes?", 
                new String[]{"Albert Einstein", "Stephen Hawking", "Carl Sagan", "Kip Thorne"}, 3));
            
            questions.add(new Question("What is the name of the process by which stars produce energy?", 
                new String[]{"Nuclear Fusion", "Nuclear Fission", "Chemical Reaction", "Radiation"}, 0));
            
            questions.add(new Question("Which artist painted 'Guernica'?", 
                new String[]{"Pablo Picasso", "Salvador Dalí", "Vincent van Gogh", "Henri Matisse"}, 0));
            
            questions.add(new Question("What is the name of the longest bone in the human body?", 
                new String[]{"Femur", "Tibia", "Humerus", "Radius"}, 0));
            
            questions.add(new Question("Which war was fought between the British and the French from 1756 to 1763?", 
                new String[]{"American Civil War", "Seven Years' War", "Napoleonic Wars", "Crimean War"}, 1));
            
            questions.add(new Question("What is the name of the largest moon of Jupiter?", 
                new String[]{"Europa", "Titan", "Ganymede", "Callisto"}, 2));
            
            questions.add(new Question("Which philosopher wrote 'The Republic'?", 
                new String[]{"Aristotle", "Plato", "Socrates", "Confucius"}, 1));
            
            questions.add(new Question("What is the name of the first woman to climb Mount Everest?", 
                new String[]{"Junko Tabei", "Reinhold Messner", "Edmund Hillary", "Tenzing Norgay"}, 0));
            
            questions.add(new Question("What is the name of the largest species of penguin?", 
                new String[]{"Emperor Penguin", "Adélie Penguin", "Chinstrap Penguin", "Gentoo Penguin"}, 0));
            
            questions.add(new Question("Which element is used in nuclear reactors to control chain reactions?", 
                new String[]{"Uranium", "Plutonium", "Cadmium", "Thorium"}, 2));
            
            questions.add(new Question("What is the name of the largest island in the Caribbean?", 
                new String[]{"Cuba", "Hispaniola", "Puerto Rico", "Jamaica"}, 0));
            
            questions.add(new Question("Which scientist formulated the uncertainty principle?", 
                new String[]{"Albert Einstein", "Niels Bohr", "Werner Heisenberg", "Erwin Schrödinger"}, 2));
            
            questions.add(new Question("What is the name of the largest ocean current in the world?", 
                new String[]{"Gulf Stream", "Antarctic Circumpolar Current", "North Atlantic Drift", "Kuroshio Current"}, 1));
            
            questions.add(new Question("Which ancient civilization is credited with inventing the wheel?", 
                new String[]{"Sumerians", "Egyptians", "Romans", "Greeks"}, 0));
            
            questions.add(new Question("What is the name of the largest species of bear?", 
                new String[]{"Grizzly Bear", "Black Bear", "Polar Bear", "Panda Bear"}, 2));
        }
    }

    private void shuffleQuestions() {
        Collections.shuffle(questions); // Shuffle the questions
    }

    private void showQuestion() {
        if (currentQuestionIndex < totalQuestionsToPlay && currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            // Add question number to the question text
            questionLabel.setText("<html><center>Q" + (currentQuestionIndex + 1) + ": " + currentQuestion.getQuestionText() + "</center></html>"); // Center-align question text

            String[] choices = currentQuestion.getChoices();
            for (int i = 0; i < choices.length; i++) {
                optionButtons[i].setText(choices[i]);
                optionButtons[i].setEnabled(true);
                optionButtons[i].setBackground(new Color(0, 102, 204)); // Reset button color
            }

            // Start the 30-second timer
            startQuestionTimer();
        } else {
            endQuiz();
        }
    }

    private void startQuestionTimer() {
        timeRemaining = 30; // Reset timer to 30 seconds
        timerLabel.setText("Time: " + timeRemaining);

        if (questionTimer != null) {
            questionTimer.stop();
        }

        questionTimer = new Timer(1000, new ActionListener() {  
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time: " + timeRemaining);

                if (timeRemaining <= 0) {
                    questionTimer.stop();
                    handleTimeUp();
                }
            }
        });
        questionTimer.start();
    }

    private void handleTimeUp() {
        // Disable buttons and enable the Next button
        for (JButton button : optionButtons) {
            button.setEnabled(false);
        }
        nextButton.setEnabled(true); // Enable Next button when timer runs out

        // Automatically move to the next question after 2 seconds
        Timer delayTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextQuestion();
            }
        });
        delayTimer.setRepeats(false); // Ensure the timer only runs once
        delayTimer.start();
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < totalQuestionsToPlay) {
            showQuestion();
            nextButton.setEnabled(false); // Disable Next button for the new question
        } else {
            endQuiz();
        }
    }

    private void endQuiz() {
        // Hide the main panel
        mainPanel.setVisible(false);

        // Create a new panel for the game over screen
        JPanel gameOverPanel = new JPanel(new BorderLayout());
        gameOverPanel.setBackground(new Color(25, 25, 112));
        gameOverPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Game Over Label
        JLabel gameOverLabel = new JLabel("Game Over!");
        gameOverLabel.setFont(new Font("Serif", Font.BOLD, 36));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverPanel.add(gameOverLabel, BorderLayout.NORTH);

        // Results Panel
        JPanel resultsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        resultsPanel.setBackground(new Color(25, 25, 112));

        JLabel totalQuestionsLabel = new JLabel("Total Questions: " + totalQuestionsToPlay);
        totalQuestionsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        totalQuestionsLabel.setForeground(Color.WHITE);
        totalQuestionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultsPanel.add(totalQuestionsLabel);

        JLabel correctLabel = new JLabel("Total Correct: " + totalCorrect);
        correctLabel.setFont(new Font("Arial", Font.BOLD, 24));
        correctLabel.setForeground(Color.WHITE);
        correctLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultsPanel.add(correctLabel);

        JLabel wrongLabel = new JLabel("Total Wrong: " + totalWrong);
        wrongLabel.setFont(new Font("Arial", Font.BOLD, 24));
        wrongLabel.setForeground(Color.WHITE);
        wrongLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultsPanel.add(wrongLabel);

        JLabel scoreLabel = new JLabel("Total Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultsPanel.add(scoreLabel);

        gameOverPanel.add(resultsPanel, BorderLayout.CENTER);

        // Button Panel for Replay and Quit
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns with 10px gaps
        buttonPanel.setBackground(new Color(25, 25, 112));

        // Replay Button with Emoji
        JButton replayButton = new JButton("Replay 🔄");
        replayButton.setFont(new Font("Arial", Font.BOLD, 18));
        replayButton.setBackground(new Color(0, 153, 0)); // Green button color
        replayButton.setForeground(Color.WHITE);
        replayButton.setFocusPainted(false);
        replayButton.addActionListener(new ReplayButtonListener());
        buttonPanel.add(replayButton);

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 18));
        quitButton.setBackground(new Color(255, 0, 0)); // Red button color
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusPainted(false);
        quitButton.addActionListener(new QuitButtonListener());
        buttonPanel.add(quitButton);

        gameOverPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the game over panel to the frame
        add(gameOverPanel);
        revalidate();
        repaint();
    }

    private class OptionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            String selectedAnswer = selectedButton.getText();
            Question currentQuestion = questions.get(currentQuestionIndex);

            // Stop the question timer
            questionTimer.stop();

            if (selectedAnswer.equals(currentQuestion.getChoices()[currentQuestion.getCorrectAnswerIndex()])) {
                score++;
                totalCorrect++;
                scoreLabel.setText("Score: " + score);
                selectedButton.setBackground(Color.GREEN); // Highlight correct answer
            } else {
                totalWrong++;
                selectedButton.setBackground(Color.RED); // Highlight wrong answer
                // Highlight the correct answer
                optionButtons[currentQuestion.getCorrectAnswerIndex()].setBackground(Color.GREEN);
            }

            // Disable all buttons
            for (JButton button : optionButtons) {
                button.setEnabled(false);
            }

            // Enable the Next button
            nextButton.setEnabled(true);

            // Automatically move to the next question after 2 seconds
            Timer delayTimer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nextQuestion();
                }
            });
            delayTimer.setRepeats(false); // Ensure the timer only runs once
            delayTimer.start();
        }
    }

    private class NextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nextQuestion();
        }
    }

    private class SkipButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Skip the current question
            nextQuestion();
        }
    }

    private class QuitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Calculate total score and show it in a dialog
            String message = "Total Correct: " + totalCorrect + "\n"
                           + "Total Wrong: " + totalWrong + "\n"
                           + "Total Score: " + score;
            JOptionPane.showMessageDialog(QuizGame.this, message, "Quiz Results", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); // Exit the application
        }
    }

    private class ReplayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset game state
            currentQuestionIndex = 0;
            score = 0;
            totalCorrect = 0;
            totalWrong = 0;
            scoreLabel.setText("Score: 0");

            // Shuffle questions
            shuffleQuestions();

            // Switch back to the main panel
            getContentPane().removeAll();
            add(mainPanel);
            mainPanel.setVisible(true); // Ensure the main panel is visible
            revalidate();
            repaint();

            // Show the first question
            showQuestion();
        }
    }

    public static void main(String[] args) {
        new QuizGame();
    }
}

class Question {
    private String questionText;
    private String[] choices;
    private int correctAnswerIndex;

    public Question(String questionText, String[] choices, int correctAnswerIndex) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getChoices() {
        return choices;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}