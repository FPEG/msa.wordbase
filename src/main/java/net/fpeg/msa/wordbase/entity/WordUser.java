package net.fpeg.msa.wordbase.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "word_user")
public class WordUser /*extends User*/ {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordUserId;

//    @OneToOne(cascade = CascadeType.ALL,mappedBy = "wordUser")
//    private User user;

    @OneToMany(mappedBy = "wordUser")
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<WordBase> wordBases;

    @OneToMany(mappedBy = "wordUser")
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<WordSource> wordSources;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "word_source_root_id")
    private WordSource wordSource;

    @ManyToMany
    @JoinTable(name = "word_user_tag",
            joinColumns = {@JoinColumn(name = "word_user_id")},
            inverseJoinColumns = {@JoinColumn(name = "word_tag_id")})
    Set<WordTag> wordTags;

    @OneToMany(mappedBy = "wordUser")
    Set<WordTenceUser> wordTenceUsers;

    @OneToMany(mappedBy = "wordUser")
    Set<WordNote> wordNotes;

}
