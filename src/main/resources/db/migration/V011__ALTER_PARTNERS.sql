ALTER TABLE public.partner ALTER COLUMN user_cashback TYPE NUMERIC(9,2);
ALTER TABLE public.partner ALTER COLUMN cashback TYPE NUMERIC(9,2);
ALTER TABLE public.provider ADD COLUMN last_update timestamp;
ALTER TABLE public.provider ADD COLUMN url varchar;
UPDATE public.provider SET url = 'https://api.lomadee.com/api/lomadee/';
ALTER TABLE public.provider DROP COLUMN token;
