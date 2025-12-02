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
    idStr.length() % 2 == 0 && idStr[0..idStr.length()/2-1] == idStr[idStr.length()/2..idStr.length()-1]
}
