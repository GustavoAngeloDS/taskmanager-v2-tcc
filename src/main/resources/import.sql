INSERT INTO roles VALUES (1, 'ROLE_USER');

INSERT INTO users VALUES ('dfcfa272-4885-4801-a849-df07fc717627', 'usuario1@gmail.com', 'User1', '$2a$10$SWKwPCSuAWFSldiX2HycLuwmDfMoJ7MkFcmwoJ8G.7lWoUrqkxIZ2'/*teste123*/, '41988556644', 'usuario1');
INSERT INTO users VALUES ('dfcfa272-4885-4801-a849-df07fc717613', 'usuario2@gmail.com', 'User2', '$2a$10$SWKwPCSuAWFSldiX2HycLuwmDfMoJ7MkFcmwoJ8G.7lWoUrqkxIZ2'/*teste123*/, '41988556644', 'usuario2');

INSERT INTO user_roles VALUES ('dfcfa272-4885-4801-a849-df07fc717627', 1);
INSERT INTO user_roles VALUES ('dfcfa272-4885-4801-a849-df07fc717613', 1);

INSERT INTO boards VALUES ('57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc', 'Quadro teste', 'Descrição exemplo', 'dfcfa272-4885-4801-a849-df07fc717627');

INSERT INTO board_users VALUES ('57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc', 'dfcfa272-4885-4801-a849-df07fc717613');

INSERT INTO stacks VALUES ('581ab551-ef9d-4e33-8063-a50da09053d7', 'TO DO', '57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');
INSERT INTO stacks VALUES ('08c4a8df-a202-4f35-bf94-74055c06ac4b', 'DOING', '57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');
INSERT INTO stacks VALUES ('949c56df-7aac-44ab-b17e-aab7174eb841', 'FINISHED', '57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');

INSERT INTO tasks VALUES ('14ce2e17-7ed9-40c0-8481-260231cbb772', 'Descrição teste', 'Título task teste', '581ab551-ef9d-4e33-8063-a50da09053d7');