import sys

file = sys.argv[1]
total = 0

with open(file, 'r') as file:
    for bank in file:
        bank = bank.strip()
        max = 0
        pos1 = 0
        for pos in range(len(bank)-1):
            battery = int(bank[pos])
            if battery > max:
                max = battery
                pos1 = pos
            if max == 9:
                break
#        print(pos1)
        max = 0
        for pos in range(pos1+1, len(bank)):
            battery = int(bank[pos])
            if battery > max:
                max = battery
                pos2 = pos
            if max == 9:
                break
#        print(pos1, pos2)
        total += int(bank[pos1])*10 + int(bank[pos2])
#        print(total)

print(total)
