package net.fpeg.msa.wordbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WordSentenceDto {
    Long wordSentenceId;
    String englishValue;
    String chineseValue;
    String wordSourceFlatValue;
    Long wordSourceId;
}
