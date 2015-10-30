package com.ils.data.model;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by mara on 7/11/15.
 */
public abstract class AbstractEntity {

    private final SecureRandom random = new SecureRandom();
    public abstract Long getId();

    /**
     * This method generate random string comprised of
     * alpha numeric characters of length 26
     *
     * @return
     */
    protected String createNewIdentifier() {
        int NB_OF_BITS_FROM_RANDOM_BIT_GENERATOR = 130;
        int BASE_32 = 32;
        return new BigInteger(NB_OF_BITS_FROM_RANDOM_BIT_GENERATOR, random).toString(BASE_32);
    }
}
