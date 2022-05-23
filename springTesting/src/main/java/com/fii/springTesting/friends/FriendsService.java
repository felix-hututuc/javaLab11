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
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendsService {

    private final FriendsRepository friendsRepository;
    private final EntityManager entityManager;
    static int disc = 0;

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

    private ArrayList<ArrayList<Integer>> getGraph() {

        ArrayList<Friends> friendsList = (ArrayList<Friends>) findAllFriends();
        ArrayList<ArrayList<Integer>> graphAdjLists = new ArrayList<ArrayList<Integer>>();

        for (Friends relation : friendsList) {
            graphAdjLists.add(new ArrayList<>());
        }

        for (Friends relation : friendsList) {
            graphAdjLists.get(relation.getFriend_1() - 1).add(relation.getFriend_2() - 1);
        }

        return graphAdjLists;
    }

    private void articulationPointsAlg(ArrayList<ArrayList<Integer>> adjList, int v,
                                          boolean[] visited, int[] depth, int[] low,
                                          int parent, boolean[] isAp) {
        int children = 0;
        visited[v] = true;
        depth[v] = low[v] = ++disc;

        for (Integer u : adjList.get(v)) {
            if (!visited[u]) {
                children++;
                articulationPointsAlg(adjList, u, visited, depth, low, v, isAp);
                low[v] = Math.min(low[v], low[u]);
                if (parent != -1 && low[u] >= depth[v]) {
                    isAp[v] = true;
                }
            } else if (u != parent) {
                low[v] = Math.min(low[v], low[u]);
            }
        }

        if (parent == -1 && children > 1) {
            isAp[v] = true;
        }
    }

    public List<Integer> getArticulationPoints() {
        ArrayList<ArrayList<Integer>> adjLists = getGraph();

        int n = adjLists.size();
        boolean[] visited = new boolean[n];
        int[] depth = new int[n];
        int[] low = new int[n];
        boolean[] isAP = new boolean[n];

        for (int vertex = 0; vertex < n; vertex++) {
            if (!visited[vertex]) {
                articulationPointsAlg(adjLists, vertex, visited, depth, low, -1, isAP);
            }
        }

        List<Integer> articulationPoints = new ArrayList<>();
        for (int vertex = 0; vertex < n; vertex++) {
            if (isAP[vertex]) {
                articulationPoints.add(vertex + 1);
            }
        }

        return articulationPoints;
    }

//    public static String createJWT(String id, String issuer, String subject, long ttlMillis) throws IOException {
//
//        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("E:\\Facultate\\Informatica_2020\\Semestrul_4\\Java\\springTesting\\src\\main\\resources\\keystore\\public.key"));
//        byte[] key = new byte[2048];
//        inputStream.read(key);
//        RSAPublicKey publicKey = RSAPrivateKey.getInstance(ASN1Sequence.fromByteArray(...))
//    }
}
