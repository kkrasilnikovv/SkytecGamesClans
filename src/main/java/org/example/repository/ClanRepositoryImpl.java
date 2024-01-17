package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Clan;

import java.sql.*;

/**
 * Реализация интерфейса ClanRepository для взаимодействия с базой данных и операций над кланами.
 */
public class ClanRepositoryImpl implements ClanRepository {
    // SQL-запрос для получения клана по идентификатору
    private static final String GET_CLAN_BY_ID = "SELECT * FROM Clan WHERE id = ?";
    // URL базы данных
    private final String jdbcUrl;
    // Имя пользователя для подключения к базе данных
    private final String username;
    // Пароль для подключения к базе данных
    private final String password;

    /**
     * Конструктор класса.
     *
     * @param jdbcUrl  URL базы данных.
     * @param username Имя пользователя для подключения к базе данных.
     * @param password Пароль для подключения к базе данных.
     */
    public ClanRepositoryImpl(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * Получает соединение с базой данных, используя предоставленные параметры подключения.
     *
     * @return Объект Connection, представляющий соединение с базой данных.
     * @throws SQLException Возникает в случае, случае ошибки при установке соединения с базой данных.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    @Override
    public Clan get(long clanId) throws NotFoundException {
        Clan clan = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CLAN_BY_ID)) {
            preparedStatement.setLong(1, clanId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    clan = mapResultSetToClan(resultSet);
                } else {
                    throw new NotFoundException("Не найден клан с идентификатором:: " + clanId);
                }
            }
        } catch (SQLException e) {
            // Обработка исключений должна быть улучшена в реальном приложении(логированием или выбросом вверх)
            e.printStackTrace();
        }
        return clan;
    }

    /**
     * Осуществляет маппинг результата запроса ResultSet в объект {@link Clan}.
     *
     * @param resultSet Результат выполнения SQL-запроса с данными о клане.
     * @return Объект {@link Clan}, представляющий информацию о клане, полученную из ResultSet.
     * @throws SQLException Возникает в случае, случае ошибки при работе с результатом SQL-запроса.
     */
    private Clan mapResultSetToClan(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int gold = resultSet.getInt("gold");

        return new Clan(id, name, gold);
    }
}
