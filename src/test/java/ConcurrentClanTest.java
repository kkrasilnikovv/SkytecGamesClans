import org.example.model.Clan;
import org.example.model.ClanMetadata;
import org.example.model.TypeReward;
import org.example.repository.ClanMetadataRepository;
import org.example.repository.ClanMetadataRepositoryImpl;
import org.example.repository.ClanRepository;
import org.example.repository.ClanRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ConcurrentClanTest {
    private ClanMetadataRepository metadataRepository;
    private ClanRepository clanRepository;

    @BeforeEach
    public void setUp() {
        metadataRepository = mock(ClanMetadataRepositoryImpl.class);
        clanRepository = mock(ClanRepositoryImpl.class);
    }

    @Test
    public void testConcurrentAddGold() throws InterruptedException {
        when(clanRepository.get(1)).thenReturn(new Clan(1, "TestClan", 100));

        Clan clan = clanRepository.get(1);
        clan.setMetadataConsumer(metadataRepository::save);

        ClanMetadata metadata = new ClanMetadata(TypeReward.VICTORY_BONUS);
        int numberOfTasks = 10;
        CountDownLatch latch = new CountDownLatch(numberOfTasks);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            executorService.submit(() -> {
                clan.addGold(5, metadata);
                latch.countDown();
            });
        }

        latch.await();

        assertEquals(150, clan.getGold());
        verify(metadataRepository, times(numberOfTasks)).save(metadata);
    }

    @Test
    public void testConcurrentReduceGold() throws InterruptedException {
        when(clanRepository.get(1)).thenReturn(new Clan(1, "TestClan", 100));

        Clan clan = clanRepository.get(1);
        clan.setMetadataConsumer(metadataRepository::save);

        ClanMetadata metadata = new ClanMetadata(TypeReward.VICTORY_BONUS);
        int numberOfTasks = 10;
        CountDownLatch latch = new CountDownLatch(numberOfTasks);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            executorService.submit(() -> {
                clan.reduceGold(5, metadata);
                latch.countDown();
            });
        }

        latch.await();

        assertEquals(50, clan.getGold());
        verify(metadataRepository, times(numberOfTasks)).save(metadata);
    }

    @Test
    public void testConcurrentModifyGold() throws InterruptedException {
        when(clanRepository.get(1)).thenReturn(new Clan(1, "TestClan", 100));

        Clan clan = clanRepository.get(1);
        clan.setMetadataConsumer(metadataRepository::save);

        ClanMetadata metadata = new ClanMetadata(TypeReward.VICTORY_BONUS);
        int numberOfTasks = 10;
        CountDownLatch latch = new CountDownLatch(numberOfTasks);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            executorService.submit(() -> {
                if (Math.random() < 0.5) {
                    // 50% вероятность добавления
                    clan.addGold(5, metadata);
                } else {
                    // 50% вероятность убавления
                    clan.reduceGold(5, metadata);
                }
                latch.countDown();
            });
        }

        latch.await();

        assertTrue(clan.getGold() >= 50 && clan.getGold() <= 150);
        verify(metadataRepository, times(numberOfTasks)).save(metadata);
    }
}
