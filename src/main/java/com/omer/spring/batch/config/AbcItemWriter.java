package com.omer.spring.batch.config;

import com.omer.spring.batch.entity.AbcAnalysisCloud;
import com.omer.spring.batch.repository.AbcAnalysisRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AbcItemWriter implements ItemWriter<AbcAnalysisCloud> {

    @Autowired
    private AbcAnalysisRepository repository;

    @Override
    public void write(List<? extends AbcAnalysisCloud> list) throws Exception {
        System.out.println("Writer Thread "+Thread.currentThread().getName());
        repository.saveAll(list);
    }
}
