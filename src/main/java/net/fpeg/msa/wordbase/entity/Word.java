package net.fpeg.msa.wordbase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Check(constraints = "word_value != ''")
public class Word {
    private static final long serialVersionUID = 1L;
    // @Id是用来标识主键的，而@GeneratedValue则是用来指定主键策略的
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "word_id")
    private Long wordId;
    //    @JsonIgnore
//    private Long userId;
    @Column(unique = true)

    private String wordValue;

//    @ManyToMany(mappedBy="words")
//    private Set<WordUser> wordUsers;

    @OneToMany(mappedBy = "word")
    private Set<WordBase> wordBases;

    @OneToMany(mappedBy = "word")
    private Set<WordPart> wordParts;

    @OneToMany(mappedBy = "word")
    Set<WordTenceUser> wordTenceUsers;

    @OneToMany(mappedBy = "word")
    Set<WordTenceUserReference> wordTenceUserReferences;

    @OneToMany(mappedBy = "word")
    Set<WordNote> wordNotes;

}
