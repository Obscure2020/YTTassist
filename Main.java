import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{ //This main method is just for testing purposes.
        ArrayList<Paragraph> subtitles = FileHandler.readSRT("PecansTester.srt");
        FileHandler.writeYTT(subtitles, new File("PecansTesterOUT.ytt"));
    }
}