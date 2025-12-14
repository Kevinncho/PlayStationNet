CREATE TABLE `User` (
  `idUser` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(255) UNIQUE NOT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255),
  `lastName` varchar(255),
  `dateOfBirth` date,
  `is_admin` boolean,
  `created_at` timestamp
);

CREATE TABLE `Role` (
  `idRole` int PRIMARY KEY,
  `roleName` varchar(255)
);

CREATE TABLE `UserRole` (
  `user_id` int,
  `role_id` int
);

CREATE TABLE `Game` (
  `idGame` int PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(255),
  `description` text,
  `price` decimal(10,2),
  `coverImage` varchar(255),
  `releaseDate` date
);

CREATE TABLE `Category` (
  `idCategory` int PRIMARY KEY,
  `name` varchar(255)
);

CREATE TABLE `GameCategory` (
  `game_id` int,
  `category_id` int
);

CREATE TABLE `Genre` (
  `idGenre` int PRIMARY KEY,
  `name` varchar(255)
);

CREATE TABLE `GameGenre` (
  `game_id` int,
  `genre_id` int
);

CREATE TABLE `Orders` (
  `idOrder` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `dateCreated` datetime,
  `totalAmount` decimal(10,2),
  `status` varchar(255)
);

CREATE TABLE `OrderItem` (
  `idOrderItem` int PRIMARY KEY AUTO_INCREMENT,
  `order_id` int,
  `game_id` int,
  `priceAtPurchase` decimal(10,2)
);

CREATE TABLE `Review` (
  `idReview` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `game_id` int,
  `rating` int,
  `content` text,
  `created_at` timestamp
);

CREATE TABLE `SubscriptionPlan` (
  `idPlan` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) COMMENT 'FREE, BASIC, ADVANCED',
  `price` decimal(10,2),
  `durationDays` int COMMENT '30 for monthly, 365 for yearly',
  `discountPercentage` decimal(5,2) COMMENT 'Ej: 0.10 for 10% off games',
  `description` text
);

CREATE TABLE `UserSubscription` (
  `idUserSubscription` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `plan_id` int,
  `startDate` datetime,
  `endDate` datetime COMMENT 'Crucial to check access',
  `isActive` boolean,
  `autoRenewal` boolean
);

CREATE TABLE `UserPaymentMethod` (
  `idPaymentMethod` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `paymentType` varchar(255) COMMENT 'CREDIT_CARD, PAYPAL',
  `provider` varchar(255) COMMENT 'STRIPE, PAYPAL_API',
  `token` varchar(255) COMMENT 'El token seguro que te da la pasarela (ej: token_12345)',
  `last4Digits` varchar(255) COMMENT 'Solo para mostrar al usuario: **** 4242',
  `isDefault` boolean
);

CREATE TABLE `Transaction` (
  `idTransaction` int PRIMARY KEY AUTO_INCREMENT,
  `order_id` int,
  `subscription_id` int,
  `payment_method_id` int,
  `referenceId` varchar(255),
  `amount` decimal(10,2),
  `currency` varchar(255) DEFAULT 'USD',
  `status` varchar(255) COMMENT 'SUCCESS, FAILED, REFUNDED',
  `date` datetime,
  `description` varchar(255) COMMENT 'Ej: ''Compra de Halo'', ''Renovación Mensual Basic'''
);

ALTER TABLE `UserRole` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`idUser`);

ALTER TABLE `UserRole` ADD FOREIGN KEY (`role_id`) REFERENCES `Role` (`idRole`);

ALTER TABLE `GameCategory` ADD FOREIGN KEY (`game_id`) REFERENCES `Game` (`idGame`);

ALTER TABLE `GameCategory` ADD FOREIGN KEY (`category_id`) REFERENCES `Category` (`idCategory`);

ALTER TABLE `GameGenre` ADD FOREIGN KEY (`game_id`) REFERENCES `Game` (`idGame`);

ALTER TABLE `GameGenre` ADD FOREIGN KEY (`genre_id`) REFERENCES `Genre` (`idGenre`);

ALTER TABLE `Orders` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`idUser`);

ALTER TABLE `OrderItem` ADD FOREIGN KEY (`order_id`) REFERENCES `Orders` (`idOrder`);

ALTER TABLE `OrderItem` ADD FOREIGN KEY (`game_id`) REFERENCES `Game` (`idGame`);

ALTER TABLE `Review` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`idUser`);

ALTER TABLE `Review` ADD FOREIGN KEY (`game_id`) REFERENCES `Game` (`idGame`);

ALTER TABLE `UserSubscription` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`idUser`);

ALTER TABLE `UserSubscription` ADD FOREIGN KEY (`plan_id`) REFERENCES `SubscriptionPlan` (`idPlan`);

ALTER TABLE `UserPaymentMethod` ADD FOREIGN KEY (`user_id`) REFERENCES `User` (`idUser`);

ALTER TABLE `Transaction` ADD FOREIGN KEY (`order_id`) REFERENCES `Orders` (`idOrder`);

ALTER TABLE `Transaction` ADD FOREIGN KEY (`subscription_id`) REFERENCES `UserSubscription` (`idUserSubscription`);

ALTER TABLE `Transaction` ADD FOREIGN KEY (`payment_method_id`) REFERENCES `UserPaymentMethod` (`idPaymentMethod`);

ALTER TABLE `User` ADD FOREIGN KEY (`is_admin`) REFERENCES `Review` (`idReview`);
