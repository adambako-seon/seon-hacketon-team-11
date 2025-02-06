select
    u1_0.id,
    u1_0.created_at,
    u1_0.created_by,
    u1_0.date_of_birth,
    u1_0.full_name,
    u1_0.place_of_birth,
    u1_0.updated_at,
    u1_0.updated_by,
    u1_0.username,
    u1_0.version,
    p1_0.user_id,
    p1_0.id,
    p1_0.created_at,
    p1_0.created_by,
    p1_0.phone_number,
    p1_0.updated_at,
    p1_0.updated_by,
    p1_0.version
from
    users u1_0
        left join
    phone p1_0
    on u1_0.id=p1_0.user_id
where
    u1_0.id=?