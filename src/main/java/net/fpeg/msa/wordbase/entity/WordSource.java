package net.fpeg.msa.wordbase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "word_source")
@NamedEntityGraph(name = "WordSource.details", attributeNodes = {@NamedAttributeNode("wordSourceChild")})
public class WordSource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("key")
    Long wordSourceId;

    @JsonProperty("title")
    String wordSourceValue;

    @JsonProperty("children")
    @OneToMany(mappedBy = "wordSourceParent", fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("wordSourceValue ASC")
    Set<WordSource> wordSourceChild;

    @ManyToOne
    @JoinColumn(name = "word_source_parent_id")
    @JsonIgnore
    WordSource wordSourceParent;

    @JsonIgnore
    @OneToMany(mappedBy = "wordSource",cascade = CascadeType.DETACH)
    Set<WordSentence> wordSentences;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "word_user_id")
    WordUser wordUser;

    @PreRemove
    private void removeAssociationsWithChilds() {
        for (WordSentence wordSentence : wordSentences) {
            wordSentence.setWordSource(null);
        }
    }
}
