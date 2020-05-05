package net.fpeg.msa.wordbase.controller;

import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/wordList")
public class WordListController {

    final
    WordListService wordListService;

    public WordListController(WordListService wordListService) {
        this.wordListService = wordListService;
    }

    /**
     * 获取用户所有单词表
     */
    @GetMapping
    public WordListDto fetch(Pageable pageable) {
        return wordListService.fetch(pageable);
    }

    /**
     * 仅根据tag列出单词
     *
     * @param tagValue tag
     * @return WordVo
     */
    @Deprecated
    @GetMapping("/tag/{tagValue}")
    public List<WordListRecordDto> fetchByTag(@PathVariable("tagValue") String tagValue) {
        return wordListService.findWordByUserIdAndTagValue(tagValue);
    }

    /**
     * 根据tag和单词包含列出单词
     *
     * @param wordValue 单词包含
     * @param tagValue  tag
     * @return WordVo
     */
    @Deprecated
    @GetMapping("/{wordValue}/tag/{tagValue}")
    public List<WordListRecordDto> findByWordConAndTag(
            @PathVariable("wordValue") String wordValue,
            @PathVariable("tagValue") String tagValue
    ) {
        return wordListService.findWordByUserIdAndValueContainingAndTagValue(wordValue, tagValue);
    }


    /**
     * 根据condition获取单词表
     *
     * @param wordConditionDto 条件
     * @return List<WordListDto>
     */
    @PostMapping("/condition")
    public WordListDto fetchByCondition(
            @RequestBody WordConditionDto wordConditionDto,
            Pageable pageable
    ) {
        return wordListService.findWordByCondition(wordConditionDto, pageable);
    }


}
