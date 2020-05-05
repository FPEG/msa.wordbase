package net.fpeg.msa.wordbase.service;

import net.fpeg.msa.wordbase.annotation.UserRest;
import net.fpeg.msa.wordbase.dao.WordSentenceDao;
import net.fpeg.msa.wordbase.dao.WordSourceDao;
import net.fpeg.msa.wordbase.dto.WordSentenceListDto;
import net.fpeg.msa.wordbase.entity.WordSentence;
import net.fpeg.msa.wordbase.dto.WordSentenceDto;
import net.fpeg.msa.wordbase.entity.WordSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static net.fpeg.msa.common.utils.MvcUtil.getUserId;

@Service
public class WordSentenceService {
    final
    WordSentenceDao wordSentenceDao;

    final
    WordSourceDao wordSourceDao;

    public WordSentenceService(WordSentenceDao wordSentenceDao, WordSourceDao wordSourceDao) {
        this.wordSentenceDao = wordSentenceDao;
        this.wordSourceDao = wordSourceDao;
    }

    /**
     * 根据单词选例句,已被替代
     *
     * @param id
     * @return
     */
    public List<WordSentence> findSentenceBySourceId(Long id) {
        System.out.println(getUserId());
        List<WordSentence> a = wordSentenceDao.findByEnglishValueContainingAndWordSource("a", wordSourceDao.getByWordSourceId(1L));
        return a;
    }

    @Transactional
    @UserRest(checkFields = {WordSentenceDto.class})
    public void add(WordSentenceDto wordSentenceDto) {
        WordSentence wordSentence = WordSentence.builder()
                .wordSource(wordSourceDao.getByWordSourceId(wordSentenceDto.getWordSourceId()))
                .chineseValue(wordSentenceDto.getChineseValue())
                .englishValue(wordSentenceDto.getEnglishValue())
                .build();
        wordSentenceDao.save(wordSentence);
    }

    @Transactional
    @UserRest(checkFields = {WordSentenceDto.class})
    public void edit(WordSentenceDto wordSentenceDto) {
        WordSentence wordSentence = wordSentenceDao.getByWordSentenceId(wordSentenceDto.getWordSentenceId());
        wordSentence.setChineseValue(wordSentenceDto.getChineseValue());
        wordSentence.setEnglishValue(wordSentenceDto.getEnglishValue());
        wordSentence.setWordSource(wordSourceDao.getByWordSourceId(wordSentenceDto.getWordSourceId()));
        wordSentenceDao.save(wordSentence);
    }

    @Transactional
    @UserRest(checkFields = {WordSentence.class})
    public void delete(Long wordSentenceId) {
        WordSentence wordSentence = wordSentenceDao.getByWordSentenceId(wordSentenceId);
        wordSentenceDao.delete(wordSentence);
    }

    public WordSentenceListDto fetch(Long wordSourceId, Pageable pageable) {
        Page<WordSentence> wordSentences = wordSentenceDao.findByWordSource_WordSourceIdInOrderByWordSentenceId(getWordSourceIdList(wordSourceId),pageable);
        List<WordSentenceDto> wordSentenceDtos = new LinkedList<>();
        for (WordSentence wordSentence : wordSentences) {
            wordSentenceDtos.add(WordSentenceDto.builder()
                    .chineseValue(wordSentence.getChineseValue())
                    .englishValue(wordSentence.getEnglishValue())
                    .wordSentenceId(wordSentence.getWordSentenceId())
                    .wordSourceId(wordSentence.getWordSource().getWordSourceId())
                    .wordSourceFlatValue("平坦值")
                    .build());
        }

        return WordSentenceListDto.builder()
                .count(wordSentences.getTotalElements())
                .wordSentenceDtos(wordSentenceDtos)
                .build();
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


}
