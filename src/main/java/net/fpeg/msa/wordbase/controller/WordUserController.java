package net.fpeg.msa.wordbase.controller;

import lombok.extern.log4j.Log4j2;
import net.fpeg.msa.wordbase.service.WordUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Log4j2
public class WordUserController {

    final
    WordUserService wordUserService;

    public WordUserController(WordUserService wordUserService) {
        this.wordUserService = wordUserService;
    }

    @PostMapping("/register/{userId}")
    public void register(@PathVariable("userId") Long userId, HttpServletRequest httpRequest){
        if(httpRequest.getHeader("eyJ0eX").equals("bWluOCIsImV4cCI6MTU5MTYxOTI5NywiaWF0IjoxNTkwNzU1M"))
        {
            wordUserService.register(userId);
        }
    }
}
