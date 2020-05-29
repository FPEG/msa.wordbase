package net.fpeg.msa.wordbase.controller;

import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.exception.UserException;
import net.fpeg.msa.wordbase.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wordTag")
public class WordTagController {

    final
    WordTagService wordTagService;

    public WordTagController(WordTagService wordTagService) {
        this.wordTagService = wordTagService;
    }

    /**
     * 插入单词标签
     * @param wordTagValue 标签内容
     * @throws UserException 插入异常
     */
    @PostMapping("/{wordTagValue}")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@PathVariable("wordTagValue") String wordTagValue) throws UserException {
        wordTagService.add(wordTagValue);
    }

    /**
     * 获取用户所有单词标签
     * @return 用户所有单词标签
     */
    @GetMapping
    public WordTagListDto fetch() {
        return wordTagService.fetch();
    }
}
