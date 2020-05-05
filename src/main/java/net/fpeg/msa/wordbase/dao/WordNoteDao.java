package net.fpeg.msa.wordbase.dao;

import net.fpeg.msa.wordbase.entity.WordNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordNoteDao extends JpaRepository<WordNote, Long> {
    WordNote getByWordNoteId(Long wordNoteId);
    WordNote getByWord_WordValueAndWordUser_WordUserId(String wordValue, Long wordUserId);
}
