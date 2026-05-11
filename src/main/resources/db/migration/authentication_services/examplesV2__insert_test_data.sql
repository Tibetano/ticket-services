INSERT INTO ticket_services.event (id, event_year, version)
VALUES ('88662959-7fe3-4687-92b9-7a587381466c', 2026, 3)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.ticket_batch (id,event_id,name,start_at,end_at,status)
VALUES ('4a9fbad4-5d8f-43e3-914e-38ba7e53eb75','88662959-7fe3-4687-92b9-7a587381466c','lote_de_abertura','2026-4-4','2026-6-6','ACTIVE')
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.ticket_batch_type (id,ticket_batch_id,ticket_type,price,total_quantity,sold_quantity,reserved_quantity)
VALUES ('5ca392a8-c3fc-423d-8d78-d8acd59452bf','4a9fbad4-5d8f-43e3-914e-38ba7e53eb75','FULL',3000,5,0,0)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.ticket_batch_type (id,ticket_batch_id,ticket_type,price,total_quantity,sold_quantity,reserved_quantity)
VALUES ('c32f4e7b-ecfd-4ef8-9832-f1f697915be8','4a9fbad4-5d8f-43e3-914e-38ba7e53eb75','HALF',3000,5,0,0)
ON CONFLICT (id) DO NOTHING;

-->pedidos
--INSERT INTO ticket_services.orders (id,customer_id,status,total_amount)
--VALUES ('111479a5-342c-436f-8e05-1763595297da','541636b3-012e-4f88-8a72-1dee3ca11f86','PAID',10000)
--ON CONFLICT (id) DO NOTHING;

--INSERT INTO ticket_services.orders (id,customer_id,status,total_amount)
--VALUES ('ec997558-aeca-4d17-8cdb-d9e7ed9828c3','541636b3-012e-4f88-8a72-1dee3ca11f86','PENDING',16000)
--ON CONFLICT (id) DO NOTHING;

-->items pedido
--INSERT INTO ticket_services.order_item (id,order_id,ticket_batch_type_id,unit_price,quantity)
--VALUES ('30a611af-ce69-4c4f-bd33-c228519f953f','111479a5-342c-436f-8e05-1763595297da','5ca392a8-c3fc-423d-8d78-d8acd59452bf',4000,1)
--ON CONFLICT (id) DO NOTHING;

--INSERT INTO ticket_services.order_item (id,order_id,ticket_batch_type_id,unit_price,quantity)
--VALUES ('20af168b-7dae-450e-8488-17f7d3e5ddd8','111479a5-342c-436f-8e05-1763595297da','c32f4e7b-ecfd-4ef8-9832-f1f697915be8',2000,1)
--ON CONFLICT (id) DO NOTHING;

--INSERT INTO ticket_services.order_item (id,order_id,ticket_batch_type_id,unit_price,quantity)
--VALUES ('6a6d2ba8-64ff-4e5a-9eba-dd98a1ad22cf','ec997558-aeca-4d17-8cdb-d9e7ed9828c3','5ca392a8-c3fc-423d-8d78-d8acd59452bf',4000,3)
--ON CONFLICT (id) DO NOTHING;

--INSERT INTO ticket_services.order_item (id,order_id,ticket_batch_type_id,unit_price,quantity)
--VALUES ('6d669383-5cff-4817-a41d-8aedcd719eea','ec997558-aeca-4d17-8cdb-d9e7ed9828c3','c32f4e7b-ecfd-4ef8-9832-f1f697915be8',2000,2)
--ON CONFLICT (id) DO NOTHING;

-->payments
--INSERT INTO ticket_services.payment (id,order_id,provider,method,provider_charge_id,provider_txid,provider_status,amount,status,
--idempotency_key,failure_reason,raw_response)
--VALUES ('438107d1-b966-4dd9-a391-02692498ebe3','111479a5-342c-436f-8e05-1763595297da','Efí-Bank','PIX','Not-used',
--'d5b47ea26f664889b85976e5967799c1','lol',10000,'PENDING','1275bc7d-781b-4a65-be29-63327edb3f88','lol','{"message": "lol"}')
--ON CONFLICT (id) DO NOTHING;

--INSERT INTO ticket_services.payment (id,order_id,provider,method,provider_charge_id,provider_txid,provider_status,amount,status,
--idempotency_key,failure_reason,raw_response)
--VALUES ('fd0d316e-b98a-492a-a16c-c18fde65fca7','ec997558-aeca-4d17-8cdb-d9e7ed9828c3','Efí-Bank','PIX','Not-used',
--'038a90898b79438096e5e8228ad6ef3a','lol',16000,'PENDING','e2ef569d-df07-42a1-9143-74cb5d8107f0','LOL','{"message": "lol"}')
--ON CONFLICT (id) DO NOTHING;

