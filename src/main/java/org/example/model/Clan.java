package org.example.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Представляет информацию о клане.
 * Использует объект блокировки для обеспечения безопасности при параллельном доступе к данным клана.
 */
public class Clan {
    // Идентификатор клана
    private final long id;
    // Имя клана
    private final String name;
    // Количество золота в казне клана
    private final AtomicInteger gold;
    // Сохраняет примененные изменения
    private Consumer<ClanMetadata> metadataConsumer;

    /**
     * Конструктор класса.
     *
     * @param id   Идентификатор клана.
     * @param name Имя клана.
     * @param gold Количество золота в казне клана.
     */
    public Clan(long id, String name, int gold) {
        this.id = id;
        this.name = name;
        this.gold = new AtomicInteger(gold);
    }

    /**
     * Возвращает идентификатор клана.
     *
     * @return Идентификатор клана.
     */
    public long getId() {
        return id;
    }

    /**
     * Возвращает имя клана.
     *
     * @return Имя клана.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает количество золота в казне клана.
     *
     * @return Количество золота в казне клана.
     */
    public int getGold() {
        return gold.get();
    }

    /**
     * Добавляет золото казне клана и сохраняет метаданные об изменении.
     *
     * @param gold     Добавляемое количество золота.
     * @param metadata Метаданные об изменении количества золота.
     */
    public void addGold(int gold, ClanMetadata metadata) {
        synchronized (this.gold) {
            metadata.setClanId(this.id);
            metadata.setOldGoldCount(this.gold.get());
            this.gold.set(this.gold.get() + gold);
            metadata.setNewGoldCount(this.gold.get());
            metadataConsumer.accept(metadata);
        }
    }

    /**
     * Вычитает золото из казны клана и сохраняет метаданные об изменении.
     *
     * @param gold     Вычитаемое количество золота.
     * @param metadata Метаданные об изменении количества золота.
     */
    public void reduceGold(int gold, ClanMetadata metadata) {
        synchronized (this.gold) {
            metadata.setClanId(this.id);
            metadata.setOldGoldCount(this.gold.get());
            if (this.gold.get() - gold >= 0) {
                this.gold.set(this.gold.get() - gold);
            } else {
                metadata.setDescription("Недостаточно золото для списания. Попытка списания:" + metadata.getDescription());
            }
            metadata.setNewGoldCount(this.gold.get());
            metadataConsumer.accept(metadata);
        }
    }

    /**
     * Устанавливает функцию-потребителя, которая будет принимать метаданные {@link ClanMetadata} об изменении клана.
     * Эта функция вызывается при каждом изменении количества золота в казне клана.
     * Предназначена для сохранения данных об изменении клана внешним обработчиком.
     *
     * @param metadataConsumer Функция-потребитель метаданных об изменении клана.
     */
    public void setMetadataConsumer(Consumer<ClanMetadata> metadataConsumer) {
        this.metadataConsumer = metadataConsumer;
    }
}