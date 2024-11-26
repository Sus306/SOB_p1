package model.entities;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

import jakarta.validation.constraints.Size;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.util.Date;
import java.util.List;


@Entity
@XmlRootElement
public class Articles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY) // Relación con el autor
    @JoinColumn(name = "author_id", nullable = false)
    private Usuaris author;

    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    @Size(max = 500) // Máximo de 500 palabras para el contenido completo
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;

    @NotNull
    private int views;

    @ElementCollection
    @CollectionTable(name = "article_topics", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "topic")
    private List<String> topics;

    @NotNull
    private boolean isPublic;

    // Constructor vacío requerido por JPA
    public Articles() {}

    // Constructor para facilitar la creación de artículos
    public Articles(Usuaris author, String title, String content, List<String> topics, boolean isPublic) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.topics = topics;
        this.isPublic = isPublic;
        this.publicationDate = new Date(); // Fecha actual como fecha de publicación
        this.views = 0; // Inicializamos las visualizaciones a cero
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public Usuaris getAuthor() {
        return author;
    }

    public void setAuthor(Usuaris author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public int getViews() {
        return views;
    }

    public void incrementViews() {
        this.views++;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", author=" + author.getUsername() + // Mostrar solo el nombre de usuario del autor
                ", title='" + title + '\'' +
                ", publicationDate=" + publicationDate +
                ", views=" + views +
                ", topics=" + topics +
                ", isPublic=" + isPublic +
                '}';
    }
}
