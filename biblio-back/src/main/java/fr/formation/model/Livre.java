package fr.formation.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @Size(min = 1, max = 100, message = "Le titre doit faire entre 1 et 100 caractères")
    @NotBlank(message = "Le titre ne peut pas être vide")
    private String titre;

    @Column(nullable = true, length = 256)
    private String resume;

    @Column(nullable = true)
    @PositiveOrZero
    private int annee;

    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    private Auteur auteur;

    @ManyToOne
    @JoinColumn(name = "editeur_id", nullable = false)
    private Editeur editeur;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = true)
    private Collec collection;

    @ManyToMany
    @JoinTable(
        name = "livre_genre",
        joinColumns = @JoinColumn(name = "livre_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    public Livre() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getResume() { return resume; }
    public void setResume(String resume) { this.resume = resume; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public Auteur getAuteur() { return auteur; }
    public void setAuteur(Auteur auteur) { this.auteur = auteur; }

    public Editeur getEditeur() { return editeur; }
    public void setEditeur(Editeur editeur) { this.editeur = editeur; }

    public Collec getCollection() { return collection; }
    public void setCollection(Collec collection) { this.collection = collection; }

    public List<Genre> getGenres() { return genres; }
    public void setGenres(List<Genre> genres) { this.genres = genres; }

    @Override
    public String toString() {
        return "Livre [id=" + id + ", titre=" + titre + ", annee=" + annee + "]";
    }
}

