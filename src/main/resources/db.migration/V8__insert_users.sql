INSERT INTO users (email, password, authorities, enabled)
VALUES
    ('dominik', '12345', '{ROLE_ADMIN, ROLE_USER}', true),
    ('test', '12345', '{ROLE_USER}', true);
