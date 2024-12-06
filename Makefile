DAY?=$(shell date +%-d)
DAY_WITH_PADDING=$(shell printf "%02d" ${DAY})
YEAR?=$(shell date +%Y)


new_day:
	@# ensure directories exist
	@mkdir -p src/main/kotlin/aoc${YEAR}/day${DAY_WITH_PADDING}
	@mkdir -p input/aoc${YEAR}
	@mkdir -p src/test/kotlin/aoc${YEAR}

	@# create source file from template
ifeq (,$(wildcard src/main/kotlin/aoc${YEAR}/day${DAY_WITH_PADDING}/Day${DAY_WITH_PADDING}.kt))
	@cp templates/DayXX.kt src/main/kotlin/aoc${YEAR}/day${DAY_WITH_PADDING}/Day${DAY_WITH_PADDING}.kt
	@sed -i 's/xx/${DAY}/g' src/main/kotlin/aoc${YEAR}/day${DAY_WITH_PADDING}/Day${DAY_WITH_PADDING}.kt
	@sed -i 's/XX/${DAY_WITH_PADDING}/g' src/main/kotlin/aoc${YEAR}/day${DAY_WITH_PADDING}/Day${DAY_WITH_PADDING}.kt
	@sed -i 's/YYYY/${YEAR}/g' src/main/kotlin/aoc${YEAR}/day${DAY_WITH_PADDING}/Day${DAY_WITH_PADDING}.kt
endif

	@# create test file from template
ifeq (,$(wildcard src/test/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}Test.kt))
	@cp templates/DayXXTest.kt src/test/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}Test.kt
	@sed -i 's/xx/${DAY}/g' src/test/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}Test.kt
	@sed -i 's/XX/${DAY_WITH_PADDING}/g' src/test/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}Test.kt
	@sed -i 's/YYYY/${YEAR}/g' src/test/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}Test.kt
endif

	@# create example input file
ifeq (,$(wildcard input/aoc${YEAR}/day${DAY_WITH_PADDING}_example.txt))
	@touch input/aoc${YEAR}/day${DAY_WITH_PADDING}_example.txt
	@curl -s "https://adventofcode.com/${YEAR}/day/${DAY}" | xmllint --html --xpath "//pre[1]/code/text()" - > "input/aoc${YEAR}/day${DAY_WITH_PADDING}_example.txt" 2> /dev/null || true
endif

	@# create input file (and download content if session cookie is given)
ifeq (,$(wildcard input/aoc${YEAR}/day${DAY_WITH_PADDING}.txt))
	@touch input/aoc${YEAR}/day${DAY_WITH_PADDING}.txt
ifneq (,$(wildcard session.txt))
	@curl -s -b $(shell head -n 1 ./session.txt 2> /dev/null) https://adventofcode.com/${YEAR}/day/${DAY}/input -o input/aoc${YEAR}/day${DAY_WITH_PADDING}.txt
endif
endif

	@# open files in IntelliJ
	@#idea input/aoc${YEAR}/day${DAY_WITH_PADDING}_example.txt src/test/kotlin/aoc${YEAR}/Day${DAY_WITH_PADDING}Test.kt input/aoc${YEAR}/day${DAY_WITH_PADDING}.txt src/main/kotlin/aoc${YEAR}/day${DAY_WITH_PADDING}/Day${DAY_WITH_PADDING}.kt > /dev/null

	@# finish by printing url to puzzle
	@echo "Files created for puzzle https://adventofcode.com/${YEAR}/day/${DAY}"
