#!/usr/bin/env python3

def part_one():
    reports = []
    with open("2024/02/input.txt") as file:
        while line := file.readline():
            reports.append([int(num) for num in line.strip().split(' ')])
    safe = 0
    for r in reports:
        if safe_report(r):
            safe += 1
    return safe

def safe_report(r):
    increasing = r[0] < r[1]
    for i in range(0, len(r) - 1):
        num1 = r[i]
        num2 = r[i + 1]
        if not safe_nums(num1, num2, increasing):
            return False
    return True
    

def safe_nums(num1, num2, increasing):
    if num1 == num2:
        return False
    elif increasing and num1 > num2:
        return False
    elif not increasing and num1 < num2:
        return False
    elif abs(num1 - num2) > 3:
        return False
    else:
        return True


def part_two():
    reports = []
    with open("2024/02/input.txt") as file:
        while line := file.readline():
            reports.append([int(num) for num in line.strip().split(' ')])
    safe = 0
    for r in reports:
        for i in range(0, len(r)):
            new_r = [r[j] for j in range(0, len(r)) if j != i]
            if safe_report(new_r):
                safe += 1
                break
    return safe

if __name__ == "__main__":
    print("Solution to Part One:", part_one())
    print("Solution to Part Two:", part_two())
