package net.fpeg.msa.wordbase.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "word_sentence")
public class WordSentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long wordSentenceId;

    @Column(columnDefinition="text")
    String chineseValue;

    @Column(columnDefinition="text")
    String englishValue;

    @ManyToOne
    @JoinColumn(name = "word_source_id")
    WordSource wordSource;
}
