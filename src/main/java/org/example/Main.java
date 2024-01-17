package org.example;

import org.example.model.Clan;
import org.example.model.ClanMetadata;
import org.example.model.TypeReward;
import org.example.repository.ClanMetadataRepository;
import org.example.repository.ClanMetadataRepositoryImpl;
import org.example.repository.ClanRepository;
import org.example.repository.ClanRepositoryImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/clan_skytec_games";
        String user = "skytec_games";
        String password = "skytec_games";

        ClanRepository clanRepository = new ClanRepositoryImpl(url, user, password);
        ClanMetadataRepository clanMetadataRepository = new ClanMetadataRepositoryImpl(url, user, password);

        Clan clan = clanRepository.get(1);
        clan.setMetadataConsumer(clanMetadataRepository::save);

        ExecutorService plus = Executors.newSingleThreadExecutor();
        ExecutorService minus = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            String description = "номер попытки ";
            plus.submit(() -> clan.addGold(5,
                    new ClanMetadata(TypeReward.MEMBER_ENLISTMENT, "+," + description + finalI)));
            if (i % 2 == 0) {
                minus.submit(() -> clan.reduceGold(5,
                        new ClanMetadata(TypeReward.MEMBER_ENLISTMENT, "-," + description + finalI)));
            }
        }
    }
}