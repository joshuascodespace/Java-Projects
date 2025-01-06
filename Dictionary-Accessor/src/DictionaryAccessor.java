//Written by oneil853@umn.edu
import java.util.Scanner;
import java.io.*;
public class DictionaryAccessor {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        Dictionary myDictionary = new Dictionary();

        //Asks user for a file, then calls a method to add the words to the dictionary.
        getInputFiles(s, myDictionary);

        //Calls the dictionary to sort itself.
        myDictionary.sortDictionary();

        doUserWish(s,myDictionary);
        s.close();
    }

    public static boolean populateDictionary(String fileName, Dictionary myDictionary) throws IOException {
        //This method returns a boolean to let the caller know whether the file was found and usable.
        //This is done by returning true if the "true" block is successful, and "false" if an error is caught.
        try {
            //Reads all items from the file and puts them into the Dictionary.
            File wordFile = new File(fileName);
            Scanner fileReader = new Scanner(wordFile);
            while (fileReader.hasNext()) {
                String fileInput = fileReader.nextLine();
                String[] fileData = fileInput.split("-");
                String wordOrPhraseInput = fileData[0];
                String definitionInput = fileData[1];
                myDictionary.addItemFromFile(wordOrPhraseInput, definitionInput);
            }
            return true;
        }
        catch(IOException e) {
            System.out.println("Error: Your file does not exist or does not have a readable format.");
            return false;
        }
    }

    public static boolean getContinuationInput(Scanner inputScanner) {
        //Asks the user if they want to add another file, and returns the result
        //Or asks again if the user's answer is invalid.
        System.out.print("Do you want to add another file? Enter 'yes' or 'no': ");
        String answer = getGeneralInput(inputScanner);
        if (answer.equals("yes")) {
            return true;
        } else if (answer.equals("no")) {
            return false;
        } else {
            System.out.println("Error: Cannot make sense of answer.");
            System.out.println("Please only enter 'yes' or 'no'.");
            return getContinuationInput(inputScanner);
        }
    }

    public static void getInputFiles(Scanner inputScanner, Dictionary myDictionary) throws IOException {
        boolean repeatLoop;
        do {
            //Asks the user for a file, then calls a method to populate the dictionary with that file.
            System.out.print("Please enter the name of the file with the dictionary items: ");
            String fileName = inputScanner.nextLine();

            //Asks the user again for a file if there was an error.
            //Calls a method to prompt the user if they'd like to add another file if there is no error.
            if (!populateDictionary(fileName,myDictionary)) {
                repeatLoop = true;
            } else {
                repeatLoop = getContinuationInput(inputScanner);
            }
        } while(repeatLoop);
    }

    public static char getUserWish(Scanner inputScanner) {
        char returnCharacter;
        String userInput;
        boolean validInput;
        System.out.println("How do you wish to interact with your dictionary?");
        do {
            System.out.println("Enter 'S' to search for a word or phrase, or the first part of that word or phrase");
            System.out.println("Enter 'D' to delete a word or phrase");
            System.out.println("Enter 'A' to add a new word or phrase");
            System.out.println("Enter 'U' to change the definition of a current a word or phrase");
            System.out.println("Enter 'C' to end the program and receive the dictionary in a file");
            userInput = getGeneralInput(inputScanner);
            validInput = (userInput.equals("s") || userInput.equals("d") || userInput.equals("a") || userInput.equals("u") || userInput.equals("c"));
            if (!(validInput)) {
                System.out.println("Your input is not valid. Please enter one of the specified characters");
            }
        } while(!(validInput));
        returnCharacter = userInput.charAt(0);
        return returnCharacter;
    }

    public static String getGeneralInput(Scanner inputScanner) {
        return (inputScanner.nextLine()).toLowerCase();
    }

    public static void doUserWish(Scanner s, Dictionary myDictionary) throws IOException{
        char userWish;
        do {
            userWish = getUserWish(s);
            switch (userWish) {
                case 's':
                    System.out.print("Please enter the word/phrase, or part of one, you wish to search for: ");
                    String wordToSearchFor = getGeneralInput(s);
                    myDictionary.searchDictionary(wordToSearchFor);
                    break;
                case 'd':
                    System.out.print("Please enter the word/phrase you wish to delete: ");
                    String wordToDelete = getGeneralInput(s);
                    myDictionary.deleteWord(wordToDelete);
                    break;
                case 'a':
                    System.out.print("Please enter the word/phrase you wish to add: ");
                    String wordToAdd = getGeneralInput(s);
                    System.out.print("Please enter the definition of the word/phrase you wish to add: ");
                    String definitionToAdd = getGeneralInput(s);
                    myDictionary.addNewWord(wordToAdd, definitionToAdd);
                    break;
                case 'u':
                    System.out.print("Please enter the word/phrase you wish to change the definition of: ");
                    String wordToChange = getGeneralInput(s);
                    System.out.print("Please enter the word/phrase's new definition: ");
                    String newDefinition = getGeneralInput(s);
                    myDictionary.updateDictionary(wordToChange, newDefinition);
                    break;
                case 'c':
                    System.out.println("Please enter the name of the file ");
                    System.out.print("you wish to have this dictionary exported to: ");
                    String exportFile = getGeneralInput(s);
                    myDictionary.exportDictionary(exportFile);
                    System.out.println("The dictionary has been closed.");
            }

        } while (userWish != 'c');
    }
}
