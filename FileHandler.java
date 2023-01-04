import java.util.*;
import java.util.stream.*;
import java.io.*;

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
}