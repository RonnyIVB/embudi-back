package com.satgy.embudi.product.service;

import com.satgy.embudi.product.model.Sequence;

import java.util.Optional;

public interface SequenceServiceI {
    public Integer getValueInt(String strSequence);
    public String getValueStr(String strSequence);
    public void increase(String strSequence);
    public Optional<Sequence> findById(Long id);
    public Sequence create(Sequence sequence);
    public Sequence update(Sequence sequence);
    public void delete(Long id);
}
