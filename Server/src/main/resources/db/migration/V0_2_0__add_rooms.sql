CREATE TABLE image (
  imageId UUID PRIMARY KEY DEFAULT gen_random_uuid (),
  name VARCHAR(255) NOT NULL,
  contentType VARCHAR(255) NOT NULL,
  url VARCHAR(1024)
);

CREATE TABLE room (
  roomId UUID PRIMARY KEY DEFAULT gen_random_uuid (),
  name VARCHAR(255) NOT NULL,
  description TEXT,
  beds INTEGER,
  lon DOUBLE PRECISION,
  lat DOUBLE PRECISION,
  cityId UUID NOT NULL,
  imageId UUID,
  enabled BOOLEAN DEFAULT TRUE,
  CONSTRAINT fk_room_city FOREIGN KEY (cityId) REFERENCES city(cityId) ON DELETE CASCADE,
  CONSTRAINT fk_image FOREIGN KEY (imageId) REFERENCES image(imageId)
);