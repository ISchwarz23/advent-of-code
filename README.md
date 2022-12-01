![](https://img.shields.io/static/v1?label=%F0%9F%93%85%20Completed%20Days&message=1&color=blue&style=flat-square)
![](https://img.shields.io/static/v1?label=%E2%AD%90%20Gained%20Stars&message=2&color=yellow&style=flat-square)

# Advent of Code (2022)

In this repository, I'm about to provide solutions for the Advent of Code[^aoc] puzzles using [Kotlin][kotlin] language.

## Motivation

I decided to do the Advent of Code in Kotlin, as it is my favorite programming language which I use way to infrequently.
I'm only
able to use them in my side projects and unfortunately not in my professional career at my current employer.  
I use this event to get a broader knowledge about the Kotlin standard library.

## Repo Structure

For each day/puzzle there is a separate file inside the source folder with the pattern `DayXX` (e.g. `Day01`). This file
contains
all the code that was used to solve the puzzle.  
The correctness is checked using a JUnit 5 tests. There is one test file for each day using the name
pattern `DayXXTest` (e.g.
`Day01Test`). These tests use the sample input from the puzzle explanation to check if the code is correct. Thereafter,
the
code is called with the real puzzle input.  
This input data is stored in text files in the project resources. The file name pattern of the input files
is `DayXX.txt`
(e.g. `Day01.txt`). The mentioned sample data is stored in files with the name pattern `DayXX_test.txt` (
e.g. `Day01_test.txt`).  
To generate a new set of files for a new das run `make new_day DAY="<day>"` (e.g. `make new_day DAY="01"`).

## Log of my AoC Journey

I tried to log my results and thoughts for each puzzle after solving it.

| #   | Name      | Stars | Comment |
| --- | --------- | ----- | ------- |
| 1   | [Calorie Counting][1]  | ⭐⭐    | _       |
| 2   | [???][2]  |       | _       |
| 3   | [???][3]  |       | _       |
| 4   | [???][4]  |       | _       |
| 5   | [???][5]  |       | _       |
| 6   | [???][6]  |       | _       |
| 7   | [???][7]  |       | _       |
| 8   | [???][8]  |       | _       |
| 9   | [???][9]  |       | _       |
| 10  | [???][10] |       | _       |
| 11  | [???][11] |       | _       |
| 12  | [???][12] |       | _       |
| 13  | [???][13] |       | _       |
| 14  | [???][14] |       | _       |
| 15  | [???][15] |       | _       |
| 16  | [???][16] |       | _       |
| 17  | [???][17] |       | _       |
| 18  | [???][18] |       | _       |
| 19  | [???][19] |       | _       |
| 20  | [???][20] |       | _       |
| 21  | [???][21] |       | _       |
| 22  | [???][22] |       | _       |
| 23  | [???][23] |       | _       |
| 24  | [???][24] |       | _       |
| 25  | [???][24] |       | _       |

[aoc]: https://adventofcode.com

[kotlin]: https://kotlinlang.org

[1]: https://adventofcode.com/2022/day/1

[2]: https://adventofcode.com/2022/day/2

[3]: https://adventofcode.com/2022/day/3

[4]: https://adventofcode.com/2022/day/4

[5]: https://adventofcode.com/2022/day/5

[6]: https://adventofcode.com/2022/day/6

[7]: https://adventofcode.com/2022/day/7

[8]: https://adventofcode.com/2022/day/8

[9]: https://adventofcode.com/2022/day/9

[10]: https://adventofcode.com/2022/day/10

[11]: https://adventofcode.com/2022/day/11

[12]: https://adventofcode.com/2022/day/12

[13]: https://adventofcode.com/2022/day/13

[14]: https://adventofcode.com/2022/day/14

[15]: https://adventofcode.com/2022/day/15

[16]: https://adventofcode.com/2022/day/16

[17]: https://adventofcode.com/2022/day/17

[18]: https://adventofcode.com/2022/day/18

[19]: https://adventofcode.com/2022/day/19

[20]: https://adventofcode.com/2022/day/20

[21]: https://adventofcode.com/2022/day/21

[22]: https://adventofcode.com/2022/day/22

[23]: https://adventofcode.com/2022/day/23

[24]: https://adventofcode.com/2022/day/24

[25]: https://adventofcode.com/2022/day/25
