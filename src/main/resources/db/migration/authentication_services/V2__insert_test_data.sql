INSERT INTO ticket_services.event (id, event_year, version)
VALUES ('88662959-7fe3-4687-92b9-7a587381466c', 2026, 3)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.ticket_batch (id,event_id,name,start_at,end_at,status)
VALUES ('4a9fbad4-5d8f-43e3-914e-38ba7e53eb75','88662959-7fe3-4687-92b9-7a587381466c','lote_de_abertura','2026-1-1','2026-4-2','ACTIVE')
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.ticket_batch_type (id,ticket_batch_id,ticket_type,price,total_quantity,sold_quantity,reserved_quantity)
VALUES ('5ca392a8-c3fc-423d-8d78-d8acd59452bf','4a9fbad4-5d8f-43e3-914e-38ba7e53eb75','FULL',4000,200,22,102)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.ticket_batch_type (id,ticket_batch_id,ticket_type,price,total_quantity,sold_quantity,reserved_quantity)
VALUES ('c32f4e7b-ecfd-4ef8-9832-f1f697915be8','4a9fbad4-5d8f-43e3-914e-38ba7e53eb75','HALF',2000,50,12,20)
ON CONFLICT (id) DO NOTHING;

--pedidos
INSERT INTO ticket_services.orders (id,customer_id,status,total_amount)
VALUES ('111479a5-342c-436f-8e05-1763595297da','729f4758-1107-4222-ab41-b78c6fb1bc7c','PENDING',10000)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.orders (id,customer_id,status,total_amount)
VALUES ('ec997558-aeca-4d17-8cdb-d9e7ed9828c3','8763925d-a6b0-4ba5-9a8e-59e1a415e3b5','PENDING',16000)
ON CONFLICT (id) DO NOTHING;

--items pedido
INSERT INTO ticket_services.order_item (id,order_id,ticket_batch_type_id,unit_price,quantity)
VALUES ('30a611af-ce69-4c4f-bd33-c228519f953f','111479a5-342c-436f-8e05-1763595297da','5ca392a8-c3fc-423d-8d78-d8acd59452bf',4000,2)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.order_item (id,order_id,ticket_batch_type_id,unit_price,quantity)
VALUES ('20af168b-7dae-450e-8488-17f7d3e5ddd8','111479a5-342c-436f-8e05-1763595297da','c32f4e7b-ecfd-4ef8-9832-f1f697915be8',2000,1)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.order_item (id,order_id,ticket_batch_type_id,unit_price,quantity)
VALUES ('6a6d2ba8-64ff-4e5a-9eba-dd98a1ad22cf','ec997558-aeca-4d17-8cdb-d9e7ed9828c3','5ca392a8-c3fc-423d-8d78-d8acd59452bf',4000,3)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.order_item (id,order_id,ticket_batch_type_id,unit_price,quantity)
VALUES ('6d669383-5cff-4817-a41d-8aedcd719eea','ec997558-aeca-4d17-8cdb-d9e7ed9828c3','c32f4e7b-ecfd-4ef8-9832-f1f697915be8',2000,2)
ON CONFLICT (id) DO NOTHING;

--payments
INSERT INTO ticket_services.payment (id,order_id,provider,method,provider_charge_id,provider_txid,provider_status,amount,status,
idempotency_key,failure_reason,raw_response)
VALUES ('438107d1-b966-4dd9-a391-02692498ebe3','111479a5-342c-436f-8e05-1763595297da','Efí-Bank','PIX','Not-used',
'd5b47ea26f664889b85976e5967799c1','lol',10000,'PENDING','1275bc7d-781b-4a65-be29-63327edb3f88','lol','{"message": "lol"}')
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.payment (id,order_id,provider,method,provider_charge_id,provider_txid,provider_status,amount,status,
idempotency_key,failure_reason,raw_response)
VALUES ('fd0d316e-b98a-492a-a16c-c18fde65fca7','ec997558-aeca-4d17-8cdb-d9e7ed9828c3','Efí-Bank','PIX','Not-used',
'038a90898b79438096e5e8228ad6ef3a','lol',16000,'PENDING','e2ef569d-df07-42a1-9143-74cb5d8107f0','LOL','{"message": "lol"}')
ON CONFLICT (id) DO NOTHING;




