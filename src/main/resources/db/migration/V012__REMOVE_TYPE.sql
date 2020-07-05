alter table wallet alter column transaction_type type varchar;
alter table wallet alter column status type varchar;
alter table wallet alter column movimentation_type type varchar;

drop type movimentation_type;
drop type status_transacion;
drop type transaction_type;