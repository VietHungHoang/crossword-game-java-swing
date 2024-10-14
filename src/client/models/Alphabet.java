package client.models;

import java.io.Serializable;

public class Alphabet implements  Serializable{
    private Long id;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Alphabet(Long id, String value) {
        this.id = id;
        this.value = value;
    }
}

