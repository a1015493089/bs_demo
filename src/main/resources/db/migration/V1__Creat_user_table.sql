create table USER
(
	ID int default   NOT NULL auto_increment ,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREAT BIGINT,
	GMT_MODIFIED BIGINT,
	BIO VARCHAR(256),
	constraint USER_PK
		primary key (ID)
);

