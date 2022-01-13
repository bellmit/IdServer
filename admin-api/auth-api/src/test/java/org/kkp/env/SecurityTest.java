package org.kkp.env;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;
import java.util.Map;

/**
 * @author Klaus
 * @since 2022/1/13
 **/

public class SecurityTest {

    @Test
    public void securityTest() {
        for (Provider p : Security.getProviders()) {
            System.out.println(p);
            for (Map.Entry entry :p.entrySet()) {
                System.out.println("\t"+entry.getKey());
            }
        }

    }
}
