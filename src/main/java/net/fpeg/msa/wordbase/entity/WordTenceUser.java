package net.fpeg.msa.wordbase.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "word_tence_user", uniqueConstraints = @UniqueConstraint(columnNames = {"original_word_id","word_user_id"}))
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class WordTenceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long wordTenceUserId;


    //    @MapsId
    @ManyToOne
    @JoinColumn(name = "original_word_id")
    private Word word;

    //    @MapsId
    @ManyToOne
    @JoinColumn(name = "word_user_id")
    private WordUser wordUser;

    @OneToMany(mappedBy = "wordTenceUser",fetch = FetchType.EAGER,orphanRemoval = true)
    Set<WordTenceUserReference> wordTenceUserReferences;

//    @PreRemove
//    private void removeAssociationsWithChilds() {
//        for (WordTenceUserReference wordTenceUserReference : wordTenceUserReferences) {
//            wordTenceUserReference.setWordSource(null);
//        }
//    }

}
