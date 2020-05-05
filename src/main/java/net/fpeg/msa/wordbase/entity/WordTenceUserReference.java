package net.fpeg.msa.wordbase.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "word_tence_user_reference")
public class WordTenceUserReference implements Serializable {

    //    @Embeddable
//    @Getter
//    @Setter
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class Pk implements Serializable {
//        @Column(name = "word_tence_user_id", nullable = false, updatable = false)
//        private Long wordTenceUserId;
//
//        @Column(name = "word_id", nullable = false, updatable = false)
//        private Long wordId;
//    }
//
//    @EmbeddedId
//    private WordTenceUserReference.Pk pk;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long wordTenceUserReferenceId;

//    @MapsId("word_tence_user_id")
    @ManyToOne
    @JoinColumn(name = "word_tence_user_id"/*, insertable = false, updatable = false*/)
    WordTenceUser wordTenceUser;

//    @MapsId("word_id")
    @ManyToOne
    @JoinColumn(name = "word_id"/*, insertable = false, updatable = false*/,nullable = false)
    Word word;

    String wordTenceValue;


}
