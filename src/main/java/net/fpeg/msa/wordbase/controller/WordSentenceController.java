package net.fpeg.msa.wordbase.controller;

import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.service.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wordSentence")
public class WordSentenceController {

    final
    WordSentenceService wordSentenceService;

    public WordSentenceController(WordSentenceService wordSentenceService) {
        this.wordSentenceService = wordSentenceService;
    }


    @GetMapping("/{wordSentenceId}")
    public WordSentenceListDto fetch(@PathVariable("wordSentenceId") Long wordSentenceId, Pageable pageable){
        return wordSentenceService.fetch(wordSentenceId,pageable);
    }

    /**
     * 插入单词例句
     * @param wordSentenceDto 单词例句dto
     */
    @PostMapping
    public void add(@RequestBody WordSentenceDto wordSentenceDto)
    {
        wordSentenceService.add(wordSentenceDto);
    }

    /**
     * 改变单词例句
     * @param wordSentenceDto 单词例句dto
     */
    @PutMapping
    public void edit(@RequestBody WordSentenceDto wordSentenceDto)
    {
        wordSentenceService.edit(wordSentenceDto);
    }

    /**
     * 删除单词例句
     * @param wordSentenceId 单词例句id
     */
    @DeleteMapping("/{wordSentenceId}")
    public void delete(@PathVariable("wordSentenceId") Long wordSentenceId)
    {
        wordSentenceService.delete(wordSentenceId);
    }
}
