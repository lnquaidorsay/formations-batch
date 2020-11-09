package com.lnquai.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import com.lnquai.batch.domaine.Formateur;

public class ChargementFormateursStepConfig {
	@Bean
	@StepScope
	public FlatFileItemReader<Formateur> formateurItemReader(
			@Value("#{jobParameters['formateursFile']}") final Resource inputFile) {
		return new FlatFileItemReaderBuilder<Formateur>().name("FormateurItemReader").resource(inputFile).delimited()
				.delimiter(";").names(new String[] { "id", "nom", "prenom", "adresseEmail" })
				.targetType(Formateur.class).build();
	}

}
