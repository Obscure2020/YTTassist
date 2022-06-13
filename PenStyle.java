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
        fontColor = -1;
    }

    public PenStyle(PenStyle other){ //Copy over all values
        bold = other.bold;
        italics = other.italics;
        underline = other.underline;
        fontColor = other.fontColor;
    }

    public boolean equals(PenStyle other){
        if(bold != other.bold) return false;
        if(italics != other.italics) return false;
        if(underline != other.underline) return false;
        if(fontColor != other.fontColor) return false;
        return true;
    }

    public boolean isDefault(){ //If text is to use default pen styling, a pen isn't actually necessary. This is the check for unnecessarity.
        if(bold) return false;
        if(italics) return false;
        if(underline) return false;
        if(fontColor != -1) return false;
        return true;
    }

    public void debugPrint(){
        if(isDefault()){
            System.out.println("Default pen.");
        } else {
            System.out.print("PenStyle:");
            if(bold) System.out.print(" bold");
            if(italics) System.out.print(" italics");
            if(underline) System.out.print(" underline");
            if(fontColor != -1) System.out.print(" fc=" + Integer.toString(fontColor, 16).toUpperCase());
            System.out.println();
        }
    }

    public String printMarkup(int index){
        StringBuilder sb = new StringBuilder("<pen id=\"");
        sb.append(index);
        sb.append('"');
        if(bold) sb.append(" b=\"1\"");
        if(italics) sb.append(" i=\"1\"");
        if(underline) sb.append(" u=\"1\"");
        if(fontColor != -1){
            sb.append(" fc=\"#");
            sb.append(Integer.toString(fontColor, 16).toUpperCase());
            sb.append('"');
        }
        sb.append("/>");
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
        fontColor = -1;
    }
}