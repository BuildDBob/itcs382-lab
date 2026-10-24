# Lab Report, Code Quality and Design Quality

Repository URL:

## Tools

| Item                    | Version or name |
| ----------------------- | --------------- |
| JDK                     |                 |
| Maven                   |                 |
| IDE                     |                 |
| SonarQube for IDE       |                 |
| AI tool and exact model |                 |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change: 357d0bc (HEAD -> main, origin/main) Starter code

Issue: Nothing recorded in parcels.csv

Table 1. SonarQube issues in the starter code

| No. | Rule                  | File:line                           | Message                                                                             |
| --- | --------------------- | ----------------------------------- | ----------------------------------------------------------------------------------- |
| 1   | sonarqube(java:S106)  | Main.java: [Ln 9, Col 9]            | Replace this use of System.out by a logger.                                         |
| 2   | sonarqube(java:S106)  | Main.java: [Ln 15, Col 17]          | Replace this use of System.out by a logger.                                         |
| 3   | sonarqube(java:S106)  | Main.java: [Ln 17, Col 17]          | Replace this use of System.out by a logger.                                         |
| 4   | sonarqube(java:S106)  | Main.java: [Ln 22, Col 17]          | Replace this use of System.out by a logger.                                         |
| 5   | sonarqube(java:S2143) | ParcelService.java: [Ln 1, Col 1]   | Use the "java.time" API for date and time.                                          |
| 6   | sonarqube(java:S1128) | ParcelService.java: [Ln 6, Col 8]   | Remove this unused import 'java.util.Date'.                                         |
| 7   | sonarqube(java:S1068) | ParcelService.java: [Ln 12, Col 17] | Remove this unused "maxParcels" private field.                                      |
| 8   | sonarqube(java:S106)  | ParcelService.java: [Ln 30, Col 17] | Replace this use of System.out by a logger.                                         |
| 9   | sonarqube(java:S4973) | ParcelService.java: [Ln 42, Col 52] | Strings and Boxed types should be compared using "equals()".                        |
| 10  | sonarqube(java:S106)  | ParcelService.java: [Ln 67, Col 17] | Replace this use of System.out by a logger.                                         |
| 11  | sonarqube(java:S106)  | ParcelService.java: [Ln 69, Col 17] | Replace this use of System.out by a logger.                                         |
| 12  | sonarqube(java:S2095) | ParcelService.java: [Ln 76, Col 33] | Use try-with-resources or close this "FileWriter" in a "finally" clause.            |
| 13  | sonarqube(java:S108)  | ParcelService.java: [Ln 80, Col 33] | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

## Task 2. AI review of the starter code

| Item                                                                                   | Value                       |
| -------------------------------------------------------------------------------------- | --------------------------- |
| Date                                                                                   | Friday October 23, 2026     |
| AI tool and exact model                                                                | Claude Opus 5.5             |
| Commit reviewed                                                                        | First Commit (Starter code) |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | Attached the Files          |
| Output file in the reviews folder                                                      | parcelpoint-review.md       |

Table 2. All findings on the starter code

