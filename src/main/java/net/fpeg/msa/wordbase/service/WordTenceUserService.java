package net.fpeg.msa.wordbase.service;

import net.fpeg.msa.wordbase.annotation.UserRest;
import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.entity.*;
import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.exception.UserException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static net.fpeg.msa.common.utils.MvcUtil.getUserId;

@Service
public class WordTenceUserService {

    final
    WordTenceUserDao wordTenceUserDao;

    final
    WordTenceUserReferenceDao wordTenceUserReferenceDao;

    final
    WordDao wordDao;

    final
    WordUserDao wordUserDao;

    public WordTenceUserService(WordTenceUserDao wordTenceUserDao, WordTenceUserReferenceDao wordTenceUserReferenceDao, WordDao wordDao, /*WordService wordService,*/ WordUserDao wordUserDao) {
        this.wordTenceUserDao = wordTenceUserDao;
        this.wordTenceUserReferenceDao = wordTenceUserReferenceDao;
        this.wordDao = wordDao;
        this.wordUserDao = wordUserDao;
    }

    /**
     * 根据单词取得所有时态
     *
     * @param wordValue
     * @return
     */
    public List<WordTenceUserReferenceDto> findTenceUser(String wordValue) {
        List<WordTenceUserReferenceDto> wordTenceUserReferenceDtos = new LinkedList<>();
        Set<WordTenceUserReference> result = null;
        Optional<WordTenceUser> wordTenceUserOptional = Optional.ofNullable(wordTenceUserDao.getByWord_WordValueAndWordUser_WordUserId(wordValue, getUserId()));
        if (wordTenceUserOptional.isPresent()) {
            result = wordTenceUserOptional.get().getWordTenceUserReferences();
        } else {
            Optional<WordTenceUser> wordTenceUserOptional1 = Optional.ofNullable(wordTenceUserDao.getByWordTenceUserReferences_Word_WordValueAndWordUser_WordUserId(wordValue, getUserId()));
            if (wordTenceUserOptional1.isPresent()) {
                result = wordTenceUserOptional1.get().getWordTenceUserReferences();
            }
        }
        if (result != null) {
            for (WordTenceUserReference wordTenceUserReference : result) {
                wordTenceUserReferenceDtos.add(WordTenceUserReferenceDto.builder()
                        .wordTenceUserId(wordTenceUserReference.getWordTenceUser().getWordTenceUserId())
                        .wordTenceUserReferenceId(wordTenceUserReference.getWordTenceUserReferenceId())
                        .wordTenceValue(wordTenceUserReference.getWordTenceValue())
                        .wordValue(wordTenceUserReference.getWord().getWordValue())
                        .originalWordValue(wordTenceUserReference.getWordTenceUser().getWord().getWordValue())
                        .build());
            }
        }
        return wordTenceUserReferenceDtos;
    }

    @UserRest(checkFields = {Word.class})
    @Transactional
    public void addUserOriginalWordValue(String wordValue) {
        WordTenceUser build = WordTenceUser.builder()
                .word(wordDao.getByWordValue(wordValue))
                .wordUser(wordUserDao.getByWordUserId(getUserId()))
                .build();
        wordTenceUserDao.save(build);
        wordTenceUserReferenceDao.save(WordTenceUserReference.builder()
                .word(wordDao.getByWordValue(wordValue))
                .wordTenceUser(build)
                .wordTenceValue("原型")
                .build()
        );
    }

    @UserRest(checkFields = {Word.class, WordTenceUser.class})
    @Transactional
    public void addUserWordValue(Long wordTenceUserId, String wordValue, String wordTenceValue) throws UserException {
        WordTenceUser byWord_wordValueAndWordUser_wordUserId = wordTenceUserDao.getByWord_WordValueAndWordUser_WordUserId(wordValue, getUserId());
        if(wordTenceUserDao.getByWord_WordValueAndWordUser_WordUserId(wordValue, getUserId())!=null)
        {
            //todo 细分异常
            throw new UserException("不可以添加另一个原型");
        }
        WordTenceUser wordTenceUser = wordTenceUserDao.getByWordTenceUserId(wordTenceUserId);
        WordTenceUserReference build = WordTenceUserReference.builder()
                .wordTenceValue(wordTenceValue)
                .word(wordDao.getByWordValue(wordValue))
                .wordTenceUser(wordTenceUserDao.getByWordTenceUserId(wordTenceUserId))
                .build();
        wordTenceUserReferenceDao.save(build);
        wordTenceUser.getWordTenceUserReferences().add(build);
        wordTenceUserDao.save(wordTenceUser);
    }

    @UserRest(checkFields = {WordTenceUserReference.class})
    @Transactional
    public void deleteUserWordValue(Long wordTenceUserReferenceId) {
        WordTenceUserReference byWordTenceUserReferenceId = wordTenceUserReferenceDao.getByWordTenceUserReferenceId(wordTenceUserReferenceId);
        wordTenceUserReferenceDao.delete(byWordTenceUserReferenceId);
    }

    @UserRest(checkFields = {WordTenceUser.class})
    @Transactional
    public void deleteUserOriginalWordValue(Long wordTenceUserId) {
        WordTenceUser wordTenceUser = wordTenceUserDao.getByWordTenceUserId(wordTenceUserId);
        wordTenceUserDao.delete(wordTenceUser);
    }


}
