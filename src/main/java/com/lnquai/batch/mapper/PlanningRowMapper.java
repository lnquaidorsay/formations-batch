package com.lnquai.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lnquai.batch.domaine.Formateur;
import com.lnquai.batch.domaine.Planning;

public class PlanningRowMapper implements RowMapper<Planning> {
	@Override
	public Planning mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		Planning planning = new Planning();
		Formateur formateur = new Formateur();
		formateur.setId(rs.getInt("id"));
		formateur.setNom(rs.getString("nom"));
		formateur.setPrenom(rs.getString("prenom"));
		formateur.setAdresseEmail(rs.getString("adresse_email"));
		planning.setFormateur(formateur);
		return planning;
	}
}
