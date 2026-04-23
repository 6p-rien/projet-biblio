package fr.formation.model;

public enum Genre {
	Aventure("Aventure"),Fantasie("Fantasie"),Romance("Romance"),Sciencefiction("Science-fiction"),Policier("Policier"),Moderne("Moderne");
	
	private String libelle;
	
	private Genre(String libelle) {
		this.libelle =libelle;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
}
