CREATE TABLE IF NOT EXISTS `log` (
  `DATE_TIME` datetime NOT NULL,
  `IP` varchar(55) NOT NULL,
  `USER_AGENT` varchar(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `blocked_ip` (
  `IP` varchar(55) NOT NULL,
  `COMMENT` varchar(255) DEFAULT NULL
);

