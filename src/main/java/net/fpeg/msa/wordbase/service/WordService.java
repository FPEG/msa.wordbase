package net.fpeg.msa.wordbase.service;

import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.entity.*;
import net.fpeg.msa.wordbase.properties.ServiceProperties;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

import static net.fpeg.msa.common.utils.MvcUtil.*;

@Service
public class WordService {

    final
    WordTagDao wordTagDao;

    final
    WordBaseDao wordBaseDao;

    final
    ServiceProperties serviceProperties;

    final
    WordSentenceService wordSentenceService;

    final
    WordDao wordDao;

    final
    WordUserDao wordUserDao;

    final
    WordSentenceDao wordSentenceDao;

    final
    WordTenceUserService wordTenceUserService;

    final
    WordTenceUserReferenceDao wordTenceUserReferenceDao;

    final
    WordNoteDao wordNoteDao;


    public WordService(WordUserDao wordUserDao, WordDao wordDao, ServiceProperties serviceProperties, WordBaseDao wordBaseDao, WordTagDao wordTagDao, WordSentenceService wordSentenceService, WordSentenceDao wordSentenceDao, WordTenceUserService wordTenceUserService, WordTenceUserReferenceDao wordTenceUserReferenceDao, WordNoteDao wordNoteDao) {
        this.wordUserDao = wordUserDao;
        this.wordDao = wordDao;
        this.serviceProperties = serviceProperties;
        this.wordBaseDao = wordBaseDao;
        this.wordTagDao = wordTagDao;
        this.wordSentenceService = wordSentenceService;
        this.wordSentenceDao = wordSentenceDao;
        this.wordTenceUserService = wordTenceUserService;
        this.wordTenceUserReferenceDao = wordTenceUserReferenceDao;
        this.wordNoteDao = wordNoteDao;
    }

    public List<Word> findWordByUserIdAndValueContaining(String word) {
        return wordDao.findByWordBases_Pk_WordUserIdAndWordValueContaining(getUserId(), word);
    }

    public boolean putWord(Long wordId, Long tagId) {
        return false;
    }

    /**
     * 插入新单词
     *
     * @param wordValue 单词id
     * @param tagValue  标签id
     * @return 查询是否出错：insertIfNotExist返回了null
     */
    @Transactional
    public boolean post(String wordValue, @Nullable String tagValue) {
        Word word = insertIfNotExist(wordValue, "wordValue", wordDao::getByWordValue, wordDao::saveAndFlush, Word.class);
        WordTag wordTag = insertIfNotExist(tagValue == null ? serviceProperties.getDefaultTagValue() : tagValue, "wordTagValue", wordTagDao::getByWordTagValue, wordTagDao::saveAndFlush, WordTag.class);
        //插入出错
        if (word == null || wordTag == null) return false;

        //catch (DataIntegrityViolationException ex)
        WordUser wordUser = wordUserDao.getByWordUserId(getUserId());
        WordBase wordBase = new WordBase();
        WordBase.Pk pk = new WordBase.Pk(wordUser.getWordUserId(), word.getWordId(), wordTag.getWordTagId());
        wordBase.setPk(pk);
        wordBase.setWord(word);
        wordBase.setWordUser(wordUser);
        wordBase.setWordTag(wordTag);
        wordBaseDao.save(wordBase);

        return true;
    }

    @Transactional
    public boolean post(String wordValue) {
        return post(wordValue, serviceProperties.getDefaultTagValue());
    }

    @Transactional
    public boolean deleteWord(String wordValue) {
        wordBaseDao.deleteByPk_WordUserIdAndPk_WordId(getUserId(), wordDao.getByWordValue(wordValue).getWordId());
        return true;
    }

    /**
     * 根据单词返回单词详细信息
     *
     * @param wordValue 单词
     * @return 详细信息
     */
    public WordInfoDto getWordInfo(String wordValue) {

        List<WordSentenceDto> wordSentenceDtos = new LinkedList<>();
        //找例句
        List<WordSentence> wordSentences = null;
        //没有时态
        if (wordTenceUserReferenceDao.findByWordTenceUser_WordUser_WordUserIdAndWord_WordValue(getUserId(), wordValue).size() == 0) {
            wordSentences = wordSentenceDao.findByEnglishValueContainingAndWordSource_WordUser_WordUserId(wordValue, getUserId());
        } else {
            wordSentences = wordSentenceDao.findAll((root, query, criteriaBuilder) -> {
                query.distinct(true);
                Root<WordTenceUserReference> mid_refer = query.from(WordTenceUserReference.class);
                Join<WordTenceUserReference, WordTenceUser> wordTenceUserJoin = mid_refer.join("wordTenceUser", JoinType.INNER);
                Join<WordTenceUser, WordTenceUserReference> mult_refer = wordTenceUserJoin.join("wordTenceUserReferences", JoinType.INNER);
                Join<WordTenceUserReference, Word> wordJoin = mult_refer.join("word", JoinType.INNER);

                Predicate p1 = criteriaBuilder.like(root.get("englishValue"), criteriaBuilder.concat("%", criteriaBuilder.concat(wordJoin.get("wordValue"), "%")));
//                Join<WordTenceUserReference, Word> extra_word = mid_refer.join("word", JoinType.INNER);
//                Predicate p2 = criteriaBuilder.equal(extra_word.get("wordValue"), wordValue);
                Predicate p2 = criteriaBuilder.equal(mid_refer.get("word").get("wordValue"), wordValue);
                Predicate p3 = criteriaBuilder.equal(wordTenceUserJoin.get("wordUser").get("wordUserId"), getUserId());

                return criteriaBuilder.and(p1, p2, p3);
            });
        }

        List<WordTenceUserReferenceDto> wordTenceUserReferences = wordTenceUserService.findTenceUser(wordValue);
        assert wordSentences != null;
        for (WordSentence wordSentence : wordSentences) {
            wordSentenceDtos.add(WordSentenceDto.builder()
                    .chineseValue(wordSentence.getChineseValue())
                    .englishValue(wordSentence.getEnglishValue())
                    .wordSentenceId(wordSentence.getWordSentenceId())
                    .wordSourceId(wordSentence.getWordSource().getWordSourceId())
                    .wordSourceFlatValue("平坦值")
                    .build());
        }

        WordNote wordNote = wordNoteDao.getByWord_WordValueAndWordUser_WordUserId(wordValue, getUserId());
        WordNoteDto wordNoteVo = new WordNoteDto();
        if (wordNote == null) {
            wordNoteVo.setWordNoteValue("");
            wordNoteVo.setWordValue(wordValue);
            wordNoteVo.setWordNoteId(-1L);
        } else {
            wordNoteVo.setWordNoteValue(wordNote.getWordNoteValue());
            wordNoteVo.setWordNoteId(wordNote.getWordNoteId());
            wordNoteVo.setWordValue(wordValue);
        }

        return WordInfoDto.builder()
                .wordSentenceListDto(WordSentenceListDto.builder().wordSentenceDtos(wordSentenceDtos).build())
                .wordValue(wordValue)
                .wordTenceUserReferenceDtos(wordTenceUserReferences)
                .wordNoteDto(wordNoteVo)
                .build();
    }
}
