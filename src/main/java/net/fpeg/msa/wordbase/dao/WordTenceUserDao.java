package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.WordTenceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WordTenceUserDao extends JpaRepository<WordTenceUser, Long>, JpaSpecificationExecutor<WordTenceUser> {

    WordTenceUser getByWordTenceUserReferences_Word_WordValueAndWordUser_WordUserId(String wordValue, Long wordUserId);
    WordTenceUser getByWord_WordValueAndWordUser_WordUserId(String wordValue, Long wordUserId);
    WordTenceUser getByWordTenceUserId(Long wordTenceUserId);
}
