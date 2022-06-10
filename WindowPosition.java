public class WindowPosition {

    private int anchorPosition;
    // 0 - Top Left    | 1 - Top Center    | 2 - Top Right
    // 3 - Center Left | 4 - True Center   | 5 - Center Right
    // 6 - Bottom Left | 7 - Bottom Center | 8 - Bottom Right
    private int alignHorizontal; // Percentage from left
    private int alignVertical; // Percentage from top

    public WindowPosition(){ //Set all values to default state
        anchorPosition = 7;
        alignHorizontal = 50;
        alignVertical = 100;
    }

    public WindowPosition(WindowPosition other){ //Copy over all values
        anchorPosition = other.anchorPosition;
        alignHorizontal = other.alignHorizontal;
        alignVertical = other.alignVertical;
    }

    public boolean equals(WindowPosition other){
        if(anchorPosition != other.anchorPosition) return false;
        if(alignHorizontal != other.alignHorizontal) return false;
        if(alignVertical != other.alignVertical) return false;
        return true;
    }

    public void debugPrint(){
        System.out.println("WindowPositon: ap" + anchorPosition + ", ah" + alignHorizontal + ", av" + alignVertical);
    }

    public String printMarkup(int index){
        StringBuilder sb = new StringBuilder("<wp id=\"");
        sb.append(index);
        sb.append("\" ap=\"");
        sb.append(anchorPosition);
        sb.append("\" ah=\"");
        sb.append(alignHorizontal);
        sb.append("\" av=\"");
        sb.append(alignVertical);
        sb.append("\"/>");
        return sb.toString();
    }

    public void setAnchorPosition(int input) throws IndexOutOfBoundsException {
        if(input < 0 || input > 8){
            StringBuilder sb = new StringBuilder("\"");
            sb.append(input);
            sb.append("\" is not a valid Anchor Position. ");
            if(input < 0){
                sb.append("Minimum value is 0 (Top Left).");
            } else {
                sb.append("Maximum value is 8 (Bottom Right).");
            }
            throw new IndexOutOfBoundsException(sb.toString());
        }
        anchorPosition = input;
    }

    public void resetAnchorPosition(){
        anchorPosition = 7;
    }

    public void setAlignHorizontal(int input){
        alignHorizontal = input;
    }

    public void resetAlignHorizontal(){
        alignHorizontal = 50;
    }

    public void setAlignVertical(int input){
        alignVertical = input;
    }

    public void resetAlignVertical(){
        alignVertical = 100;
    }

}