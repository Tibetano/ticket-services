INSERT INTO ticket_services.event (id, event_year, version)
VALUES ('88662959-7fe3-4687-92b9-7a587381466c', 2026, 3)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.ticket_batch (id,event_id,name,start_at,end_at,status)
VALUES ('4a9fbad4-5d8f-43e3-914e-38ba7e53eb75','88662959-7fe3-4687-92b9-7a587381466c','lote_de_abertura','2026-4-1','2026-4-2','ACTIVE')
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.ticket_batch_type (id,ticket_batch_id,ticket_type,price,total_quantity,sold_quantity,reserved_quantity)
VALUES ('5ca392a8-c3fc-423d-8d78-d8acd59452bf','4a9fbad4-5d8f-43e3-914e-38ba7e53eb75','FULL',4000,200,22,102)
ON CONFLICT (id) DO NOTHING;

INSERT INTO ticket_services.ticket_batch_type (id,ticket_batch_id,ticket_type,price,total_quantity,sold_quantity,reserved_quantity)
VALUES ('c32f4e7b-ecfd-4ef8-9832-f1f697915be8','4a9fbad4-5d8f-43e3-914e-38ba7e53eb75','HALF',2000,50,12,20)
ON CONFLICT (id) DO NOTHING;
