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
                    char opcode = pieces[i].charAt(1);
                    if(opcode == 'b'){
                        if(pieces[i].length() > 2) throw new IllegalArgumentException("Invalid provision of extra characters in a \"!b\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetBold();
                    } else if (opcode == 'i'){
                        if(pieces[i].length() > 2) throw new IllegalArgumentException("Invalid provision of extra characters in an \"!i\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetItalics();
                    } else if(opcode == 'u'){
                        if(pieces[i].length() > 2) throw new IllegalArgumentException("Invalid provision of extra characters in a \"!u\" command.");
                        PenStyle ps = style.penStyle();
                        ps.resetUnderline();
                    }
                } else {
                    char opcode = pieces[i].charAt(0);
                    if(opcode == 'b'){
                        if(pieces[i].length() > 1) throw new IllegalArgumentException("Invalid provision of extra characters in a \"b\" command.");
                        PenStyle ps = style.penStyle();
                        ps.setBold();
                    } else if(opcode == 'i'){
                        if(pieces[i].length() > 1) throw new IllegalArgumentException("Invalid provision of extra characters in an \"i\" command.");
                        PenStyle ps = style.penStyle();
                        ps.setItalics();
                    } else if(opcode == 'u'){
                        if(pieces[i].length() > 1) throw new IllegalArgumentException("Invalid provision of extra characters in a \"u\" command.");
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