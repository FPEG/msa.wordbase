package net.fpeg.msa.wordbase.service;

import net.fpeg.msa.common.dto.BaseDto;
import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.entity.*;
import net.fpeg.msa.wordbase.exception.UserException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static net.fpeg.msa.common.utils.MvcUtil.getUserId;
import static net.fpeg.msa.common.utils.MvcUtil.insertIfNotExist;

@Service
public class WordTagService {

    final
    WordTagDao wordTagDao;

    final
    WordUserDao wordUserDao;

    public WordTagService(WordTagDao wordTagDao, WordUserDao wordUserDao) {
        this.wordTagDao = wordTagDao;
        this.wordUserDao = wordUserDao;
    }

    public void add(String tagValue) throws UserException {
        WordTag wordTag = insertIfNotExist(tagValue, "wordTagValue", wordTagDao::getByWordTagValue, wordTagDao::saveAndFlush, WordTag.class);
        //插入出错
        if (wordTag == null) throw new UserException("未知错误");
        WordUser wordUser = wordUserDao.getByWordUserId(getUserId());
        if (!wordUser.getWordTags().add(wordTag))
            throw new UserException("tag已存在");
        wordUserDao.save(wordUser);
    }

    public WordTagListDto fetch() {
        List<WordTagDto> wordTagDtos = new ArrayList<>();
        for (WordTag wordTag : wordTagDao.findByWordUsers_WordUserId(getUserId())) {
            wordTagDtos.add(new WordTagDto(wordTag.getWordTagValue()));
        }
        return WordTagListDto.builder()
                .wordTagDtos(wordTagDtos)
                .build();
    }

}
