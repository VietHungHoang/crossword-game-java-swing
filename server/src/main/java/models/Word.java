package models;

import java.io.Serializable;

public class Word  implements Serializable {
    private Long id;
    private String word;

    public Word(Long id, String word) {
        this.id = id;
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getWordName() {
        return word;
    }

    public void setWordName(String wordName) {
        this.word = wordName;
    }
}

