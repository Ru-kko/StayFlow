CREATE TABLE country (
    countryId UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    countryName VARCHAR(255) NOT NULL
);

CREATE TABLE city (
    cityId UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    cityName VARCHAR(255) NOT NULL,
    optimizedName VARCHAR(255) NOT NULL,
    countryId UUID NOT NULL,
    CONSTRAINT fk_country FOREIGN KEY (countryId) REFERENCES country(countryId) ON DELETE CASCADE
);

INSERT INTO country (countryId, countryName) VALUES
    ('3f5a8c1e-1a23-4f67-9b8e-5c6d7e8f9a10', 'Colombia'),
    ('2d4b7c9e-5f61-4a23-8c9d-7e6f5b4a3c2d', 'Mexico'),
    ('6a5d4c3b-8e9f-1f72-3d6c-5b4a2e7f8c9d', 'Brazil'),
    ('7e8f6c5d-3a4b-2d9e-1f72-6b5c4a8f9c7d', 'Panama');