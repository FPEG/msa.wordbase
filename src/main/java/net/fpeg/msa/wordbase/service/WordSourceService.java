package net.fpeg.msa.wordbase.service;

import net.fpeg.msa.wordbase.annotation.UserRest;
import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.entity.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static net.fpeg.msa.common.utils.MvcUtil.getUserId;


@Service
public class WordSourceService {
    final
    WordSourceDao wordSourceDao;

    final
    WordUserDao wordUserDao;

    public WordSourceService(WordSourceDao wordSourceDao, WordUserDao wordUserDao) {
        this.wordSourceDao = wordSourceDao;
        this.wordUserDao = wordUserDao;
    }

    public WordSource findSource() {
        //选择根节点
        return wordSourceDao.getByWordSourceId(wordUserDao.getByWordUserId(getUserId()).getWordSource().getWordSourceId());
    }

    @Transactional
    @UserRest(checkFields = {WordSource.class},ignoreEdit = true)
    public void add(Long wordSourceId, String wordSourceValue) {
        WordSource wordSource = wordSourceDao.getByWordSourceId(wordSourceId);
        WordSource build = WordSource.builder()
                .wordSourceValue(wordSourceValue)
                .wordSourceParent(wordSource)
                .wordUser(wordUserDao.getByWordUserId(getUserId()))
                .build();
        wordSourceDao.save(build);
        wordSource.getWordSourceChild().add(build);
        wordSourceDao.save(wordSource);
    }

    @Transactional
    @UserRest(checkFields = {WordSource.class})
    public void edit(Long wordSourceId, String wordSourceValue) {
        WordSource wordSource = wordSourceDao.getByWordSourceId(wordSourceId);
        wordSource.setWordSourceValue(wordSourceValue);
        wordSourceDao.save(wordSource);
    }

    @Transactional
    @UserRest(checkFields = {WordSource.class})
    public void delete(Long wordSourceId) {
        WordSource wordSource = wordSourceDao.getByWordSourceId(wordSourceId);
        wordSourceDao.delete(wordSource);
    }
}
