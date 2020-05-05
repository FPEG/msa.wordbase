package net.fpeg.msa.wordbase.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "word_note", uniqueConstraints = @UniqueConstraint(columnNames = {"word_id","word_user_id"}))
public class WordNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    Long wordNoteId;

    String wordNoteValue;

    @ManyToOne
    @JoinColumn(name = "word_id")
    Word word;

    @ManyToOne
    @JoinColumn(name = "word_user_id")
    WordUser wordUser;
}
