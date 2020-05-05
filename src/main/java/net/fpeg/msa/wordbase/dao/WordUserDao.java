package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.WordUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WordUserDao extends JpaRepository<WordUser, Long>, JpaSpecificationExecutor<WordUser> {
    WordUser getByWordUserId(Long wordUserId);
}
