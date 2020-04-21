package com.pandaz.api.config;

import lombok.Data;

/**
 * 获取的jwt公钥
 *
 * @author Carzer
 * @since 2019-12-26
 */
@Data
public class JwtPublicKey {

    /**
     * 算法
     */
    private String alg;

    /**
     * public key
     */
    private String value;
}