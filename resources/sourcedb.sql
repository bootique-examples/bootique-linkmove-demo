SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sourcedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sourcedb` DEFAULT CHARACTER SET latin1 ;
USE `sourcedb` ;

-- -----------------------------------------------------
-- Table `sourcedb`.`s_domain`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sourcedb`.`s_domain` (
  `id` BIGINT(20) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `domain_host` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `sourcedb`.`s_article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sourcedb`.`s_article` (
  `id` BIGINT(19) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `body` MEDIUMTEXT NULL DEFAULT NULL,
  `domain_id` BIGINT(19) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `domain_id` (`domain_id` ASC),
  CONSTRAINT `article_ibfk_1`
    FOREIGN KEY (`domain_id`)
    REFERENCES `sourcedb`.`s_domain` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `sourcedb`.`s_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sourcedb`.`s_tag` (
  `id` BIGINT(19) NOT NULL,
  `name` VARCHAR(200) NULL DEFAULT NULL,
  `article_id` BIGINT(19) NOT NULL,
  `color` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `article_id` (`article_id` ASC),
  CONSTRAINT `tag_ibfk_1`
    FOREIGN KEY (`article_id`)
    REFERENCES `sourcedb`.`s_article` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
