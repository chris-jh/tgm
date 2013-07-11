CREATE TABLE people (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
    version INTEGER DEFAULT 0 NOT NULL,
    person VARCHAR(26)
);

CREATE TABLE emulator (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
    version INTEGER DEFAULT 0 NOT NULL,
    name VARCHAR(100),
    PRIMARY KEY(id)
);

CREATE TABLE emulator_game (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
    version INTEGER DEFAULT 0 NOT NULL,
    emulator_ref INTEGER NOT NULL,
    name VARCHAR(100),
    rom_location VARCHAR(100),
    PRIMARY KEY(id),
    FOREIGN KEY (emulator_ref) REFERENCES emulator(id)
);