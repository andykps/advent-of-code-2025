BEGIN {
    dial = 50
}

{
    dir = substr($0, 1, 1)
    rot = substr($0, 2) + 0
#    print dir, rot
    if (dir == "L") {
        dial = (dial - rot + 100) % 100
    } else if (dir == "R") {
        dial = (dial + rot) % 100
    }
    if (dial == 0) {
        password = password + 1
    }
#    print dial
}

END {
    print password
}
