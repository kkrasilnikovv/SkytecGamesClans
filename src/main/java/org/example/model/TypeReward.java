package org.example.model;

/**
 * Перечисление, представляющее различные типы вознаграждений или событий для кланов.
 */
public enum TypeReward {
    // Выполнение задания
    TASK_COMPLETION,

    // Пополнение участника клана
    MEMBER_ENLISTMENT,

    // Бонус за победу
    VICTORY_BONUS,

    // Поддержка союза
    ALLIANCE_SUPPORT,

    // Бонус за завоевание территории
    TERRITORY_CONQUEST_BONUS,

    // Потеря территории
    TERRITORY_LOSS,

    // Штраф за поражение
    DEFEAT_PENALTY,

    // Налог на развитие клана
    DEVELOPMENT_TAX,

    // Затраты на строительство
    CONSTRUCTION_COST
}
