package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
    private final StringProperty name;

    public Person(String nm) {
        this.name = new SimpleStringProperty(nm);
    }

    public String getLastName(){
        return name.get();
    }
}
