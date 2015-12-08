package com.ani.octopus.service.agent.core.validate;

import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by zhaoyu on 15-12-7.
 */
public class DomainValidationUtilsTest {

    @Test
    public void testAniStubValidation() {
        AniStub aniStub = new AniStub();
        Set<ValidateMessage> messageSet = DomainObjectValidator.validateDomainObject(aniStub);
        System.out.println(messageSet);
        System.out.println(DomainObjectValidator.isDomainObjectValid(aniStub));
    }

}