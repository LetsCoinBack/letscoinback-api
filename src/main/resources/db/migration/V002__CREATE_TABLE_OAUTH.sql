create table oauth_client_details (
  client_id varchar(255) primary key,
  resource_ids varchar(256),
  client_secret varchar(256),
  scope varchar(256),
  authorized_grant_types varchar(256),
  web_server_redirect_uri varchar(256),
  authorities varchar(256),
  access_token_validity integer,
  refresh_token_validity integer,
  additional_information varchar(4096),
  autoapprove varchar(256)
);

insert into oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, autoapprove)
values ('webmodel', null, '$2y$12$cImoy7EIFO9EzjcenRQKYOWAn6FIbBtrxRZtZOrSSVaNp.DfRX6CO', 'READ,WRITE', 'password', null, null, 21600, 2592000, null);
