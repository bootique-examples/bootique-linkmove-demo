
INSERT INTO sourcedb.s_domain (id,name,domain_host) VALUES(1,'ObjectStyle LLC', 'www.objectstyle.com/about');
INSERT INTO sourcedb.s_domain (id,name,domain_host) VALUES(2,'Bootique', 'bootique.io/docs/0/getting-started/');
INSERT INTO sourcedb.s_domain (id,name,domain_host) VALUES(3,'LinkMove', 'github.com/nhl/link-move');

INSERT INTO sourcedb.s_article (id,title,body, domain_id) VALUES(1,'About us','ObjectStyle has deep roots in the open source community...', 1);
INSERT INTO sourcedb.s_article (id,title,body, domain_id) VALUES(2,'Getting Started with Bootique','Getting Started with Bootique...', 2);
INSERT INTO sourcedb.s_article (id,title,body, domain_id) VALUES(3,'LinkMove','LinkMove  an ETL solution...', 3);

INSERT INTO sourcedb.s_tag (id,name,article_id, color) VALUES(1,'ObjectStyle', 1, 'red');
INSERT INTO sourcedb.s_tag (id,name,article_id, color) VALUES(2,'Bootique', 2, 'green');
INSERT INTO sourcedb.s_tag (id,name,article_id, color) VALUES(3,'LinkMove', 3, 'blue');