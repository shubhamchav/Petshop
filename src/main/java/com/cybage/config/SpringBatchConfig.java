package com.cybage.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.cybage.daos.DemoRepository;
import com.cybage.entities.Demo;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DemoRepository repository;
	
	@Bean
	public FlatFileItemReader<Demo> reader(){
		FlatFileItemReader<Demo>itemReader=new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("src/main/resources/demo.csv"));
		itemReader.setName("csvReader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}

	private LineMapper<Demo> lineMapper() {
		DefaultLineMapper<Demo>lineMapper=new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("itemCategoryId","itemCategoryName","itemCategoryImage","itemCategoryDescription");
		BeanWrapperFieldSetMapper<Demo>fieldSetMapper=new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Demo.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		return lineMapper;
	}

	@Bean
	public DemoProcessor processor() {
		return new DemoProcessor();
	}
	@Bean
	public RepositoryItemWriter<Demo>writer(){
		RepositoryItemWriter<Demo>writer=new RepositoryItemWriter<>();
		writer.setRepository(repository);
		writer.setMethodName("save");
		return writer;
	}
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("csv-step").<Demo,Demo>chunk(10)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
		
	}
	@Bean
	public Job runJob() {
		return jobBuilderFactory.get("importDemos")
				.flow(step1()).end().build();
		}
}
