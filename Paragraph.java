import java.util.*;
import java.time.Duration;

public class Paragraph {

    private String manuscript;
    private ArrayList<String> lines;
    private ArrayList<StyleState> styles;
    private Duration startStamp;
    private Duration endStamp;

    public Paragraph(String startSRT, String endSRT){
        manuscript = "";
        lines = new ArrayList<>();
        styles = new ArrayList<>();
        startStamp = parseSRTstamp(startSRT);
        endStamp = parseSRTstamp(endSRT);
    }

    private Duration parseSRTstamp(String input){
        StringBuilder sb = new StringBuilder(20);
        sb.append("PT");
        sb.append(input);
        sb.replace(4, 5, "H");
        sb.replace(7, 8, "M");
        sb.replace(10, 11, ".");
        sb.append('S');
        return Duration.parse(sb);
    }

    private String formatSRTstamp(Duration input){
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

    public void debugPrint(){
        System.out.println(" Paragraph Debug: ");
        System.out.println("==================");
        System.out.println();
        System.out.print("start time = ");
        System.out.println(formatSRTstamp(startStamp));
        System.out.print("  end time = ");
        System.out.println(formatSRTstamp(endStamp));
        System.out.println();
        System.out.println("manuscript = ");
        System.out.println("---------------------");
        System.out.println(manuscript);
        System.out.println("---------------------");
        System.out.println();
        for(int i=0; i<lines.size(); i++){
            System.out.println("[BEGIN]" + lines.get(i) + "[END]");
            styles.get(i).debugPrint();
            System.out.println();
        }
    }

    public void newManuscript(String input) throws IllegalArgumentException{
        String[] pieces = input.split("(?<=\\[)|(?=\\[)|(?<=\\])|(?=\\])|(?=,)");
        boolean styleMode = false;
        StringBuilder sb = new StringBuilder();
        StyleState style = new StyleState();
        if(pieces[0].equals("[")){
            styleMode = true;
        } else {
            styles.add(new StyleState());
            sb.append(pieces[0]);
        }
        for(int i=1; i<pieces.length; i++){
            String p = pieces[i];
            if(p.equals("[")){
                if(styleMode){
                    locationError(input, pieces, i, 0, "Invalid use of a second [ before a closing ].");
                } else {
                    lines.add(sb.toString());
                    sb.setLength(0);
                    styleMode = true;
                }
                continue;
            }
            if(p.equals("]")){
                if(styleMode){
                    styles.add(style);
                    style = new StyleState(style);
                    styleMode = false;
                } else {
                    sb.append(p);
                }
                continue;
            }
            if(styleMode){
                int commaOffset = 0;
                if(p.charAt(0) == ','){
                    p = p.substring(1);
                    commaOffset = 1;
                }
                if(p.charAt(0) == '!'){
                    if(p.length() < 3) locationError(input, pieces, i, 1+commaOffset, "Opcode too short.");;
                    String opcode = p.substring(1,3);
                    if(opcode.equals("bo")){
                        if(p.length() > 3) locationError(input, pieces, i, 3+commaOffset, "Invalid provision of extra characters in a \"!bo\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetBold();
                    } else if (opcode.equals("it")){
                        if(p.length() > 3) locationError(input, pieces, i, 3+commaOffset, "Invalid provision of extra characters in an \"!it\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetItalics();
                    } else if(opcode.equals("un")){
                        if(p.length() > 3) locationError(input, pieces, i, 3+commaOffset, "Invalid provision of extra characters in a \"!un\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetUnderline();
                    } else if(opcode.equals("fc")){
                        if(p.length() > 3) locationError(input, pieces, i, 3+commaOffset, "Invalid provision of extra characters in an \"!fc\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetFontColor();
                    } else if(opcode.equals("ap")){
                        if(p.length() > 3) locationError(input, pieces, i, 3+commaOffset, "Invalid provision of extra characters in an \"!ap\" command.");
                        WindowPosition wp = style.windowPosition();
                        wp.resetAnchorPosition();
                        wp.resetAlignVertical();
                        wp.resetAlignHorizontal();
                    } else {
                        locationError(input, pieces, i, 1+commaOffset, "Invalid opcode.");
                    }
                } else {
                    if(p.length() < 2) locationError(input, pieces, i, commaOffset, "Opcode too short.");
                    String opcode = p.substring(0,2);
                    if(opcode.equals("bo")){
                        if(p.length() > 2) locationError(input, pieces, i, 2+commaOffset, "Invalid provision of extra characters in a \"bo\" command.");
                        PenStyle ps = style.penStyle();
                        ps.setBold();
                    } else if(opcode.equals("it")){
                        if(p.length() > 2) locationError(input, pieces, i, 2+commaOffset, "Invalid provision of extra characters in an \"it\" command.");
                        PenStyle ps = style.penStyle();
                        ps.setItalics();
                    } else if(opcode.equals("un")){
                        if(p.length() > 2) locationError(input, pieces, i, 2+commaOffset, "Invalid provision of extra characters in a \"un\" command.");
                        PenStyle ps = style.penStyle();
                        ps.setUnderline();
                    } else if(opcode.equals("fc")){
                        PenStyle ps = style.penStyle();
                        try {
                            ps.setFontColor(p.substring(2));
                        } catch (Exception e){
                            locationError(input, pieces, i, 2+commaOffset, e.getMessage());
                        }
                    } else if(opcode.equals("ap")){
                        WindowPosition wp = style.windowPosition();
                        try{
                            int point = Integer.valueOf(p.substring(2));
                            wp.setAnchorPosition(point);
                            int[] av = {0, 0, 0, 50, 50, 50, 100, 100, 100};
                            int[] ah = {0, 50, 100, 0, 50, 100, 0, 50, 100};
                            wp.setAlignVertical(av[point]);
                            wp.setAlignHorizontal(ah[point]);
                        } catch (Exception e){
                            locationError(input, pieces, i, 2+commaOffset, e.getMessage());
                        }
                    } else {
                        locationError(input, pieces, i, commaOffset, "Invalid opcode.");
                    }
                }
            } else {
                sb.append(pieces[i]);
            }
        }
        if(!styleMode && !sb.isEmpty()) lines.add(sb.toString());
        if(styles.size() > lines.size()) styles.remove(styles.size()-1);
        manuscript = input;
    }

    private void locationError(String input, String[] pieces, int index, int n, String reason) throws IllegalArgumentException{
        int end = 0;
        for(int i=0; i<index; i++) end += pieces[i].length();
        StringBuilder arrow = new StringBuilder(end);
        for(int i=0; i<end+n; i++) arrow.append('-');
        StringBuilder source = new StringBuilder(input.substring(0,end));
        while(source.lastIndexOf("\n")>=0){
            source.delete(0, 1);
            arrow.deleteCharAt(0);
        }
        if(source.length() > 35){
            while(source.length() > 35){
                source.delete(0, 1);
                arrow.deleteCharAt(0);
            }
            source.insert(0, "...");
            arrow.insert(0, "---");
        }
        source.append(pieces[index]);
        int preEndPad = source.length();
        int k = 1;
        while(source.length()-preEndPad<35 && index+k<pieces.length){
            source.append(pieces[index+k]);
            k++;
        }
        if(source.length()-preEndPad>35 || index+k<pieces.length){
            while(source.length()-preEndPad>35) source.delete(source.length()-1, source.length());
            source.append("...");
        }
        arrow.append("^ HERE");

        StringBuilder result = new StringBuilder(reason);
        result.append("\n\t=====Location=====\n\t");
        result.append(source);
        result.append("\n\t");
        result.append(arrow);
        throw new IllegalArgumentException(result.toString());
    }

    public String markupTiming(){
        long start = startStamp.toMillis();
        long length = endStamp.toMillis() - start;
        StringBuilder sb = new StringBuilder("<p t=\"");
        sb.append(start);
        sb.append("\" d=\"");
        sb.append(length);
        sb.append('"');
        return sb.toString();
    }

    public ArrayList<String> getLines(){
        return lines;
    }

    public ArrayList<StyleState> getStyles(){
        return styles;
    }
}