| No. | File:line                                                                                                  | Finding                                                                                                                                                                       | Found by (SonarQube, AI or both) | SonarQube rule or course term            | Correct? (yes or no, and why)                                                                                                                                         |
| --- | ---------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------- | ---------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 1   | Main.java: [Ln 6-27]                                                                                       | `main` combines parsing, a five-branch if-chain and output in one method.                                                                                                     | AI                               | Long method                              | No, too weak. Its cognitive complexity is about 7, below SonarQube's S3776 threshold of 15, so this is a matter of taste.                                             |
| 2   | Main.java: [Ln 6-27]                                                                                       | `main` has **logical** cohesion: it selects unrelated actions by `words[0]`.                                                                                                  | AI                               | Logical cohesion                         | Yes. The command word picks one of several unrelated actions, although this is normal for a command-line dispatcher.                                                  |
| 3   | Main.java: [Ln 8]                                                                                          | The `Scanner` is never closed.                                                                                                                                                | AI                               | Resource leak                            | Yes, but trivial. It wraps `System.in`, which is why SonarQube does not flag it.                                                                                      |
| 4   | Main.java: [Ln 9, Col 9]                                                                                   | Replace this use of System.out by a logger.                                                                                                                                   | SonarQube                        | java:S106                                | No. This program is a console app and `System.out` is its user interface; a logger is the wrong tool for that output.                                                 |
| 5   | Main.java: [Ln 13, Ln 17]                                                                                  | Input is not validated. Missing words throw `ArrayIndexOutOfBoundsException` and non-numbers throw `NumberFormatException`, and either one ends the program.                  | AI                               | Probable bug (unhandled exception)       | Yes. Typing `store` with no arguments crashes the program.                                                                                                            |
| 6   | Main.java: [Ln 15, Col 17]                                                                                 | Replace this use of System.out by a logger.                                                                                                                                   | SonarQube                        | java:S106                                | No. Same reason as No. 4.                                                                                                                                             |
| 7   | Main.java: [Ln 17, Col 17]                                                                                 | Replace this use of System.out by a logger.                                                                                                                                   | SonarQube                        | java:S106                                | No. Same reason as No. 4.                                                                                                                                             |
| 8   | Main.java: [Ln 22]                                                                                         | Replace this use of System.out by a logger.                                                                                                                                   | SonarQube                        | java:S106                                | No. Same reason as No. 4.                                                                                                                                             |
| 9   | Main.java: [Ln 22]                                                                                         | Main reaches through `ParcelService` into its `Locker` list to count free lockers.                                                                                            | AI                               | Strong coupling (Law of Demeter)         | Yes. Main has to know that lockers exist and how to tell when one is free.                                                                                            |
| 10  | ParcelService.java: [Ln 1, Col 1]                                                                          | Use the "java.time" API for date and time.                                                                                                                                    | SonarQube                        | java:S2143                               | Yes, but redundant. It is caused only by the unused `Date` import, and deleting that import (No. 11) fixes both.                                                      |
| 11  | ParcelService.java: [Ln 6, Col 8]                                                                          | Remove this unused import 'java.util.Date'.                                                                                                                                   | Both                             | java:S1128 / unused import               | Yes. `Date` is never referenced.                                                                                                                                      |
| 12  | ParcelService.java: [Ln 9]                                                                                 | The class represents several things: locker inventory, parcel registry, fee policy, notifier, persistence and console output.                                                 | AI                               | Weak class cohesion                      | Yes. Each of these responsibilities could change for a different reason.                                                                                              |
| 13  | ParcelService.java: [Ln 12, Col 17]                                                                        | Remove this unused "maxParcels" private field.                                                                                                                                | Both                             | java:S1068 / unused field                | Yes. The AI also points out that the 100-parcel limit is never enforced.                                                                                              |
| 14  | ParcelService.java: [Ln 20-22]                                                                             | `getLockers()` returns the internal mutable list, so callers can add or remove lockers.                                                                                       | AI                               | Exposed internal collection              | Yes. Calling `getLockers().clear()` from Main would compile and run.                                                                                                  |
| 15  | ParcelService.java: [Ln 24-37]                                                                             | The same parcel code can be stored twice, and `pickUp` then releases whichever matches first.                                                                                 | AI                               | Probable bug                             | Yes. Nothing checks for a duplicate code.                                                                                                                             |
| 16  | ParcelService.java: [Ln 24-37]                                                                             | `storeParcel` has **procedural** cohesion: storing, sending the SMS and rewriting the whole file are related only by order (`saveToFile` works on all parcels, not this one). | AI                               | Procedural cohesion                      | Debatable. It could be called communicational, since every step touches the same parcel, but `saveToFile` uses different data, which supports procedural.             |
| 17  | ParcelService.java: [Ln 28, Ln 44, Ln 67, Ln 68, Ln 78] → Parcel.java: [Ln 7]                              | **Content** coupling: `ParcelService` reads and writes `Parcel`'s internal `status` field directly.                                                                           | AI                               | Content coupling                         | Yes. Another class writes the field directly instead of going through a method.                                                                                       |
| 18  | ParcelService.java: [Ln 30, Col 17]                                                                        | Replace this use of System.out by a logger.                                                                                                                                   | SonarQube                        | java:S106                                | Yes. A service class should not print, but the better fix is a `Notifier` class rather than a logger.                                                                 |
| 19  | ParcelService.java: [Ln 39]                                                                                | The storage duration `days` comes from the caller at pickup instead of being recorded at store time, so the fee depends on what the operator types.                           | AI                               | Probable bug (data integrity)            | Debatable. It is true if fees must be trustworthy, but it may be a deliberate simplification in the lab.                                                              |
| 20  | ParcelService.java: [Ln 39-50]                                                                             | `pickUp` has **procedural** cohesion: releasing the locker, persisting and computing the fee are joined only by sequence.                                                     | AI                               | Procedural cohesion                      | Debatable, for the same reason as No. 16.                                                                                                                             |
| 21  | ParcelService.java: [Ln 41-42]                                                                             | The code reaches through `Locker` to `Parcel` with `locker.getParcel().getCode()`.                                                                                            | AI                               | Strong coupling (Law of Demeter)         | Yes, but minor.                                                                                                                                                       |
| 22  | ParcelService.java: [Ln 42, Col 52]                                                                        | Strings and Boxed types should be compared using "equals()".                                                                                                                  | Both                             | java:S4973 / object identity vs equality | Yes. The code typed at pickup and the stored code come from separate `split()` calls, so `==` is always false. The AI also gives the consequence: pickup never works. |
| 23  | ParcelService.java: [Ln 49] → Main.java: [Ln 17]                                                           | Connascence of **meaning**: a return of 0 means both "free pickup" and "parcel not found", so Main prints "Fee: 0" for an unknown code.                                       | AI                               | Connascence of meaning (convention)      | Yes, with one inaccuracy. The program actually prints "Fee: 0.0" because the return type is `double`.                                                                 |
| 24  | ParcelService.java: [Ln 52]                                                                                | **Stamp** coupling: `fee` receives the whole `Parcel` but uses only `getSize()`.                                                                                              | AI                               | Stamp coupling                           | Yes.                                                                                                                                                                  |
| 25  | ParcelService.java: [Ln 52-62]                                                                             | `fee` uses none of `ParcelService`'s attributes, only `Parcel` data (feature envy).                                                                                           | AI                               | Weak class cohesion (feature envy)       | Yes. The method reads no field of `ParcelService`.                                                                                                                    |
| 26  | ParcelService.java: [Ln 53-61]                                                                             | The fee rules use magic numbers (3, 10.0, 15.0, 20.0) and `double` for money.                                                                                                 | AI                               | Code smell (magic numbers)               | Yes. The matching SonarQube rule, S109, is not in the default profile.                                                                                                |
| 27  | ParcelService.java: [Ln 64] ← Main.java: [Ln 19, Ln 21]                                                    | **Control** coupling: the boolean `forStaff` steers the callee's logic.                                                                                                       | AI                               | Control coupling                         | Yes. The caller passes a flag that chooses which branch the callee runs.                                                                                              |
| 28  | ParcelService.java: [Ln 64-72]                                                                             | `printParcels` has **logical** cohesion: a flag selects one of two different reports.                                                                                         | AI                               | Logical cohesion                         | Yes.                                                                                                                                                                  |
| 29  | ParcelService.java: [Ln 67, Col 17]                                                                        | Replace this use of System.out by a logger.                                                                                                                                   | SonarQube                        | java:S106                                | Yes, but a logger is the wrong fix. The service should return the data and let Main print it.                                                                         |
| 30  | ParcelService.java: [Ln 69, Col 17]                                                                        | Replace this use of System.out by a logger.                                                                                                                                   | SonarQube                        | java:S106                                | Yes, but a logger is the wrong fix. Same reason as No. 29.                                                                                                            |
| 31  | ParcelService.java: [Ln 76, Col 33]                                                                        | Use try-with-resources or close this "FileWriter" in a "finally" clause.                                                                                                      | Both                             | java:S2095 / resource leak               | Yes. This explains the observed issue "Nothing recorded in parcels.csv", because the writer is never flushed.                                                         |
| 32  | ParcelService.java: [Ln 80, Col 33]                                                                        | Remove this block of code, fill it in, or add a comment explaining why it is empty.                                                                                           | Both                             | java:S108 / empty block                  | Yes. A failed save would be completely silent.                                                                                                                        |
| 33  | Parcel.java: [Ln 4-6]; Locker.java: [Ln 4-5]                                                               | Fields that never change after construction are not `final`.                                                                                                                  | AI                               | Code smell (immutability)                | Yes, but minor.                                                                                                                                                       |
| 34  | Parcel.java: [Ln 6]; Locker.java: [Ln 5]; ParcelService.java: [Ln 15-17, Ln 56, Ln 58]; Main.java: [Ln 13] | Connascence of **meaning (convention)**: every class must agree that 1, 2 and 3 mean small, medium and large.                                                                 | AI                               | Connascence of meaning (convention)      | Yes. Changing the code numbers would break every one of these places.                                                                                                 |
| 35  | Parcel.java: [Ln 7]                                                                                        | The field `status` is `public`.                                                                                                                                               | AI                               | Non-private field                        | Yes. This is the root cause of No. 17.                                                                                                                                |
| 36  | Parcel.java: [Ln 9]                                                                                        | `size` is not validated. Size 0 fits any locker, and size 4 or more never fits a locker but would be charged at the large rate.                                               | AI                               | Probable bug (missing validation)        | Partly. The size-0 problem is real, but a parcel of size 4 or more is never stored, so it can never be charged. That half of the claim is wrong.                      |
| 37  | Parcel.java: [Ln 9] ← Main.java: [Ln 13]                                                                   | Connascence of **position**: the constructor takes `(String, String, int)`, and swapping `code` and `studentId` compiles silently.                                            | AI                               | Connascence of position                  | Yes.                                                                                                                                                                  |
| 38  | Parcel.java: [Ln 13]; ParcelService.java: [Ln 28, Ln 44, Ln 68]                                            | Connascence of **meaning (convention)**: the status strings "NEW", "STORED" and "PICKED_UP" are shared, and a typo still compiles.                                            | AI                               | Connascence of meaning (convention)      | Yes.                                                                                                                                                                  |
| 39  | Locker.java: [Ln 8] ← ParcelService.java: [Ln 15-17]                                                       | Connascence of **position**: `Locker(int, int)` makes calls like `new Locker(1, 1)` ambiguous.                                                                                | AI                               | Connascence of position                  | Yes.                                                                                                                                                                  |
| 40  | Locker.java: [Ln 25-27]                                                                                    | Public `setParcel` lets any caller put a parcel in a locker without checking size or occupancy, which bypasses `storeParcel`.                                                 | AI                               | Weak encapsulation                       | Yes.                                                                                                                                                                  |

