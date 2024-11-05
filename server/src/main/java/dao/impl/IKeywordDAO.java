package dao.impl;

import models.Keyword;

import java.util.List;

public interface IKeywordDAO {
    public Keyword findById(Long id);
    public int countAll();
}
