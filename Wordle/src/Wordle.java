// Written by oneil853
import java.util.Scanner;
import java.io.*;
public class Wordle {
    //Allows background color for terminal to be accessed easily.
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String RED_BACKGROUND = "\033[41m";    // RED

    public static void main(String[] args) throws Exception {
        //Variables are declared that need to be "remembered" after each round.
        //Like which words are used and how many times the game has been played.
        String[] usedWords = new String[50];
        int timesPlayed = 0;

        Scanner s = new Scanner(System.in);
            do {
                //Creates a list of all the words the program can choose from.
                String[] allWords = getWords(usedWords, timesPlayed);

                //Gets a random word from the list of words.
                String solution = getSolution(allWords);

                //Allows the program to remember which words have been used
                //By storing them in a list.
                usedWords[timesPlayed] = solution;

                //Keeps track of the times the game is played
                //So that the program knows how many words cannot be chosen.
                timesPlayed++;

                //These two variables keep track of what was printed during previous guesses.
                String toPrint = " ";
                String[] alphabetFeedback = new String[27];

                //For loop runs once for each guess.
                for (int i = 0; i < 6; i++) {
                    //Gets the user's guess.
                    String guess = getUserInput();

                    //Calls method to check accuracy of guess
                    char[] guessAccuracy = guessCheck(solution, guess);

                    //Calls method to inform user of their guess's accuracy via terminal
                    alphabetFeedback = printFeedback(guess,guessAccuracy,toPrint,alphabetFeedback);

                    //I wanted to return a string and a String list,
                    //so I stored my string as the last element in the list
                    //and now I update the second value.
                    toPrint = alphabetFeedback[26];

                    //Checks to see if the user won by seeing
                    //If all letters were evaluated to be correct and in the right place.
                    boolean victory = true;
                    for (int j = 0; j < 5; j++) {
                         if (!(guessAccuracy[j] == 'c')) {
                             victory = false;
                         }
                    }
                    //Congradulates the user if they won, then stops the for loop
                    //So that the program doesn't ask for more guesses
                    if (victory) {
                        System.out.println("Congratulations! You win!!");
                        break;
                    }
                    //If the user has used all their guesses and hasn't guessed the word,
                    //The porgram informs them of their failure and the word.
                    if (i == 5) {
                        System.out.println("Unfortunately, you have failed. The word is: " + solution);
                        System.out.println("Better luck next time");
                    }
                }
                //Starts the game again if the user wishes to play again
                //By calling a method to get the user's input on that matter.
            } while (playAgainInput(s));
        //Calls for the file with the words to be updated to not contain used words.
        updateList(getWords(usedWords, timesPlayed));
    }

    public static String[] getWords(String[] usedWords, int timesPlayed) throws Exception {
        int counter = 0;

        //Creates a string to store all words read from word file
        String[] wordsList = new String[5758-timesPlayed];

        //Links the file with the words to the program so that it can be read.
        File wordOptions = new File("sgb-words.txt");
        Scanner fileScanner = new Scanner(wordOptions);

        //This if statement runs to simplify the code if
        //Creating the list is as simple as reading the file
        //Which happens when the program doesn't need to
        //avoid picking previously picked words.
        if (timesPlayed == 0)
            //Runs until all words are read from the file.
            while (fileScanner.hasNextLine()) {
                //Puts all the words from the file into a list.
                wordsList[counter] = fileScanner.nextLine();

                //Lets the program know the indexes covered.
                counter++;
        } else {
            while (fileScanner.hasNextLine()) {
                //Stores a word from the document.
                String currentWord = fileScanner.nextLine();

                //Checks if the word has been used by comparing the next word in the file
                //to the list of words that have been used.
                boolean wordUsed = false;
                for (int i = 0; i < timesPlayed; i++) {
                    if (usedWords[i].equals(currentWord)) {
                        wordUsed = true;
                        break;
                    }
                }

                //Puts all the words from the file into a list.
                //If the word has not been used
                if (!wordUsed) {
                    wordsList[counter] = currentWord;

                    //Lets the program know the indexes covered.
                    counter++;
                }
            }
        }

        //Passes the list created to main.
        return wordsList;
    }

    public static String getSolution(String[] options) {

        //Find out how many words the program has to choose from.
        int counter = 0;
        while (options[counter]!=null) {
            counter++;
        }

        //Creates a random number, including all words from the list as possibilities
        //Then sets the solution word to be the number from that index
        int randomIndex = (int) (Math.random()*(counter));
        return options[randomIndex];
    }

    public static String getUserInput() {
        Scanner guessScanner = new Scanner(System.in);




        //Loops asks the user for a guess, checks to make sure it's valid
        //Then re-runs the loop if it's not.
        boolean validGuess = true;
        String guess;
        do {
            validGuess = true;

            //Gets user input
            System.out.print("Please enter a guess: ");
            guess = guessScanner.next();

            //Checks if the length is 5.
            if (guess.length() != 5) {
                System.out.println("Please enter a guess that is 5 letters long");
                validGuess = false;
            } else {
                //For loop checks if each value is a letter
                for (int i = 0; i < 5; i++) {
                    if (!Character.isLetter(guess.charAt(i))) {
                        validGuess = false;
                        break;
                    }
                }
            }
        } while(!validGuess);
        return guess;
    }

