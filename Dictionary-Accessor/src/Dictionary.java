//Written by oneil853@umn.edu
import java.util.ArrayList;
import java.io.*;
public class Dictionary {
    final private ArrayList<DictionaryEntry> dictionaryList = new ArrayList<DictionaryEntry>();

    public void addItemFromFile(String wordOrPhrase, String definition) {
        //Creates a dictionary entry based on file input, but converts the definition to lower case.
        DictionaryEntry newEntry = new DictionaryEntry(wordOrPhrase.toLowerCase(), definition.toLowerCase());

        boolean repeat = false;

        //Loop checks for a match. If one is found, that match is replaced with the new entry.
        //Otherwise, the new entry is added to the end of the list.
        for (int i = 0; i < dictionaryList.size(); i++) {
            if (dictionaryList.get(i).compareTo(wordOrPhrase) == 0) {
                repeat = true;
                dictionaryList.set(i,newEntry);
                break;
            }
        }
        if (!(repeat)) {
            dictionaryList.add(newEntry);
        }
    }

    public void sortNewItem(DictionaryEntry newItem) {
        int beginningIndex=0;
        int middleIndex=(dictionaryList.size())/2;
        int endIndex=dictionaryList.size();

        //Binary Search
        while(beginningIndex != endIndex){
            int comparisonResults = newItem.compareTo(dictionaryList.get(middleIndex));
            if (comparisonResults > 0) {
                beginningIndex=middleIndex+1;
                middleIndex=(beginningIndex+endIndex)/2;
            } else if (comparisonResults < 0) {
                endIndex=middleIndex;
                middleIndex=(beginningIndex+endIndex)/2;
            } else {
                endIndex=middleIndex;
                beginningIndex=middleIndex;
            }
        }
        dictionaryList.add(middleIndex,newItem);
    }

    public void sortDictionary() {
        ArrayList<DictionaryEntry> oldList = new ArrayList<DictionaryEntry>(dictionaryList);
        dictionaryList.clear();

        for (int i = 0; i < oldList.size(); i++) {
            sortNewItem(oldList.get(i));
        }
    }

    public void searchDictionary(String stringToFind) {
        //Binary search is conducted to efficiently find a match.
        int indexReached = binarySearch(stringToFind, false);
        boolean found = !(indexReached==-1);

        //If binary search found a match, the matches are printed. Otherwise, the user is informed of the
        //lack of a match
        if (found) {
            while(found) {
                //This prints out the first match, then checks to see if the next item is a match
                //The loop either repeats to print the second match, or ends.
                String wordOrPhraseToPrint = ((dictionaryList.get(indexReached)).getData())[0];
                String definitionToPrint = ((dictionaryList.get(indexReached)).getData())[1];
                System.out.println("\n" + wordOrPhraseToPrint + " - " + definitionToPrint);
                String lettersToCompare;
                //This if statement checks if a word after the index exists to avoid an error.
                if (indexReached == dictionaryList.size()-1) {
                    break;
                }
                //This if statement checks to see if taking a substring that is as long as the user's guess is possible
                if ((dictionaryList.get(indexReached+1)).getData()[0].length() < stringToFind.length()) {
                    lettersToCompare = ((dictionaryList.get(++indexReached)).getData())[0];
                } else {
                    lettersToCompare = ((dictionaryList.get(++indexReached)).getData())[0].substring(0,stringToFind.length());
                }
                int comparisonResults = lettersToCompare.compareTo(stringToFind);
                if (comparisonResults!=0) {
                    found = false;
                }
            }
            System.out.println();
        } else {
            System.out.println("No words were found in the dictionary starting with \"" + stringToFind + "\"");
        }
    }
    public void deleteWord(String wordToFind) {
//Binary search is conducted to efficiently find a match.
        int indexReached = binarySearch(wordToFind, false);
        boolean found = !(indexReached == -1);
        //If binary search found a match, the matches are printed. Otherwise, the user is informed of the
        //lack of a match.
        if (found) {
            //This checks to see if there are more than one occurrence of the word requested
            //By checking the next value in the list (The previous was already checked in the back-tracking).

            String lettersToCompare;
            int comparisonResults;
            if (indexReached != dictionaryList.size()-1) {
                if ((dictionaryList.get(indexReached+1)).getData()[0].length() < wordToFind.length()) {
                    lettersToCompare = ((dictionaryList.get(indexReached+1)).getData())[0];
                } else {
                    lettersToCompare = ((dictionaryList.get(indexReached+1)).getData())[0].substring(0,wordToFind.length());
                }
                comparisonResults = lettersToCompare.compareTo(wordToFind);
            } else {
                comparisonResults = -1;
            }


            //If there is only one occurrence, the word is removed from the dictionary.
            //Otherwise, the user is informed of the numerous possibilities, which are then printed.
            if(comparisonResults!=0) {
                dictionaryList.remove(indexReached);
                System.out.println("The word requested for deletion has been successfully deleted.");
            } else {
                System.out.println("There was more than one possible candidate for deletion found: " + "\n");
                while(found) {
                    //This prints out the first match, then checks to see if the next item is a match
                    //The loop either repeats to print the second match, or ends.
                    String wordOrPhraseToPrint = ((dictionaryList.get(indexReached)).getData())[0];
                    String definitionToPrint = ((dictionaryList.get(indexReached)).getData())[1];
                    System.out.println(wordOrPhraseToPrint + " - " + definitionToPrint + "\n");

                    //This if statement checks if a word after the index exists to avoid an error.
                    if (indexReached == dictionaryList.size()-1) {
                        break;
                    }
                    //This if statement checks to see if taking a substring that is as long as the user's guess is possible
                    if ((dictionaryList.get(indexReached+1)).getData()[0].length() < wordToFind.length()) {
                        lettersToCompare = ((dictionaryList.get(++indexReached)).getData())[0];
                    } else {
                        lettersToCompare = ((dictionaryList.get(++indexReached)).getData())[0].substring(0,wordToFind.length());
                    }
                    comparisonResults = lettersToCompare.compareTo(wordToFind);
                    if (comparisonResults!=0) {
                        found = false;
                    }
                }
            }
        } else {
            System.out.println("No words were found in the dictionary starting with \"" + wordToFind + "\"");
        }
    }

