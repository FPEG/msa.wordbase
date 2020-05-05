package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.WordSentence;
import net.fpeg.msa.wordbase.entity.WordSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface WordSentenceDao extends JpaRepository<WordSentence, Long>, JpaSpecificationExecutor<WordSentence> {
    List<WordSentence> findByWordSource(WordSource wordSource);
    WordSentence getByWordSentenceId(Long id);
    List<WordSentence> findByEnglishValueContainingAndWordSource(String wordValue, WordSource wordSource);
    List<WordSentence> findByEnglishValueContainingAndWordSource_WordUser_WordUserId(String wordValue, Long id);
    Page<WordSentence> findByWordSource_WordSourceIdInOrderByWordSentenceId(Set<Long> set, Pageable pageable);
}
