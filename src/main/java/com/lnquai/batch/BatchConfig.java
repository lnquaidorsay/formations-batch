package com.lnquai.batch;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lnquai.batch.validators.MyJobParametersValidator;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Bean
	public JobParametersValidator defaultJobParametersValidator() {
		DefaultJobParametersValidator bean = new DefaultJobParametersValidator();
		bean.setRequiredKeys(new String[] { "formateursFile", "formationsFile", "seancesFile" });
		bean.setOptionalKeys(new String[] { "run.id" });// Permet d'eviter l'erreur d'executer plusieurs fois un batch
		return bean;
	}

	@Bean
	public JobParametersValidator myJobParametersValidator() {
		return new MyJobParametersValidator();
	}

	@Bean
	public JobParametersValidator compositeJobParametersValidator() {
		CompositeJobParametersValidator bean = new CompositeJobParametersValidator();
		bean.setValidators(Arrays.asList(defaultJobParametersValidator(), myJobParametersValidator()));
		return bean;
	}

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
				System.out.println("Hello ");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Job job(final JobBuilderFactory jobBuilderFactory, final Step chargementFormateursStep) {
		return jobBuilderFactory.get("formations-batch").start(chargementFormateursStep)
				.validator(compositeJobParametersValidator()).incrementer(new RunIdIncrementer()).build();
	}

}
