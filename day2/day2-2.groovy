def filename = args[0]
if (filename == null) {
    println "Please provide a filename as an argument."
    System.exit(1)
}

def total = 0

def lines = new File(filename).readLines()
lines.each { line ->
    def ranges = line.split(",")
    ranges.each { range ->
        def bounds = range.split("-").collect { it as long }
        (bounds[0]..bounds[1]).each { id ->
            if (hasRepeats(id)) {
//                println id
                total += id
            }
        }
    }
}

println total

def hasRepeats(long id) {
    String idStr = id.toString()
    if (idStr.length() > 1) {
        // loop possible group lengths from 1 up to half string length
        for (int i = 1; i <= idStr.length() / 2; i++) {
            // use collate to group string chars into groups of that length and count duplicates
            // if there are at least 2 of a group and the number of duplicates matches the string
            // length divided by the group length then there is a repeat (this second check is to
            // avoid matches of smaller groups e.g. 110 shouldn't be a match although there are 2
            // 1's in a group)
            if (idStr.toList().collate(i).countBy{it}.values().findAll{it>=2}.any{it==idStr.length()/i}) {
                return true
            }
        }
    }
    false
}
