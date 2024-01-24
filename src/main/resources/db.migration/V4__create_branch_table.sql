CREATE TABLE branch
(
    id              BIGSERIAL PRIMARY KEY,
    uuid            UUID,
    created_on      TIMESTAMP WITH TIME ZONE,
    name            VARCHAR(255) NOT NULL,
    last_commit_sha VARCHAR(255) NOT NULL,
    repo_id         BIGINT
);

ALTER TABLE branch
    ADD CONSTRAINT FK_BRANCH_ON_REPO FOREIGN KEY (repo_id) REFERENCES repo (id);