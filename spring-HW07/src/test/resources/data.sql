-- authors
insert into t_author(id, name) values ('c20c2f9b-89c7-449e-a78c-63fb1fdb26c3', 'Mark Twain');
insert into t_author(id, name) values ('e07e6aab-d89f-4f85-b453-efccaaff0975', 'Aleksandr Dumas');
insert into t_author(id, name) values ('fbddd6ac-f4dd-4a39-804d-03d45d30241b', 'Jules Verne');
insert into t_author(id, name) values ('cc02c80f-de17-4d98-94b6-994670a08ce7', 'Shakespeare');
-- genre
insert into t_genre(id, name) values ('defd5e0b-1af4-45c0-a68a-1ea5a21296cf','Adventure');
insert into t_genre(id, name) values ('b67847da-c4fa-44f0-a4cf-9d53fbddfb96','Fairy');
insert into t_genre(id, name) values ('6d91add4-e6c8-41e3-abbc-f3f615b06bae', 'Piece');
insert into t_genre(id, name) values ('1bc88153-8458-4d9d-bc75-06f2241d3a42', 'Drama');
-- books
-- Mark Twain
insert into t_book(id, title, authorId, genreId) values ('777a5b51-67bb-4f3d-991f-ff025abd8668','The Adventures of Tom Sawyer','c20c2f9b-89c7-449e-a78c-63fb1fdb26c3','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('68027695-b57a-4a93-866e-c019f1805a80','Adventures of Huckleberry Finn','c20c2f9b-89c7-449e-a78c-63fb1fdb26c3','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('b703537c-6eaf-4eeb-bddc-16b3274836d6','Tom Sawyer Abroad','c20c2f9b-89c7-449e-a78c-63fb1fdb26c3','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('bab960d8-de54-4675-9b27-d2beda528fef','Tom Sawyer, Detective','c20c2f9b-89c7-449e-a78c-63fb1fdb26c3','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('c5f79e3d-5db1-4aaa-8e52-3d11c601db1f','Huck Finn and Tom Sawyer Among the Indians','c20c2f9b-89c7-449e-a78c-63fb1fdb26c3','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
-- Aleksandr Dumas
insert into t_book(id, title, authorId, genreId) values ('4cd0a149-b4bf-47b2-872d-655736d90804','The Count of Monte Cristo','e07e6aab-d89f-4f85-b453-efccaaff0975','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('7eece38e-3aa8-42cd-809a-450be6434626','The Three Musketeers','e07e6aab-d89f-4f85-b453-efccaaff0975','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('1de296ba-b172-417c-a5d0-96efdda934c9','Twenty Years After','e07e6aab-d89f-4f85-b453-efccaaff0975','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('05c3dd0b-7bb8-4947-bf0e-538a6a1f52b9','The Pale Lady','e07e6aab-d89f-4f85-b453-efccaaff0975','b67847da-c4fa-44f0-a4cf-9d53fbddfb96');
insert into t_book(id, title, authorId, genreId) values ('bbe76b81-63d0-4328-a342-73b38febd6f6','The Wolf Leader','e07e6aab-d89f-4f85-b453-efccaaff0975','b67847da-c4fa-44f0-a4cf-9d53fbddfb96');
-- Jules Verne
insert into t_book(id, title, authorId, genreId) values ('631c7fda-6063-4daa-b7fa-d47262e178f4','The Adventures of Captain Hatteras','fbddd6ac-f4dd-4a39-804d-03d45d30241b','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('6a49f95a-9cab-4a49-bbc6-e3f447b35fcc','Journey to the Center of the Earth','fbddd6ac-f4dd-4a39-804d-03d45d30241b','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('c63d52ad-8ae2-49bb-bd77-baae870488c9','A Floating City','fbddd6ac-f4dd-4a39-804d-03d45d30241b','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('86584bc4-7b01-4e6e-ac06-ced496bab549','Michael Strogoff','fbddd6ac-f4dd-4a39-804d-03d45d30241b','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');
insert into t_book(id, title, authorId, genreId) values ('78ec723a-8cab-4526-b31e-fad4e03f6bfe','Off on a Comet','fbddd6ac-f4dd-4a39-804d-03d45d30241b','defd5e0b-1af4-45c0-a68a-1ea5a21296cf');

insert into t_book(id, title, authorId, genreId) values ('4123f3ef-31c6-43ba-ab82-6882d22f7998', 'Antony and Cleopatra','cc02c80f-de17-4d98-94b6-994670a08ce7', '6d91add4-e6c8-41e3-abbc-f3f615b06bae');
insert into t_book(id, title, authorId, genreId) values ('de3cab24-ac4b-4fc8-a5ed-76a489814120', 'Coriolanus','cc02c80f-de17-4d98-94b6-994670a08ce7', '6d91add4-e6c8-41e3-abbc-f3f615b06bae');
