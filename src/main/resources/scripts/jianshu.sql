USE jianshu;

CREATE DATABASE IF NOT EXISTS test
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS user (
  id           BIGINT(20) PRIMARY KEY
  COMMENT '主键',
  name         VARCHAR(60) COMMENT '真名',
  nickname     VARCHAR(60) COMMENT '昵称',
  sex     VARCHAR(1) COMMENT '性别',
  account        VARCHAR(50) COMMENT '账号',
  password     VARCHAR(16) COMMENT '密码' NOT NULL,
  email        VARCHAR(100) COMMENT '电子邮箱',
  telephone    VARCHAR(100) COMMENT '联系方式',
  introduction VARCHAR(100) COMMENT '自我简介',
  image        VARCHAR(200) COMMENT '头像URL',
  site         VARCHAR(200) COMMENT '个人网站URL',
  QR_code      VARCHAR(200) COMMENT '微信二维码',
  c_time       DATETIME DEFAULT now() COMMENT '创建日期' ,
  u_time        DATETIME COMMENT '修改日期',
  UNIQUE(account)
)ENGINE = Innodb
  DEFAULT CHARSET utf8
  COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS article_content (
  id      BIGINT(20) PRIMARY KEY COMMENT '主键',
  content TEXT COMMENT '内容'
);


CREATE TABLE IF NOT EXISTS article (
  id         BIGINT(20) PRIMARY KEY
  COMMENT '主键',
  title      VARCHAR(100) NOT NULL
  COMMENT '标题',
  author     VARCHAR(60) COMMENT '作者',
  content_id BIGINT(20) COMMENT '内容',
  up         INT UNSIGNED DEFAULT 0
  COMMENT '被赞数',
  reading    INT UNSIGNED DEFAULT 0
  COMMENT '阅读数',
  catagory   TINYINT UNSIGNED COMMENT '文章分类',
  c_time DATETIME     DEFAULT now()
  COMMENT '创建日期',
  u_time DATETIME COMMENT '更新日期',
  FOREIGN KEY (contect_id) REFERENCES article_content (id)  ON DELETE CASCADE
) ENGINE = Innodb  DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS COMMENT(
  id BIGINT(20) PRIMARY KEY COMMENT '主键',
  article_id BIGINT(20) NOT NULL COMMENT '文章Id',
  content VARCHAR(1000) NOT NULL  COMMENT '评论',
  user_id BIGINT(20) NOT NULL COMMENT '评论人',
  up INTEGER COMMENT '赞',
  down INTEGER COMMENT '踩',
  c_time DATETIME DEFAULT now() COMMENT '评论时间',
  FOREIGN KEY(article_id) REFERENCES article(id)  ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(user_id) REFERENCES user(id)  ON UPDATE CASCADE ON DELETE CASCADE
)DEFAULT CHARSET utf8 COLLATE utf8_general_ci  ENGINE = Innodb COMMENT '评论表';


CREATE TABLE IF NOT EXISTS REPLY(
  id BIGINT(20) PRIMARY KEY COMMENT '主键',
  comment_id BIGINT(20) NOT NULL COMMENT '评论Id',
  content VARCHAR(1000) NOT NULL  COMMENT '回复内容',
  from_user_id BIGINT(20) NOT NULL COMMENT '回复人',
  to_user_id BIGINT(20) NOT NULL COMMENT '回复对象',
  c_time DATETIME DEFAULT now() COMMENT '回复时间',
  FOREIGN KEY(comment_id) REFERENCES COMMENT(id)  ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(from_user_id) REFERENCES user(id)  ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(to_user_id) REFERENCES user(id)  ON UPDATE CASCADE ON DELETE CASCADE
)DEFAULT CHARSET utf8 COLLATE utf8_general_ci  ENGINE = Innodb COMMENT '评论表';

CREATE TABLE IF NOT EXISTS follower  (
  id BIGINT(20) PRIMARY KEY COMMENT '主键',
  from_user_id BIGINT(20) NOT NULL COMMENT '关注者',
  to_id BIGINT(20) NOT NULL COMMENT '被关注者',
  c_time DATETIME DEFAULT now() COMMENT '关注时间',
  `type` TINYINT UNSIGNED,
  FOREIGN KEY(from_user_id) REFERENCES user(id) ON UPDATE CASCADE ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ENGINE innodb COMMENT '关注表';

CREATE TABLE IF NOT  EXISTS topic (
   id BIGINT(20) PRIMARY KEY '' COMMENT '主键',
   topic VARCHAR(12) NOT NULL COMMENT '专题名',
   announcement text COMMENT '公告',
   img VARCHAR(200) COMMENT '图片'
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ENGINE innodb COMMENT '专题表';

CREATE TABLE IF NOT EXISTS include(
	id BIGINT(20) PRIMARY KEY  COMMENT '主键',
    topic_id BIGINT(20)  NOT NULL COMMENT '主题ID',
    article_id BIGINT(20)  NOT NULL COMMENT '收录文章ID',
    c_time DATETIME DEFAULT now() COMMENT '收录时间',
    FOREIGN KEY(topic_id) REFERENCES topic(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(article_id) REFERENCES article(id) ON UPDATE CASCADE ON DELETE CASCADE
)DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ENGINE innodb COMMENT '专题收录表';



