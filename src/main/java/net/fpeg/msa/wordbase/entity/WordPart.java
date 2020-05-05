package net.fpeg.msa.wordbase.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "word_part",uniqueConstraints = @UniqueConstraint(columnNames = {"part_value","word_id"},name = "jpauni"))
public class WordPart  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordPartId;

    @ManyToOne
    @JoinColumn(name = "word_id" /*, insertable = false, updatable = false*/)
    private Word word;

    @Column(name = "part_value")
    private String partValue;

    @Builder.Default
    @OneToMany(mappedBy = "wordPart",cascade = CascadeType.ALL)
    private Set<BaiduMean> baiduMeans = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "wordPart")
    private Set<HaiciMean> haiciMeans= new HashSet<>();

}
