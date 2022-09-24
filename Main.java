import java.time.Duration;

public class Main {

    private static Duration parseSRTstamp(String input){
        StringBuilder sb = new StringBuilder(20);
        sb.append("PT");
        sb.append(input);
        sb.replace(4, 5, "H");
        sb.replace(7, 8, "M");
        sb.replace(10, 11, ".");
        sb.append('S');
        return Duration.parse(sb);
    }

    private static String formatSRTstamp(Duration input){
        StringBuilder sb = new StringBuilder(20);
        sb.append(input.toHoursPart());
        while(sb.length() < 2) sb.insert(0, "0");
        sb.append(':');
        sb.append(input.toMinutesPart());
        while(sb.length() < 5) sb.insert(3, "0");
        sb.append(':');
        sb.append(input.toSecondsPart());
        while(sb.length() < 8) sb.insert(6, "0");
        sb.append(',');
        sb.append(input.toMillisPart());
        while(sb.length() < 12) sb.insert(9, "0");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception{ //This main method is just for testing purposes.
        /*
        String testInput = "[fc00FF00,ap3]This string is green and on the left.[fc00FFFF,!ap]This string is cyan and at the bottom.";
        Paragraph testPara = new Paragraph();
        testPara.newManuscript(testInput);
        testPara.debugPrint();
        */

        Duration time = parseSRTstamp("00:08:03,000");
        System.out.println(time.toString());
        System.out.println(time.toMillis());
        System.out.println(formatSRTstamp(time));
    }

}
