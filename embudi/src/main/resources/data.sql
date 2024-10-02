INSERT INTO role (roleid, rolename) VALUES
    (1, 'User'),
    (2, 'Admin')
on conflict do nothing;