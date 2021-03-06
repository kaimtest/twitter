create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
create table messages (id bigint not null, filename varchar(255), tag varchar(255), text varchar(255), user_id bigint, primary key (id)) engine=MyISAM;
create table user_role (user_id bigint not null, roles varchar(255)) engine=MyISAM;
create table usr (id bigint not null, activation_code varchar(255), active bit not null, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=MyISAM;
alter table messages add constraint FK5f19dxsguyxb310o6hn9ccmbt foreign key (user_id) references usr (id);
alter table user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr (id);
