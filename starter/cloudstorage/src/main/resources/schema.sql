CREATE TABLE IF NOT EXISTS FILES(
	fileId INT NOT NULL AUTO_INCREMENT,
	fileName varchar(100) NOT NULL,
	path varchar(225) NOT NULL,
    userId INT NOT NULL,
      PRIMARY KEY (fileId)
)
CREATE TABLE IF NOT EXISTS CREDENTIALS(
	credentialId INT NOT NULL AUTO_INCREMENT,
	url varchar(255) NOT NULL,
	username varchar(100) NOT NULL,
	keyz varchar(100) NOT NULL,
	password varchar(100) NOT NULL,
    userId INT NOT NULL,
      PRIMARY KEY (credentialId)
)
CREATE TABLE IF NOT EXISTS NOTES(
	noteId INT NOT NULL AUTO_INCREMENT,
	noteTitle varchar(100) NOT NULL,
    noteDescription varchar(255) NOT NULL,
    userId INT NOT NULL,
      PRIMARY KEY (noteId)
)
CREATE TABLE IF NOT EXISTS USERS(
	userId INT NOT NULL AUTO_INCREMENT,
	username varchar(255) NOT NULL,
    salt varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    firstName varchar(255) NOT NULL,
    lastName varchar(255) NOT NULL,
      PRIMARY KEY (userId)
)