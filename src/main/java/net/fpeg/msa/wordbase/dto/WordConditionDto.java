package net.fpeg.msa.wordbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordConditionDto {
    String wordValue;
    String wordTagValue;
    Boolean showTence;
    Long wordSourceId;
}
