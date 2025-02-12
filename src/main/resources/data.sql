
CREATE TABLE IF NOT EXISTS `user` (
                                      idx BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      username VARCHAR(255),
    nickname VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    phone INT,
    sex VARCHAR(10),
    enabled BOOLEAN,
    UNIQUE KEY unique_email (email)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS orders (
                                      idx BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      orderId INT,
                                      productName VARCHAR(255),
    paymentDate DATETIME,
    user_idx BIGINT,
    CONSTRAINT fk_orders_user FOREIGN KEY (user_idx) REFERENCES `user`(idx)
    ON DELETE SET NULL ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS email_verify (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            uuid VARCHAR(255) NOT NULL,
    user_idx BIGINT,
    CONSTRAINT fk_email_verify_user FOREIGN KEY (user_idx) REFERENCES `user`(idx)
    ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;