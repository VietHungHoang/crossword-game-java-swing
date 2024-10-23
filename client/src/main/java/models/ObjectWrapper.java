package models;

import java.io.Serializable;

public class ObjectWrapper implements Serializable {

    private String identifier;
    private Object object;

    public ObjectWrapper() {
    }

    public ObjectWrapper(String identifier, Object object) {
        this.identifier = identifier;
        this.object = object;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getStatus(){
        return this.identifier.split(";")[1];
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}