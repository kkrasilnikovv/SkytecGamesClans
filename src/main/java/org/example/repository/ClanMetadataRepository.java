package org.example.repository;

import org.example.model.ClanMetadata;

/**
 * Предоставляет методы для взаимодействия с базой данных, осуществляя операции с метаданными кланов.
 */
public interface ClanMetadataRepository {
    /**
     * Сохраняет метаданные о клане.
     *
     * @param metadata Метаданные о клане.
     */
    void save(ClanMetadata metadata);
}
