//Written by oneil853@umn.edu
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryEntryTest {
    String firstWord = "Happy";
    String firstDefinition = "Not knowing the true meaning of suffering";


    DictionaryEntry firstEntry = new DictionaryEntry(firstWord,firstDefinition);
    @Test
    void getData() {
        String[] firstString = {firstWord,firstDefinition};
        String[] wrongString = {firstWord, "Knowing the true meaning of suffering"};
        assertTrue(firstString[0].equals(firstEntry.getData()[0]) && firstString[1].equals(firstEntry.getData()[1]));
        assertFalse(wrongString[0].equals(firstEntry.getData()[0]) && wrongString[1].equals(firstEntry.getData()[1]));
    }


    @Test
    void compareTo() {
        String secondWord = "Sadness";
        String secondDefinition = "Knowing the true meaning of suffering";
        DictionaryEntry secondEntry = new DictionaryEntry(secondWord,secondDefinition);
        assertTrue(firstEntry.compareTo(secondEntry) < 0);
        String[] secondList = {"Depression", secondDefinition};
        secondEntry.setData(secondList);
        assertTrue(firstEntry.compareTo(secondEntry) > 0);
        assertEquals(firstEntry.compareTo(firstEntry),0);
        assertEquals(secondEntry.compareTo("Depression"),0);
        assertTrue(secondEntry.compareTo("Sadness") < 0);
        Integer five = new Integer(5);
        assertEquals(firstEntry.compareTo(five),0);
    }

    @Test
    void testToString() {
        assertTrue("Happy - Not knowing the true meaning of suffering".equals(firstEntry.toString()));
    }

    @Test
    void setData() {
        String newWord = "Sadness";
        String newDefinition = "Knowing the true meaning of suffering";
        String[] newList = {newWord,newDefinition};
        firstEntry.setData(newList);
        assertTrue(newList[0].equals(firstEntry.getData()[0]) && newList[1].equals(firstEntry.getData()[1]));
        assertFalse(firstWord.equals(firstEntry.getData()[0]) && firstDefinition.equals(firstEntry.getData()[1]));
    }
}