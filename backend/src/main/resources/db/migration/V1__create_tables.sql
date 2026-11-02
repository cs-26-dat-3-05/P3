-- All tables from the design chapter in ER diagram are initialized --

-- Transaction table - the subset marked by mastercard payment services its a failed payout to this case --
CREATE TABLE Transaction(
    TransactionID INT PRIMARY KEY,
    ULID CHAR(26) NOT NULL UNIQUE, 
    BookingDate DATE NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    Currency CHAR(3) NOT NULL,
    RemittanceText TEXT NULL,
    CreatedAt TIMESTAMP NOT NULL
);
-- Organizer table --
CREATE TABLE Organizer(
        OrganizerID VARCHAR(30) PRIMARY KEY,
        Name VARCHAR(100) NOT NULL,
        Email TEXT NOT NULL,
        Phone VARCHAR(50) NULL,
        RegistrationNumber VARCHAR(50) NOT NULL,
        AccountNumber VARCHAR(50) NOT NULL
);
-- User table. I used user_Table as name, because user is a keyword in sql --
    CREATE TABLE User_Table(
    UserID SMALLINT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL
);

-- Case table --
CREATE TABLE Cases(
        CaseID INT PRIMARY KEY,
        OrganizerID VARCHAR(30) NOT NULL REFERENCES Organizer(OrganizerID), 
        TransactionID INT NOT NULL REFERENCES Transaction(TransactionID), 
        Status SMALLINT NOT NULL,
        EventID VARCHAR(30) NOT NULL, 
        RejectedRegistrationNumber VARCHAR(30) NOT NULL,
        RejectedAccountNumber VARCHAR(30) NOT NULL,
        Amount DECIMAL(10,2) NOT NULL,
        CreatedAt TIMESTAMP NOT NULL
);

-- Status table --
    CREATE TABLE Status(
    StatusID INT PRIMARY KEY, 
    CaseID INT NOT NULL REFERENCES Cases(CaseID),
    UserID SMALLINT NOT NULL REFERENCES User_Table(UserID),
    State SMALLINT NOT NULL,
    CreatedAt TIMESTAMP NOT NULL
);

-- Retransfer table --
CREATE TABLE Retransfer(
    RetransferID INT PRIMARY KEY,
    CaseID INT NOT NULL REFERENCES Cases(CaseID),
    RegistrationNumber VARCHAR(50) NOT NULL,
    AccountNumber VARCHAR(50) NOT NULL,
    Amount DECIMAL(10,2) NOT NULL,
    PaymentID VARCHAR(50) NULL,
    StatusCode VARCHAR(50) NULL,
    CreatedAt TIMESTAMP NOT NULL
);
-- Note table -- 
    CREATE TABLE Note(
    NoteID INT PRIMARY KEY,
    CaseID INT NOT NULL REFERENCES Cases(CaseID),
    UserID SMALLINT NOT NULL REFERENCES User_Table(UserID),
    Content TEXT NOT NULL,
    CreatedAt TIMESTAMP NOT NULL
);



