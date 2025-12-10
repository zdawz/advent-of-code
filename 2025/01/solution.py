#!/usr/bin/env python3

def part_one(rotations):
    dial = 50
    zeroes = 0
    for rotation in rotations:
        direction = rotation[0]
        clicks = int(rotation[1:]) % 100
        
        if direction == 'L':
            dial -= clicks
        elif direction == 'R':
            dial += clicks
        
        if dial > 99:
            dial -= 100
        elif dial < 0:
            dial += 100
        
        if dial == 0:
            zeroes += 1
        
    return zeroes

def part_two(rotations):
    dial = 50
    zeroes = 0
    for rotation in rotations:
        direction = rotation[0]
        clicks = int(rotation[1:])
        round_trips = clicks // 100
        leftover = clicks % 100
        old_dial = dial
        
        if direction == 'L':
            dial -= leftover
        elif direction == 'R':
            dial += leftover

        if dial == 0:
            zeroes += 1

        if dial > 99:
            dial -= 100
            zeroes += 1
        elif dial < 0:
            dial += 100
            if old_dial != 0:
                zeroes += 1
        
        zeroes += round_trips

    return zeroes

if __name__ == "__main__":
    rotations = []

    with open("./input.txt") as file:
        while line := file.readline():
            rotations.append(line)

    print("Solution to Part One:", part_one(rotations))
    print("Solution to Part Two:", part_two(rotations))
