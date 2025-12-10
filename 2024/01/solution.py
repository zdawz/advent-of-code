#!/usr/bin/env python3

def part_one():
    list1, list2 = [], []
    with open("2024/01/input.txt") as file:
        while line := file.readline():
            ids = [int(num) for num in line.strip().split('   ')]
            list1.append(ids[0])
            list2.append(ids[1])
    list1.sort()
    list2.sort()
    total = 0
    for i in range(0, len(list1)):
        total += abs(list1[i] - list2[i])
    return total

def part_two():
    list1, dict2 = [], {}
    with open("2024/01/input.txt") as file:
        while line := file.readline():
            ids = [int(num) for num in line.strip().split('   ')]
            list1.append(ids[0])
            dict2[ids[1]] = dict2.get(ids[1], 0) + 1
    score = 0
    for num in list1:
        score += num * dict2.get(num, 0)
    return score

if __name__ == "__main__":
    print("Solution to Part One:", part_one())
    print("Solution to Part Two:", part_two())
