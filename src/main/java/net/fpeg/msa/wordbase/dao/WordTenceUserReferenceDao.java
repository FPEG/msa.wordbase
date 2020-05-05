package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.WordTenceUserReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WordTenceUserReferenceDao extends JpaRepository<WordTenceUserReference, Long>, JpaSpecificationExecutor<WordTenceUserReference> {
    List<WordTenceUserReference> findByWordTenceUser_WordUser_WordUserIdAndWord_WordValue(Long wordUserId, String wordValue);
    WordTenceUserReference findByWordTenceUser_WordUser_WordUserIdAndWord_WordValueAndWordTenceValue(Long wordUserId, String wordValue, String wordTenceValue);
    WordTenceUserReference getByWordTenceUserReferenceId(Long wordTenceUserReferenceId);
}
