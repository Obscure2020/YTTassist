public class Main {

    public static void main(String[] args) throws Exception{
        //This main() is just for testing purposes.
        String testInput = "This is some text. [bo]Now the text is bold. [it]Now the text is bold and italcs. [un]Now the text is bold, italics, and underlined. [!bo,!it,!un]Now the text is back to normal.";
        Paragraph testPara = new Paragraph();
        testPara.newManuscript(testInput);
        testPara.debugPrint();
    }

}
