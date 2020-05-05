package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.Word;
import net.fpeg.msa.wordbase.entity.WordUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordDao extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {
    List<Word> findByWordValueContaining(String value);
//    @Query("select w from Word w left join WordUser wu where w.wordUsers.id = :userId")
//    List<Word> findByUsersIdAndValueContaining(@Param("userId") Long userId);
//    List<Word> findByWordUsers_Id()
    Word getByWordValue(String value);
    Page<Word> findDistinctByWordBases_Pk_WordUserId(Long id, Pageable pageable);
    int countByWordBases_Pk_WordUserId(Long id);
    List<Word> findByWordBases_Pk_WordUserIdAndWordValueContainingAndWordBases_Pk_WordTagId(Long wordId, String wordValue, Long tagId);
    List<Word> findByWordBases_Pk_WordUserIdAndWordBases_Pk_WordTagId(Long wordId, Long tagId);
    List<Word> findByWordBases_Pk_WordUserIdAndWordValueContaining(Long id, String wordValue);
    List<Word> findByWordBases_WordUser(WordUser wordUser);
    List<Word> findByWordBases_WordUserAndWordValueContaining(WordUser wordUser, String wordValue);

//    Long getIdBy
}

