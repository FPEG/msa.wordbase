package net.fpeg.msa.wordbase.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "baidu_mean", uniqueConstraints = @UniqueConstraint(columnNames = {"baidu_mean_value","word_part_id"},name = "jpauni2"))
public class BaiduMean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long baiduMeanId;

    @Column(name = "baidu_mean_value")
    private String baiduMeanValue;

    @ManyToOne
    @JoinColumn(name = "word_part_id")
    private WordPart wordPart;
}
