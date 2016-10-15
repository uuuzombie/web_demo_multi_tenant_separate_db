# web_demo_multi_tenant_separate_db

This is a multi-tenant demo that uses a separate database approach.

Some approaches:

1.ThreadLocal
AppContext + BaseDao

2.AOP + Connection switch database

3.Hibernate + Connection switch database
//TODO

4.Hibernate 4.0+
hibernate.multiTenancy = DATABASE
//TODO
