package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.WordSource;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface WordSourceDao extends JpaRepository<WordSource, Long> {
    Set<WordSource> findByWordSourceParent(WordSource wordSource);

    WordSource getByWordSourceId(Long id);

    @EntityGraph(value = "WordSource.details", type = EntityGraph.EntityGraphType.FETCH)
    WordSource getOneByWordSourceId(Long id);



    @Query(value = "select word_source.word_user_id from word_source where word_source_id = :wordSourceId",nativeQuery = true)
    Long getUserIdByWordSourceId(@Param("wordSourceId") Long wordSourceId);
}
