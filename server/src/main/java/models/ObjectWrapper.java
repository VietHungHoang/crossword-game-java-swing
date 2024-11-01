package models;

import java.io.Serializable;

public class ObjectWrapper implements Serializable {
    private String identifier;
    private Object object;
    private long elapsedTime; // Thêm trường elapsedTime để lưu thời gian đã trôi qua
  
    public ObjectWrapper() {
    }

    public ObjectWrapper(String identifier, Object object) {
        this.identifier = identifier;
        this.object = object;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getStatus() {
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

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
