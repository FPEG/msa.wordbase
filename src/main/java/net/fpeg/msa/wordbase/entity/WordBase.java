package net.fpeg.msa.wordbase.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "word_base")
public class WordBase implements Serializable {
    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pk implements Serializable {
        @Column(name = "word_user_id", nullable = false, updatable = false)
        private Long wordUserId;

        @Column(name = "word_id", nullable = false, updatable = false)
        private Long wordId;

        @Column(name = "word_tag_id", nullable = false, updatable = false)
        private Long wordTagId;
    }

    @EmbeddedId
    private Pk pk;

    @ManyToOne
    @JoinColumn(name = "word_user_id", insertable = false, updatable = false)
    private WordUser wordUser;

    @ManyToOne
    @JoinColumn(name = "word_id", insertable = false, updatable = false)
    private Word word;

    @ManyToOne
    @JoinColumn(name = "word_tag_id", insertable = false, updatable = false)
    private WordTag wordTag;

}
