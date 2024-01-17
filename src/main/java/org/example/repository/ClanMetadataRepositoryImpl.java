package org.example.repository;

import org.example.model.ClanMetadata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Реализация интерфейса ClanMetadataRepository, предоставляющая метод для асинхронного сохранения метаданных о клане в базе данных.
 */
public class ClanMetadataRepositoryImpl implements ClanMetadataRepository {
    // SQL-запрос для сохранения метаданных в базе данных
    private static final String SAVE_METADATA_QUERY = "INSERT INTO ClanMetadata (clan_id, old_gold_count, new_gold_count, type_reward, description) VALUES (?, ?, ?, ?, ?)";
    // URL базы данных
    private final String jdbcUrl;
    // Имя пользователя для подключения к базе данных
    private final String username;
    // Пароль для подключения к базе данных
    private final String password;
    // Очередь для асинхронного сохранения метаданных
    private final Queue<ClanMetadata> queue;
    // Пул потоков для асинхронного сохранения метаданных
    private final ExecutorService executorService;

    /**
     * Конструктор класса.
     *
     * @param jdbcUrl  URL базы данных.
     * @param username Имя пользователя для подключения к базе данных.
     * @param password Пароль для подключения к базе данных.
     */
    public ClanMetadataRepositoryImpl(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.queue = new ConcurrentLinkedQueue<>();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void save(ClanMetadata metadata) {
        queue.add(metadata);
        executorService.submit(this::processQueue);
    }

    /**
     * Обрабатывает очередь метаданных и сохраняет их в базу данных.
     */
    private void processQueue() {
        while (!queue.isEmpty()) {
            ClanMetadata metadata = queue.poll();
            if (metadata != null) {
                saveToDatabase(metadata);
            }
        }
    }

    /**
     * Сохраняет метаданные о клане в базу данных.
     *
     * @param metadata Метаданные о клане.
     */
    private void saveToDatabase(ClanMetadata metadata) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(SAVE_METADATA_QUERY)) {

            statement.setLong(1, metadata.getClanId());
            statement.setInt(2, metadata.getOldGoldCount());
            statement.setInt(3, metadata.getNewGoldCount());
            statement.setString(4, metadata.getTypeReward().toString());
            statement.setString(5, metadata.getDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            // Обработка исключений должна быть улучшена в реальном приложении(логированием или выбросом вверх)
            e.printStackTrace();
        }
    }
}
