DAY_WITH_PADDING=$(shell printf "%02d" ${DAY})
YEAR?=2022

new_day:
    # ensure directories exist
	@mkdir -p src/main/kotlin/aoc${YEAR}
	@mkdir -p src/main/resources/aoc${YEAR}
	@mkdir -p src/test/kotlin/aoc${YEAR}

    # create source file from template
	@cp templates/DayXX.kt src/main/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}.kt
	@sed -i 's/XX/${DAY_WITH_PADDING}/g' src/main/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}.kt
	@sed -i 's/YYYY/${YEAR}/g' src/main/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}.kt

    # create test file from template
	@cp templates/DayXXTest.kt src/test/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}Test.kt
	@sed -i 's/XX/${DAY_WITH_PADDING}/g' src/test/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}Test.kt
	@sed -i 's/YYYY/${YEAR}/g' src/test/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}Test.kt

    # create test input file from template
	@touch src/main/resources/aoc${YEAR}/Day${DAY_WITH_PADDING}_test.txt

    # create input file from template
	@touch src/main/resources/aoc${YEAR}/Day${DAY_WITH_PADDING}.txt
	@sh -c "if [ -f './session.txt' ]; then \
		curl -s -b $(shell head -n 1 ./session.txt 2> /dev/null) https://adventofcode.com/${YEAR}/day/${DAY}/input -o src/main/resources/aoc${YEAR}/Day${DAY_WITH_PADDING}.txt; \
	fi"

	@echo "Template created for puzzle https://adventofcode.com/${YEAR}/day/${DAY}"