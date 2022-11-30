
new_day:
	@cp templates/DayXX.kt src/main/kotlin/aoc2022/Day${DAY}.kt
	@cp templates/DayXXTest.kt src/test/kotlin/aoc2022/Day${DAY}Test.kt
	@touch src/main/resources/Day${DAY}.txt
	@touch src/main/resources/Day${DAY}_test.txt