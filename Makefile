DAY_WITH_PADDING=$(shell printf "%02d" ${DAY})

new_day:
	@cp templates/DayXX.kt src/main/kotlin/aoc2022/Day${DAY_WITH_PADDING}.kt
	@sed -i 's/XX/${DAY_WITH_PADDING}/g' src/main/kotlin/aoc2022/Day${DAY_WITH_PADDING}.kt
	@cp templates/DayXXTest.kt src/test/kotlin/aoc2022/Day${DAY_WITH_PADDING}Test.kt
	@sed -i 's/XX/${DAY_WITH_PADDING}/g' src/test/kotlin/aoc2022/Day${DAY_WITH_PADDING}Test.kt
	@touch src/main/resources/Day${DAY_WITH_PADDING}.txt
	@touch src/main/resources/Day${DAY_WITH_PADDING}_test.txt
	@sh -c "if [ -f ./session.txt ]; then \
		curl -s -b $(shell head -1 ./session.txt) https://adventofcode.com/2022/day/${DAY}/input -o src/main/resources/Day${DAY_WITH_PADDING}.txt; \
	fi"
