package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.Word;
import net.fpeg.msa.wordbase.entity.WordBase;
import net.fpeg.msa.wordbase.entity.WordTag;
import net.fpeg.msa.wordbase.entity.WordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordBaseDao extends JpaRepository<WordBase, Long>, JpaSpecificationExecutor<WordBase> {
    List<WordBase> findByPk_WordUserIdAndWord_WordValue(Long wordUserId, String wordValue);

    void deleteByWordUserAndWordTagAndWord(WordUser wordUser, WordTag wordTag, Word word);

    void deleteByPk_WordUserIdAndPk_WordId(Long WordUserId, Long wordId);

}
