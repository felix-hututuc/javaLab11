package com.fii.springTesting.friends;

import com.fii.springTesting.user.User;
//import org.bouncycastle.asn1.ASN1Sequence;
//import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

@Service
public class FriendsService {

    private final FriendsRepository friendsRepository;
    private final EntityManager entityManager;

    @Autowired
    public FriendsService(FriendsRepository friendsRepository, EntityManager entityManager) {
        this.friendsRepository = friendsRepository;
        this.entityManager = entityManager;
    }

    public List<Friends> findAllFriends() {
        return friendsRepository.findAll();
    }

    public void addFriends(Friends friends) {
        friendsRepository.save(friends);
    }

    public List<User> findMostPopular(Integer k) {

        return entityManager.createNativeQuery("select s.id, s.name from users s join (select friend_1, count(friend_1) as c from friends group by friend_1 order by c desc LIMIT :k) d on s.id = d.friend_1")
                .setParameter("k", k)
                .getResultList();
    }

//    public static String createJWT(String id, String issuer, String subject, long ttlMillis) throws IOException {
//
//        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("E:\\Facultate\\Informatica_2020\\Semestrul_4\\Java\\springTesting\\src\\main\\resources\\keystore\\public.key"));
//        byte[] key = new byte[2048];
//        inputStream.read(key);
//        RSAPublicKey publicKey = RSAPrivateKey.getInstance(ASN1Sequence.fromByteArray(""))
//    }
}
