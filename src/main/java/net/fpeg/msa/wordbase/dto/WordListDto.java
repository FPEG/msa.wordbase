package net.fpeg.msa.wordbase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordListDto {
    Long count;
    List<WordListRecordDto> wordListRecordDtos;
}
