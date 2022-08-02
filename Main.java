public class Main {

    public static void main(String[] args) throws Exception{ //This main method is just for testing purposes.
        String testInput = "[fc00FF00,ap3]This string is green and on the left.[fc00FFFF,!ap]This string is cyan and at the bottom.";
        Paragraph testPara = new Paragraph();
        testPara.newManuscript(testInput);
        testPara.debugPrint();
    }

}
