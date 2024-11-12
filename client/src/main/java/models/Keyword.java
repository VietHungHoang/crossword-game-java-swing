package models;

import java.io.Serializable;

public class Keyword implements  Serializable{
    private Long id;
    private String value;
    private static final long serialVersionUID = 1L;
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

    public Keyword(Long id, String value) {
        this.id = id;
        this.value = value;
    }
    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }

}

