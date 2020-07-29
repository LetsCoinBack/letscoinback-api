update public."user" set authority = 'MASTER' where id = 1;

create table public.partner_segment (
  id serial primary key not null,
  description varchar not null
);

alter table public.partner add column partner_segment_id serial;

insert into public.partner_segment values (1, 'Loja de Departamento');

update public.partner set partner_segment_id = 1;

alter table public.partner ADD CONSTRAINT fk_partner_segment_id FOREIGN KEY (partner_segment_id) REFERENCES public.partner_segment (id);
