//Written by oneil853@umn.edu
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    Dictionary testDictionary = new Dictionary();


    @Test
    void sortNewItem() {

        DictionaryEntry firstEntry = new DictionaryEntry("b","The second letter of the alphabet");
        testDictionary.sortNewItem(firstEntry);
        testDictionary.addNewWord("b","The second letter of the alphabet");
        testDictionary.sortNewItem(firstEntry);

        testDictionary.addNewWord("a","The first letter of the alphabet");

        DictionaryEntry secondEntry = new DictionaryEntry("a","The first letter of the alphabet");
        testDictionary.sortNewItem(secondEntry);

        testDictionary.addNewWord("c","The third letter of the alphabet");

        DictionaryEntry thirdEntry = new DictionaryEntry("c","The third letter of the alphabet");
        testDictionary.sortNewItem(thirdEntry);
    }

    @Test
    void addItemFromFile() {
        testDictionary.addItemFromFile("d", "The fourth letter of the alphabet");
        testDictionary.addItemFromFile("c", "The third letter of the alphabet");
        testDictionary.addItemFromFile("c", "The third letter of the alphabet");
    }

    @Test
    void sortDictionary() {
        testDictionary.addNewWord("b","The second letter of the alphabet");
        testDictionary.addNewWord("a","The first letter of the alphabet");
        testDictionary.addNewWord("ab","Not a letter in the alphabet");
        testDictionary.sortDictionary();
    }

    @Test
    void searchDictionary() {
        testDictionary.addNewWord("b","The second letter of the alphabet");
        testDictionary.addNewWord("a","The first letter of the alphabet");
        testDictionary.addNewWord("ab","Not a letter in the alphabet");
        testDictionary.searchDictionary("a");
        testDictionary.searchDictionary("b");
        testDictionary.searchDictionary("d");
        testDictionary.searchDictionary("ab");
    }

//    @Test
//    void deleteWord() {
//        testDictionary.addNewWord("b","The second letter of the alphabet");
//        testDictionary.addNewWord("a","The first letter of the alphabet");
//        testDictionary.addNewWord("ab","Not a letter in the alphabet");
//        testDictionary.addNewWord("cd","Not a letter in the alphabet");
//        testDictionary.addNewWord("abc","Not a letter in the alphabet");
//        testDictionary.addNewWord("abc","Not a letter in the alphabet");
//        testDictionary.deleteWord("a");
//        testDictionary.deleteWord("b");
//        testDictionary.deleteWord("d");
//        testDictionary.deleteWord("ab");
//        testDictionary.deleteWord("cd");
//        testDictionary.deleteWord("abc");
//        testDictionary.addNewWord("abc","Not a letter in the alphabet");
//        testDictionary.addNewWord("z","The twenty-sixth letter of the alphabet");
//        testDictionary.addNewWord("zy","Not a letter in the alphabet");
//        testDictionary.deleteWord("z");
//    }

    @Test
    void addNewWord() {
        testDictionary.addNewWord("a", "The first letter of the alphabet");
        testDictionary.addNewWord("b","The second letter of the alphabet");
        testDictionary.addNewWord("ab", "Not a letter in the alphabet");
    }
//
    @Test
    void updateDictionary() {
        testDictionary.addNewWord("a", "The first letter of the alphabet");
        testDictionary.addNewWord("b","The second letter of the alphabet");
        testDictionary.addNewWord("ab", "Not a letter in the alphabet");
        testDictionary.addNewWord("d", "The fourth letter of the alphabet");
        testDictionary.updateDictionary("ab", "Not yet a letter in the alphabet");
        testDictionary.updateDictionary("e", "Euler's constant");
        testDictionary.updateDictionary("d", "The fourth letter of the alphabet");
    }
//
    @Test
    void exportDictionary() throws Exception {
        testDictionary.addNewWord("b","The second letter of the alphabet");
        testDictionary.exportDictionary("test.txt");
    }
}