    public void addNewWord(String wordOrPhraseToAdd, String definitionToAdd) {
        //If binary search does not find the word, the user is informed. Otherwise, the new entry is added.
        //and the user is informed.
        if (binarySearch(wordOrPhraseToAdd,false) != -1) {
            System.out.println("Error: The word you tried to add already exists in the dictionary");
        } else {
            DictionaryEntry newEntry = new DictionaryEntry(wordOrPhraseToAdd, definitionToAdd);
            sortNewItem(newEntry);
            System.out.println("The word/phrase " + wordOrPhraseToAdd + " was successfully added to the dictionary");
        }
    }

    private int binarySearch(String toFind, boolean returnIndexIfNotFound) {
        //Binary search is conducted to efficiently find a match.
        boolean found = false;
        int stringLength = toFind.length();
        int beginningIndex=0;
        int middleIndex=(dictionaryList.size())/2;
        int endIndex=dictionaryList.size();
        while(beginningIndex != endIndex) {
            //This gets the DictionaryEntry at the middle index, gets its information, takes the word
            // or phrase from that information, then finally abridges it to be as long as the user's input
            String lettersToCompare;
            if ((dictionaryList.get(middleIndex)).getData()[0].length() < stringLength) {
                lettersToCompare = ((dictionaryList.get(middleIndex)).getData())[0];
            } else {
                lettersToCompare = ((dictionaryList.get(middleIndex)).getData())[0].substring(0, stringLength);
            }

            //The process detailed above allows for the two words to be exactly compared using compareTo().
            int comparisonResults = toFind.compareTo(lettersToCompare);
            if (comparisonResults > 0) {
                beginningIndex=middleIndex+1;
                middleIndex=((beginningIndex+endIndex))/2;
            } else if (comparisonResults < 0) {
                endIndex=middleIndex;
                middleIndex=(beginningIndex+endIndex)/2;
            } else {
                endIndex=middleIndex;
                beginningIndex=middleIndex;
                found = true;
            }
        }
        //If the word is found, this loop back-tracks to see where in the dictionary there first is a match.
        boolean previousWordMatches = found;
        while(previousWordMatches) {
            String lettersToCompare;
            if (middleIndex == 0) {
                break;
            }
            if ((dictionaryList.get(middleIndex-1)).getData()[0].length() < stringLength) {
                lettersToCompare = ((dictionaryList.get(--middleIndex)).getData())[0];
            } else {
                lettersToCompare = ((dictionaryList.get(--middleIndex)).getData())[0].substring(0,stringLength);
            }
            //The process detailed above allows for the two words to be exactly compared using compareTo().
            int comparisonResults = lettersToCompare.compareTo(toFind);
            if (comparisonResults!=0) {
                previousWordMatches = false;
                middleIndex++;
            }
        }
        //If the caller requested that the index should only be returned if the item was found
        // which is done via parameter, and the index was not found, -1 is returned.
        //Otherwise, the index reached gets returned, or the first index where there is a match.
        if (!(returnIndexIfNotFound) && !(found)) {
            return -1;
        } else {
            return middleIndex;
        }
    }

    public void updateDictionary(String wordToUpdate, String definitionToUpdate) {
        //Calls binary search to see if the word requested for update exists.
        //Performs the update if possible, and reports the success to the user.
        int indexToReplace = binarySearch(wordToUpdate, false);
        if (indexToReplace == -1) {
            System.out.println("Error: The word or phrase you wish to update does not exist.");
        } else {
            DictionaryEntry newEntry = new DictionaryEntry(wordToUpdate, definitionToUpdate);
            dictionaryList.set(indexToReplace, newEntry);
            System.out.println("The definition of the word " + wordToUpdate + " was successfully replaced");
        }
    }

    public void exportDictionary(String fileName) throws IOException{
        //Creates necessary objects for output writing.
        File exportFile = new File(fileName);
        FileOutputStream fos = new FileOutputStream(exportFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        //Writes all the words and their definitions in the dictionary into a file, line by line.
        for(int i = 0; i < dictionaryList.size(); i++) {
            String toWrite = (dictionaryList.get(i)).toString();
            bw.write(toWrite);
            bw.newLine();
        }
        bw.close();
    }
}
