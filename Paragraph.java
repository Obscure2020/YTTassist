import java.util.*;

public class Paragraph {

    private String manuscript;
    private ArrayList<String> lines;
    private ArrayList<StyleState> styles;

    public Paragraph(){
        manuscript = "";
        lines = new ArrayList<>();
        styles = new ArrayList<>();
    }

    public void debugPrint(){
        System.out.println(" Paragraph Debug: ");
        System.out.println("==================");
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
            if(pieces[i].equals("[")){
                if(styleMode){
                    throw new IllegalArgumentException("Invalid use of a second [ before a closing ].");
                } else {
                    lines.add(sb.toString());
                    sb.setLength(0);
                    styleMode = true;
                }
                continue;
            }
            if(pieces[i].equals("]")){
                if(styleMode){
                    styles.add(style);
                    style = new StyleState(style);
                    styleMode = false;
                } else {
                    sb.append(pieces[i]);
                }
                continue;
            }
            if(styleMode){
                if(pieces[i].charAt(0) == ',') pieces[i] = pieces[i].substring(1);
                if(pieces[i].charAt(0) == '!'){
                    if(pieces[i].length() < 3) throw new IllegalArgumentException("Opcode too short.");
                    String opcode = pieces[i].substring(1,3);
                    if(opcode.equals("bo")){
                        if(pieces[i].length() > 3) throw new IllegalArgumentException("Invalid provision of extra characters in a \"!bo\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetBold();
                    } else if (opcode.equals("it")){
                        if(pieces[i].length() > 3) throw new IllegalArgumentException("Invalid provision of extra characters in an \"!it\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetItalics();
                    } else if(opcode.equals("un")){
                        if(pieces[i].length() > 3) throw new IllegalArgumentException("Invalid provision of extra characters in a \"!un\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetUnderline();
                    }
                } else {
                    if(pieces[i].length() < 2) throw new IllegalArgumentException("Opcode too short.");
                    String opcode = pieces[i].substring(0,2);
                    if(opcode.equals("bo")){
                        if(pieces[i].length() > 2) throw new IllegalArgumentException("Invalid provision of extra characters in a \"bo\" command.");
                        PenStyle ps = style.penStyle();
                        ps.setBold();
                    } else if(opcode.equals("it")){
                        if(pieces[i].length() > 2) throw new IllegalArgumentException("Invalid provision of extra characters in an \"it\" command.");
                        PenStyle ps = style.penStyle();
                        ps.setItalics();
                    } else if(opcode.equals("un")){
                        if(pieces[i].length() > 2) throw new IllegalArgumentException("Invalid provision of extra characters in a \"un\" command.");
                        PenStyle ps = style.penStyle();
                        ps.setUnderline();
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
}