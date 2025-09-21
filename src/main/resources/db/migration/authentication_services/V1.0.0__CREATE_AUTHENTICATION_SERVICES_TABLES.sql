--CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE SCHEMA IF NOT EXISTS authentication_services;

CREATE TABLE authentication_services.tb_user_account_roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL UNIQUE
);

CREATE TABLE authentication_services.tb_user_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    status VARCHAR(80) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'VERIFIED', 'BLOCKED')),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE authentication_services.tb_auth_roles (
    auth_credentials_id UUID NOT NULL,
    role_id INT NOT NULL,

    PRIMARY KEY (auth_credentials_id, role_id),
    FOREIGN KEY (auth_credentials_id) REFERENCES authentication_services.tb_user_accounts(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES authentication_services.tb_user_account_roles(id) ON DELETE CASCADE
);

CREATE TABLE authentication_services.tb_auth_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    revoked BOOLEAN NOT NULL DEFAULT true,
    token_type VARCHAR(100) NOT NULL,
    auth_credentials_id UUID NOT NULL,

    FOREIGN KEY (auth_credentials_id) REFERENCES authentication_services.tb_user_accounts(id) ON DELETE CASCADE
);


