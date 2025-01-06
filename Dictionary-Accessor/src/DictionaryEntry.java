//Written by oneil853@umn.edu
import java.util.Comparator;
public class DictionaryEntry implements Comparable {

    // instance variables
    private String wordOrPhrase;
	private String definition;

    // constructors
    public DictionaryEntry() {}

    public DictionaryEntry(String wop, String def) {
        this.wordOrPhrase = wop;
		this.definition = def;
    }

	// You are free to implement other constructors as you see fit

    // You should implement at least the getters and setters below
	
	// returns a String array with the wordOrPhrase at location zero and definition 
	// at location 1
    public String [] getData() {
        return new String[] {wordOrPhrase, definition};
    }

	// accepts a String array with the wordOrPhrase at location zero and the definition
	// of the wordOrPhrase at location 1 and sets the variables accordingly
    public void setData(String [] data) {
        wordOrPhrase = data[0];
        definition = data[1];
    }
	
	// Define a Comparator method that can be used to sort an ArrayList of Nodes in Lexically
	// Ascending order - that is, from A to Z, according to the wordOrPhrase
	// Note that you must keep the exact method signature provided here
	
	@Override
	public int compareTo(Object o) {
        //If a dictionary entry was given,
        // Cast object 'o' to DictionaryEntry class, so that "getData" function could be used.
        if (o.getClass().equals(DictionaryEntry.class)) {
            DictionaryEntry otherEntry = (DictionaryEntry) o;
            String[] compareToData = otherEntry.getData();
            return wordOrPhrase.compareTo(compareToData[0]);
        }
        if (o.getClass().equals(String.class)) {
            return wordOrPhrase.compareTo((String) o);
        }
        System.out.println("Object: DictionaryEntry can only be compared with strings, or another DictionaryEntry. Equality will be returned");
        return -0;
    }

    public String toString() {
        return (wordOrPhrase + " - " + definition);
    }
}