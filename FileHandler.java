import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileHandler {
    private static boolean simpleMatch(final String s, final String pat, final char x){
        if(s.length() != pat.length()) return false;
        return IntStream.range(0,pat.length()).parallel().filter(i->pat.charAt(i)!=x).allMatch(i->s.charAt(i)==pat.charAt(i));
    }

    public static ArrayList<Paragraph> readSRT(String pathname) throws FileNotFoundException{
        Scanner scan = new Scanner(new File(pathname));
        ArrayList<String> input = new ArrayList<>();
        while(scan.hasNext()) input.add(scan.nextLine().trim());
        scan.close();
        int[] t = IntStream.range(0,input.size()).filter(i->simpleMatch(input.get(i),"xx:xx:xx,xxx --> xx:xx:xx,xxx",'x')).toArray();
        ArrayList<String> manuscripts = new ArrayList<>(t.length);
        for(int i=0; i<t.length-1; i++){
            StringBuilder sb = new StringBuilder();
            for(int s=t[i]+1; s<t[i+1]-1; s++){
                sb.append(input.get(s));
                sb.append('\n');
            }
            manuscripts.add(sb.toString().trim());
        }
        { //Last paragraph
            StringBuilder sb = new StringBuilder();
            for(int s=t[t.length-1]+1; s<input.size(); s++){
                sb.append(input.get(s));
                sb.append('\n');
            }
            manuscripts.add(sb.toString().trim());
        }
        ArrayList<String> starts = new ArrayList<>(t.length);
        ArrayList<String> ends = new ArrayList<>(t.length);
        for(int i : t){
            starts.add(input.get(i).substring(0,12));
            ends.add(input.get(i).substring(17));
        }
        ArrayList<Paragraph> result = new ArrayList<>(t.length);
        for(int i=0; i<t.length; i++){
            Paragraph p = new Paragraph(starts.get(i), ends.get(i));
            p.newManuscript(manuscripts.get(i));
            result.add(p);
        }
        return result;
    }

    private static int getID(WindowPosition item, ArrayList<WindowPosition> list){
        int spot = list.indexOf(item);
        if(spot < 0){
            spot = list.size();
            list.add(item);
        }
        return spot + 1;
    }

    private static int getID(PenStyle item, ArrayList<PenStyle> list){
        int spot = list.indexOf(item);
        if(spot < 0){
            spot = list.size();
            list.add(item);
        }
        return spot + 1;
    }

    private static String markupSafe(String input){
        HashMap<String,String> conv = new HashMap<>(8);
        conv.put("\"", "&#34;");
        conv.put("&", "&#38;");
        conv.put("'", "&#39;");
        conv.put("/", "&#47;");
        conv.put("<", "&#60;");
        conv.put("=", "&#61;");
        conv.put(">", "&#62;");
        conv.put("\\", "&#92;");
        return Arrays.stream(input.split("")).map(s -> conv.getOrDefault(s, s)).reduce("", String::concat);
    }

    public static void writeYTT(ArrayList<Paragraph> input, File output) throws IOException{
        //PrintStream pw = System.out;
        PrintWriter pw = new PrintWriter(output, StandardCharsets.UTF_8);
        ArrayList<WindowPosition> wpList = new ArrayList<>();
        ArrayList<PenStyle> penList = new ArrayList<>();
        StringBuilder body = new StringBuilder();
        for(Paragraph p : input){
            ArrayList<String> lines = p.getLines();
            ArrayList<StyleState> styles = p.getStyles();
            body.append(p.markupTiming());
            body.append(" wp=\"");
            int wpID = getID(styles.get(0).windowPosition(), wpList);
            body.append(wpID);
            body.append("\"><s></s><s p=\"");
            int penID = getID(styles.get(0).penStyle(), penList);
            body.append(penID);
            body.append("\">");
            body.append(markupSafe(lines.get(0)));
            for(int i=1; i<lines.size(); i++){
                body.append("</s>");
                int prevWP = wpID;
                wpID = getID(styles.get(i).windowPosition(), wpList);
                penID = getID(styles.get(i).penStyle(), penList);
                if(wpID != prevWP){
                    body.append("</p>\n");
                    body.append(p.markupTiming());
                    body.append(" wp=\"");
                    body.append(wpID);
                    body.append("\"><s></s>");
                }
                body.append("<s p=\"");
                body.append(penID);
                body.append("\">");
                body.append(markupSafe(lines.get(i)));
            }
            body.append("</s></p>\n");
        }
        //Prints with "\n" rather than Println are used to ensure Unix newline standard.
        pw.print("<?xml version=\"1.0\" encoding=\"utf-8\" ?><timedtext format=\"3\">\n<head>\n");
        for(int i=0; i<wpList.size(); i++){
            pw.print(wpList.get(i).printMarkup(i+1) + "\n");
        }
        for(int i=0; i<penList.size(); i++){
            pw.print(penList.get(i).printMarkup(i+1) + "\n");
        }
        pw.print("</head>\n<body>\n");
        pw.print(body);
        pw.print("</body>\n</timedtext>\n");
        pw.close();
    }
}