CREATE TYPE ticket_batch_status AS ENUM ('ACTIVE', 'FINISHED', 'HIDDEN');
CREATE TYPE ticket_type_enum AS ENUM ('FULL', 'HALF', 'SOLIDARITY');
CREATE TYPE order_status AS ENUM ('PENDING', 'PAID', 'EXPIRED', 'CANCELED');
CREATE TYPE ticket_status AS ENUM ('VALID', 'USED', 'CANCELED');


CREATE TYPE payment_status AS ENUM ('PENDING', 'WAITING_PAYMENT', 'CONFIRMED', 'FAILED', 'CANCELED', 'REFUNDED');
CREATE TYPE payment_method AS ENUM ('PIX', 'BOLETO', 'CREDIT_CARD');



CREATE SCHEMA IF NOT EXISTS ticket_services;



CREATE TABLE ticket_services.event (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_year      INTEGER NOT NULL,
    version         INTEGER NOT NULL
);



CREATE TABLE ticket_services.ticket_batch (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id        UUID NOT NULL,
    name            VARCHAR(120) NOT NULL,
    start_at        TIMESTAMP NOT NULL,
    end_at          TIMESTAMP NOT NULL,
    status          ticket_batch_status NOT NULL DEFAULT 'HIDDEN',

    CONSTRAINT chk_ticket_batch_dates CHECK (end_at > start_at),

    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES ticket_services.event(id) ON DELETE CASCADE
);

--CREATE INDEX idx_ticket_batch_event_id ON ticket_batch(event_id);
--CREATE INDEX idx_ticket_batch_status ON ticket_batch(status);



CREATE TABLE ticket_services.ticket_batch_type (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ticket_batch_id     UUID NOT NULL,
    ticket_type         ticket_type_enum NOT NULL,
    price               INTEGER NOT NULL, -- em centavos
    total_quantity      INTEGER NOT NULL,
    sold_quantity       INTEGER NOT NULL DEFAULT 0,
    reserved_quantity   INTEGER NOT NULL DEFAULT 0,

    CONSTRAINT fk_tbt_batch FOREIGN KEY (ticket_batch_id) REFERENCES ticket_services.ticket_batch(id) ON DELETE CASCADE,

    CONSTRAINT uq_batch_ticket_type UNIQUE (ticket_batch_id, ticket_type),

    CONSTRAINT chk_price_positive CHECK (price >= 0),
    CONSTRAINT chk_total_quantity_positive CHECK (total_quantity >= 0),
    CONSTRAINT chk_sold_quantity_positive CHECK (sold_quantity >= 0),
    CONSTRAINT chk_reserved_quantity_positive CHECK (reserved_quantity >= 0),
    CONSTRAINT chk_quantities_valid CHECK (sold_quantity + reserved_quantity <= total_quantity)
);

--CREATE INDEX idx_tbt_batch_id ON ticket_batch_type(ticket_batch_id);



CREATE TABLE ticket_services.orders (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_id     UUID NOT NULL,
    status          order_status NOT NULL DEFAULT 'PENDING',
    total_amount    INTEGER NOT NULL, -- em centavos
    expires_at      TIMESTAMP,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_total_amount_positive CHECK (total_amount >= 0),
    CONSTRAINT chk_order_expiration CHECK (expires_at IS NULL OR expires_at > created_at)
);

--CREATE INDEX idx_orders_customer_id ON orders(customer_id);
--CREATE INDEX idx_orders_status ON orders(status);
--CREATE INDEX idx_orders_expires_at ON orders(expires_at);



CREATE TABLE ticket_services.order_item (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id                UUID NOT NULL,
    ticket_batch_type_id    UUID NOT NULL,
    unit_price              INTEGER NOT NULL, -- preço no momento da compra
    quantity                INTEGER NOT NULL,

    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES ticket_services.orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_tbt FOREIGN KEY (ticket_batch_type_id) REFERENCES ticket_services.ticket_batch_type(id),

    CONSTRAINT chk_unit_price_positive CHECK (unit_price >= 0),
    CONSTRAINT chk_quantity_positive CHECK (quantity > 0)
);

--CREATE INDEX idx_order_item_order_id ON order_item(order_id);
--CREATE INDEX idx_order_item_tbt_id ON order_item(ticket_batch_type_id);



CREATE TABLE ticket_services.ticket (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_item_id           UUID NOT NULL,
    ticket_batch_type_id    UUID NOT NULL,
    owner_name              VARCHAR(150),
    qr_code_hash            VARCHAR(255) NOT NULL UNIQUE,
    status                  ticket_status NOT NULL DEFAULT 'VALID',
    issued_at               TIMESTAMP NOT NULL DEFAULT NOW(),
    checked_in_at           TIMESTAMP,

    CONSTRAINT fk_ticket_order_item FOREIGN KEY (order_item_id) REFERENCES ticket_services.order_item(id) ON DELETE CASCADE,
    CONSTRAINT fk_ticket_tbt FOREIGN KEY (ticket_batch_type_id) REFERENCES ticket_services.ticket_batch_type(id),

    CONSTRAINT chk_checking_after_issue CHECK (checked_in_at IS NULL OR checked_in_at >= issued_at)

);

--CREATE INDEX idx_ticket_order_item_id ON ticket(order_item_id);
--CREATE INDEX idx_ticket_tbt_id ON ticket(ticket_batch_type_id);
--CREATE INDEX idx_ticket_status ON ticket(status);






CREATE TABLE ticket_services.payment (
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id                UUID NOT NULL,

    provider                VARCHAR(50) NOT NULL, -- EFI_BANK
    method                  payment_method NOT NULL,

    provider_charge_id      VARCHAR(150),  -- id da cobrança
    provider_txid           VARCHAR(150),  -- txid do PIX
    provider_status         VARCHAR(50),   -- status original do banco

    amount                  INTEGER NOT NULL,
    status                  payment_status NOT NULL DEFAULT 'PENDING',

    idempotency_key         VARCHAR(150) UNIQUE,
    failure_reason          TEXT,

    raw_response            JSONB,

    expires_at              TIMESTAMP,
    confirmed_at            TIMESTAMP,

    created_at              TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES ticket_services.orders(id) ON DELETE CASCADE
);

--CREATE INDEX idx_payment_order_id ON ticket_services.payment(order_id);
--CREATE INDEX idx_payment_provider_txid ON ticket_services.payment(provider_txid);
--CREATE INDEX idx_payment_status ON ticket_services.payment(status);


