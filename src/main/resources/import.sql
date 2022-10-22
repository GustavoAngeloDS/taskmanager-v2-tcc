SET TIMEZONE TO 'America/Sao_Paulo';

INSERT INTO roles VALUES (1, CURRENT_TIMESTAMP, 'ROLE_USER');

INSERT INTO users VALUES ('dfcfa272-4885-4801-a849-df07fc717627', CURRENT_TIMESTAMP, 'usuario1@gmail.com', 'User1', '$2a$10$SWKwPCSuAWFSldiX2HycLuwmDfMoJ7MkFcmwoJ8G.7lWoUrqkxIZ2'/*teste123*/, '41988556644', 'usuario1');
INSERT INTO users VALUES ('dfcfa272-4885-4801-a849-df07fc717613', CURRENT_TIMESTAMP, 'usuario2@gmail.com', 'User2', '$2a$10$SWKwPCSuAWFSldiX2HycLuwmDfMoJ7MkFcmwoJ8G.7lWoUrqkxIZ2'/*teste123*/, '41988556644', 'usuario2');
INSERT INTO users VALUES ('dc5323dd-ab1c-4e3e-8990-62a3131c0651', CURRENT_TIMESTAMP, 'usuario3@gmail.com', 'User3', '$2a$10$SWKwPCSuAWFSldiX2HycLuwmDfMoJ7MkFcmwoJ8G.7lWoUrqkxIZ2'/*teste123*/, '41988556644', 'usuario3');


INSERT INTO user_roles VALUES ('dfcfa272-4885-4801-a849-df07fc717627', 1);
INSERT INTO user_roles VALUES ('dfcfa272-4885-4801-a849-df07fc717613', 1);

INSERT INTO boards VALUES ('57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc', CURRENT_TIMESTAMP , 'Quadro teste', 'Descrição exemplo', 'dfcfa272-4885-4801-a849-df07fc717627');

INSERT INTO board_users (user_id, board_id) VALUES ('dfcfa272-4885-4801-a849-df07fc717627', '57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');
INSERT INTO board_users (user_id, board_id) VALUES ('dfcfa272-4885-4801-a849-df07fc717613', '57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');
INSERT INTO board_users (user_id, board_id) VALUES ('dc5323dd-ab1c-4e3e-8990-62a3131c0651', '57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');


INSERT INTO stacks VALUES ('581ab551-ef9d-4e33-8063-a50da09053d7', CURRENT_TIMESTAMP, 'TO DO', 0, '57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');
INSERT INTO stacks VALUES ('08c4a8df-a202-4f35-bf94-74055c06ac4b', CURRENT_TIMESTAMP, 'DOING', 1, '57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');
INSERT INTO stacks VALUES ('949c56df-7aac-44ab-b17e-aab7174eb841', CURRENT_TIMESTAMP, 'FINISHED', 2,'57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');

INSERT INTO delivery_date VALUES ('419c56df-7aac-44ab-b17e-aab71712b841', CURRENT_TIMESTAMP, false, true, CURRENT_TIMESTAMP, '18:30');
INSERT INTO notification_configuration (id, message, notification_type, title) VALUES ('401dc56d-7aac-44ab-b17e-aab71712b841', 'A atividade ID [14ce2e17-7ed9-40c0-8481-260231cbb772] vencerá em breve', 'EMAIL', 'Lembrete de vencimento de atividade');

INSERT INTO tags (id, name, board_id) VALUES ('8c2c964d-23ed-4245-9617-fc01d2b31123', 'Etiqueta teste', '57a4489e-4b6b-4f4e-992d-fa3d0e50c6dc');

INSERT INTO tasks VALUES ('14ce2e17-7ed9-40c0-8481-260231cbb000', CURRENT_TIMESTAMP, '0', 0, '0', '419c56df-7aac-44ab-b17e-aab71712b841','401dc56d-7aac-44ab-b17e-aab71712b841', '581ab551-ef9d-4e33-8063-a50da09053d7', '8c2c964d-23ed-4245-9617-fc01d2b31123');

INSERT INTO task_users VALUES ('14ce2e17-7ed9-40c0-8481-260231cbb000', 'dfcfa272-4885-4801-a849-df07fc717627');

INSERT INTO internal_tasks VALUES ('123e0917-3325-4082-8e04-c1c9c4062844', CURRENT_TIMESTAMP, false, 'Checkar índice teste 1', 0, '14ce2e17-7ed9-40c0-8481-260231cbb000');
INSERT INTO internal_tasks VALUES ('8c2c964d-23ed-4245-9617-fc01d2b31f1a', CURRENT_TIMESTAMP, true, 'Abastecer teste 2', 1, '14ce2e17-7ed9-40c0-8481-260231cbb000');
