import java.util.InputMismatchException;

public class PenStyle {

    private boolean bold;
    private boolean italics;
    private boolean underline;
    private int fontColor;

    public PenStyle(){ //Set all values to default state
        bold = false;
        italics = false;
        underline = false;
        fontColor = Integer.parseInt("FFFFFF", 16);
    }

    public PenStyle(PenStyle other){ //Copy over all values
        bold = other.bold;
        italics = other.italics;
        underline = other.underline;
        fontColor = other.fontColor;
    }

    public boolean equals(Object other){
        if(!(other instanceof PenStyle)) return false;
        PenStyle brother = (PenStyle) other;
        if(bold != brother.bold) return false;
        if(italics != brother.italics) return false;
        if(underline != brother.underline) return false;
        if(fontColor != brother.fontColor) return false;
        return true;
    }

    public void debugPrint(){
        System.out.print("PenStyle:");
        if(bold) System.out.print(" bold");
        if(italics) System.out.print(" italics");
        if(underline) System.out.print(" underline");
        StringBuilder sb = new StringBuilder(Integer.toString(fontColor, 16).toUpperCase());
        while(sb.length() < 6) sb.insert(0, "0");
        sb.insert(0, " fc=");
        System.out.println(sb);
    }

    public String printMarkup(int index){
        StringBuilder sb = new StringBuilder("<pen id=\"");
        sb.append(index);
        sb.append('"');
        if(bold) sb.append(" b=\"1\"");
        if(italics) sb.append(" i=\"1\"");
        if(underline) sb.append(" u=\"1\"");
        sb.append(" fc=\"#");
        StringBuilder sb2 = new StringBuilder(Integer.toString(fontColor, 16).toUpperCase());
        while(sb2.length() < 6) sb2.insert(0, "0");
        sb.append(sb2);
        sb.append("\"/>");
        return sb.toString();
    }

    public void setBold(){
        bold = true;
    }

    public void resetBold(){
        bold = false;
    }

    public void setItalics(){
        italics = true;
    }

    public void resetItalics(){
        italics = false;
    }

    public void setUnderline(){
        underline = true;
    }

    public void resetUnderline(){
        underline = false;
    }

    public void setFontColor(String input) throws InputMismatchException, NumberFormatException{
        if(input.length() != 6){
            StringBuilder sb = new StringBuilder("\"");
            sb.append(input);
            sb.append("\" is not a valid Color. Colors must be six hexadecimal digits.");
            throw new InputMismatchException(sb.toString());
        }
        int newColor = Integer.parseInt(input, 16);
        if(newColor < 0){
            StringBuilder sb = new StringBuilder("\"");
            sb.append(input);
            sb.append("\" is not a valid Color. Colors must be between 000000 and FFFFFF.");
            throw new InputMismatchException(sb.toString());
        }
        fontColor = newColor;
    }

    public void resetFontColor(){
        fontColor = Integer.parseInt("FFFFFF", 16);
    }
}