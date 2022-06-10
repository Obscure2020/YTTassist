public class Main {

    public static void main(String[] args) throws Exception{
        //This main() is just for testing purposes.
        //String testInput = "This is some text. [b]This is some bold text. [!b]Once again, this text is normal. [i,u]This text is both Italics and underlined, [!i,!u]but this text isn't.";
        String testInput = "This is some text. [b]Now the text is bold. [i]Now the text is bold and italcs. [u]Now the text is bold, italics, and underlined. [!b,!i,!u]Now the text is back to normal.";
        Paragraph testPara = new Paragraph();
        testPara.newManuscript(testInput);
        testPara.debugPrint();
    }

}
