package net.fpeg.msa.wordbase.service;

import net.fpeg.msa.wordbase.annotation.UserRest;
import net.fpeg.msa.wordbase.dao.WordDao;
import net.fpeg.msa.wordbase.dao.WordSourceDao;
import net.fpeg.msa.wordbase.dao.WordTagDao;
import net.fpeg.msa.wordbase.dto.WordListDto;
import net.fpeg.msa.wordbase.entity.*;
import net.fpeg.msa.wordbase.properties.ServiceProperties;
import net.fpeg.msa.wordbase.dto.WordConditionDto;
import net.fpeg.msa.wordbase.dto.WordListRecordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;

import static net.fpeg.msa.common.utils.MvcUtil.getUserId;


@Service
public class WordListService {

    final
    WordDao wordDao;

    final
    WordTagDao wordTagDao;

    final
    ServiceProperties serviceProperties;

    final
    WordSourceDao wordSourceDao;


    public WordListService(WordDao wordDao, WordTagDao wordTagDao, ServiceProperties serviceProperties, WordSourceDao wordSourceDao) {
        this.wordDao = wordDao;
        this.wordTagDao = wordTagDao;
        this.serviceProperties = serviceProperties;
        this.wordSourceDao = wordSourceDao;
    }


    public List<WordListRecordDto> findWordByUserIdAndTagValue(String tagValue) {
        List<WordListRecordDto> wordListDtoListRecord = new ArrayList<>();
        for (Word word : wordDao.findByWordBases_Pk_WordUserIdAndWordBases_Pk_WordTagId(getUserId(), wordTagDao.getByWordTagValue(tagValue).getWordTagId())) {
            wordListDtoListRecord.add(new WordListRecordDto(word.getWordValue()));
        }
        return wordListDtoListRecord;
    }

    public List<WordListRecordDto> findWordByUserIdAndValueContainingAndTagValue(String wordValue, String tagValue) {
        tagValue = tagValue.equals("undefined") ? serviceProperties.getDefaultTagValue() : tagValue;
        Long tagId = wordTagDao.getByWordTagValue(tagValue).getWordTagId();
        List<Word> wordList = wordDao.findByWordBases_Pk_WordUserIdAndWordValueContainingAndWordBases_Pk_WordTagId(getUserId(), wordValue, tagId);
        List<WordListRecordDto> wordListDtoListRecord = new ArrayList<>();
        for (Word word : wordList)
            wordListDtoListRecord.add(new WordListRecordDto(word.getWordValue()));

        return wordListDtoListRecord;
    }

    public WordListDto fetch(Pageable pageable) {
        Page<Word> page = wordDao.findDistinctByWordBases_Pk_WordUserId(getUserId(), pageable);
        List<WordListRecordDto> wordListRecordDtos = new ArrayList<>();
        for (Word word : page) {
            wordListRecordDtos.add(new WordListRecordDto(word.getWordValue()));
        }
        return new WordListDto(page.getTotalElements(), wordListRecordDtos);
    }

    /**
     * @param wordSourceId 根据来源查单词列表
     * @return 单词列表
     */
    @UserRest(checkFields = {WordSource.class})
    public List<Word> findBySourceId(Long wordSourceId) {
        List<Word> all = wordDao.findAll((root, query, cb) -> {
            Join<Word, WordBase> joinBase = root.join("bases", JoinType.LEFT);
            Root<WordSentence> wordSentenceRoot = query.from(WordSentence.class);
            //确定例句
            Predicate p1 = cb.like(wordSentenceRoot.get("englishValue"), cb.concat(cb.concat("%", root.get("wordValue")), "%"));
            //确定来源，注解确认了例句的用户
            Predicate p2 = cb.equal(wordSentenceRoot.get("wordSource").get("wordSourceId"), wordSourceId);
            //确定用户
            Predicate p3 = cb.equal(joinBase.get("wordUser").get("wordUserId"), getUserId());
            return cb.and(p1, p2, p3);
        });
        return all;
    }

