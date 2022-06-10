public class StyleState {

    private WindowPosition wp;
    private PenStyle pen;

    public StyleState(){ //Initialize members to default settings
        wp = new WindowPosition();
        pen = new PenStyle();
    }

    public StyleState(StyleState other){ //Copy over settings of members
        wp = new WindowPosition(other.wp);
        pen = new PenStyle(other.pen);
    }

    public WindowPosition windowPosition(){ //Expose wp for use
        return wp;
    }

    public PenStyle penStyle(){ //Expose pen for use
        return pen;
    }

    public void debugPrint(){
        wp.debugPrint();
        pen.debugPrint();
    }
}