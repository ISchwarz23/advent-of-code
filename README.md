# Advent of Code

```
                            |                         _...._
                         \  _  /                    .::o:::::.
                          (\o/)                    .:::'''':o:.
                      ---  / \  ---                :o:_    _:::
                           >*<                     `:}_>()<_{:'
                          >0<@<                 @    `'//\\'`    @
                         >>>@<<*              @ #     //  \\     # @
                        >@>*<0<<<           __#_#____/'____'\____#_#__
                       >*>>@<<<@<<         [__________________________]
                      >@>>0<<<*<<@<         |=_- .-/\ /\ /\ /\--. =_-|
                     >*>>0<<@<<<@<<<        |-_= | \ \\ \\ \\ \ |-_=-|
                    >@>>*<<@<>*<<0<*<       |_=-=| / // // // / |_=-_|
      \*/          >0>>*<<@<>0><<*<@<<      |=_- |`-'`-'`-'`-'  |=_=-|
  ___\\U//___     >*>>@><0<<*>>@><*<0<<     | =_-| o          o |_==_|
  |\\ | | \\|    >@>>0<*<<0>>@<<0<<<*<@<    |=_- | !     (    ! |=-_=|
  | \\| | _(UU)_ >((*))_>0><*<0><@<<<0<*<  _|-,-=| !    ).    ! |-_-=|_
  |\ \| || / //||.*.*.*.|>>@<<*<<@>><0<<@>>>>>|  ( ~~~ )/   (((((((())))))))
      ~~~~~~~~         '""""`------'  `w---w`     `------------'
```


In this repository, I'm about to provide solutions for the Advent of Code[^aoc] puzzles using [Kotlin][kotlin] language.

## Motivation

I decided to do the Advent of Code in Kotlin, as it is my favorite programming language which I use way too infrequently.
I'm only able to use them in my side projects and unfortunately not in my professional career at my current employer.  
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
This input data is stored in text files in the "input" folder on the project root. The file name pattern of the input files
is `dayXX.txt`
(e.g. `day01.txt`). The mentioned sample data is stored in files with the name pattern `dayXX_example.txt` (
e.g. `day01_example.txt`).

## Usage

The described files described in the section "Repo Structure" can be generated for a specific day using the
command `make new_day`. This will generate the files required for the current day of the month and the current year.
This behavior can be overridden using `make new_day DAY=<day> YEAR=<year>` (e.g. `make new_day DAY=1 YEAR=2019`).

## Setup

To download the actual puzzle input when executing the `make new_day ...` command (only possible when the puzzle for
the requested day was already released) and be able to automatically upload your puzzle answers, you need to store your
session cookie to the file `session.txt` in the project root.
The file content should look like `session=abc...xyz`.

## Log of my AoC Journey 2024 ![](https://img.shields.io/static/v1?label=%E2%AD%90%20Gained%20Stars&message=36&color=yellow&style=flat-square)

I tried to log my results and thoughts for each puzzle after solving it.

| #  | Name                              | Stars | Comment                                                                                             |
|----|-----------------------------------|-------|-----------------------------------------------------------------------------------------------------|
| 1  | [Historian Hysteria][2024-1]      | ⭐⭐    | Straight forward.                                                                                   |
| 2  | [Red-Nosed Reports][2024-2]       | ⭐⭐    | Not happy with the code for part 2.                                                                 |
| 3  | [Mull It Over][2024-3]            | ⭐⭐    | Straight forward.                                                                                   |
| 4  | [Ceres Search][2024-4]            | ⭐⭐    | Straight forward.                                                                                   |
| 5  | [Print Queue][2024-5]             | ⭐⭐    | Straight forward.                                                                                   |
| 6  | [Guard Gallivant][2024-6]         | ⭐⭐    | Not happy with my part 2 solution.                                                                  |
| 7  | [Bridge Repair][2024-7]           | ⭐⭐    | Straight forward.                                                                                   |
| 8  | [Resonant Collinearity][2024-8]   | ⭐⭐    | Straight forward.                                                                                   |
| 9  | [Disk Fragmenter][2024-9]         | ⭐⭐    | Didn't think about more than 1 digit numbers. This is why I had to reimplement my solution.         |
| 10 | [Hoof It][2024-10]                | ⭐⭐    | Straight forward. Part 2 was even more easy than part 1.                                            |
| 11 | [Plutonian Pebbles][2024-11]      | ⭐⭐    | Pretty easy when using a map (instead of a list).                                                   |
| 12 | [Garden Groups][2024-12]          | ⭐⭐    | It took me a while to calculate "sides" of the gardens.                                             |
| 13 | [Claw Contraption][2024-13]       | ⭐⭐    | It took me very long to realize the problem is a linear system of equations.                        |
| 14 | [Restroom Redoubt][2024-14]       | ⭐⭐    | Really liked the style of part 2.                                                                   |
| 15 | [Warehouse Woes][2024-15]         | ⭐⭐    | Had to reimplement everything for part 2.                                                           |
| 16 | [Reindeer Maze][2024-16]          | ⭐⭐    | Improved to much in the beginning. Part 2 forced me to remove most of the performance improvements. |
| 17 | [Chronospatial Computer][2024-17] | ⭐⭐    | Part 1 was very simple, but it took me a while to find a solution for part 2.                       |
| 17 | [RAM Run][2024-18]                | ⭐⭐    | Worked unexpectedly smooth.                                                                         |

## Log of my AoC Journey 2023 ![](https://img.shields.io/static/v1?label=%E2%AD%90%20Gained%20Stars&message=15&color=yellow&style=flat-square)

I tried to log my results and thoughts for each puzzle after solving it.

| #   | Name                                      | Stars | Comment |
|-----|-------------------------------------------|-------|---------|
| 1   | [Trebuchet?!][2023-1]                     | ⭐⭐    |         |
| 2   | [Cube Conundrum][2023-2]                  | ⭐⭐    |         |
| 3   | [Gear Ratios][2023-3]                     | ⭐⭐    |         |
| 4   | [Scratchcards][2023-4]                    | ⭐⭐    |         |
| 5   | [If You Give A Seed A Fertilizer][2023-5] | ⭐⭐    |         |
| 6   | [Wait For It][2023-6]                     | ⭐⭐    |         |
| 7   | [Camel Cards][2023-7]                     | ⭐⭐    |         |
| 8   | [Haunted Wasteland][2023-8]               | ⭐     |         |
Had to quit because of sickness. Maybe I will continue in a later point in time.

## Log of my AoC Journey 2022 ![](https://img.shields.io/static/v1?label=%E2%AD%90%20Gained%20Stars&message=50&color=yellow&style=flat-square)

I tried to log my results and thoughts for each puzzle after solving it.

| #   | Name                                | Stars | Comment                                                                                                                                                                                                                                    |
|-----|-------------------------------------|-------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | [Calorie Counting][2022-1]          | ⭐⭐    | Was not able to find a way to calculate the calorie sum as stream, so I did it in a loop.                                                                                                                                                  |
| 2   | [Rock Paper Scissors][2022-2]       | ⭐⭐    | Guess there is a more performant way, but I focused more on readability.                                                                                                                                                                   |
| 3   | [Rucksack Reorganization][2022-3]   | ⭐⭐    | In my opinion this was the most easy puzzle so far.                                                                                                                                                                                        |
| 4   | [Camp Cleanup][2022-4]              | ⭐⭐    | Also a pretty easy one, when sorting the pair of section assignments.                                                                                                                                                                      |
| 5   | [Supply Stacks][2022-5]             | ⭐⭐    | Today, parsing the input was more difficult than the actual puzzle.                                                                                                                                                                        |
| 6   | [Tuning Trouble][2022-6]            | ⭐⭐    | Pretty short puzzle today. Took me less than 10 minutes for both parts.                                                                                                                                                                    |
| 7   | [No Space Left On Device][2022-7]   | ⭐⭐    | Today I was not able to work on the puzzle when it was released. Had to postpone it, until I finished working.                                                                                                                             |
| 8   | [Treetop Tree House][2022-8]        | ⭐⭐    | Found a pretty generic solution to 'iterate' over the trees. Don't know it is easy to understand.                                                                                                                                          |
| 9   | [Rope Bridge][2022-9]               | ⭐⭐    | Unfortunately I had to spend a long time debugging, as I had a bug when moving diagonal.                                                                                                                                                   |
| 10  | [Cathode-Ray Tube][2022-10]         | ⭐⭐    | Very sub-optimal circumstances today. First I started more than half an hour late, then it took me ages to understand the second part.                                                                                                     |
| 11  | [Monkey in the Middle][2022-11]     | ⭐⭐    | A lot of parsing today. But puzzle itself was not that hard.                                                                                                                                                                               |
| 12  | [Hill Climbing Algorithm][2022-12]  | ⭐⭐    | Lost a lot of time, as my algorithm never find a solution. I expected my algorithm to be buggy, instead I had the wrong assumption, that is is only allowed to move one height level down. But there was no limit described in the puzzle. |
| 13  | [Distress Signal][2022-13]          | ⭐⭐    |                                                                                                                                                                                                                                            |
| 14  | [Regolith Reservoir][2022-14]       | ⭐⭐    |                                                                                                                                                                                                                                            |
| 15  | [Beacon Exclusion Zone][2022-15]    | ⭐⭐    | Pretty easy compared to the ones before.                                                                                                                                                                                                   |
| 16  | [Proboscidea Volcanium][2022-16]    | ⭐⭐    | Solutions takes 5 minutes to finish for part 2. Good enough for now...                                                                                                                                                                     |
| 17  | [Pyroclastic Flow][2022-17]         | ⭐⭐    | This one was really fun! :)                                                                                                                                                                                                                |
| 18  | [Boiling Boulders][2022-18]         | ⭐⭐    |                                                                                                                                                                                                                                            |
| 19  | [Not Enough Minerals][2022-19]      | ⭐⭐    |                                                                                                                                                                                                                                            |
| 20  | [Grove Positioning System][2022-20] | ⭐⭐    |                                                                                                                                                                                                                                            |
| 21  | [Monkey Math][2022-21]              | ⭐⭐    | Really liked this one! 'Cause Maths ;)                                                                                                                                                                                                     |
| 22  | [Monkey Map][2022-22]               | ⭐⭐    | Cool puzzle, but I was not able to implement a general solution for part 2. So for now I use hardcoded rules only working for the given input.                                                                                             |
| 23  | [Unstable Diffusion][2022-23]       | ⭐⭐    | Pretty easy one                                                                                                                                                                                                                            |
| 24  | [Blizzard Basin][2022-24]           | ⭐⭐    | Solved using A*                                                                                                                                                                                                                            |
| 25  | [Full of Hot Air][2022-25]          | ⭐⭐    |                                                                                                                                                                                                                                            |


## Log of my AoC Journey 2021 ![](https://img.shields.io/static/v1?label=%E2%AD%90%20Gained%20Stars&message=50&color=yellow&style=flat-square)

I tried to log my results and thoughts for each puzzle after solving it.

| #   | Name                               | Stars | Comment                                                                                                                                                                                                                                                                                             |  
|-----|------------------------------------|-------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | [Sonar Sweep][2021-1]              | ⭐⭐    | Did a very basic/unimproved implementation that did the job. No performance improvements needed to calculate solutions.<br/>_Edit: Did improvement using [windowed()][windowed] function._                                                                                                          |
| 2   | [Dive!][2021-2]                    | ⭐⭐    | Did not see the challenge in this puzzle. Straight forward implementation did the job.                                                                                                                                                                                                              |
| 3   | [Binary Diagnostic][2021-3]        | ⭐⭐    | Can't really estimate the quality of the solution, but it looks a bit bloated. Guess there is a more elegant way to solve this puzzle.                                                                                                                                                              |
| 4   | [Giant Squid][2021-4]              | ⭐⭐    | Implemented an object-oriented solution. Instead of a 2d-array I used a simple list for the board.                                                                                                                                                                                                  |
| 5   | [Hydrothermal Venture][2021-5]     | ⭐⭐    | Implemented an object-oriented solution again. Already guessed the part 2 of the puzzle, so diagonals were supported right from the beginning.                                                                                                                                                      |
| 6   | [Lanternfish][2021-6]              | ⭐⭐    | Part 1 was straight forward (5 min). Performance was so bad that I had to completely redo it for part 2. But now I'm pretty proud on the solution, as it is simple and fast.                                                                                                                        |
| 7   | [The Treachery of Whales][2021-7]  | ⭐⭐    | Was pretty fast in finding solution for part 1 (5 min). Also for part 2 (10 min), but had issues as the best position isn't always exactly the average of the positions.                                                                                                                            |
| 8   | [Seven Segment Search][2021-8]     | ⭐⭐    | Can't make any statement about the puzzles level of difficulty, as I had only 3 hours of sleep last night.                                                                                                                                                                                          |
| 9   | [Smoke Basin][2021-9]              | ⭐⭐    | Added some foolish bugs to the recursion of part 2. This required some time to correct it. Best would have been to start part 2 all over instead of bug fixing it.                                                                                                                                  |
| 10  | [Syntax Scoring][2021-10]          | ⭐⭐    | Really liked this puzzle. As soon as you see, that the issue is related to stacks, it is a straight forward implementation.                                                                                                                                                                         |
| 11  | [Dumbo Octopus][2021-11]           | ⭐⭐    | Tried to implement a highly optimized solution from the beginning, but failed. Started all over again and implemented it just as described in the puzzle. Solution was fast enough.                                                                                                                 |
| 12  | [Passage Pathing][2021-12]         | ⭐⭐    | As I did a route optimization side project (using A* algorithm) this was straight forward. Instead of finding only the optimal route (as in my side project), I just needed to find all routes (which is more easy).                                                                                |
| 13  | [Transparent Origami][2021-13]     | ⭐⭐    | Had some issues cause I was mixing up horizontal and vertical folding all the time. So had to invest some time in bug fixing.                                                                                                                                                                       |
| 14  | [Extended Polymerization][2021-14] | ⭐⭐    | Needed to reimplement everything for part 2, as part 1 was very in-performant. This took me a looot of time.                                                                                                                                                                                        |
| 15  | [Chiton][2021-15]                  | ⭐⭐    | Only needed to do simple adaptations on solution of part 1 to be able to process part 2 in time. But I did a dumb error when calculation neighbours of current field which cost me about 2 hours to find.                                                                                           |
| 16  | [Packet Decoder][2021-16]          | ⭐⭐    | Really liked the topic this puzzle. Also the implementation went pretty smooth.I had only some minor bugs that were found during testing using the given samples.                                                                                                                                   |
| 17  | [Trick Shot][2021-17]              | ⭐⭐    | This puzzle took me a long time, as I tried to implement it iteratively. When doing the maths using pen and paper it was solved pretty fast. Especially part 1 is bad code but great maths.                                                                                                         |
| 18  | [Snailfish][2021-18]               | ⭐⭐    | I'm not quite happy with my solution, as the code is not very easy to understand. Maybe even the way I implemented the recursion can be improved.                                                                                                                                                   |
| 19  | [Beacon Scanner][2021-19]          | ⭐⭐    | I guess my solution is the brute force way. It takes about 20 seconds to solve one of the parts. There has to be a better/more elegant way, but I was not able to find it.                                                                                                                          |
| 20  | [Trench Map][2021-20]              | ⭐⭐    | This puzzle was more easy compared to the previous ones. Only applying the filter to the infinite surrounding pixels was a bit tricky.                                                                                                                                                              |
| 21  | [Dirac Dice][2021-21]              | ⭐⭐    | Part 1 was too easy, so expected part 2 to be very hard and I was right. Nevertheless I guess my solution was very good, as it finds the correct solution in about 500ms.                                                                                                                           |
| 22  | [Reactor Reboot][2021-22]          | ⭐⭐    | I created an improved version for part 2, but it was not fast enough. I would need to invest more time to find a better solution for part 2. _Edit: The improvement for part 2 was pretty simple. I just adapted the splitting logic of the clusters, to produce the least number of sub-clusters._ |
| 23  | [Amphipod][2021-23]                | ⭐⭐    | Solution is working for part 1, but is too slow for part 2. Unfortunately, I have no idea how to improve it. _Edit: The improvement for part 2 was pretty simple. I just had to reduce the size of the known locations by storing the hash of it instead of the complete objects._                  |
| 24  | [Arithmetic Logic Unit][2021-24]   | ⭐⭐    | Was finally able to solve the puzzle after over 1 year with a lot of help from the internet.                                                                                                                                                                                                        |
| 25  | [Sea Cucumber][2021-25]            | ⭐⭐    | Implemented a very nice solution for part 1 which is not only fast, but also adaptable. The order of the movement can be specified. Unfortunately I don't have enough stars to attempt part 2, so I have to come back to it later.                                                                  |


[^aoc]:
    [Advent of Code][aoc] – an annual event in December since 2015.
    Every year since then, with the first day of December, a programming puzzles contest is published every day for twenty-four days.
    A set of Christmas-oriented challenges provide any input you have to use to answer using the language of your choice.

[aoc]: https://adventofcode.com
[kotlin]: https://kotlinlang.org
[windowed]: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/windowed.html

[2021-1]: https://adventofcode.com/2021/day/1
[2021-2]: https://adventofcode.com/2021/day/2
[2021-3]: https://adventofcode.com/2021/day/3
[2021-4]: https://adventofcode.com/2021/day/4
[2021-5]: https://adventofcode.com/2021/day/5
[2021-6]: https://adventofcode.com/2021/day/6
[2021-7]: https://adventofcode.com/2021/day/7
[2021-8]: https://adventofcode.com/2021/day/8
[2021-9]: https://adventofcode.com/2021/day/9
[2021-10]: https://adventofcode.com/2021/day/10
[2021-11]: https://adventofcode.com/2021/day/11
[2021-12]: https://adventofcode.com/2021/day/12
[2021-13]: https://adventofcode.com/2021/day/13
[2021-14]: https://adventofcode.com/2021/day/14
[2021-15]: https://adventofcode.com/2021/day/15
[2021-16]: https://adventofcode.com/2021/day/16
[2021-17]: https://adventofcode.com/2021/day/17
[2021-18]: https://adventofcode.com/2021/day/18
[2021-19]: https://adventofcode.com/2021/day/19
[2021-20]: https://adventofcode.com/2021/day/20
[2021-21]: https://adventofcode.com/2021/day/21
[2021-22]: https://adventofcode.com/2021/day/22
[2021-23]: https://adventofcode.com/2021/day/23
[2021-24]: https://adventofcode.com/2021/day/24
[2021-25]: https://adventofcode.com/2021/day/25

[2022-1]: https://adventofcode.com/2022/day/1
[2022-2]: https://adventofcode.com/2022/day/2
[2022-3]: https://adventofcode.com/2022/day/3
[2022-4]: https://adventofcode.com/2022/day/4
[2022-5]: https://adventofcode.com/2022/day/5
[2022-6]: https://adventofcode.com/2022/day/6
[2022-7]: https://adventofcode.com/2022/day/7
[2022-8]: https://adventofcode.com/2022/day/8
[2022-9]: https://adventofcode.com/2022/day/9
[2022-10]: https://adventofcode.com/2022/day/10
[2022-11]: https://adventofcode.com/2022/day/11
[2022-12]: https://adventofcode.com/2022/day/12
[2022-13]: https://adventofcode.com/2022/day/13
[2022-14]: https://adventofcode.com/2022/day/14
[2022-15]: https://adventofcode.com/2022/day/15
[2022-16]: https://adventofcode.com/2022/day/16
[2022-17]: https://adventofcode.com/2022/day/17
[2022-18]: https://adventofcode.com/2022/day/18
[2022-19]: https://adventofcode.com/2022/day/19
[2022-20]: https://adventofcode.com/2022/day/20
[2022-21]: https://adventofcode.com/2022/day/21
[2022-22]: https://adventofcode.com/2022/day/22
[2022-23]: https://adventofcode.com/2022/day/23
[2022-24]: https://adventofcode.com/2022/day/24
[2022-25]: https://adventofcode.com/2022/day/25

[2023-1]: https://adventofcode.com/2023/day/1
[2023-2]: https://adventofcode.com/2023/day/2
[2023-3]: https://adventofcode.com/2023/day/3
[2023-4]: https://adventofcode.com/2023/day/4
[2023-5]: https://adventofcode.com/2023/day/5
[2023-6]: https://adventofcode.com/2023/day/6
[2023-7]: https://adventofcode.com/2023/day/7
[2023-8]: https://adventofcode.com/2023/day/8

[2024-1]: https://adventofcode.com/2024/day/1
[2024-2]: https://adventofcode.com/2024/day/2
[2024-3]: https://adventofcode.com/2024/day/3
[2024-4]: https://adventofcode.com/2024/day/4
[2024-5]: https://adventofcode.com/2024/day/5
[2024-6]: https://adventofcode.com/2024/day/6
[2024-7]: https://adventofcode.com/2024/day/7
[2024-8]: https://adventofcode.com/2024/day/8
[2024-9]: https://adventofcode.com/2024/day/9
[2024-10]: https://adventofcode.com/2024/day/10
[2024-11]: https://adventofcode.com/2024/day/11
[2024-12]: https://adventofcode.com/2024/day/12
[2024-13]: https://adventofcode.com/2024/day/13
[2024-14]: https://adventofcode.com/2024/day/14
[2024-15]: https://adventofcode.com/2024/day/15
[2024-16]: https://adventofcode.com/2024/day/16
[2024-17]: https://adventofcode.com/2024/day/17
[2024-18]: https://adventofcode.com/2024/day/18
[2024-19]: https://adventofcode.com/2024/day/19
[2024-20]: https://adventofcode.com/2024/day/20
[2024-21]: https://adventofcode.com/2024/day/21
[2024-22]: https://adventofcode.com/2024/day/22
[2024-23]: https://adventofcode.com/2024/day/23
[2024-24]: https://adventofcode.com/2024/day/24
[2024-25]: https://adventofcode.com/2024/day/25
