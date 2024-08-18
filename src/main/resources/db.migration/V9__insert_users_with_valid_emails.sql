INSERT INTO users (email, password, authorities, enabled)
VALUES
    ('dominik.bytnar@gmail.com', '$2a$10$PoAIsQAIlCajKiA8Km2Z3OrE7sRbNUma2jJITd/uzxO2jubQEm75W', '{ROLE_ADMIN, ROLE_USER}', true)
-- this user has same email as email claim that will be contained in jwt token
-- which means that we have user which logged using google oauth2 in database and we can get his roles