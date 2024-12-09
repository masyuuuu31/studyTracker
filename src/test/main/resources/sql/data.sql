INSERT INTO m_user ( user_id,user_name,password,created_at,updated_at ) 
VALUES ( "k.saito10114@gmail.com","saito","saitokosei","2024-12-07",'2024-12-08' );

INSERT INTO m_user ( user_id,user_name,password,created_at,updated_at ) 
VALUES ( "yuutakaizyuu2@gmail.com","nishizawa","nishizawayuta",'2024-12-07','2024-12-08' );

INSERT INTO m_user ( user_id,user_name,password,created_at,updated_at ) 
VALUES ( "inoueritsu131@gmail.com","inoue","inoueritsu",'2024-12-07','2024-12-08' );

INSERT INTO m_user ( user_id,user_name,password,created_at,updated_at ) 
VALUES ( "test@example.com","test","test_example",'2024-12-07','2024-12-08' );

メインタスク
INSERT INTO main_task ( title, description, deadline, percent, total_time, author_id, status, created_at, updated_at ) 
VALUES ( "SQLマスター", "SQL構文の習得", '2024-12-31', 0, 0, "k.saito10114@gmail.com", 0, '2024-12-07', '2024-12-07' );

INSERT INTO main_task ( title, description, deadline, percent, total_time, author_id, status, created_at, updated_at ) 
VALUES ( "海賊王に俺はなる！", "この世界の海で一番自由な男", '2024-12-31', 0, 0, "yuutakaizyuu2@gmail.com", 0, '2024-12-07', '2024-12-07' );

INSERT INTO main_task ( title, description, deadline, percent, total_time, author_id, status, created_at, updated_at ) 
VALUES ( "痩せる", "目指せ体重20kg減", '2024-12-31', 0, 0, "inoueritsu131@gmail.com", 0, '2024-12-07', '2024-12-07' );


サブタスク
INSERT INTO sub_task (title,description,target_id,author_id,started_at,finished_at,total_time,status,created_at,updated_at )
VALUES ( "SQL1","SQLの習得1",1,"k.saito10114@gmail.com",'2024-12-07-11:00','2024-12-07-20:00',0,0,'2024-12-07','2024-12-07' );

INSERT INTO sub_task (title,description,target_id,author_id,started_at,finished_at,total_time,status,created_at,updated_at )
VALUES ( "SQL1","SQLの習得1",1,"k.saito10114@gmail.com",'2024-12-08-10:00','2024-12-08-19:00',0,0,'2024-12-07','2024-12-07' );

INSERT INTO sub_task (title,description,target_id,author_id,started_at,finished_at,total_time,status,created_at,updated_at )
VALUES ( "SQL2","SQLの習得2",1,"k.saito10114@gmail.com",'2024-12-09-08:30','2024-12-09-17:30',0,0,'2024-12-08','2024-12-08' );

INSERT INTO sub_task (title,description,target_id,author_id,started_at,finished_at,total_time,status,created_at,updated_at )
VALUES ( "SQL2","SQLの習得2",1,"k.saito10114@gmail.com",'2024-12-09-08:30','2024-12-09-17:30',0,0,'2024-12-08','2024-12-08' );

