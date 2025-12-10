#!/usr/bin/env python3

def part_one(ranges):
    invalid = 0
    for r in ranges:
        start = int(r[0])
        end = int(r[1])
        for n in range(start, end + 1):
            nStr = str(n)
            nLen = len(nStr)
            if nLen % 2 != 0:
                continue
            firstHalf = nStr[:(nLen // 2)]
            secondHalf = nStr[(nLen // 2):]
            if firstHalf == secondHalf:
                invalid += n
    return invalid

def part_two(ranges):
    invalid = 0
    for r in ranges:
        start = int(r[0])
        end = int(r[1])
        for n in range(start, end + 1):
            nStr = str(n)
            nLen = len(nStr)
            for i in range(1, (nLen // 2) + 1):
                if nLen % i != 0:
                    continue
                pattern = nStr[:i]
                matches = True
                for j in range(i, nLen - i + 1, i):
                    if pattern != nStr[j:j + i]:
                        matches = False
                        break
                if matches:
                    invalid += n
                    break
                        
    return invalid

if __name__ == "__main__":
    ranges = []

    with open("./input.txt") as file:
        while line := file.readline():
            rangeStrs = line.strip().split(',')
            for rangeStr in rangeStrs:
                ranges.append(rangeStr.split('-'))

    print("Solution to Part One:", part_one(ranges))
    print("Solution to Part Two:", part_two(ranges))
