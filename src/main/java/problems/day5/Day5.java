package problems.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;
import utils.Vector2;

public class Day5 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
        getRulesAndPages result = getGetRulesAndPages(inputArray);
        return solvePart1(result.rules(), result.pages());
    }

    private static getRulesAndPages getGetRulesAndPages(List<String> inputArray) {
        var i = 0;
        List<Vector2> rules = new ArrayList<>();
        while (i < inputArray.size()) {
            var line = inputArray.get(i);
            var parts = line.split("\\|");
            i++;
            if (line.isEmpty()) {
                break;
            }
            rules.add(new Vector2(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
        var pages = new ArrayList<List<Integer>>();
        while (i < inputArray.size()) {
            var input = inputArray.get(i);
            pages.add(Arrays.stream(input.split(",")).map(Integer::parseInt).toList());
            i++;
        }
        getRulesAndPages result = new getRulesAndPages(rules, pages);
        return result;
    }

    private record getRulesAndPages(List<Vector2> rules, ArrayList<List<Integer>> pages) {
    }

    private Long solvePart1(List<Vector2> rules, ArrayList<List<Integer>> pages) {
        var result = 0L;
        for (var page : pages) {
            var isValid = getIsValid(rules, page);
            if (isValid) {
                result += getMiddleValue(page);

            }
        }
        return result;
    }

    private int getMiddleValue(List<Integer> page) {
        return page.get(page.size() / 2);
    }

    private boolean getIsValid(List<Vector2> rules, List<Integer> page) {
        var pageToRulesThatApply = new HashMap<Integer, List<Vector2>>();
        page.forEach(p -> {
            rules.forEach(r -> {
                if (p == r.x() || p <= r.y()) {
                    if (!pageToRulesThatApply.containsKey(p)) {
                        pageToRulesThatApply.put(p, new ArrayList<>());
                    }
                    pageToRulesThatApply.get(p).add(r);
                }
            });
        });

        for (var entry : pageToRulesThatApply.entrySet()) {
            var listOfRules = entry.getValue();
            for (var rule : listOfRules) {
                var first = page.indexOf(rule.x());
                var second = page.indexOf(rule.y());
                if (first == -1 || second == -1) {
                    continue;
                }
                if (first > second) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        getRulesAndPages result = getGetRulesAndPages(inputArray);
        return solvePart2(result.rules(), result.pages());
    }

    private Long solvePart2(List<Vector2> rules, ArrayList<List<Integer>> pages) {
        var result = 0L;
        var calculated = 0;
        for (var page : pages) {
            var isValid = getIsValid(rules, page);
            if (isValid) continue;
            var mutablePage = new LinkedList<>(page);
            while (!getIsValid(rules, mutablePage)) {
                reorderPage(rules, mutablePage);
            }
            result += getMiddleValue(mutablePage);
            calculated++;
            System.out.println(calculated);
        }
        return result;
    }

    private void reorderPage(List<Vector2> rules, LinkedList<Integer> page) {
        var pageToRulesThatApply = new HashMap<Integer, List<Vector2>>();
        page.forEach(p -> {
            rules.forEach(r -> {
                if (p == r.x() || p <= r.y()) {
                    if (!pageToRulesThatApply.containsKey(p)) {
                        pageToRulesThatApply.put(p, new ArrayList<>());
                    }
                    pageToRulesThatApply.get(p).add(r);
                }
            });
        });

        for (var entry : pageToRulesThatApply.entrySet()) {
            var listOfRules = entry.getValue();
            for (var rule : listOfRules) {
                var first = page.indexOf(rule.x());
                var second = page.indexOf(rule.y());
                if (first == -1 || second == -1) {
                    continue;
                }
                if (first > second) {
                    // Move the first to the second
                    var temp = page.get(first);
                    page.remove(first);
                    page.add(second, temp);
                }
            }
        }
    }

    private final String sampleInput = """
            47|53
            97|13
            97|61
            97|47
            75|29
            61|13
            75|53
            29|13
            97|29
            53|29
            61|53
            97|53
            61|29
            47|13
            75|47
            97|75
            47|61
            75|61
            47|29
            75|13
            53|13
            
            75,47,61,53,29
            97,61,53,29,13
            75,29,13
            75,97,47,61,53
            61,13,29
            97,13,75,29,47
            """;

    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of(sampleInput, 143L));
    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.of(Arguments.of(sampleInput, 123L));
    }
}
