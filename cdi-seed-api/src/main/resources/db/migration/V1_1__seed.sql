CREATE TABLE IF NOT EXISTS `seed` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `description` varchar(150) NOT NULL,
   `email` varchar(150) NOT NULL,
   
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;