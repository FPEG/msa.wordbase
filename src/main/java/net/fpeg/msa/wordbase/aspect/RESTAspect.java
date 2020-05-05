package net.fpeg.msa.wordbase.aspect;

import net.fpeg.msa.wordbase.annotation.UserRest;
import net.fpeg.msa.wordbase.entity.*;
import net.fpeg.msa.wordbase.dao.*;
import net.fpeg.msa.wordbase.dto.*;
import net.fpeg.msa.wordbase.exception.UserException;
import net.fpeg.msa.wordbase.service.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static net.fpeg.msa.common.utils.MvcUtil.getUserId;

@Aspect
@Component
public class RESTAspect {

    final
    WordUserDao wordUserDao;

    final
    WordSentenceDao wordSentenceDao;

    final
    WordDao wordDao;

    final
    WordBaseDao wordBaseDao;

    final
    WordSourceDao wordSourceDao;

    public RESTAspect(WordUserDao wordUserDao, WordSentenceDao wordSentenceDao, WordDao wordDao, WordBaseDao wordBaseDao, WordSourceDao wordSourceDao) {
        this.wordSourceDao = wordSourceDao;
        this.wordUserDao = wordUserDao;
        this.wordSentenceDao = wordSentenceDao;
        this.wordDao = wordDao;
        this.wordBaseDao = wordBaseDao;
    }
    /**
     * 拦截跨用户更改请求
     *
     * @param pjp
     * @throws UserException
     */
    @Before("execution(* net.fpeg.msa.wordbase.service..*.*(..)) && @annotation(net.fpeg.msa.wordbase.annotation.UserRest)")
    public void handleControllerMethod(JoinPoint pjp) throws UserException {
        //取得被拦截的方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method objMethod = signature.getMethod();
        UserRest userRest = objMethod.getAnnotation(UserRest.class);
        for (Class<?> clazz : userRest.checkFields()) {
            if (clazz == WordSource.class) {
                Long wordSourceId = (Long) pjp.getArgs()[getIndex(objMethod,"wordSourceId")];
                Long userIdByWordSourceId = wordSourceDao.getUserIdByWordSourceId(wordSourceId);
                if (!userIdByWordSourceId.equals(getUserId())) {
                    throw new UserException("单词来源节点不对");
                }
                if (!userRest.ignoreEdit() && wordSourceId.equals(wordUserDao.getByWordUserId(getUserId()).getWordSource().getWordSourceId())) {
                    throw new UserException("禁止改变根节点");
                }

            }
            if(clazz == WordSentenceDto.class)
            {
                WordSentenceDto wordSentenceVo = (WordSentenceDto) pjp.getArgs()[getIndex(objMethod,"wordSentenceDto",WordSentenceDto.class)];
                Long wordUserId = wordSourceDao.getByWordSourceId(wordSentenceVo.getWordSourceId()).getWordUser().getWordUserId();
                if (!wordUserId.equals(getUserId())) {
                    throw new UserException("例句来源节点不对");
                }
            }
            if(clazz == WordSentence.class)
            {
                Long wordSentenceId = (Long) pjp.getArgs()[getIndex(objMethod,"wordSentenceId")];
                Long wordUserId = wordSourceDao.getByWordSourceId(wordSentenceDao.getByWordSentenceId(wordSentenceId).getWordSource().getWordSourceId()).getWordUser().getWordUserId();
                if (!wordUserId.equals(getUserId())) {
                    throw new UserException("例句来源节点不对");
                }
            }
            if(clazz == Word.class)
            {
                String wordValue = (String) pjp.getArgs()[getIndex(objMethod,"wordValue",String.class)];
                if(wordBaseDao.findByPk_WordUserIdAndWord_WordValue(getUserId(),wordValue).size()==0)
                {
                    throw new UserException("单词不对");
                }
            }
        }
    }



    private int getIndex(Method targetMethod, String paramName) {
        return getIndex(targetMethod,paramName,Long.class);
    }

    private int getIndex(Method targetMethod, String paramName, Class<?> paramClazz) {
        int pos = 0;
        for (Parameter parameter : targetMethod.getParameters()) {
            if (parameter.getName().equals(paramName) && parameter.getType() == paramClazz) {
                break;
            }
            pos++;
        }
        return pos;
    }
}
