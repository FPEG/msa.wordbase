package net.fpeg.msa.wordbase.controller;

import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/word")
public class WordController {

    final
    WordDao wordDao;

    final
    WordUserDao wordUserDao;

    final
    WordService wordService;


    @Autowired
    public WordController(WordDao wordDao, WordUserDao wordUserDao, WordService wordService) {
        this.wordDao = wordDao;
        this.wordUserDao = wordUserDao;
        this.wordService = wordService;
    }


    /**
     * 插入单词
     * @param wordValue 单词
     * @param tagValue 标签
     */
    @PostMapping("/{wordValue}/{tagValue}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void post(
            @PathVariable("wordValue") String wordValue,@PathVariable("tagValue") String tagValue
    ) {
        wordService.post(wordValue, tagValue);
    }

    @DeleteMapping("/{wordValue}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("wordValue") String wordValue
    ) {
        wordService.deleteWord(wordValue);
    }

    /**
     * 获取单词详细信息
     *
     * @param wordValue 单词
     */
    @GetMapping("/{wordValue}")
    public WordInfoDto getWordInfo(
            @PathVariable("wordValue") String wordValue
    ) {
        return wordService.getWordInfo(wordValue);
    }

}
