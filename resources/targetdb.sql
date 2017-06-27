SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema targetdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `targetdb` ;
USE `targetdb` ;

-- -----------------------------------------------------
-- Table `targetdb`.`t_domain`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `targetdb`.`t_domain` (
  `id` BIGINT(19) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `vhost` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `targetdb`.`t_article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `targetdb`.`t_article` (
  `id` BIGINT(19) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `body` MEDIUMTEXT NULL,
  `domain_id` BIGINT(19) NOT NULL,
  `quote_index` DOUBLE NOT NULL,
  `published_on` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_article_domain_idx` (`domain_id` ASC),
  CONSTRAINT `fk_article_domain`
    FOREIGN KEY (`domain_id`)
    REFERENCES `targetdb`.`t_domain` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `targetdb`.`t_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `targetdb`.`t_tag` (
  `id` BIGINT(19) NOT NULL,
  `name` VARCHAR(200) NULL,
  `article_id` BIGINT(19) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tag_article1_idx` (`article_id` ASC),
  CONSTRAINT `fk_tag_article1`
    FOREIGN KEY (`article_id`)
    REFERENCES `targetdb`.`t_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
