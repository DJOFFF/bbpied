package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DeltaPoint {
    private final IntegerProperty delta;
    private final IntegerProperty xaxis;

    public DeltaPoint(int dta, int axis){
        this.delta = new SimpleIntegerProperty(dta);
        this.xaxis = new SimpleIntegerProperty(axis);
    }

    public int getDelta(){
        return delta.get();
    }

    public int getXaxis(){
        return xaxis.get();
    }
}
