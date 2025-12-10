#!/usr/bin/env python3

import re

def part_one():
    mem = ''
    with open("2024/03/input.txt") as file:
        mem = file.read()
    return do_ops(mem)

def do_ops(mem):
    sum = 0
    for op in re.findall("mul\((\d{1,3}),(\d{1,3})\)", mem):
        sum += int(op[0]) * int(op[1])
    return sum

if __name__ == "__main__":
    print("Solution to Part One:", part_one())
