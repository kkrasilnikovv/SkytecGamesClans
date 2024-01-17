package org.example.model;

/**
 * Отвечает за представление информации об изменении количества золота в казне клана в результате
 * различных видов вознаграждений или событий.
 */
public class ClanMetadata {
    // Идентификатор клана
    private long clanId;
    // Старое количество золота в казне клана
    private int oldGoldCount;
    // Новое количество золота в казне клана
    private int newGoldCount;
    // Тип вознаграждения
    private final TypeReward typeReward;
    // Описание события
    private String description;

    /**
     * Конструктор без описания события.
     *
     * @param typeReward Тип вознаграждения.
     */
    public ClanMetadata(TypeReward typeReward) {
        this.typeReward = typeReward;
    }

    /**
     * Конструктор с описанием события.
     *
     * @param typeReward  Тип вознаграждения.
     * @param description Описание события.
     */
    public ClanMetadata(TypeReward typeReward, String description) {
        this.typeReward = typeReward;
        this.description = description;
    }

    /**
     * Возвращает идентификатор клана.
     *
     * @return Идентификатор клана.
     */
    public long getClanId() {
        return clanId;
    }

    /**
     * Устанавливает значение идентификатора клана.
     *
     * @param clanId Идентификатора клана.
     */
    public void setClanId(long clanId) {
        this.clanId = clanId;
    }

    /**
     * Возвращает старое количество золота в казне клана.
     *
     * @return Старое количество золота.
     */
    public int getOldGoldCount() {
        return oldGoldCount;
    }

    /**
     * Устанавливает старое количество золота в казне клана.
     *
     * @param oldGoldCount Старое количество золота.
     */
    public void setOldGoldCount(int oldGoldCount) {
        this.oldGoldCount = oldGoldCount;
    }

    /**
     * Возвращает новое количество золота в казне клана.
     *
     * @return Новое количество золота.
     */
    public int getNewGoldCount() {
        return newGoldCount;
    }

    /**
     * Устанавливает новое количество золота в казне клана.
     *
     * @param newGoldCount Новое количество золота.
     */
    public void setNewGoldCount(int newGoldCount) {
        this.newGoldCount = newGoldCount;
    }

    /**
     * Возвращает тип вознаграждения.
     *
     * @return Тип вознаграждения.
     */
    public TypeReward getTypeReward() {
        return typeReward;
    }

    /**
     * Возвращает описание события.
     *
     * @return Описание события.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает новое описание события.
     *
     * @param description Новое количество золота.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}