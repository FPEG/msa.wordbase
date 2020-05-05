package net.fpeg.msa.wordbase.controller;

import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.service.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wordNote")
public class WordNoteController {

    final
    WordNoteServicrs wordNoteServicrs;

    public WordNoteController(WordNoteServicrs wordNoteServicrs) {
        this.wordNoteServicrs = wordNoteServicrs;
    }

    @PutMapping
    public void edit(@RequestBody WordNoteDto wordNoteDto) {
        wordNoteServicrs.edit(wordNoteDto);
    }

}