2.1 Name two problems that only the AI review found.

One problem found was the lack of input validation (No. 5). This is a key finding as a lack of input validation is a huge security risk. Another is No. 12 regarding weak cohesion; this is also a good finding regarding code quality that SonarQube did not detect.

2.2 Name one problem that only SonarQube found, or write none.

One that SonarQube found was a service class directly printing (No.18).

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.

Every AI finding was right, either by clearly saying yes or no or saying 'debatable'. I checked by understanding the code first then reading the AI findings. Finally I asked AI to do the last two columns of Table 2 and evaluated my own findings to the output.

## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it                                                                                      |
| -------------- | ------------- | ------------------------------------------------------------------------------------------------------------------------- |
| 1-4            | Kept          | Kept because this app is a console app and Main is the key way to view and interact with the app. It must be shown there. |
| 5              | Fixed         | 4d020a0                                                                                                                   |
| 6              | Fixed         | 4d020a0                                                                                                                   |
| 7              | Fixed         | fccfc7a                                                                                                                   |
| 8              | Kept          | a6c056f                                                                                                                   |
| 9              | Fixed         | 41b5a6b                                                                                                                   |
| 10-11          | Fixed         | 16c8fc4                                                                                                                   |
| 12             | Fixed         |                                                                                                                           |
| 13             | Fixed         |                                                                                                                           |

Number of SonarQube issues that remain after your fixes:

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| ---------------------------- | ---------------- | ------ |
|                              |                  |        |
|                              |                  |        |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item                                                     | bikeshare-poor | bikeshare-good |
| -------------------------------------------------------- | -------------- | -------------- |
| Date                                                     |                |                |
| AI tool and exact model                                  |                |                |
| Prompt (B or C as given, or your changed prompt in full) |                |                |
| Builds with mvn package (yes or no)                      |                |                |
| Number of classes, interfaces, enums and records         |                |                |
| SonarQube issues, total                                  |                |                |
| SonarQube issues, by rule                                |                |                |
| AI review findings in Check 1                            |                |                |
| AI review findings in Check 2                            |                |                |
| Review output file in the reviews folder                 |                |                |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose                                                                                      | Files or parts that it produced or changed | How you checked its output                                                                    |
| ----------------------- | -------------------------------------------------------------------------------------------- | ------------------------------------------ | --------------------------------------------------------------------------------------------- |
| Opus 5.5 Medium         | Filling in last two columns of Table 2 (SonarQube Rule/ Course Term and finding correctness) | Table 2                                    | Read and understood code and AI check findings before comparing my own to the Table 2 output. |
