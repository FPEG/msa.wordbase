package net.fpeg.msa.wordbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WordInfoDto {
    WordSentenceListDto wordSentenceListDto;
    List<WordTenceUserReferenceDto> wordTenceUserReferenceDtos;
    String wordValue;
    WordNoteDto wordNoteDto;
}
