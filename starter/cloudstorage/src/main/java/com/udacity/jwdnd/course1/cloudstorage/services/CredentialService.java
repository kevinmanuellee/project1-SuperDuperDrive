package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public void insertOrUpdateCredential (Credential credential) {
        System.out.println("Insert Credential Method");
        String password = credential.getPassword(); //get password from credential form
        // <start> encrypt the password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        // <end> encrypt the password
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);

        if (credential.getCredentialId() == null) {
            credentialMapper.insertCredential(credential);//userId will be provided in the controller, credentialId is auto-generated
        } else {
            credentialMapper.updateCredential(credential);
        }

        //for debugging
        System.out.println("Credential ID : " + credential.getCredentialId());
        System.out.println("User ID : " + credential.getUserId());
        System.out.println("Url : " + credential.getUrl());
        System.out.println("Username : " + credential.getUsername());
        System.out.println("Password : " + credential.getPassword());
        System.out.println("Key : " + credential.getKey());
    }

    public Credential getCredential(Credential credential){
        String encryptedPassword = credential.getPassword();
        String key = credential.getKey();
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, key);
        credential.setPassword(decryptedPassword);
        return credential;
    }

    public Integer deleteCredential(Integer credentialid, Integer userId) {
        return credentialMapper.deleteCredential(credentialid, userId);
    }

    public List<Credential> getAllCredentials(Integer userId){
        return credentialMapper.getAllCredentials(userId);
    }
}
