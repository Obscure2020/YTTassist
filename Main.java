public class Main {

    public static void main(String[] args) throws Exception{ //This main method is just for testing purposes.
        Paragraph testPara = new Paragraph("00:00:13,208", "00:00:17,833");
        //String testInput = "[fc00FF00,ap3]This string is green and on the left.[fc00FFFF,!ap]This string is cyan and at the bottom.";
        String testInput = "But in any case, according to\na [fc0080FFA]2017 study[!fc] by the IDFA,";
        testPara.newManuscript(testInput);
        testPara.debugPrint();
    }

}
