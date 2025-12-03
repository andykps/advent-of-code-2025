import sys

file = sys.argv[1]
total = 0

with open(file, 'r') as file:
    for bank in file:
        bank = bank.strip()
        lastPos = -1
        for digit in range(12,0,-1):
            max = 0
            maxPos = 0
            for pos in range(lastPos+1, len(bank)-digit+1):
                battery = int(bank[pos])
                if battery > max:
                    max = battery
                    maxPos = pos
                if max == 9:
                    break
#            print(f"Digit: {digit}, Max: {max}, MaxPos: {maxPos}, Bank: {bank}")
            total += int(bank[maxPos])*(10**(digit-1))
            lastPos = maxPos

print(total)
