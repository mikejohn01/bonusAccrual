-- schema owner
CREATE USER bonus_accrual WITH password 'bonus_accrual';

-- schema user
-- CREATE USER bonus_accrual_sh WITH password 'bonus_accrual_sh';

-- create schema
CREATE SCHEMA bonus_accrual AUTHORIZATION bonus_accrual;

GRANT USAGE, CREATE ON SCHEMA bonus_accrual TO bonus_accrual;

ALTER DEFAULT PRIVILEGES FOR USER bonus_accrual IN SCHEMA bonus_accrual GRANT
    SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON TABLES TO bonus_accrual;

ALTER DEFAULT PRIVILEGES FOR USER bonus_accrual IN SCHEMA bonus_accrual GRANT USAGE ON SEQUENCES TO bonus_accrual;
ALTER DEFAULT PRIVILEGES FOR USER bonus_accrual IN SCHEMA bonus_accrual GRANT EXECUTE ON FUNCTIONS TO bonus_accrual;

alter user bonus_accrual set search_path = 'bonus_accrual';

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA bonus_accrual TO bonus_accrual;