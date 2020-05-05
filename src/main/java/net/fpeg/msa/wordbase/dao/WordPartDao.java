package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.Word;
import net.fpeg.msa.wordbase.entity.WordPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WordPartDao extends JpaRepository<WordPart, Long>, JpaSpecificationExecutor<WordPart> {

    WordPart getByWordAndPartValue(Word word, String partValue);
}
