create table public.user (
   id SERIAL PRIMARY KEY,
   name VARCHAR not null,
   email VARCHAR not null,
   cel varchar,
   password varchar not null,
   wallet varchar,
   available boolean not null default true,
   authority VARCHAR not null default  'User',
   photo varchar,
   indicate int,
   CONSTRAINT unq_email UNIQUE(email)
);
