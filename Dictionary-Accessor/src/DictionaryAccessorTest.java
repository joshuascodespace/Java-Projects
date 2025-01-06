//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class DictionaryAccessorTest {
//
//    @Test
//    void populateDictionaryWithUKSlang() throws IOException {
//        Dictionary myDictionary = new Dictionary();
//        assertTrue(DictionaryAccessor.populateDictionary("UK_SlangDictionary.txt", myDictionary));
//    }
//
//    @Test
//    void populateDictionaryWithUSSlang() throws IOException {
//        Dictionary myDictionary = new Dictionary();
//        assertTrue(DictionaryAccessor.populateDictionary("US_SlangDictionary-1.txt", myDictionary));
//    }
//
//    @Test
//    void populateDictionaryWithResultsDoc() throws IOException {
//        Dictionary myDictionary = new Dictionary();
//        assertTrue(DictionaryAccessor.populateDictionary("no.txt", myDictionary));
//    }
//
//    @Test
//    void populateDictionaryWithInvalid() throws IOException {
//        Dictionary myDictionary = new Dictionary();
//        assertFalse(DictionaryAccessor.populateDictionary("Invalid Name", myDictionary));
//    }
//
//}