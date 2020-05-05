package net.fpeg.msa.wordbase.controller;

import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.entity.*;
import net.fpeg.msa.wordbase.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wordSource")
public class WordSourceController {

    final
    WordSourceService wordSourceService;

    public WordSourceController(WordSourceService wordSourceService) {
        this.wordSourceService = wordSourceService;
    }

    /**
     * 获取用户所有单词来源
     * @return WordSource
     */
    @GetMapping
    public WordSource findWordSource() {
        return wordSourceService.findSource();
    }

    /**
     * 插入单词来源子节点
     * @param wordSourceId 单词来源节点id
     * @param wordSourceValue 单词来源节点值
     */
    @PostMapping("/{wordSourceId}/{wordSourceValue}")
    public void addWordSource(
            @PathVariable("wordSourceId") Long wordSourceId,
            @PathVariable("wordSourceValue") String wordSourceValue) {
        wordSourceService.add(wordSourceId, wordSourceValue);
    }

    /**
     * 改变单词来源节点
     * @param wordSourceId 单词来源节点id
     * @param wordSourceValue 单词来源节点值
     */
    @PutMapping("/{wordSourceId}/{wordSourceValue}")
    public void editWordSource(
            @PathVariable("wordSourceId") Long wordSourceId,
            @PathVariable("wordSourceValue") String wordSourceValue) {
        wordSourceService.edit(wordSourceId, wordSourceValue);
    }

    /**
     * 删除单词来源节点
     * @param wordSourceId 单词来源节点id
     */
    @DeleteMapping("/{wordSourceId}")
    public void deleteWordSource(
            @PathVariable("wordSourceId") Long wordSourceId) {
        wordSourceService.delete(wordSourceId);
    }

}
