package problems.day9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import problems.ProblemBase;

public class Day9 extends ProblemBase {
    @Override
    public Long solvePart1(List<String> inputArray) {
//        var uncompressed = getUncompressed(inputArray);
//        var compacted = compact(uncompressed);
//
//        return getTotal(compacted);
        return 0L;
    }

    @Override
    public Long solvePart2(List<String> inputArray) {
        var uncompressed = getUncompressed(inputArray);
        var blocks = getBlocks(uncompressed);
        var compactedBlocks = compactPart2(blocks);
        return getTotalFromBlocks(compactedBlocks);
    }

    private Long getTotalFromBlocks(List<Block> compactedBlocks) {
        var total = 0L;
        var counter = 0;
        for (var block : compactedBlocks) {
            for (int i = 0; i < block.size(); i++) {
                if (block.value() == null) {
                    counter++;
                    continue;
                }
                total += counter * Long.parseLong(String.valueOf(block.value()));
                counter++;
            }
        }
        return total;
    }

    private List<Block> compactPart2(BlockList blocks) {
        var listOfBlocksClone =
                new ArrayList<>(blocks).reversed().stream().filter(block -> block.value() != null).toList();
        for (var block : listOfBlocksClone) {
            if (block.value() == null) continue;
            updateBlocksList(blocks, block);
        }

        return blocks;
    }

    private void updateBlocksList(BlockList blocks, Block blockToMove) {
        var originalIndex = blocks.indexOf(blockToMove);
        for (int startPointer = 0; startPointer < originalIndex; startPointer++) {
            var freeSpaceBlock = blocks.get(startPointer);
            if (freeSpaceBlock.value() != null) continue;
            if (freeSpaceBlock.size() >= blockToMove.size()) {
                blocks.remove(blockToMove);
                blocks.add(startPointer, blockToMove);
                freeSpaceBlock.setSize(freeSpaceBlock.size() - blockToMove.size());
                blocks.smush(new Block(null, blockToMove.size()), originalIndex);
                break;
            }
        }
    }

    private BlockList getBlocks(List<String> uncompressed) {
        var blocks = new BlockList();
        var startOfBlock = 0;
        var previousValue = uncompressed.get(0);
        for (int i = 1; i < uncompressed.size(); i++) {
            var current = uncompressed.get(i);
            if (current.equals(previousValue)) {
                continue;
            } else {
                Integer valueToAdd = null;
                try {
                    valueToAdd = Integer.parseInt(previousValue);
                } catch (NumberFormatException e) {
                }
                blocks.add(new Block(valueToAdd, i - startOfBlock));
                startOfBlock = i;
                previousValue = current;
            }
        }
        blocks.add(new Block(Integer.parseInt(previousValue), uncompressed.size() - startOfBlock));
        return blocks;
    }

    static final class BlockList extends ArrayList<Block> {
        public void smush(Block block, int index) {
            var leftBlock = this.get(index);
            if (this.size() <= index + 1) {
                this.add(index + 1, block);
                return;
            }
            var rightBlock = this.get(index + 1);
            if (leftBlock.value() == null) {
                leftBlock.setSize(leftBlock.size() + block.size());
                if (rightBlock.value() == null) {
                    leftBlock.setSize(leftBlock.size() + rightBlock.size());
                    this.remove(rightBlock);
                }
                return;
            }
            if (rightBlock.value() == null) {
                rightBlock.setSize(rightBlock.size() + block.size());
                return;
            } else {
                this.add(index + 1, block);
            }
        }
    }

    static final class Block {
        private Integer value;
        private int size;

        Block(Integer value, int size) {
            this.value = value;
            this.size = size;
        }

        public Block(Block block) {
            this.value = block.value();
            this.size = block.size();
        }

        public Integer value() {
            return value;
        }

        public int size() {
            return size;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "Block[" + "value=" + value + ", " + "size=" + size + ']';
        }

    }

    private Long getTotal(List<String> compacted) {
        var total = 0L;
        for (int i = 0; i < compacted.size(); i++) {
            var current = compacted.get(i);
            if (current.equals(".")) {
                continue;
            }
            total += i * Long.parseLong(current);
        }
        return total;
    }

    private List<String> compact(List<String> uncompressed) {
        var startPointer = 0;
        var endPointer = uncompressed.size() - 1;
        var currentStart = uncompressed.get(startPointer);
        var currentEnd = uncompressed.get(endPointer);
        var result = new LinkedList<>(uncompressed);

        while (startPointer < endPointer) {
            currentStart = uncompressed.get(startPointer);
            currentEnd = uncompressed.get(endPointer);
            if (currentStart.equals(".")) {
                while (currentEnd.equals(".")) {
                    currentEnd = uncompressed.get(--endPointer);
                }
                if (endPointer < startPointer) {
                    break;
                }
                result.set(startPointer, currentEnd);
                result.set(endPointer, currentStart);
                endPointer--;
            }
            startPointer++;
        }
        return result;
    }

    private List<String> getUncompressed(List<String> inputArray) {
        var compressed = Arrays.stream(inputArray.get(0).split("")).map(Short::valueOf).toList();
        var uncompressed = new ArrayList<String>();
        var fileId = 0;
        for (int index = 0; index < compressed.size(); index++) {
            var current = compressed.get(index);
            if (index % 2 == 0) {
                uncompressed.addAll(Collections.nCopies(current, String.valueOf(fileId)));
                fileId++;
            } else {
                uncompressed.addAll(Collections.nCopies(current, "."));
            }
        }
        return uncompressed;
    }


    @Override
    public Stream<Arguments> getPart1Examples() {
        return Stream.of(Arguments.of("2333133121414131402", 1928L), Arguments.of("12345", 60L), Arguments.of("123456"
                , 60L), Arguments.of("19", 0L), Arguments.of("123", 6L), Arguments.of("2222", 5L), Arguments.of(
                "22222", 19L)

        );

    }

    @Override
    public Stream<Arguments> getPart2Examples() {
        return Stream.of(Arguments.of("2333133121414131402", 2858L));
    }
}
