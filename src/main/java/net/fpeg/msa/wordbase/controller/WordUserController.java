package net.fpeg.msa.wordbase.controller;

import net.fpeg.msa.wordbase.service.WordUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordUserController {

    @Autowired
    WordUserService wordUserService;

    @PostMapping("/register/{userId}")
    public void register(@PathVariable("userId") Long userId)
    {
        wordUserService.register(userId);
    }
}
