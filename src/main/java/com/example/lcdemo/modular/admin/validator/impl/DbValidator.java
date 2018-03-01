
package com.example.lcdemo.modular.admin.validator.impl;

import com.example.lcdemo.modular.admin.validator.IReqValidator;
import com.example.lcdemo.modular.admin.validator.dto.Credence;
import com.itspeed.higu.base.exception.HiguException;
import com.itspeed.higu.base.exception.HiguExceptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DbValidator implements IReqValidator {

   /* @Autowired
    UserAuthMapper userAuthMapper;


    @Override
    public UserAuth validate(Credence credence) {
        UserAuth userAuth = new UserAuth();
        userAuth.setIdentifier(credence.getIdentifier());
        userAuth.setIdentityType(credence.getIdentityType());
        UserAuth dbUserAuth = userAuthMapper.selectOne(userAuth);
        if (dbUserAuth == null) {
            throw new HiguException(HiguExceptionEnum.HAS_NOT_USER);
        }
        if (credence.getCredential().equals(dbUserAuth.getCredential())) {
            return dbUserAuth;
        }
        return null;
    }*/
}

