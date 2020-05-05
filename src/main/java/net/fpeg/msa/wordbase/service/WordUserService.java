package net.fpeg.msa.wordbase.service;

import net.fpeg.msa.wordbase.dao.WordUserDao;
import net.fpeg.msa.wordbase.entity.WordSource;
import net.fpeg.msa.wordbase.entity.WordUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordUserService {
    @Autowired
    WordUserDao wordUserDao;

    public void register(Long userId) {
        if(wordUserDao.countByWordUserId(userId)==0)
        {
            //级联保存
            WordUser wordUser = WordUser.builder()
                    .wordUserId(userId)
                    .build();
            WordSource wordSource = WordSource.builder()
                    .wordUser(wordUser)
                    .wordSourceValue("根节点")
                    .build();
            wordUser.setWordSource(wordSource);
            wordUserDao.save(wordUser);
        }
    }
}
