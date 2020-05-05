package net.fpeg.msa.wordbase.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordTag {
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // 自增长策略
    private Long wordTagId;

    @Column(unique = true)
    private String wordTagValue;

    @OneToMany(mappedBy = "wordTag")
    private Set<WordBase> wordBases;

//    @OneToMany(mappedBy = "wordTag")
//    private Set<WordUserTag> wordUserTags;

    @ManyToMany(mappedBy = "wordTags")
    Set<WordUser> wordUsers;
}
