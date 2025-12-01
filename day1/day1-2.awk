BEGIN {
    dial = 50
}

{
    dir = substr($0, 1, 1)
    rot = substr($0, 2) + 0
#    print dir, rot
    pdial = dial # previous dial
    if (dir == "L") {
        # rotate dial back by partial rotation and normalize to 0-99
        dial = ((dial - rot%100) + 100) % 100
        # count number of full rotations (passes of 0)
        password = password + int(rot/100)
        # add an extra count if we ended up on zero or passed it
        # i.e. previous is less than current but not if we started on 0 where we don't want to count twice
        if (dial == 0 || (pdial != 0 && pdial < dial))
            password = password + 1
    } else if (dir == "R") {
        # rotate dial forward and normalize to 0-99
        dial = (dial + rot) % 100
        # count number of full rotations (passes of 0)
        password = password + int(rot/100)
        # add an extra count if we ended up on zero or passed it
        # don't need to check if previous dial was zero as it can't be when it's greater than dial
        if (dial == 0 || pdial > dial)
            password = password + 1
    }
#    print pdial, $0, dial, password
}

END {
    print password
}
