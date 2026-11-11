# Software Engineering - Lab 1

A Java console program that validates user credentials, reports invalid input
records and prints the valid users in username order.

## Files

- `User.java`: immutable credentials, constructor validation and record formatting.
- `UsersApp.java`: line-by-line input, error handling, `ArrayList<User>` and sorting.
- `examples/users.txt`: the supplied input fixture, with its filename normalized.
- `examples/expected-out.txt`: the supplied expected standard output, unchanged.
- `examples/actual-out.txt`: captured standard output from the verified run.
- `examples/actual-errors.txt`: captured diagnostics from that run.
- `examples/verification.txt`: a summary of executed checks.

## Build and run

Use a JDK; the program was tested on OpenJDK 25.0.1.

```sh
javac -Xlint:all User.java UsersApp.java
java UsersApp < examples/users.txt > out.txt 2> errors.txt
diff -u examples/expected-out.txt out.txt
```

The last command reports no differences for the supplied example. The program
also accepts one filename argument:

```sh
java UsersApp examples/users.txt
```

No arguments means standard input. One argument means that UTF-8 file. More than
one argument, or an unreadable input file, produces a diagnostic and exit status 1.

## Validation and ordering

The `User` constructor checks username validity before password validity and
checks length limits before character-format rules. All five error messages
are the exact messages specified by the assignment.

- Email: maximum 50 characters; the exercise's ASCII local-part, domain and
  alphabetic suffix rules, not a full RFC email validator.
- Password: 8 through 12 characters, including at least one ASCII letter, one
  digit and one symbol from `!@#$%^&*()-_=+`.
- Password validation scans characters and tracks the three required categories.
  Either letter case is accepted; both cases are not required.
- Exactly two whitespace-separated fields are expected per input line.
- Invalid rows are reported without stopping later rows from being processed.
- Valid rows are sorted with case-sensitive Java string comparison. Duplicate
  valid rows are retained.

## Output streams

The exercise asks for diagnostics, but its expected output fixture contains only
valid users. Therefore valid records go to **stdout**, and each invalid original
line plus its required error message goes to **stderr**. No prompts or headers
are added to stdout. The captured stdout matches the fixture byte-for-byte.

## Verification

The implementation passed compilation with `-Xlint:all`, 51 constructor checks,
the exact sample comparison and additional checks for whitespace, duplicates,
ordering, malformed/empty input, file/stdin equivalence and invocation failures.
See `examples/verification.txt` for the recorded results.

## Submission

The course submission is a separate PDF containing the submitting pair's names
and student IDs, this laboratory repository link, explanations, execution
evidence and the complete text of both source files. Personal submission details
and the PDF are intentionally not stored in this public repository.

The assignment explicitly prints credentials. This behavior is for the
educational exercise only, not a pattern for production password storage.
