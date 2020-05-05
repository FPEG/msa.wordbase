package net.fpeg.msa.wordbase.service;

import net.fpeg.msa.wordbase.dao.WordDao;
import net.fpeg.msa.wordbase.dao.WordNoteDao;
import net.fpeg.msa.wordbase.dao.WordUserDao;
import net.fpeg.msa.wordbase.entity.WordNote;
import net.fpeg.msa.wordbase.dto.WordNoteDto;
import org.springframework.stereotype.Service;

import static net.fpeg.msa.common.utils.MvcUtil.getUserId;


@Service
public class WordNoteServicrs {

    final
    WordNoteDao wordNoteDao;

    final
    WordUserDao wordUserDao;

    final
    WordDao wordDao;

    public WordNoteServicrs(WordDao wordDao, WordNoteDao wordNoteDao, WordUserDao wordUserDao) {
        this.wordDao = wordDao;
        this.wordNoteDao = wordNoteDao;
        this.wordUserDao = wordUserDao;
    }

    public void edit(WordNoteDto wordNoteDto) {
        if (wordNoteDto.getWordNoteId() == -1) {
            WordNote build = WordNote.builder()
                    .wordNoteValue(wordNoteDto.getWordNoteValue())
                    .wordUser(wordUserDao.getByWordUserId(getUserId()))
                    .word(wordDao.getByWordValue(wordNoteDto.getWordValue()))
                    .build();
            wordNoteDao.save(build);
        }
        else{
            WordNote wordNote = wordNoteDao.getByWordNoteId(wordNoteDto.getWordNoteId());
            wordNote.setWordNoteValue(wordNoteDto.getWordNoteValue());
            wordNote.setWord(wordDao.getByWordValue(wordNoteDto.getWordValue()));
            wordNoteDao.save(wordNote);
        }
    }
}
