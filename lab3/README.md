# Software Engineering - Lab 3

Lab 2's login, now with a lockout: after `n` wrong passwords an email is
blocked for `t` seconds. The login runs on its own threads so the window stays
responsive. Classes, threads, the `synchronized` experiment and the answers are
in [REPORT.md](REPORT.md).

## Run

From this folder (needs a JDK 17+ and Maven), with n = 3 and t = 30:

```sh
mvn javafx:run -Djavafx.args="3 30"
```

In IntelliJ, put `3 30` in the run configuration's Program arguments. Missing or
invalid values are asked for again before the login screen opens.
