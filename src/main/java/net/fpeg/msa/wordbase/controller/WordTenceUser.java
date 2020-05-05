package net.fpeg.msa.wordbase.controller;

import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.exception.UserException;
import net.fpeg.msa.wordbase.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wordTenceUser")
public class WordTenceUser {

    final
    WordTenceUserService wordTenceUserService;

    public WordTenceUser(WordTenceUserService wordTenceUserService) {
        this.wordTenceUserService = wordTenceUserService;
    }

    /**
     * 设定单词原型
     * @param originalWordValue 原型值
     */
    @PostMapping("/originalWord/{originalWordValue}")
    public void addOriginalWord(@PathVariable("originalWordValue") String originalWordValue) {
        wordTenceUserService.addUserOriginalWordValue(originalWordValue);
    }

    /**
     * 插入用户单词时态
     * @param wordValue 单词
     * @param wordTenceUserId 原型表id
     * @param wordTenceValue 时态值
     * @throws UserException 异常
     */
    @PutMapping("/{wordTenceUserId}/{wordValue}/{wordTenceValue}")
    public void addWord(
            @PathVariable("wordValue") String wordValue,
            @PathVariable("wordTenceUserId") Long wordTenceUserId,
            @PathVariable("wordTenceValue") String wordTenceValue
    ) throws UserException {
        wordTenceUserService.addUserWordValue(wordTenceUserId, wordValue, wordTenceValue);
    }

    /**
     * 删除用户单词时态
     * @param wordTenceUserReferenceId reference时态id
     */
    @DeleteMapping("/{wordTenceUserReferenceId}")
    public void deleteWord(
            @PathVariable("wordTenceUserReferenceId") Long wordTenceUserReferenceId
    ) {
        wordTenceUserService.deleteUserWordValue(wordTenceUserReferenceId);
    }

    /**
     * 删除用户单词时态
     * @param wordTenceUserId wordTenceUser时态id
     */
    @DeleteMapping("/originalWord/{wordTenceUserId}")
    public void deleteOriginalWord(
            @PathVariable("wordTenceUserId") Long wordTenceUserId
    ) {
        wordTenceUserService.deleteUserOriginalWordValue(wordTenceUserId);
    }
}
