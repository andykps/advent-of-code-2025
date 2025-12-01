BEGIN {
    dial = 50
}

{
    dir = substr($0, 1, 1)
    rot = substr($0, 2) + 0
#    print dir, rot
    pdial = dial # previous dial
    if (dir == "L") {
        dial = ((dial - rot%100) + 100) % 100
        password = password + int(rot/100)
        if (dial == 0 || (pdial != 0 && pdial < dial))
            password = password + 1
#        dial = (dial + 100) % 100
    } else if (dir == "R") {
        password = password + int(rot/100)
        dial = (dial + rot) % 100
        if (dial == 0 || pdial > dial)
            password = password + 1
    }
    print pdial, $0, dial, password
}

END {
    print password
}
