package com.omer.spring.batch.config;

import com.omer.spring.batch.entity.AbcAnalysisCloud;
import com.omer.spring.batch.listener.StepSkipListener;
import com.omer.spring.batch.repository.AbcAnalysisRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.io.File;

@Configuration
@EnableBatchProcessing
//@AllArgsConstructor
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private AbcAnalysisRepository customerRepository;
    @Autowired
    private AbcItemWriter customerItemWriter;

    @Bean
    @StepScope
    public PoiItemReader<AbcAnalysisCloud> itemReader(@Value("#{jobParameters[fullPathFileName]}") String pathToFIle) {
        PoiItemReader<AbcAnalysisCloud> reader = new PoiItemReader<>();
        reader.setName("excel-Reader");
        reader.setLinesToSkip(1);
        reader.setResource(new FileSystemResource(new File(pathToFIle)));
        reader.setRowMapper(excelRowMapper());
        return reader;
    }

//   private RowMapper<StudentDTO> excelRowMapper() {
//        BeanWrapperRowMapper<StudentDTO> rowMapper = new BeanWrapperRowMapper<>();
//        rowMapper.setTargetType(StudentDTO.class);
//        return rowMapper;
//    }

    /**
     * If your Excel document has no header, you have to create a custom
     * row mapper and configure it here.
     */
    private RowMapper<AbcAnalysisCloud> excelRowMapper() {
       return new AbcAnalysisExcelRowMapper();
    }

    @Bean
    public AbcProcessor processor() {
        return new AbcProcessor();
    }

    @Bean
    public RepositoryItemWriter<AbcAnalysisCloud> writer() {
        RepositoryItemWriter<AbcAnalysisCloud> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName("save");
        return writer;
    }


    @Bean
    public Step step1(ItemReader<AbcAnalysisCloud> itemReader) {
        return stepBuilderFactory.get("slaveStep").<AbcAnalysisCloud, AbcAnalysisCloud>chunk(10)
                .reader(itemReader)
                .processor(processor())
                .writer(customerItemWriter)
                .faultTolerant()
                .listener(skipListener())
                .taskExecutor(taskExecutor())
                .build();
    }


    @Bean
    public Job runJob(ItemReader<AbcAnalysisCloud> itemReader) {
        return jobBuilderFactory.get("importCustomer")
                .flow(step1(itemReader))
                .end()
                .build();
    }

    @Bean
    public SkipListener skipListener() {
        return new StepSkipListener();
    }
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }


}
