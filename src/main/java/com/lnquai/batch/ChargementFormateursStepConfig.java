package com.lnquai.batch;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.lnquai.batch.domaine.Formateur;

//Classe de configuration pour la step de chargement des formateurs
@Configuration
public class ChargementFormateursStepConfig {
	// Implementation du bean ItemReader avec type lu Formateur.Pour lire la liste
	// de formateurs
	// il faut accéder au fichier grâce à l'objet Resource.On va utiliser le
	// jobParameters pour accéder au chemin de ce fichier là.
	// Spring va récupérer le chemin vers ce fichier via le jobParameter et va
	// instancier un bean de type Resource et le mettre à disposition de cette
	// méthode
	// On va créer un flatfileitemreader en utilisant un builder.
	// On commence d'abord par définir son nom, puis définir d'où il lira les lignes
	// liées au formateur
	// On va lire le fichier CSV delimitée par des points ; donc on va le dire à
	// Spring
	// Puis les noms de colonnes,la classe cible puis le build
	// Attention , tout bean qui doit accéder au jobParameter doit être annotée par
	// StepScope. Le bean ne sera créé qu'à l'intérieur de la scope et à ce moment
	// là le jobParamater est mise
	// à disposition du bean pour sa construction
	@Bean
	@StepScope
	public FlatFileItemReader<Formateur> formateurItemReader(
			@Value("#{jobParameters['formateursFile']}") final Resource inputFile) {
		return new FlatFileItemReaderBuilder<Formateur>().name("FormateurItemReader").resource(inputFile).delimited()
				.delimiter(";").names(new String[] { "id", "nom", "prenom", "adresseEmail" })
				.targetType(Formateur.class).build();
	}

	@Bean
	public ItemWriter<Formateur> formateurItemWriter() {
		return (items) -> items.forEach(System.out::println);
	}

	// Une fois ce step créé, il faut la déclarer dans le job
	@Bean
	public Step chargementFormateursStep(final StepBuilderFactory builderFactory) {
		return builderFactory.get("chargementFormateursStep").<Formateur, Formateur>chunk(10)
				.reader(formateurItemReader(null)).writer(formateurItemWriter()).build();
	}

//	@Bean
//	public JdbcBatchItemWriter<Formateur> formateurItemWriter(final DataSource dataSource) {
//		return new JdbcBatchItemWriterBuilder<Formateur>().dataSource(dataSource).sql(FORMATEURS_INSERT_QUERY)
//				.itemPreparedStatementSetter(new FormateurItemPreparedStatementSetter()).build();
//	}

	// Une fois ce step créé, il faut la déclarer dans le job
//	@Bean
//	public Step chargementFormateursStep(final StepBuilderFactory builderFactory) {
//		return builderFactory.get("chargementFormateursStep").<Formateur, Formateur>chunk(10)
//				.reader(formateurItemReader(null)).writer(formateurItemWriter(null)).build();
//	}

}
