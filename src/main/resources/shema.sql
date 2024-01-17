CREATE TABLE IF NOT EXISTS Clan
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gold INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS ClanMetadata
(
    id             SERIAL PRIMARY KEY,
    clan_id        BIGINT REFERENCES Clan (id) NOT NULL,
    old_gold_count INT                         NOT NULL,
    new_gold_count INT                         NOT NULL,
    type_reward    VARCHAR(255)                NOT NULL,
    description    TEXT,
    FOREIGN KEY (clan_id) REFERENCES Clan (id)
);
