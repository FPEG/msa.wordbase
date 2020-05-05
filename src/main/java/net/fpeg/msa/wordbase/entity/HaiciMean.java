package net.fpeg.msa.wordbase.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "haici_mean")
public class HaiciMean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long haiciMeanId;

    private String haiciMeanValue;

    @ManyToOne
    @JoinColumn(name = "word_part_id"/*, insertable = false, updatable = false*/)
    private WordPart wordPart;
}
