import org.example.exception.NotFoundException;
import org.example.model.Clan;
import org.example.model.ClanMetadata;
import org.example.model.TypeReward;
import org.example.repository.ClanMetadataRepository;
import org.example.repository.ClanMetadataRepositoryImpl;
import org.example.repository.ClanRepository;
import org.example.repository.ClanRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClanTest {
    private ClanMetadataRepository metadataRepository;
    private ClanRepository clanRepository;

    @BeforeEach
    public void setUp() {
        metadataRepository = mock(ClanMetadataRepositoryImpl.class);
        clanRepository = mock(ClanRepositoryImpl.class);
    }

    @Test
    public void testAddGold() {
        when(clanRepository.get(1)).thenReturn(new Clan(1, "TestClan", 100));

        Clan clan = clanRepository.get(1);
        clan.setMetadataConsumer(metadataRepository::save);

        ClanMetadata metadata = new ClanMetadata(TypeReward.VICTORY_BONUS);
        clan.addGold(30, metadata);

        assertEquals(clan.getGold(), 130);
        verify(metadataRepository, times(1)).save(metadata);
    }

    @Test
    public void testReduceGold() {
        when(clanRepository.get(1)).thenReturn(new Clan(1, "TestClan", 100));

        Clan clan = clanRepository.get(1);
        clan.setMetadataConsumer(metadataRepository::save);

        ClanMetadata metadata = new ClanMetadata(TypeReward.VICTORY_BONUS);
        clan.reduceGold(30, metadata);

        assertEquals(clan.getGold(), 70);
        verify(metadataRepository, times(1)).save(metadata);
    }

    @Test
    public void testNotFound() {
        long nonExistingClanId = 999;
        when(clanRepository.get(nonExistingClanId)).thenThrow(
                new NotFoundException("Не найден клан с идентификатором: " + nonExistingClanId));

        assertThrows(NotFoundException.class, () -> clanRepository.get(nonExistingClanId));
    }
}
