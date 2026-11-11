# Software Engineering Labs

Course labs by Harel Timor and Guy Blumenfeld (see [SUBMITTERS.md](SUBMITTERS.md)), each one building on the previous. Every lab lives in its own
folder with its own README; `main` holds the latest state of all labs.

| Lab | Folder | Topic | Snapshot |
|-----|--------|-------|----------|
| 1 | [`lab1/`](lab1/) | Console credential validation and sorted user output | tag `lab1` |
| 2 | [`lab2/`](lab2/) | JavaFX login window reusing Lab 1's `User` | tag `lab2` |
| 3 | [`lab3/`](lab3/) | Threads: login lockout after n failures for t seconds | branch `lab3` |

## Open in IntelliJ IDEA

Clone the repository with **File → New → Project from Version Control** (or
`git clone`) and open its root folder. The root `pom.xml` lists every lab, so
IntelliJ imports all of them as Maven modules.

- Lab 1: run `UsersApp`, with working directory `lab1/` and argument `examples/users.txt`.
- Lab 2: from `lab2/`, run `mvn javafx:run` (see [lab2/README.md](lab2/README.md)).
- Lab 3: from `lab3/`, run `mvn javafx:run -Djavafx.args="3 30"` (see [lab3/README.md](lab3/README.md)).

## Workflow

Each lab is developed on a `labN` branch and merged into `main` with a pull
request. The `labN` tags mark each lab as submitted, so a submission link can point at
`https://github.com/harelarti/Software-Engineering-Labs/tree/labN`.
