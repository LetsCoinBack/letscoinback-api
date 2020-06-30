create table public.notification (
  id serial primary key not null,
  user_id SERIAL references public.user (id),
  notification_type VARCHAR not null,
  title varchar not null,
  body varchar not null,
  date TIMESTAMP DEFAULT NOW(),
  read boolean default false
);