    Set<Long> getWordSourceIdList(Long wordSourceId) {
        WordSource wordSource = wordSourceDao.getByWordSourceId(wordSourceId);
        Set<Long> returnList = new HashSet<>();
        recurseFindWordSourceId(wordSource, returnList);
        return returnList;
    }

    private void recurseFindWordSourceId(WordSource wordSource, Set<Long> parentList) {
        parentList.add(wordSource.getWordSourceId());
        for (WordSource source : wordSource.getWordSourceChild()) {
            recurseFindWordSourceId(source, parentList);
        }
    }

    public WordListDto findWordByCondition(WordConditionDto wordConditionDto, Pageable pageable) {
        Page<Word> all = wordDao.findAll((root, query, cb) -> {
            //确定用户
            Join<Word, WordBase> wordBaseJoin = root.join("wordBases", JoinType.LEFT);
            Predicate pBase = cb.equal(wordBaseJoin.get("wordUser").get("wordUserId"), getUserId());
            query.distinct(true);
            if (wordConditionDto.getWordTagValue() != null) {
                Predicate pTag = cb.like(wordBaseJoin.get("wordTag").get("wordTagValue"), "%" + wordConditionDto.getWordTagValue() + "%");
                pBase = cb.and(pBase, pTag);
            }
            if (wordConditionDto.getWordValue() != null) {
                Predicate pWord = cb.like(root.get("wordValue"), "%" + wordConditionDto.getWordValue() + "%");
                pBase = cb.and(pBase, pWord);
            }
            if (wordConditionDto.getWordSourceId() != null) {
                if (wordConditionDto.getShowTence()) {
//                    Root<Word> all_word = query.from(Word.class);
                    Join<Word, WordTenceUserReference> mid_reference = root.join("wordTenceUserReferences", JoinType.INNER);
                    Join<WordTenceUserReference, WordTenceUser> wordTenceUserJoin = mid_reference.join("wordTenceUser", JoinType.INNER);
                    Join<WordTenceUser, WordTenceUserReference> mult_refer = wordTenceUserJoin.join("wordTenceUserReferences", JoinType.INNER);
                    Join<WordTenceUserReference, Word> mult_word = mult_refer.join("word", JoinType.INNER);

                    Subquery<WordSentence> subquery = query.subquery(WordSentence.class);
                    Root<WordSentence> table2 = subquery.from(WordSentence.class);
                    subquery.select(table2);
                    //这里换成all_word也行？？
                    Predicate p11 = cb.like(table2.get("englishValue"), cb.concat(cb.concat("%", mult_word.get("wordValue")), "%"));
                    Predicate p12 = cb.equal(table2.get("wordSource").get("wordSourceId"), wordConditionDto.getWordSourceId());
                    Predicate p13 = table2.get("wordSource").get("wordSourceId").in(getWordSourceIdList(wordConditionDto.getWordSourceId()));

                    subquery.where(cb.and(p11, p13));
                    Predicate p9 = cb.exists(subquery);
                    pBase = cb.and(pBase, p9);
                } else {
                    Root<WordSentence> wordSentenceRoot = query.from(WordSentence.class);
                    //确定例句
                    Predicate p1 = cb.like(wordSentenceRoot.get("englishValue"), cb.concat(cb.concat("%", root.get("wordValue")), "%"));
                    //确定来源，注解确认了例句的用户
                    Predicate p2 = cb.equal(wordSentenceRoot.get("wordSource").get("wordSourceId"), wordConditionDto.getWordSourceId());
                    Predicate p3 = wordSentenceRoot.get("wordSource").get("wordSourceId").in(getWordSourceIdList(wordConditionDto.getWordSourceId()));
                    Predicate pWordSource = cb.and(p1, p3);
                    pBase = cb.and(pBase, pWordSource);
                }

            }
            return pBase;
        }, pageable);

        List<WordListRecordDto> wordListRecordDtos = new LinkedList<>();
        for (Word word : all) {
            wordListRecordDtos.add(new WordListRecordDto(word.getWordValue()));
        }
        return new WordListDto(all.getTotalElements(), wordListRecordDtos);

    }

}
