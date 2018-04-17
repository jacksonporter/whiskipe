CREATE TABLE users(
	ID int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
	username nvarchar(100) NOT NULL,
	firstname nvarchar(100),
	lastname nvarchar(100),
	active bit NOT NULL
)

CREATE TABLE items(
	ID int IDENTITY(1, 1) NOT NULL,
	name nvarchar(100) NOT NULL,
	quantity int NOT NULL,
	size real NOT NULL,
	userid int FOREIGN KEY REFERENCES users(ID) NOT NULL
);