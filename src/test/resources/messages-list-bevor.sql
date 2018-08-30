delete FROM messages;

INSERT into messages(id, text, tag, user_id) VALUE
(1, 'ein', 'my-tag!', 1)
(2, 'zwei', 'more', 1)
(3, 'drei', 'my-tag', 1)
(4, 'vier', 'more', 1);

ALTER sequence hibernate_sequence restart WITH 10;