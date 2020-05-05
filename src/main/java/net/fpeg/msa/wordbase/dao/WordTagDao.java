package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.Word;
import net.fpeg.msa.wordbase.entity.WordTag;
import net.fpeg.msa.wordbase.entity.WordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordTagDao extends JpaRepository<WordTag, Long> {

    WordTag getByWordTagValue(String tagValue);

    List<WordTag> findByWordBases_WordUserAndWordBases_Word(WordUser wordUser, Word word);

//    List<WordTag> findByWordUserTags_Pk_WordUserId(Long id);
    List<WordTag> findByWordUsers_WordUserId(Long id);
}

