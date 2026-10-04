# DAA Assignment 2 - Data Structures

Student: BALAGAZY ABYLAIKHAN

This project contains my implementations of DynamicArray, MyLinkedList and MinHeap using primitive int values.

## Build

```bash
mvn clean test
```

## Run benchmark

```bash
mvn -q exec:java
```

The benchmark writes `results/results.csv`.

## Project layout

- `src/main/java/structures` - data structures
- `src/main/java/metrics` - operation counters
- `src/main/java/benchmark` - workloads and CSV export
- `src/test/java` - JUnit 5 tests
- `results/results.csv` - benchmark results
- `results/plots` - generated charts
- `REPORT.md` - analysis and loop invariant proofs

## Git

Required branches were used: `feature/array`, `feature/list`, `feature/heap`, `feature/metrics`.
The release tag is `v1.0` on `main`.

GitHub repository: ADD_YOUR_GITHUB_REPOSITORY_LINK_HERE
