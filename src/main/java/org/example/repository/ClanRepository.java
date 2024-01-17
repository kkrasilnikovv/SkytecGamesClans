package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Clan;

/**
 * Предоставляет методы для взаимодействия с базой данных, осуществляя операции с кланами.
 */
public interface ClanRepository {
    /**
     * Возвращает информацию о клане по его идентификатору.
     *
     * @param clanId Идентификатор клана, который нужно найти.
     * @return Объект {@link Clan}, представляющий информацию о клане.
     * @throws NotFoundException Возникает в случае, если клан с указанным идентификатором не найден.
     */
    Clan get(long clanId) throws NotFoundException;

}
