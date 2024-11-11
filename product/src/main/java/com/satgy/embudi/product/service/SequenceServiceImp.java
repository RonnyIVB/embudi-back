package com.satgy.embudi.product.service;

import com.satgy.embudi.product.general.Str;
import com.satgy.embudi.product.model.Sequence;
import com.satgy.embudi.product.repository.SequenceRepositoryI;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SequenceServiceImp implements SequenceServiceI {

    @Autowired
    private SequenceRepositoryI repo;

    /**
     * // Get or create the sequence
     * @param strSequence strSequence is like this: 'nameSequence.15'
     * @return the sequence
     */
    @Transactional
    private Sequence getOrInsertSequence(String strSequence){
        String description = strSequence.split("\\.")[0];
        Integer length = Integer.parseInt(strSequence.split("\\.")[1]);
        Optional<Sequence> os = repo.findByDescription(description);
        if (os.isPresent()) {
            return os.get();
        } else {
            // If the sequence doesn't exist so create it
            Sequence sequence = new Sequence(1, description, length);
            repo.save(sequence);
            return sequence;
        }
    }

    @Override
    public Integer getValueInt(String strSequence) {
        return getOrInsertSequence(strSequence).getValue();
    }

    @Override
    public String getValueStr(String strSequence) {
        Sequence sequence = getOrInsertSequence(strSequence);
        return Str.ceros(sequence.getValue(), sequence.getLength());
    }

    @Override
    @Transactional
    public void increase(String strSequence) {
        Sequence sequence = getOrInsertSequence(strSequence);
        sequence.setValue(sequence.getValue() + 1);
        update(sequence);
    }

    @Override
    public Optional<Sequence> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Sequence create(Sequence sequence) {
        return repo.save(sequence);
    }

    @Override
    @Transactional
    public Sequence update(Sequence sequence) {
        return repo.save(sequence);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
