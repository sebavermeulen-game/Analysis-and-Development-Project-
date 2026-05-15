/*
----------------------------------
- Create your migration scripts here
----------------------------------
*/

DROP TABLE IF EXISTS subscriptions;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS orders;

CREATE TABLE users
(
    id               VARCHAR not null PRIMARY KEY,
    SubscriptionId   INTEGER,
    FOREIGN KEY (SubscriptionId) REFERENCES subscriptions(subscriptionId)
);

CREATE TABLE subscriptions
(
    subscriptionId       INTEGER  not null primary key autoincrement,
    subscriptionType     VARCHAR(255) NOT NULL CHECK (subscriptionType IN ('Individual User', 'Businesses', 'Government & Large Organisations')),
    subscriptionPlan     VARCHAR(255) NOT NULL,
    hours                INTEGER NOT NULL,
    range                INTEGER NOT NULL,
    intensity            VARCHAR(255) NOT NULL,
    extraInformation     VARCHAR(255),
    requestInformation   VARCHAR(255),
    subscriptionSupport  VARCHAR(255) NOT NULL,
    price                DECIMAL(10, 2) NOT NULL
);

CREATE TABLE orders
(
    orderId      INTEGER  not null primary key autoincrement,
    latitude     DOUBLE   NOT NULL,
    longitude    DOUBLE   NOT NULL,
    intensity    interval [0, 100] NOT NULL,
    startTime    DATETIME NOT NULL,
    endTime      DATETIME NOT NULL,
    range        INTEGER  NOT NULL,
    userid       VARCHAR  NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id)
);