    public static char[] guessCheck(String answer, String guess) {
        //guessFeedback list holds the accuracy of each letter in the guess
        //I will represent the right letter and location with 'c', for "correct"
        //I will represent the right letter, wrong location with 'p', for partially correct.
        //I will represent the wrong letter with 'w', for wrong.
        char[] guessFeedback = {'w','w','w','w','w'};

        for (int i = 0; i < 5; i++) {
            //Checks if the letter is correct and in the right location.
            //If so, no further analysis needs to be done of that letter; the letter is "correct".
            if (answer.charAt(i)==guess.charAt(i)) {
                guessFeedback[i] = 'c';
                continue;
            }

            //Loop checks whether the letter has been compared previously, and how many times.
            //This allows the program to know how to compare the letters.
            //Only repetitions occurring before the letter's index are counted.
            //This is done because a letter has not been "repeated"
            // until it has already occurred in the word.
            int repeat = 0;
            for (int j = 0; j < i; j++){
                if (guess.charAt(i)==guess.charAt(j)) {
                    repeat++;
                }
            }

            //Compares your guess with letter at different spots in the answer
            //In order to see if your answer is partially correct, if it isn't repeated
            for (int j = 0; j < 5; j++) {
                if (guess.charAt(i)==answer.charAt(j)) {
                    if (repeat == 0) {
                        if (answer.charAt(j) != guess.charAt(j))
                            //If the answer is the same as the guess at that spot,
                            //Feedback will be given later that the later guess is
                            //both correct and in the right spot, and therefore feedback
                            //on this partial match-up does not count.
                            guessFeedback[i] = 'p';
                    } else {
                        //This means that if a repeated letter appears twice, on the second repetition,
                        // It will be counted, since repeat will then be 0.
                        // If a repeated letter only appears once, this statement causes it to
                        // Not be shown the second time, since the match-up was already covered.
                        repeat--;
                    }
                }
            }
        }

//        for (int i = 0; i < 5; i++) {
//            System.out.println(guessFeedback[i]);
//        }

        return guessFeedback;
    }

    public static String[] printFeedback(String guess, char[] guessFeedback, String memory, String[] alphabetMemory) {
        //Takes in the user's guess, the feedback on their guess, the input on previous guesses,
        //and the keyboard, reflecting their guesses.
        //Memory contains all the previous guesses, and the current guess gets added to it
        //When the method runs, and all are printed together.
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        //Prints the word the user guessed in green.
        System.out.println(" " + GREEN + guess + ANSI_RESET);

        //For loop runs once for each letter in the guess.
        for (int i = 0; i < 5; i++) {
            int letterIndex = 0;
            for (int j = 0; j < 26; j++){
                //Nested loop runs to find out what the index is in the alphabet of the user's guess's letter.
                //This is needed to store information about that letter in a parallel list.
                if (guess.charAt(i) == alphabet[j])
                    letterIndex = j;
            }
            //Statement codes each letter in the guess and on the keyboard a color
            //Based on the correctness of their guess
            switch (guessFeedback[i]) {
                case 'c':
                    memory += GREEN_BACKGROUND;
                    alphabetMemory[letterIndex] = GREEN_BACKGROUND;
                    break;
                case 'p':
                    memory += YELLOW_BACKGROUND;
                    if (alphabetMemory[letterIndex] != null) {
                        //This if-statement causes the background of a keyboard character
                        //To not change to yellow if it was previously green.
                        //Because: "the keyboard should track the highest case reached by each letter"
                        if (!(alphabetMemory[letterIndex].equals(GREEN_BACKGROUND)))
                            alphabetMemory[letterIndex] = YELLOW_BACKGROUND;
                    } else {
                        alphabetMemory[letterIndex] = YELLOW_BACKGROUND;
                    }
                    break;
                case 'w':
                    memory += RED_BACKGROUND;
                    alphabetMemory[letterIndex] = RED_BACKGROUND;
            }
            //Adds the character itself to the memory of what to print.
            memory += guess.charAt(i);
            //Resets the background color to neutral.
            memory += ANSI_RESET;
        }
        //Indents after the word is printed.
        memory += "\n ";

        //Prints the user's current guess and past guesses with color-codes.
        System.out.println(memory);

        //Runs once for each letter on the keyboard
        for (int i = 0; i < 26; i++){
            //Changes the background color of a letter, if it's been guessed.
            if (alphabetMemory[i] != null) {
                System.out.print(alphabetMemory[i]);
            }
            //Prints the letter and resets the background
            System.out.print(alphabet[i]);
            System.out.print(ANSI_RESET);

            //Indents the keyboard after every 9 letters so it's not on one line
            //And indents at the end.
            if (i == 8 || i == 17 || i == 25) {
                System.out.println("");
            }
        }
        //Adds the memory into the alphabet-color memory string so that both can be returned.
        alphabetMemory[26]=memory;

        //Passes a record of what was printed back to main.
        return alphabetMemory;
    }

    public static boolean playAgainInput(Scanner s) {
        boolean playAgain = false;

        //Loop created to ask the user if they wish to play again.
        //If the user's answer is invalid, ask them again
        String answer;
        do {
            //Asks the user if they wish to play again.
            System.out.print("Do you wish to play again? Enter 'yes' or 'no': ");
            answer = s.nextLine();

            //Allows program to accept uppercase.
            answer = answer.toLowerCase();

            //Checks if answer is valid,
            //and determines whether the user wants to play again based on it.
            if (answer.equals("yes"))
                playAgain = true;
            else if (answer.equals("no"))
                playAgain = false;
            else
                System.out.println("Error: Please only answer 'yes' or 'no'");
        } while (!(answer.equals("yes") || answer.equals("no")));
        return playAgain;
    }

    public static void updateList(String[] updatedList) throws IOException {
        //Creates classes to allow for file editing.
        File newWords = new File("sgb-words.txt");
        FileOutputStream fos = new FileOutputStream(newWords);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        //Writes every word into the file. The purpose is to replace the file with
        //A version of itself without the words that have been used.
        int index = 0;
        while (updatedList[index] != null) {
            bw.write(updatedList[index]);
            bw.newLine();
            index++;
        }
        bw.close();
    }
}


