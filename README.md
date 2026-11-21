# Software Engineering - Lab 4

Acceptance tests for the login of our semester project system, EventPlanner.
[REPORT.md](REPORT.md) (Hebrew) has the login use case (UC-1, main scenario and
every branch), the test accounts, 16 acceptance tests with a different goal each,
and a table mapping each UC branch to its tests.

This lab has no code. The tests describe the login from the project story:
four account types (client, supplier, customer-service rep, admin) and supplier
states (approved, pending, blocked). The lockout after `n` wrong passwords for
`t` seconds comes from Lab 3 (branch
[`lab3`](https://github.com/harelarti/Software-Engineering-Labs/tree/lab3)).
