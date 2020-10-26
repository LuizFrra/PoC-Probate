package com.poc.jwt.Security.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

public class JWTKeyGenerator {

    public boolean useRSA;

    KeyPairGenerator keyPairGenerator;
    KeyFactory keyFactory;
    KeyPair keyPair;
    int keySize = -1;

    X509EncodedKeySpec encodedPublicKey;
    PKCS8EncodedKeySpec encodedPrivateKey;

    RSAPublicKey rsaPublicKey;
    RSAPrivateKey rsaPrivateKey;
    String symmetricKey = "";
    Algorithm algorithm;

    Logger logger = (Logger) LoggerFactory.getLogger(JWTKeyGenerator.class);

    public JWTKeyGenerator() {
        logger.info("Initialaizing JWTKeyGenerator.");
    }

    public void setKeySize(int value) {
        keySize = value;
    }

    public void generateKey() throws Exception {

        if(keySize == -1 && useRSA) {
            throw new Exception("You must set a KeySize.");
        }
        if(keySize < 512 && useRSA) {
            throw new Exception("Key Size Have to Be Greater or Equal 512.");
        }

        if(useRSA) {
            generateRSAKeys();
            encodeKeys();
            setRSA();
        } else {
            symmetricKey = "";
            symmetricKey += UUID.randomUUID().toString();
        }
    }

    private void generateRSAKeys() {

        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(this.keySize);
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        keyPair = keyPairGenerator.generateKeyPair();
    }

    private void encodeKeys() {
        encodedPublicKey = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());
        encodedPrivateKey = new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded());
    }

    private void setRSA() {
        try {
            rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(encodedPublicKey);
            rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(encodedPrivateKey);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public Algorithm getAlgorithm() {
        if(algorithm == null) {
            if(useRSA)
                algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
            else
                algorithm = Algorithm.HMAC512(symmetricKey);
        }
        return algorithm;
    }
}
