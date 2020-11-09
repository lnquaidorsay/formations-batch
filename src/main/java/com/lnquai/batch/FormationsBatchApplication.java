package com.lnquai.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FormationsBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormationsBatchApplication.class, args);
		// ascii art generator sur google permet de changer le banner de spring boot
		// prendre le premier lien
		// http://patorjk.com/software/taag/#p=testall&f=Graffiti&t=formations-batch
		// Après avoir saisi le texte il faut cliquer sur Test All puis faire Select &
		// Copy sur le modèle souhaité
		// Mettre un fichier banner.txt au niveau du dossier resources
		// Spring profil : https://www.baeldung.com/spring-profiles ;
		// https://dzone.com/articles/spring-boot-profiles-1
	}

}
