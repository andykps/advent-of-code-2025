my ($filename) = @ARGV;
my $ROLL = '@';
my $REMOVED = 'x';
my $EMPTY = '.';

open my $handle, '<', $filename or die "Could not open file: $!";
chomp(my @lines = <$handle>);
close $handle;

my $total_removed = 0;
my $removed = 0;

do {
    $removed = remove_accessible();
    $total_removed += $removed;
} while ($removed > 0);

print "Total removed: $total_removed\n";

sub remove_accessible {
    my $removed = 0;
    for my $y (0 .. $#lines) {
        my @line = split //, $lines[$y];
        for my $x (0 .. $#line) {
            if (@line[$x] eq $ROLL) {
                my $neighbours = count_neighbours($x, $y);
                if ($neighbours < 4) {
                    substr($lines[$y], $x, 1, $REMOVED);
                }
            }
        }
    }
    for my $y (0 .. $#lines) {
        for my $x (0 .. length($lines[$y]) - 1) {
            if (substr($lines[$y], $x, 1) eq $REMOVED) {
                substr($lines[$y], $x, 1, $EMPTY);
                $removed++;
            }
        }
    }
    return $removed;
}

sub count_neighbours {
    my ($x, $y) = @_;
    my $neighbours = 0;
    for my $dy (-1 .. 1) {
        for my $dx (-1 .. 1) {
            next if $dx == 0 && $dy == 0;
            my $nx = $x + $dx;
            my $ny = $y + $dy;
            if ($nx >= 0 && $nx <= length($lines[$y]) && $ny >= 0 && $ny <= $#lines) {
                my $cell = substr($lines[$ny], $nx, 1); 
                if ($cell eq $ROLL || $cell eq $REMOVED) {
                    $neighbours++;
                }
            }
        }
    }
    return $neighbours;
}
