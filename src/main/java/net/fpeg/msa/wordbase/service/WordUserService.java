package net.fpeg.msa.wordbase.service;

import net.fpeg.msa.common.utils.MvcUtil;
import net.fpeg.msa.wordbase.dao.WordTagDao;
import net.fpeg.msa.wordbase.dao.WordUserDao;
import net.fpeg.msa.wordbase.entity.WordSource;
import net.fpeg.msa.wordbase.entity.WordTag;
import net.fpeg.msa.wordbase.entity.WordUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class WordUserService {
    @Autowired
    WordUserDao wordUserDao;

    @Autowired
    WordTagDao wordTagDao;

    public void register(Long userId) {
        if (wordUserDao.countByWordUserId(userId) == 0) {
            //级联保存
            WordUser wordUser = WordUser.builder()
                    .wordUserId(userId)
                    .build();
            WordTag wordTag = MvcUtil.insertIfNotExist("默认", "wordTagValue", wordTagDao::getByWordTagValue, wordTagDao::save, WordTag.class);
            wordTag.getWordUsers().add(wordUser);
            HashSet<WordTag> wordTags = new HashSet<>(Collections.singletonList(wordTag));
            WordSource wordSource = WordSource.builder()
                    .wordUser(wordUser)
                    .wordSourceValue("根节点")
                    .build();
            wordUser.setWordSource(wordSource);
            wordUser.setWordTags(wordTags);
            wordUserDao.save(wordUser);
        }
    }
}
