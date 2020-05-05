package net.fpeg.msa.wordbase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 单词列表中每一项的信息
 */
@Data
@AllArgsConstructor
public class WordListRecordDto {
    private String wordValue;
}
