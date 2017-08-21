SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE IF NOT EXISTS `mc_users` (
  `xf_user_id` int(11) NOT NULL,
  `xf_username` varchar(255) NOT NULL,
  `xf_email` varchar(255) NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`xf_user_id`),
  UNIQUE KEY `uuid` (`uuid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