-->tickets
--INSERT INTO ticket_services.ticket (id,order_item_id,ticket_batch_type_id,owner_name,qr_code_hash,status,issued_at,checked_in_at) VALUES
--('3f5b8c91-7c2a-4a9e-b1c2-8f1d2a6e9b7c','30a611af-ce69-4c4f-bd33-c228519f953f','5ca392a8-c3fc-423d-8d78-d8acd59452bf','nomequalquer','3f5b8c91-7c2a-4a9e-b1c2-8f1d2a6e9b7cX','VALID','2026-05-01 19:36:24.115','2026-05-01 19:36:24.115');
--INSERT INTO ticket_services.ticket (id,order_item_id,ticket_batch_type_id,owner_name,qr_code_hash,status,issued_at,checked_in_at) VALUES
--('a9d4e2f7-1c3b-4d8f-9e2a-6b7c1d3e5f90','20af168b-7dae-450e-8488-17f7d3e5ddd8','c32f4e7b-ecfd-4ef8-9832-f1f697915be8','nomequalquer','a9d4e2f7-1c3b-4d8f-9e2a-6b7c1d3e5f90X','VALID','2026-05-01 19:36:24.115','2026-05-01 19:36:24.115');
--INSERT INTO ticket_services.ticket (id,order_item_id,ticket_batch_type_id,owner_name,qr_code_hash,status,issued_at,checked_in_at) VALUES
--('5c1a7e3b-9d2f-4b6c-a8e1-3f7d2c9a0b4e','6a6d2ba8-64ff-4e5a-9eba-dd98a1ad22cf','5ca392a8-c3fc-423d-8d78-d8acd59452bf','nomequalquer','5c1a7e3b-9d2f-4b6c-a8e1-3f7d2c9a0b4eX','VALID','2026-05-01 19:36:24.115','2026-05-01 19:36:24.115');
--INSERT INTO ticket_services.ticket (id,order_item_id,ticket_batch_type_id,owner_name,qr_code_hash,status,issued_at,checked_in_at) VALUES
--('d2e7f1c9-6b3a-4f8d-9c1e-7a5b2d3f8c6e','6a6d2ba8-64ff-4e5a-9eba-dd98a1ad22cf','5ca392a8-c3fc-423d-8d78-d8acd59452bf','nomequalquer','d2e7f1c9-6b3a-4f8d-9c1e-7a5b2d3f8c6eX','VALID','2026-05-01 19:36:24.115','2026-05-01 19:36:24.115');
--INSERT INTO ticket_services.ticket (id,order_item_id,ticket_batch_type_id,owner_name,qr_code_hash,status,issued_at,checked_in_at) VALUES
--('8b3d1f7a-2c9e-4a6f-b5d1-0e7c3a2f9d8b','6a6d2ba8-64ff-4e5a-9eba-dd98a1ad22cf','5ca392a8-c3fc-423d-8d78-d8acd59452bf','nomequalquer','8b3d1f7a-2c9e-4a6f-b5d1-0e7c3a2f9d8bX','VALID','2026-05-01 19:36:24.115','2026-05-01 19:36:24.115');
--INSERT INTO ticket_services.ticket (id,order_item_id,ticket_batch_type_id,owner_name,qr_code_hash,status,issued_at,checked_in_at) VALUES
--('c7a2e5d9-4f1b-4c8a-9d3e-2b6f7c1a5e0d','6d669383-5cff-4817-a41d-8aedcd719eea','c32f4e7b-ecfd-4ef8-9832-f1f697915be8','nomequalquer','c7a2e5d9-4f1b-4c8a-9d3e-2b6f7c1a5e0dX','VALID','2026-05-01 19:36:24.115','2026-05-01 19:36:24.115');
--INSERT INTO ticket_services.ticket (id,order_item_id,ticket_batch_type_id,owner_name,qr_code_hash,status,issued_at,checked_in_at) VALUES
--('1e9c3a7b-5d2f-4b8e-a6c1-9f0d3b7a2e5c','6d669383-5cff-4817-a41d-8aedcd719eea','c32f4e7b-ecfd-4ef8-9832-f1f697915be8','nomequalquer','1e9c3a7b-5d2f-4b8e-a6c1-9f0d3b7a2e5cX','VALID','2026-05-01 19:36:24.115','2026-05-01 19:36:24.115');



